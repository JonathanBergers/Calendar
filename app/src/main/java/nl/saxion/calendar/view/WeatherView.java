package nl.saxion.calendar.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.StringRes;
import android.view.View;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EView;

import nl.saxion.calendar.R;

/**
 * Created by jonathan on 21-9-15.
 */
@EView
public class WeatherView extends View {


    public WeatherView(Context context) {
        super(context);
    }


    @AfterInject
    public void init(){


        setBackgroundColor(Color.RED);
    }
}
