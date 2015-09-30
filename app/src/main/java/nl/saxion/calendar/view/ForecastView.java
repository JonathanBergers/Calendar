package nl.saxion.calendar.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import nl.saxion.calendar.R;
import nl.saxion.calendar.model.Forecast;
import nl.saxion.calendar.model.Model;

/**
 * Created by jonathan on 24-9-15.
 */
@EViewGroup
public class ForecastView extends LinearLayout{


    @ViewById
    TextView textViewCity, textViewMessage;

    @ViewById
    MaterialEditText materialEditTextTemp, materialEditTextHumidity, materialEditTextWindspeed, materialEditTextPressure, materialEditTextTempMin, materialEditTextTempMax;

    @Bean
    Model model;


    public ForecastView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.weather_view, this);
    }




    @SuppressWarnings("ResourceType")
    public void setData(Forecast f){

        textViewCity.setText(f.getLocation().getCity());
        textViewMessage.setText(f.getWeather().get(0).getDescription());
        materialEditTextHumidity.setText("" + f.getHumidity());
        materialEditTextTemp.setText("" + f.getTemp());
        materialEditTextWindspeed.setText("" + f.getWindSpeed());
        materialEditTextTempMin.setText("" + f.getTemp_min());
        materialEditTextTempMax.setText("" + f.getTemp_max());
        materialEditTextPressure.setText("" + f.getPressure());

        materialEditTextHumidity.setVisibility(getVisible(model.getSettings().isHumidity()));
        materialEditTextTemp.setVisibility(getVisible(model.getSettings().isWindspeed()));
        materialEditTextWindspeed.setVisibility(getVisible(model.getSettings().isWindspeed()));
        materialEditTextTempMin.setVisibility(getVisible(model.getSettings().isTempMin()));
        materialEditTextTempMax.setVisibility(getVisible(model.getSettings().isTempMax()));
        materialEditTextPressure.setVisibility(getVisible(model.getSettings().isPressure()));

    }

    public int getVisible(boolean b) {
        if (b) {
            return View.VISIBLE;
        }
        else return View.GONE;
    }

    @AfterViews
    public void init(){

    }
}
