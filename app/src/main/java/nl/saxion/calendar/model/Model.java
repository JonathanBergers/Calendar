package nl.saxion.calendar.model;

import com.google.gson.JsonElement;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.rest.RestService;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import nl.saxion.calendar.client.OpenweatherClient;

/**
 * Created by jonathan on 24-9-15.
 */
@EBean
public class Model {



    @RestService
    OpenweatherClient openweatherClient;

    @Bean
    JsonConverterWeather forecastConverter;



    private Map<String, Forecast> locationForecasts = new TreeMap<>();







}
