package nl.saxion.calendar.client;

import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import com.google.gson.JsonObject;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.GeocodingResult;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import nl.saxion.calendar.model.EventWrapper;
import nl.saxion.calendar.model.Forecast;
import nl.saxion.calendar.model.Location;
import nl.saxion.calendar.model.Model;
import nl.saxion.calendar.utils.Resources;
import nl.saxion.calendar.utils.Updatable;

/**
 * Created by jonathan on 7-10-15.
 */
@EBean
public class GoogleCalendarClient {
    @Bean
    Model model;
    @RestService
    OpenweatherClient openweatherClient;
    private Exception mLastError = null;

    public GoogleCalendarClient() {
    }


    /**
     * haalt events op
     * als een event een locatie heeft dan wordt hier de long en lat van opgehaald
     *
     * @param callBack
     */
    @Background
    public void retrieveEvents(Updatable<List<Event>> callBack) {


        HttpTransport transport = AndroidHttp.newCompatibleTransport();


        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        com.google.api.services.calendar.Calendar mService = getmService();
        com.google.api.client.util.DateTime now = new com.google.api.client.util.DateTime(System.currentTimeMillis());
        Events events;

        Log.d("CALCLIENT", String.valueOf(mService == null));


        GeoApiContext context = new GeoApiContext().setApiKey(Resources.GEOCODING_KEY);


        try {
            // haal events op
            events = mService.events().list("primary")
                    .setMaxResults(30)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();


        } catch (IOException e1) {
            e1.printStackTrace();
            Log.d("ERROR", "cant get calendar");
            return;
        }

        List<Event> items = events.getItems();
        List<EventWrapper> eventWrapperList = new ArrayList<>();

        for (Event e : items) {


            final EventWrapper eventWrapper = new EventWrapper(e);
            String location = e.getLocation();

            if (location != null) {
                Log.d("GEOCODING", location);
                // haal locatie op bij event


                // split de locatie
                String[] locationMogelijkheden = location.split(",");

                boolean found = false;
                // voor elk deel van de locatie zoek een geo coordinaat
                for (String s : locationMogelijkheden) {

                    Log.d("GEOCODING", "TRYING TO FIND GEO FOR: " + s);


                    s = s.trim();
                    s = s.trim();

                    if (found) {
                        break;
                    }
                    GeocodingApiRequest request = GeocodingApi.geocode(context,
                            s);


                    try {
                        GeocodingResult[] results = request.await();
                        Log.d("GEOCODING", "RESULT" + Arrays.toString(results));
                        if (results.length > 0) {

                            GeocodingResult r = results[0];


                            Location l = new Location(e.getDescription(), r.geometry.location.lat, r.geometry.location.lng);
                            Log.d("FOUND LOCATION", l.toString());

                            model.retrieveForecastLocationLatLong(new Updatable<Forecast>() {
                                @Override
                                public void update(Forecast input) {
                                    eventWrapper.setForecast(input);

                                }
                            }, l);

                            found = true;


                        }


                    } catch (Exception ex) {

                        Log.d("GEOCODING", ex.getMessage() + ex.getCause());
                    }

                }


            }


            eventWrapperList.add(eventWrapper);

        }

        model.setEvents(eventWrapperList);
        invokeCallBack(callBack, items);


    }

    @Background
    protected void searchCity(String city) {
        JsonObject result = openweatherClient.recieveCurrentWeather(city);
        if (result != null) {

            JsonObject coord = result.getAsJsonObject("coord");
            if (coord != null) {
                double resultLon = coord.get("lon").getAsDouble();
                double resultLat = coord.get("lat").getAsDouble();
                String resultCity = result.get("name").getAsString();

                Location resultLocation = new Location(resultCity, resultLat, resultLon);


            } else {
                // give error , plaats niet gevonden
            }
        } else {
            //give error, result is null, internet conn fout ?
        }

    }


    @UiThread
    protected <T> void invokeCallBack(Updatable<T> callBack, T input) {
        callBack.update(input);
    }


    @Background
    public void getAgendas(Updatable callback) {

        com.google.api.services.calendar.Calendar mService = getmService();

        ArrayList<CalendarListEntry> r = new ArrayList<>();

        // Iterate through entries in calendar list
        String pageToken = null;
        do {
            CalendarList calendarList = null;
            try {
                calendarList = mService.calendarList().list().setPageToken(pageToken).execute();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("GET AGENDA", "ERROR");
                return;
            }
            List<CalendarListEntry> items = calendarList.getItems();

            for (CalendarListEntry calendarListEntry : items) {
                r.add(calendarListEntry);
                System.out.println(calendarListEntry.getSummary());
            }
            pageToken = calendarList.getNextPageToken();
        } while (pageToken != null);

        callback.update(r);
        return;
    }

    @Background
    public void makeAgenda(Calendar agenda) {

        com.google.api.services.calendar.Calendar mService = getmService();

// Insert the new calendar
        Calendar createdCalendar = null;
        try {
            createdCalendar = mService.calendars().insert(agenda).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CalendarListEntry agendaListEntry = new CalendarListEntry();
        agendaListEntry.setId(createdCalendar.getId());

// Insert the new calendar list entry
        CalendarListEntry createdCalendarListEntry = null;
        try {
            createdCalendarListEntry = mService.calendarList().insert(agendaListEntry).execute();
            model.setWeatherAgenda(createdCalendarListEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Background
    public void exportForecasts(ArrayList<Forecast> forecasts) {


        com.google.api.services.calendar.Calendar mService = getmService();


        DateTimeZone timeZone = DateTimeZone.forID("Europe/Amsterdam");
        org.joda.time.DateTime now = DateTime.now(timeZone);
        org.joda.time.DateTime beginToday = now.withTimeAtStartOfDay();
        org.joda.time.DateTime beginTomorrow = now.plusDays(1).withTimeAtStartOfDay();


        for (Forecast f : forecasts) {


            double temp;
            int pressure;
            int humidity;
            double temp_min;
            double temp_max;
            double windSpeed;

            Event event = new Event()
                    .setSummary("Weerbericht")
                    .setLocation(model.getStandardLocation().getCity())
                    .setDescription(f.toString());

            Date startDate = beginToday.toDate();
            Date endDate = beginTomorrow.toDate();

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String startDateStr = dateFormat.format(startDate);
            String endDateStr = dateFormat.format(endDate);


            com.google.api.client.util.DateTime startDateTime = new com.google.api.client.util.DateTime(startDateStr);
            com.google.api.client.util.DateTime endDateTime = new com.google.api.client.util.DateTime(endDateStr);

            // Must use the setDate() method for an all-day event (setDateTime() is used for timed events)
            EventDateTime startEventDateTime = new EventDateTime().setDate(startDateTime);
            EventDateTime endEventDateTime = new EventDateTime().setDate(endDateTime);

            event.setStart(startEventDateTime);
            event.setEnd(endEventDateTime);

            EventAttendee[] attendees = new EventAttendee[]{};
            event.setAttendees(Arrays.asList(attendees));

            Event.Reminders reminders = new Event.Reminders();
            event.setReminders(reminders);

            String calendarId = model.getWeatherAgenda().getId();
            try {
                event = mService.events().insert(calendarId, event).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }


            //add +1 day
            beginToday = beginTomorrow;
            beginTomorrow = beginToday.plusDays(1).withTimeAtStartOfDay();
        }


    }

    private com.google.api.services.calendar.Calendar getmService() {
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        return new com.google.api.services.calendar.Calendar.Builder(
                transport, jsonFactory, model.getCredentials())
                .setApplicationName("CalendarApplication")
                .build();
    }


}
