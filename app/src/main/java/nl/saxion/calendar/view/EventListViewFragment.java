package nl.saxion.calendar.view;


import android.content.Context;
import android.util.Log;

import com.google.common.base.Function;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import java.util.List;

import javax.annotation.Nullable;

import nl.saxion.calendar.R;
import nl.saxion.calendar.model.EventWrapper;
import nl.saxion.calendar.model.Model;
import nl.saxion.calendar.utils.Updatable;

/**
 * Created by jonathan on 17-9-15.
 */

@EFragment(R.layout.refresh_recyclerview_fragment)
public class EventListViewFragment extends GenericListViewFragment<EventWrapper, EventView> implements Updatable<List<EventWrapper>> {


    @Bean
    Model model;



    @AfterViews
    public void afterInject(){
        model.retrieveEvents(this);

    }


    @Override
    public void update(List<EventWrapper> input) {

        Log.d("EVENTFRAG", input.toArray().toString());
        mAdapter.notifyDataSetChanged();




    }


    @Override
    public Function<Void, List<EventWrapper>> getItems() {
        return new Function<Void, List<EventWrapper>>() {
            @Nullable
            @Override
            public List<EventWrapper> apply(@Nullable Void input) {
                return model.getEvents();
            }
        };
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
