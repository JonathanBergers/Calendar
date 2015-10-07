//package nl.saxion.calendar.client;
//
//import android.os.AsyncTask;
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.google.api.client.extensions.android.http.AndroidHttp;
//import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
//import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
//import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.client.util.DateTime;
//import com.google.api.services.calendar.model.Event;
//import com.google.api.services.calendar.model.Events;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by jonathan on 7-10-15.
// */
//public class GoogleCalendarClient {
//    private com.google.api.services.calendar.Calendar mService = null;
//    private Exception mLastError = null;
//
//    public GoogleCalendarClient() {
//
//
//
////            HttpTransport transport = AndroidHttp.newCompatibleTransport();
////            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
////            mService = new com.google.api.services.calendar.Calendar.Builder(
////                    transport, jsonFactory, credential)
////                    .setApplicationName("Calendar")
////                    .build();
//        }
//    }
//
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
//
//
//}