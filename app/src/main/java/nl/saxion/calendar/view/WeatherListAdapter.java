package nl.saxion.calendar.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import org.androidannotations.annotations.EBean;

import nl.saxion.calendar.R;

/**
 * Created by jonathan on 21-9-15.
 */
public class WeatherListAdapter extends RecyclerView.Adapter<WeatherViewHolder> {


    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_view, parent, false);
        WeatherViewHolder holder = new WeatherViewHolder(v);

        return holder;


    }

    @Override
    public void onBindViewHolder(WeatherViewHolder holder, int position) {

    }




    @Override
    public int getItemCount() {
        return 40;
    }


}
