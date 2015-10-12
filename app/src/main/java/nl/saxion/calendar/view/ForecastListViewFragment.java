package nl.saxion.calendar.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import com.google.common.base.Function;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import javax.annotation.Nullable;

import nl.saxion.calendar.R;
import nl.saxion.calendar.model.Forecast;

/**
 * Created by falco on 24-9-15.
 */
@EFragment(R.layout.weather_lisview_fragment)
public class ForecastListViewFragment extends GenericListViewFragment<Forecast, ForecastView> {


    @ViewById
    SwipeRefreshLayout swipeRefreshLayout;


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


    @AfterViews
    public void initRefreshlayout(){


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

}
