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

    String stringCreated, stringStartDate, stringEndDate;

    @Bean
    Model model;

    public EventView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.event_view, this);
    }

    @Override
    public void setData(Event event) {


        DateTime a = event.getCreated();
        Log.d("DATETIME", a.toStringRfc3339());
  //TODO convertion

                //converts the dates to strings
                stringCreated = convertDateToString(event.getCreated());
        stringStartDate = convertEventDateToString(event.getStart());
        stringEndDate = convertEventDateToString(event.getEnd());

        textViewSummary.setText(event.getSummary());
        textViewDescription.setText(event.getDescription());
        textViewCreator.setText(event.getCreator().toString());
        textViewCreated.setText(stringCreated);
        textViewStartDate.setText(stringStartDate);
        textViewEndDate.setText(stringEndDate);
        textViewTag.setText(event.getEtag());
        textViewLocationEvent.setText(event.getLocation());


    }

    /**
     * convert a DateTime to a string
     * @param date the DateTime to convert
     * @return a string with the converted DateTime
     */
    public String convertDateToString(DateTime date) {

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm");
        String stringDate = df.format(date);

        return stringDate;
    }

    /**
     * convert an EventDateTime to a string
     * @param date the EventDateTime to convert
     * @return a string with the converted EventDateTime
     */
    public String convertEventDateToString(EventDateTime date) {

        DateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm");
        String stringDate = df.format(date);

        return stringDate;
    }

}
