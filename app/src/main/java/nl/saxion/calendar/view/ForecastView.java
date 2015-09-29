package nl.saxion.calendar.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import nl.saxion.calendar.R;
import nl.saxion.calendar.model.Forecast;

/**
 * Created by jonathan on 24-9-15.
 */
@EViewGroup
public class ForecastView extends LinearLayout{


    @ViewById
    TextView textViewCity, textViewMessage;

    @ViewById
    MaterialEditText materialEditTextTemp, materialEditTextHumidity, materialEditTextWindspeed, materialEditTextPressure, materialEditTextTempMin, materialEditTextTempMax;



    public ForecastView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.weather_view, this);
    }




    public void setData(Forecast f){

        textViewCity.setText(f.getLocation().getCity());
        textViewMessage.setText(f.getWeather().get(0).getDescription());
        materialEditTextHumidity.setText(""+ f.getHumidity());
        materialEditTextTemp.setText("" + f.getTemp());
        materialEditTextWindspeed.setText("" + f.getWindSpeed());
        materialEditTextTempMin.setText(""+ f.getTemp_min());
        materialEditTextTempMax.setText(""+ f.getTemp_max());
        materialEditTextPressure.setText(""+ f.getPressure());



    }



    @AfterViews
    public void init(){

    }
}
