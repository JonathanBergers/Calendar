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
    TextView textViewSummary, textViewDescription, textViewCreator, textViewCreated,
            textViewStartDate, textViewEndDate, textViewTag, textViewLocationEvent;


    @Bean
    Model model;

    public EventView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.event_view, this);
    }

    @Override
    public void setData(Event event) {

        textViewSummary.setText(event.getSummary());
        textViewDescription.setText(event.getDescription());
        textViewCreator.setText(event.getCreator().toString());
        textViewCreated.setText(event.getCreated().toStringRfc3339());
        textViewStartDate.setText(event.getStart().toString());
        textViewEndDate.setText(event.getEnd().toString());
        textViewTag.setText(event.getEtag());
        textViewLocationEvent.setText(event.getLocation());


    }

}
