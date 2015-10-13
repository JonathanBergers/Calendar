package nl.saxion.calendar.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import nl.saxion.calendar.R;
import nl.saxion.calendar.model.Model;

/**
 * Created by Alec on 12-10-2015.
 */
@EViewGroup
public class EventView extends LinearLayout implements SetData<Event> {

    @ViewById
    TextView materialEditTextEventTitle, materialEditTextEventDescription, materialEditTextEventCreator, materialEditTextEventCreated,
            materialEditTextEventStartDate, materialEditTextEventEndDate, materialEditTextEventTag, materialEditTextEventLocation;


    @Bean
    Model model;

    public EventView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.event_view, this);
    }

    @Override
    public void setData(Event event) {

        materialEditTextEventTitle.setText(event.getSummary());
        materialEditTextEventDescription.setText(event.getDescription());
        materialEditTextEventCreator.setText(event.getCreator().getDisplayName());
        materialEditTextEventCreated.setText(event.getCreated().toStringRfc3339());
        materialEditTextEventStartDate.setText(event.getStart().getTimeZone());
        materialEditTextEventEndDate.setText(event.getEnd().getTimeZone());
        materialEditTextEventTag.setText(event.getEtag());
        materialEditTextEventLocation.setText(event.getLocation());


    }

}
