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
import org.androidannotations.annotations.rest.Get;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import lombok.Getter;
import nl.saxion.calendar.R;
import nl.saxion.calendar.model.Forecast;
import nl.saxion.calendar.model.Model;
import nl.saxion.calendar.utils.Updatable;
import nl.saxion.calendar.view.SetData;

/**
 * Created by jonathan on 24-9-15.
 */
@EViewGroup
public class ForecastView extends LinearLayout implements SetData<Forecast>, Updatable<Bitmap> {



    @ViewById
    ImageView imageViewIcon;

    @ViewById
    TextView textViewCity, textViewMessage;

    @ViewById
    MaterialEditText materialEditTextTemp, materialEditTextHumidity, materialEditTextWindspeed, materialEditTextPressure, materialEditTextTempMin, materialEditTextTempMax;

    private @Getter String bitmapUrl;

    @Bean
    Model model;


    public ForecastView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.weather_view, this);
    }




    public void setData(Forecast f){

        bitmapUrl = "http://openweathermap.org/img/w/" + f.getWeather().get(0).getIconId() + ".png";
        getBitmapFromURL(this, getBitmapUrl());
        textViewCity.setText(f.getLocation().getCity());
        textViewMessage.setText(f.getWeather().get(0).getDescription());
        materialEditTextHumidity.setText("" + f.getHumidity());
        materialEditTextTemp.setText("" + f.getTemp());
        materialEditTextWindspeed.setText("" + f.getWindSpeed());
        materialEditTextTempMin.setText("" + f.getTemp_min());
        materialEditTextTempMax.setText("" + f.getTemp_max());
        materialEditTextPressure.setText("" + f.getPressure());

        //makes attribute visible or invisible depending on whether they are
        //checked or not in settings
        materialEditTextHumidity.setVisibility(getVisible(model.getViewSettings().isHumidity()));
        materialEditTextWindspeed.setVisibility(getVisible(model.getViewSettings().isWindspeed()));
        materialEditTextTempMin.setVisibility(getVisible(model.getViewSettings().isTempMin()));
        materialEditTextTempMax.setVisibility(getVisible(model.getViewSettings().isTempMax()));
        materialEditTextPressure.setVisibility(getVisible(model.getViewSettings().isPressure()));

    }




    @Background
    public  void getBitmapFromURL(Updatable<Bitmap> callBack, String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            callBack.update(myBitmap);
        } catch (IOException e) {
            e.printStackTrace();

        }
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

    @Override
    @UiThread
    public void update(Bitmap input) {
        imageViewIcon.setImageBitmap(input);
    }
}
