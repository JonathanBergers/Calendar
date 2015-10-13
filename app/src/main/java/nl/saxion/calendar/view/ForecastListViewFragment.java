package nl.saxion.calendar.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.google.common.base.Function;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import javax.annotation.Nullable;

import nl.saxion.calendar.R;
import nl.saxion.calendar.model.Forecast;
import nl.saxion.calendar.utils.Updatable;

/**
 * Created by falco on 24-9-15.
 */
@EFragment(R.layout.refresh_recyclerview_fragment)
public class ForecastListViewFragment extends GenericListViewFragment<Forecast, ForecastView> implements Updatable<List<Forecast>> {


    @ViewById
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public Function<Void, List<Forecast>> getItems() {
        return new Function<Void, List<Forecast>>() {
            @Nullable
            @Override
            public List<Forecast> apply(@Nullable Void input) {
                return model.getLocationForecasts();
            }
        };
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
                model.retrieveForecasts(ForecastListViewFragment.this);
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    @Override
    public void update(List<Forecast> input) {


        //mAdapter.mvp_notifyDataSetChanged();

        Log.d("FORECASTFRAG", "Update");

        //recyclerView.getAdapter().notifyDataSetChanged();

        mAdapter.notifyDataSetChanged();

        //genericListAdapter.notifyDataSetChanged();
       // mAdapter.notifyDataSetChanged();

    }
}
