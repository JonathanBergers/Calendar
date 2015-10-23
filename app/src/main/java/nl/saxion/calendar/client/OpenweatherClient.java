package nl.saxion.calendar.client;

import com.google.gson.JsonObject;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import nl.saxion.calendar.model.Location;
import nl.saxion.calendar.utils.Resources;

/**
 * Created by jonathan on 17-9-15.
 */
@Rest(rootUrl = Resources.OPENWEATHER_BASEURL, converters = GsonHttpMessageConverter.class)
public interface OpenweatherClient {


    @Get("weather?q={city}&APPID="+ Resources.OPENWEATHER_APP_ID)
    JsonObject recieveCurrentWeather(String city);

    @Get("weather?lat={lat}&lon={lon}&APPID="+ Resources.OPENWEATHER_APP_ID)
    JsonObject recieveCurrentWeather(double lat, double lon);


    @Get("weather?zip={zip},nl"+ Resources.OPENWEATHER_APP_ID)
    JsonObject recieveCurrentWeatherZIP(String zip);

}
