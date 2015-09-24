package nl.saxion.calendar.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EView;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import nl.saxion.calendar.R;

/**
 * Created by jonathan on 24-9-15.
 */
@EViewGroup
public class ForecastView extends LinearLayout{


    @ViewById
    TextView textView;

    public ForecastView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.weather_view, this);
    }




    @AfterViews
    public void init(){

    }
}
