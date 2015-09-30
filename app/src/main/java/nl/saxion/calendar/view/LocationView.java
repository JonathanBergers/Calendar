package nl.saxion.calendar.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import nl.saxion.calendar.R;
import nl.saxion.calendar.model.Location;

/**
 * Created by falco on 28-9-15.
 */
@EViewGroup
public class LocationView extends LinearLayout {

    @ViewById(R.id.materialEditText_Location)
    MaterialEditText TELocation;

    @ViewById(R.id.button_LocationSearch)
    Button BSearch;

    public LocationView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.location_view, this);
    }
    public void setData(Location l){

        TELocation.setText(l.getCity());



    }
}