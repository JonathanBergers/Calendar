package nl.saxion.calendar.view;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.common.base.Function;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import javax.annotation.Nullable;

import nl.saxion.calendar.R;
import nl.saxion.calendar.model.Forecast;
import nl.saxion.calendar.utils.Updatable;

/**
 * Created by falco on 24-9-15.
 */
@EFragment(R.layout.refresh_recyclerview_fragment)
public class ForecastListViewFragment extends GenericListViewFragment<Forecast, ForecastView> implements Updatable<List<Forecast>> {


    @ViewById
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public Function<Void, List<Forecast>> getItems() {
        return new Function<Void, List<Forecast>>() {
            @Nullable
            @Override
            public List<Forecast> apply(@Nullable Void input) {
                return model.getLocationForecasts();
            }
        };
    }

    @Override
    protected Function<Context, ForecastView> createView() {
        return new Function<Context, ForecastView>() {
            @Nullable
            @Override
            public ForecastView apply(@Nullable Context input) {
                return ForecastView_.build(input);
            }
        };
    }


    @AfterViews
    public void initForecastFragment(){


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                model.retrieveForecasts(ForecastListViewFragment.this);
                swipeRefreshLayout.setRefreshing(false);

            }
        });



        LocationManager myLocationManager =
                (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        LocationListener myLocationListener = new MyLocationListener();

        myLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                0, 0, myLocationListener);


        Toast.makeText(getContext(), model.getCurrentLocation() + " is de huidige locatie", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void update(List<Forecast> input) {
        mAdapter.notifyDataSetChanged();

    }

    public class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {

            //gets the coordinates of your current location
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();



            //saves the latitude and longitude in the model
            model.setCurrentLocation(new nl.saxion.calendar.model.Location("Huidige Locatie", latitude, longitude));

            //retrieves lat and long coordinates from the model
//            String gpsLocation = "current location is: latitude = " +
//                    model.getCurrentLocation().getLat() + " and longitude = " + model.getCurrentLocation().getLon();

            // logs coordinates and shows them with a toast
           // Log.d("OnLocationChanged ", gpsLocation);
            //Toast.makeText(getApplicationContext(), gpsLocation, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

            //Toast.makeText(getApplicationContext(), "GPS enabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {

           // Toast.makeText(getApplicationContext(), "GPS disabled", Toast.LENGTH_SHORT).show();

        }

    }

}
