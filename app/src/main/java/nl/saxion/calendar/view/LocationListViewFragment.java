package nl.saxion.calendar.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.widget.Button;

import com.google.common.base.Function;
import com.google.gson.JsonObject;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import java.util.List;

import javax.annotation.Nullable;

import nl.saxion.calendar.R;
import nl.saxion.calendar.client.OpenweatherClient;
import nl.saxion.calendar.model.Location;

/**
 * Created by falco on 24-9-15.
 */
@EFragment(R.layout.location_recyclerview_fragment)
public class LocationListViewFragment extends GenericListViewFragment<Location, LocationView> {

    @ViewById
    SwipeRefreshLayout swipeRefreshLayout;

    @RestService
    OpenweatherClient openweatherClient;

    @ViewById
    Button buttonAddLocation;

    @ViewById
    MaterialEditText materialEditTextLocation;

    @Click
    public void buttonAddLocation() {

        String input = materialEditTextLocation.getText().toString();

        if (!input.isEmpty()) {
            searchCity(input);
        }


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

                getRightCity(city, resultLocation);
            } else {
                // give error , plaats niet gevonden
            }
        } else {
            //give error, result is null, internet conn fout ?
        }

    }

    @UiThread
    public void getRightCity(String searedCity, final Location resultLocation) {
        if (searedCity.equalsIgnoreCase(resultLocation.getCity())) {
            model.addLocation(resultLocation);
        } else {
            //geef gebruiker keuze
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            model.addLocation(resultLocation);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            //do nothing for now
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("u zocht op: " + searedCity + "\nBedoelt u: " + resultLocation.getCity() + "?").setPositiveButton("Ja", dialogClickListener)
                    .setNegativeButton("Nee", dialogClickListener).show();
        }

    }


    @Override
    public Function<Void, List<Location>> getItems() {
        return new Function<Void, List<Location>>() {
            @Nullable
            @Override
            public List<Location> apply(@Nullable Void input) {
                return model.getLocations();
            }
        };
    }

    @Override
    protected Function<Context, LocationView> createView() {
        return new Function<Context, LocationView>() {
            @Nullable
            @Override
            public LocationView apply(Context input) {
                return LocationView_.build(input);
            }
        };
    }

    @AfterViews
    public void initRefreshlayout() {


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                model.getLocations();
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }

}
