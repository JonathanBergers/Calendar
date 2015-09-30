package nl.saxion.calendar.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EView;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import nl.saxion.calendar.R;
import nl.saxion.calendar.model.Forecast;
import nl.saxion.calendar.model.Model;

/**
 * Created by jonathan on 24-9-15.
 */
@EViewGroup
public class ForecastView extends LinearLayout{



    @ViewById
    ImageView imageViewIcon;

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


    public void setData(Forecast f){



        getBitmapFromURL("http://openweathermap.org/img/w/" + f.getWeather().get(0).getIconId() + ".png");
        textViewCity.setText(f.getLocation());
        textViewMessage.setText(f.getWeather().get(0).getDescription());
        materialEditTextHumidity.setText("" + f.getHumidity());
        materialEditTextTemp.setText("" + f.getTemp());
        materialEditTextWindspeed.setText("" + f.getWindSpeed());
        materialEditTextTempMin.setText("" + f.getTemp_min());
        materialEditTextTempMax.setText("" + f.getTemp_max());
        materialEditTextPressure.setText("" + f.getPressure());

        //makes attribute visible or invisible depending on whether they are
        //checked or not in settings
        materialEditTextHumidity.setVisibility(getVisible(model.getSettings().isHumidity()));
        materialEditTextTemp.setVisibility(getVisible(model.getSettings().isWindspeed()));
        materialEditTextWindspeed.setVisibility(getVisible(model.getSettings().isWindspeed()));
        materialEditTextTempMin.setVisibility(getVisible(model.getSettings().isTempMin()));
        materialEditTextTempMax.setVisibility(getVisible(model.getSettings().isTempMax()));
        materialEditTextPressure.setVisibility(getVisible(model.getSettings().isPressure()));

    }




    @Background
    public  void getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            setImage(myBitmap);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    @UiThread
    public void setImage(Bitmap b){
        imageViewIcon.setImageBitmap(b);

    }



    //if true show view else make invisible
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
