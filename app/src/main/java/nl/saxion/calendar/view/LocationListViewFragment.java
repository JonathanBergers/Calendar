package nl.saxion.calendar.view;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
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
public class LocationListViewFragment extends Fragment {

    @RestService
    OpenweatherClient openweatherClient;

    @ViewById
    RecyclerView recyclerView;

    @Bean
    Model model;

    RecyclerViewMaterialAdapter mAdapter;


    @AfterViews
    public void init(){


        // set layout manager, or else bug, nullpointer exep
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(null, 2);
        recyclerView.setLayoutManager(linearLayoutManager);

        // set adapter
        mAdapter = new RecyclerViewMaterialAdapter(new LocationListAdapter(model));
        recyclerView.setAdapter(mAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), recyclerView, null);


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

                getRightCity(city, resultLocation);
            }else{
                // give error , plaats niet gevonden
            }
        } else {
            //give error, result is null, internet conn fout ?
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
            builder.setMessage(model.getCredentials().getSelectedAccountName() + "u zocht op: "+searedCity+"\nBedoelt u: "+resultLocation.getCity()+"?").setPositiveButton("Ja", dialogClickListener)
                    .setNegativeButton("Nee", dialogClickListener).show();
        }
        return;
    }



}
