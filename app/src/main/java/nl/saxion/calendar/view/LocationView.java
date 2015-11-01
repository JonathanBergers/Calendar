package nl.saxion.calendar.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import nl.saxion.calendar.R;
import nl.saxion.calendar.model.Location;
import nl.saxion.calendar.model.Model;

/**
 * Created by falco on 28-9-15.
 */
@EViewGroup
public class LocationView extends LinearLayout implements SetData<Location> {

    @ViewById(R.id.materialEditText_Location)
    MaterialEditText materialEditTextLocation;

    @ViewById(R.id.button_LocationSearch)
    Button buttonSearch;

    @Bean
    Model model;


    public LocationView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.location_view, this);
    }

    public void setData(Location l) {
        if (l == null) {
            materialEditTextLocation.setText("zoek hier");
            buttonSearch.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    model.searchCity(materialEditTextLocation.getText().toString(), getContext());

                }
            });
        } else {
            //wanneer het niet de eerste editText is in de lijst, disable de EditText en
            //verander de naam van de Button naar Standaard locatie
            materialEditTextLocation.setText(l.getCity());
            materialEditTextLocation.setEnabled(false);
            buttonSearch.setText("Standaard locatie");
            //wanneer je op standaard locatie klikt, zal die de stad opslaan in het model
            buttonSearch.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getContext(), materialEditTextLocation.getText().toString() + " ingesteld als standaard locatie", Toast.LENGTH_SHORT).show();
                    model.setStandardLocation(new Location(materialEditTextLocation.getText().toString(), 0, 0));
                }
            });
        }


    }


}
