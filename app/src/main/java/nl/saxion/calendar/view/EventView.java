package nl.saxion.calendar.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import nl.saxion.calendar.R;
import nl.saxion.calendar.model.EventWrapper;
import nl.saxion.calendar.model.Forecast;
import nl.saxion.calendar.model.Model;
import nl.saxion.calendar.utils.Updatable;

/**
 * Created by Alec on 12-10-2015.
 */
@EViewGroup(R.layout.event_view)
public class EventView extends LinearLayout implements SetData<EventWrapper>, Updatable<Bitmap> {

    @ViewById
    TextView textViewSummary, textViewDescription;

    @ViewById
    MaterialEditText materialEditTextStart, materialEditTextEnd;

    @ViewById
    FrameLayout frameLayout;

    @ViewById
    ImageView imageView;

    @Bean
    Model model;


    Forecast forecast;

    boolean showingForecast = false;

    public EventView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.event_view, this);


    }


    @Override
    public void setData(final EventWrapper event) {


        textViewSummary.setText(event.getSummary());
        textViewDescription.setText(event.getDescription());
        materialEditTextStart.setText(event.getStart());
        materialEditTextEnd.setText(event.getEnd());


        final Forecast f = event.getForecast();
        if (f != null) {
            this.forecast = f;
            Log.d("EVENTJO", event.getForecast().toString());

            ForecastView fv = ForecastView_.build(getContext());
            fv.setData(forecast);
            frameLayout.addView(fv);
            fv.getBitmapFromURL(this, fv.getBitmapUrl());
            showingForecast = true;

        }

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if (forecast == null) return false;

                if (!showingForecast) {
                    ForecastView fv = ForecastView_.build(getContext());
                    fv.setData(forecast);
                    frameLayout.addView(fv);
                    showingForecast = true;
                } else {
                    frameLayout.removeAllViews();
                    showingForecast = false;
                }
                return false;
            }
        });


    }


    @Override
    @UiThread
    public void update(Bitmap input) {
        imageView.setImageBitmap(input);
        imageView.setMinimumWidth(100);
        imageView.setMinimumHeight(100);
    }
}
