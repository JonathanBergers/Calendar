package nl.saxion.calendar.client;

import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.gson.JsonObject;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.PendingResult;
import com.google.maps.errors.ZeroResultsException;
import com.google.maps.internal.ExceptionResult;
import com.google.maps.model.GeocodingResult;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.saxion.calendar.model.Forecast;
import nl.saxion.calendar.model.JsonConverterWeather;
import nl.saxion.calendar.model.Location;
import nl.saxion.calendar.model.Model;
import nl.saxion.calendar.utils.Resources;
import nl.saxion.calendar.utils.Updatable;
import nl.saxion.calendar.model.EventWrapper;

/**
 * Created by jonathan on 7-10-15.
 */
@EBean
public class GoogleCalendarClient {
    private Exception mLastError = null;

    @Bean
    Model model;


    @RestService
    OpenweatherClient openweatherClient;

    public GoogleCalendarClient() {    }


    /**haalt events op
     * als een event een locatie heeft dan wordt hier de long en lat van opgehaald
     *
     * @param callBack
     */
    @Background
    public void retrieveEvents(Updatable<List<Event>> callBack){



        HttpTransport transport = AndroidHttp.newCompatibleTransport();



        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        com.google.api.services.calendar.Calendar mService = getmService();
        DateTime now = new DateTime(System.currentTimeMillis());
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

        for(Event e: items){


            final EventWrapper eventWrapper = new EventWrapper(e);
            String location = e.getLocation();

            if(location != null) {
                Log.d("GEOCODING", location);
                // haal locatie op bij event


                // split de locatie
                String[] locationMogelijkheden = location.split(",");

               boolean found = false;
                // voor elk deel van de locatie zoek een geo coordinaat
                for(String s: locationMogelijkheden){

                    Log.d("GEOCODING", "TRYING TO FIND GEO FOR: " + s);


                    s = s.trim();
                    s = s.trim();

                    if(found){
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
    protected void searchCity(String city){
        JsonObject result = openweatherClient.recieveCurrentWeather(city);
        if(result!=null){

            JsonObject coord = result.getAsJsonObject("coord");
            if(coord!=null) {
                double resultLon = coord.get("lon").getAsDouble();
                double resultLat = coord.get("lat").getAsDouble();
                String resultCity = result.get("name").getAsString();

                Location resultLocation = new Location(resultCity, resultLat, resultLon);


            }else{
                // give error , plaats niet gevonden
            }
        } else {
            //give error, result is null, internet conn fout ?
        }

    }


    @UiThread
    protected  <T> void invokeCallBack(Updatable<T> callBack, T input){
        callBack.update(input);
    }



    @Background
    public void getAgendas(Updatable callback){

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
    public void makeAgenda(Calendar agenda){

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

    private com.google.api.services.calendar.Calendar getmService(){
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        return new com.google.api.services.calendar.Calendar.Builder(
                transport, jsonFactory, model.getCredentials())
                .setApplicationName("CalendarApplication")
                .build();
    }




//    /**
//     * An asynchronous task that handles the Google Calendar API call.
//     * Placing the API calls in their own task ensures the UI stays responsive.
//     */
//    private class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {
//        private com.google.api.services.calendar.Calendar mService = null;
//        private Exception mLastError = null;
//
//        public MakeRequestTask(GoogleAccountCredential credential) {
//            HttpTransport transport = AndroidHttp.newCompatibleTransport();
//            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
//            mService = new com.google.api.services.calendar.Calendar.Builder(
//                    transport, jsonFactory, credential)
//                    .setApplicationName("Calendar")
//                    .build();
//        }
//
//        /**
//         * Background task to call Google Calendar API.
//         * @param params no parameters needed for this task.
//         */
//        @Override
//        public List<String> doInBackground(Void... params) {
//            try {
//                return getDataFromApi();
//            } catch (Exception e) {
//                mLastError = e;
//                cancel(true);
//                return null;
//            }
//        }
//
//        /**
//         * Fetch a list of the next 10 events from the primary calendar.
//         * @return List of Strings describing returned events.
//         * @throws IOException
//         */
//        private List<String> getDataFromApi() throws IOException {
//            // List the next 10 events from the primary calendar.
//            DateTime now = new DateTime(System.currentTimeMillis());
//            List<String> eventStrings = new ArrayList<String>();
//            Events events = mService.events().list("primary")
//                    .setMaxResults(10)
//                    .setTimeMin(now)
//                    .setOrderBy("startTime")
//                    .setSingleEvents(true)
//                    .execute();
//            List<Event> items = events.getItems();
//
//            for (Event event : items) {
//
//                DateTime start = event.getStart().getDateTime();
//                if (start == null) {
//                    // All-day events don't have start times, so just use
//                    // the start date.
//                    start = event.getStart().getDate();
//                }
//                eventStrings.add(
//                        String.format("%s (%s)", event.getSummary(), start));
//            }
//
//            Log.d("LOGIN", eventStrings.toString());
//            return eventStrings;
//        }
//
//
//        @Override
//        protected void onPreExecute() {
//            mOutputText.setText("");
//            mProgress.show();
//        }
//
//        @Override
//        protected void onPostExecute(List<String> output) {
//            mProgress.hide();
//            Log.d("ONPOST", output.toString());
//            if (output == null || output.size() == 0) {
//                mOutputText.setText("No results returned.");
//            } else {
//                output.add(0, "Data retrieved using the Google Calendar API:");
//                mOutputText.setText(TextUtils.join("\n", output));
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            mProgress.hide();
//            if (mLastError != null) {
//                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
//                    showGooglePlayServicesAvailabilityErrorDialog(
//                            ((GooglePlayServicesAvailabilityIOException) mLastError)
//                                    .getConnectionStatusCode());
//                } else if (mLastError instanceof UserRecoverableAuthIOException) {
//                    startActivityForResult(
//                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
//                            LoginActivity.REQUEST_AUTHORIZATION);
//                } else {
//                    mOutputText.setText("The following error occurred:\n"
//                            + mLastError.getMessage());
//                }
//            } else {
//                mOutputText.setText("Request cancelled.");
//            }
//        }
//    }
//
//


}
