package nl.saxion.calendar.model;

<<<<<<< HEAD
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;

import nl.saxion.calendar.client.OpenweatherClient;

/**
 * Created by jonathan on 24-9-15.
 */
@EBean(scope = EBean.Scope.Singleton)
public class Model extends Observable{



    @RestService
    OpenweatherClient openweatherClient;

    @Bean
    JsonConverterWeather forecastConverter;



    private Map<String, Forecast> locationForecasts = new TreeMap<>();


    public List<Forecast> getLocationForecasts(){

        List<Forecast> l = new ArrayList<>();
        for(String s: locationForecasts.keySet()){
            l.add(locationForecasts.get(s));
        }
        return l;

    }


    @Background
    public void retrieveForecasts(String... city){


        for(String s: city){

            locationForecasts.put(s, forecastConverter.fromJsonObject(openweatherClient.recieveCurrentWeather(s)));


        }


    }







}
=======
import org.androidannotations.annotations.EBean;

/**
 * Created by Alec on 21-9-2015.
 */
@EBean (scope = EBean.Scope.Singleton)
public class Model {

    private Double latitude;
    private Double longitude;

    public void setLatitude(Double latitude) {

        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {

        this.longitude = longitude;
    }

    public Double getLatitude() {

        return latitude;
    }

    public Double getLongitude() {

        return longitude;
    }
}



>>>>>>> origin/Alex2
