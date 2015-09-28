package nl.saxion.calendar.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import nl.saxion.calendar.model.Forecast;

/**
 * Created by jonathan on 21-9-15.
 */
public class ForecastViewHolder extends RecyclerView.ViewHolder {


    ForecastView forecastView;

    public ForecastViewHolder(View itemView) {
        super(itemView);
        forecastView = (ForecastView) itemView;

    }


    public void setData(Forecast f){

        forecastView.setData(f);

    }





}
