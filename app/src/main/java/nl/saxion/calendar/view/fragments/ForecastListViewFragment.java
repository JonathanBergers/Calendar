package nl.saxion.calendar.view.fragments;

import android.content.Context;

import com.google.common.base.Function;

import org.androidannotations.annotations.EFragment;

import java.util.List;

import javax.annotation.Nullable;

import nl.saxion.calendar.R;
import nl.saxion.calendar.model.Forecast;
import nl.saxion.calendar.view.ForecastView;
import nl.saxion.calendar.view.ForecastView_;

/**
 * Created by falco on 24-9-15.
 */
@EFragment(R.layout.weather_lisview_fragment)
public class ForecastListViewFragment extends GenericListViewFragment<Forecast, ForecastView> {



    @Override
    public List<Forecast> getItems() {

        //return new ArrayList<>();
        return model.getLocationForecasts();
    }

    @Override
    protected Function<Context, ForecastView> createView() {
        return new Function<Context, ForecastView>() {
            @Nullable
            @Override
            public ForecastView apply(@Nullable Context input) {
                return ForecastView_.build(input);
            }
        };
    }


}
