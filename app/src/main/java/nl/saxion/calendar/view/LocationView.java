package nl.saxion.calendar.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import nl.saxion.calendar.R;
import nl.saxion.calendar.model.Location;
import nl.saxion.calendar.model.Model;

/**
 * Created by falco on 28-9-15.
 */
@EViewGroup
public class LocationView extends LinearLayout {

    @ViewById(R.id.materialEditText_Location)
    MaterialEditText TELocation;

    @ViewById(R.id.button_LocationSearch)
    Button BSearch;

    @Bean
    Model model;



    public LocationView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.location_view, this);
    }
    public void setData(Location l){
        if(l==null){
            TELocation.setText("zoek hier");
            BSearch.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    model.searchCity(TELocation.getText().toString(),getContext());

                }
            });
        } else {
            //wanneer het niet de eerste editText is in de lijst, disable de EditText en
            //verander de naam van de Button naar Standaard locatie
            TELocation.setText(l.getCity());
            TELocation.setEnabled(false);
            BSearch.setText("Standaard locatie");
            //wanneer je op standaard locatie klikt, zal die de stad opslaan in het model
            BSearch.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    model.setStandaardLocatie(TELocation.getText().toString());
                    Log.d("LocationView!!!", "city is " + model.getStandaardLocatie());
                }
            });
        }




    }

    //wanneer geklikt, stelt die locatie in als standaard locatie
//    @Click
//    void buttonStandaardLocatie() {
//        model.setStandaardLocatie(TELocation.getText().toString());
//
//        Log.d("LocationView!!!", "city is " + model.getStandaardLocatie());
//    }




}
