package nl.saxion.calendar.model;

import com.google.api.services.calendar.model.Event;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by jonathan on 23-10-15.
 */
public class EventWrapper {

    private final
    @Getter
    Event event;
    private
    @Getter
    final String start, end;
    private
    @Getter
    @Setter
    Forecast forecast;
    private
    @Getter
    @Setter
    Location location;


    public EventWrapper(Event event) {
        this.event = event;


        // format start and end
        String[] startArray = getEvent().getStart().toString().split("\"");
        String[] endArray = getEvent().getEnd().toString().split("\"");

        String startString = startArray[3];
        String endString = endArray[3];


        endString = endString.replace('T', ' ');


        if (startString.length() > 10) {
            startString = startString.substring(0, startString.length() - 13);
            startString = startString.replace('T', ' ');
        }
        if (endString.length() > 10) {
            endString = endString.substring(0, endString.length() - 13);
            endString = endString.replace('T', ' ');
        }


        this.start = startString;
        this.end = endString;


        //

    }


    public String getSummary() {
        return getEvent().getSummary();
    }

    public String getDescription() {
        return getEvent().getDescription();
    }
}
