package nl.saxion.calendar.model;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;

/**
 * Created by falco on 17-9-15.
 */

@EBean(scope = EBean.Scope.Singleton)
public class JsonConverterWeather {


    /**
     * Returns a forecast object given a jsonobject from the client
     *
     * @param jsonObject
     * @return
     */
    public Forecast fromJsonObject(JsonObject jsonObject) {

        assert jsonObject != null : "jsonobject null";


        ArrayList<Weather> weatherArray = new ArrayList<>();
        double temp;
        int pressure;
        int humidity;
        double temp_min;
        double temp_max;
        double windSpeed;
        String iconId;
        Location location;


        JsonArray weatherMessageArray = jsonObject.get("weather").getAsJsonArray();

        assert weatherMessageArray != null : "no weather message in json";


        for (JsonElement j : weatherMessageArray) {

            JsonObject jo = j.getAsJsonObject();
            assert jo != null : "weather json object null";

            String main = jo.get("main").getAsString();
            String description = jo.get("description").getAsString();

            iconId = jo.get("icon").getAsString();

            assert main != null && !main.isEmpty() : "main string null or empty";
            assert description != null && !description.isEmpty() : "description string null or empty";

            weatherArray.add(new Weather(main, description, iconId));

        }
        // get the main weather

        JsonObject main = jsonObject.get("main").getAsJsonObject();
        assert main != null : "main json object null";
        temp = main.get("temp").getAsDouble();
        pressure = main.get("pressure").getAsInt();
        humidity = main.get("humidity").getAsInt();
        temp_min = main.get("temp_min").getAsDouble();
        temp_max = main.get("temp_max").getAsDouble();


        //get the windspeed
        JsonObject wind = jsonObject.get("wind").getAsJsonObject();
        windSpeed = wind.get("speed").getAsDouble();

        //get the location

        JsonObject coord = jsonObject.getAsJsonObject("coord");
        double lon = coord.get("lon").getAsDouble();
        double lat = coord.get("lat").getAsDouble();
        String city = jsonObject.get("name").getAsString();

        location = new Location(city, lat, lon);


        return new Forecast(weatherArray, temp, pressure, humidity, temp_min, temp_max, windSpeed, location);


    }

    public ArrayList<Forecast> getForecastsFromJson(JsonObject jsonObject) {

        ArrayList<Forecast> forcasts = new ArrayList<>();


        JsonObject coord = jsonObject.getAsJsonObject("city").getAsJsonObject("coord");
        double lon = coord.get("lon").getAsDouble();
        double lat = coord.get("lat").getAsDouble();
        String city = jsonObject.getAsJsonObject("city").get("name").getAsString();

        Location location = new Location(city, lat, lon);


        JsonArray forecastsJsonArray = jsonObject.get("list").getAsJsonArray();


        Log.d("FORECASTS", "" + lon);
        Log.d("FORECASTS", "" + lat);
        Log.d("FORECASTS", "" + city);
        for (JsonElement o : forecastsJsonArray) {
            forcasts.add(getForecastFromJson(o.getAsJsonObject(), location));
        }

        return forcasts;

    }

    private Forecast getForecastFromJson(JsonObject jsonObject, Location location) {

        ArrayList<Weather> weatherArray = new ArrayList<>();
        double temp;
        int pressure;
        int humidity;
        double temp_min;
        double temp_max;
        double windSpeed;
        String iconId;

        //set temperatures
        JsonObject jsonTemp = jsonObject.get("temp").getAsJsonObject();

        temp = jsonTemp.get("day").getAsDouble();
        temp_min = jsonTemp.get("min").getAsDouble();
        temp_max = jsonTemp.get("max").getAsDouble();

        pressure = jsonObject.get("pressure").getAsInt();
        humidity = jsonObject.get("humidity").getAsInt();
        windSpeed = jsonObject.get("speed").getAsDouble();

        JsonArray weatherMessageArray = jsonObject.get("weather").getAsJsonArray();

        for (JsonElement j : weatherMessageArray) {

            JsonObject jo = j.getAsJsonObject();

            String main = jo.get("main").getAsString();
            String description = jo.get("description").getAsString();

            iconId = jo.get("icon").getAsString();

            weatherArray.add(new Weather(main, description, iconId));

        }

        return new Forecast(weatherArray, temp, pressure, humidity, temp_min, temp_max, windSpeed, location);


    }


}
