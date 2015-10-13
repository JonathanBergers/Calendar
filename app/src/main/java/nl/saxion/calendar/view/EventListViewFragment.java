package nl.saxion.calendar.view;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.google.api.services.calendar.model.Event;
import com.google.common.base.Function;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import nl.saxion.calendar.R;
import nl.saxion.calendar.model.Forecast;
import nl.saxion.calendar.model.Model;
import nl.saxion.calendar.utils.Updatable;

/**
 * Created by jonathan on 17-9-15.
 */

@EFragment(R.layout.weather_lisview_fragment)
public class EventListViewFragment extends GenericListViewFragment<Event, EventView> implements Updatable<List<Event>> {


    @Bean
    Model model;



    @AfterViews
    public void afterInject(){
        model.retrieveEvents(this);

    }


    @Override
    public void update(List<Event> input) {

        Log.d("EVENTFRAG", input.toArray().toString());
        mAdapter.notifyDataSetChanged();


    }

    @Override
    public List<Event> getItems() {

        return model.getEvents();
    }

    @Override
    protected Function<Context, EventView> createView() {
        return new Function<Context, EventView>() {
            @Nullable
            @Override
            public EventView apply(@Nullable Context input) {
                return EventView_.build(input);
            }
        };
    }


}
