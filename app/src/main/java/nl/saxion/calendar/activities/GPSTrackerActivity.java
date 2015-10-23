//package nl.saxion.calendar.activities;
//
//import android.app.Activity;
//import android.content.Context;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import org.androidannotations.annotations.AfterInject;
//import org.androidannotations.annotations.Bean;
//import org.androidannotations.annotations.EActivity;
//
//
//import nl.saxion.calendar.R;
//import nl.saxion.calendar.model.Model;
//
//@EActivity(R.layout.activity_gpstracker)
//public class GPSTrackerActivity extends BaseActivity {
//
//
//    @Bean
//    Model model;
//
//    private TextView location;
//
//    @AfterInject
//    public void init() {
//        // use location manager to get gps location
//               LocationManager myLocationManager =
//                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        LocationListener myLocationListener = new MyLocationListener();
//
//        myLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
//                0, 0, myLocationListener);
//
//        //retrieves lat and long coordinates from the model
//        String gpsLocation = "current location is: latitude = " +
//                model.getCurrentLocation().getLat() + " and longitude = " + model.getCurrentLocation().getLon();
//
//        // logs coordinates and shows them with a toast
//        Log.d("OnLocationChanged ", gpsLocation);
//
//
//
////        location = (TextView) findViewById(R.id.location);
////
////        location.setText(gpsLocation);
////
////        Log.d("GPS locations", gpsLocation + "");
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    public class MyLocationListener implements LocationListener {
//        @Override
//        public void onLocationChanged(Location location) {
//
//            //gets the coordinates of your current location
//            double latitude = location.getLatitude();
//            double longitude = location.getLongitude();
//
//            //saves the latitude and longitude in the model
//            model.addLocation(new nl.saxion.calendar.model.Location("Huidige Locatie", latitude, longitude));
//
//            //retrieves lat and long coordinates from the model
//            String gpsLocation = "current location is: latitude = " +
//                    model.getCurrentLocation().getLat() + " and longitude = " + model.getCurrentLocation().getLon();
//
//            // logs coordinates and shows them with a toast
//            Log.d("OnLocationChanged ", gpsLocation);
//            Toast.makeText(getApplicationContext(), gpsLocation, Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//
//        }
//
//        @Override
//        public void onProviderEnabled(String provider) {
//
//            Toast.makeText(getApplicationContext(), "GPS enabled", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onProviderDisabled(String provider) {
//
//            Toast.makeText(getApplicationContext(), "GPS disabled", Toast.LENGTH_SHORT).show();
//
//        }
//
//    }
//
//
//}
//
