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

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nl.saxion.calendar.model.Model;
import nl.saxion.calendar.utils.Updatable;

/**
 * Created by jonathan on 7-10-15.
 */
@EBean
public class GoogleCalendarClient {
    private Exception mLastError = null;

    @Bean
    Model model;


    public GoogleCalendarClient() {    }




    @Background
    public void retrieveEvents(Updatable<List<Event>> callBack){



        HttpTransport transport = AndroidHttp.newCompatibleTransport();



        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        com.google.api.services.calendar.Calendar mService = getmService();
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events;

        Log.d("CALCLIENT", String.valueOf(mService == null));

        try {
            events = mService.events().list("primary")
                    .setMaxResults(10)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();




            List<Event> items = events.getItems();
            model.setEvents(items);
            invokeCallBack(callBack, items);




        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @UiThread
    protected  <T, V> void invokeCallBack(Updatable<List<Event>> callBack, List<Event> input){
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
