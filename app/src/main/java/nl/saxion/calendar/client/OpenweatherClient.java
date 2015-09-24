package nl.saxion.calendar.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import nl.saxion.calendar.utils.Resources;

/**
 * Created by jonathan on 17-9-15.
 */
@Rest(rootUrl = Resources.OPENWEATHER_BASEURL, converters = GsonHttpMessageConverter.class)
public interface OpenweatherClient {



    @Get("weather?q={city}")
    JsonObject recieveCurrentWeather(String city);

    @Get("weather?lat={lat}&lon={lon}")
    JsonObject recieveCurrentWeather(double lat, double lon);


}
