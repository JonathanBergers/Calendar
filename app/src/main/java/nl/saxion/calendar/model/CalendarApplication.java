package nl.saxion.calendar.model;

import android.app.Application;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EApplication;

/**
 * De applicatie klasse.
 * Hierin wordt alles geinitialiseerd
 * Created by jonathan on 17-9-15.
 */
@EApplication
public class CalendarApplication extends Application {





    /**Aan het begin van de applicatie
     * Hier kunnen zaken geinitialiseerd worden
     */
    @AfterInject
    protected void init(){


    }



}
