package nl.saxion.calendar.view;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import nl.saxion.calendar.model.Location;
import nl.saxion.calendar.model.Model;

/**
 * Created by falco on 28-9-15.
 */



public class LocationListAdapter extends RecyclerView.Adapter<LocationViewHolder> {


    Model model;

    public LocationListAdapter(Model model) {
        this.model = model;
    }


    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create the weatherview
        ViewGroup v = new LocationView(parent.getContext());
        v = LocationView_.build(parent.getContext());

        // create the viewholder
        LocationViewHolder holder = new LocationViewHolder(v);


        return holder;
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {

        if(position==0){
            holder.setData(null);
            return;
        }
        Location l  = model.getLocations().get(position-1);

        Log.d("JOO", (l.toString()));

        holder.setData(l);


    }

    @Override
    public int getItemCount() {
        return model.getLocations().size()+1;
    }


}
