package nl.saxion.calendar.view;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import nl.saxion.calendar.model.Forecast;
import nl.saxion.calendar.model.Model;
import nl.saxion.calendar.model.Weather;

/**
 * Created by jonathan on 21-9-15.
 */
public class ForecastListAdapter extends RecyclerView.Adapter<ForecastViewHolder> implements Observer {


    public ForecastListAdapter(Model model) {
        this.model = model;
    }


    Model model;


    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {




        // create the weatherview
        ViewGroup v = new ForecastView(parent.getContext());
        v = ForecastView_.build(parent.getContext());

        // create the viewholder
        ForecastViewHolder holder = new ForecastViewHolder(v);

        return holder;


    }


    /**This is where the view gets manipulated.
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {

        Forecast w = model.getLocationForecasts().get(position);

        Log.d("JOO", (w.toString()));

        holder.setData("JO");



    }




    @Override
    public int getItemCount() {


        List<Forecast> forecastList = model.getLocationForecasts();
        return forecastList.size();
    }






    @Override
    public void update(Observable observable, Object data) {





    }
}
