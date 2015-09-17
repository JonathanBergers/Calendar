package nl.saxion.calendar.client;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import nl.saxion.calendar.utils.Resources;

/**
 * Created by jonathan on 17-9-15.
 */
@Rest(rootUrl = Resources.OPENWEATHER_BASEURL, converters = GsonHttpMessageConverter.class)
public interface OpenweatherClient {



    @Get("q=London")
    JsonElement getWeather();



}
