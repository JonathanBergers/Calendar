package nl.saxion.calendar.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.api.client.json.Json;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import nl.saxion.calendar.R;
import nl.saxion.calendar.model.Location;
import nl.saxion.calendar.model.Model;

/**
 * Created by Alec on 12-10-2015.
 */
@EViewGroup
public class EventView extends LinearLayout implements SetData<Event> {

    @ViewById
    TextView textViewSummary, textViewDescription;

    @ViewById
    MaterialEditText materialEditTextStart, materialEditTextEnd;

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

        String[] startArray = event.getStart().toString().split("\"");
        String[] endArray = event.getEnd().toString().split("\"");

        String startString = startArray[3];
        String endString  = endArray[3];




        endString = endString.replace('T', ' ');


        if(startString.length() > 10){
            startString = startString.substring(0, startString.length() - 13);
            startString = startString.replace('T', ' ');
        }
        if(endString.length() > 10){
            endString = endString.substring(0, endString.length() - 13);
            endString = endString.replace('T', ' ');
        }



        materialEditTextStart.setText(startString);
        materialEditTextEnd.setText(endString);






    }

}
