package nl.saxion.calendar.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import nl.saxion.calendar.model.Location;

/**
 * Created by falco on 28-9-15.
 */
public class LocationViewHolder extends RecyclerView.ViewHolder {

    LocationView locationView;

    public LocationViewHolder(View itemView) {
        super(itemView);
        locationView = (LocationView) itemView;
    }






    public void setData(Location l){

        locationView.setData(l);

    }
}
