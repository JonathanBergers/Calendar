package nl.saxion.calendar.view;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import nl.saxion.calendar.R;
import nl.saxion.calendar.client.OpenweatherClient;
import nl.saxion.calendar.model.Location;
import nl.saxion.calendar.model.Model;

/**
 * Created by falco on 24-9-15.
 */
@EFragment(R.layout.location_listview_fragment)
public class LocationViewFragment extends Fragment {

    @RestService
    OpenweatherClient openweatherClient;

    @ViewById(R.id.editText_LocationSearch)
    EditText ETLocationSearch;

    @ViewById(R.id.button_LocationSearch)
    Button BLocationSearch;

    @ViewById
    RecyclerView recyclerView;

    @Bean
    Model model;




    @AfterViews
    public void init(){


        //searchCity("Vorden");
        BLocationSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchCity(ETLocationSearch.getText().toString());
            }
        });
    }



    @Background
    protected void searchCity(String city){
        JsonObject result = openweatherClient.recieveCurrentWeather(city);
        if(result!=null){

            JsonObject coord = result.getAsJsonObject("coord");
            double resultLon = coord.get("lon").getAsDouble();
            double resultLat = coord.get("lat").getAsDouble();
            String resultCity = result.get("name").getAsString();

            Location resultLocation = new Location(resultCity, resultLat, resultLon);

            getRightCity(city, resultLocation);
        } else {
            //give error
        }
        return;
    }

    @UiThread
    public void getRightCity(String searedCity, final Location resultLocation){
        if(searedCity.equalsIgnoreCase(resultLocation.getCity())){
            model.addLocation(resultLocation);
        } else {
            //geef gebruiker keuze
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
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
            builder.setMessage("u zocht op: "+searedCity+"\nBedoelt u: "+resultLocation.getCity()+"?").setPositiveButton("Ja", dialogClickListener)
                    .setNegativeButton("Nee", dialogClickListener).show();
        }
        return;
    }



}
