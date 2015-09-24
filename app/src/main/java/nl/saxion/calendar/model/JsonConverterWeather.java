package nl.saxion.calendar.model;

import android.util.Log;

import com.google.gson.JsonElement;

import org.androidannotations.annotations.EBean;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by falco on 17-9-15.
 */

@EBean(scope = EBean.Scope.Singleton)
public class JsonConverterWeather {

    /**
     *
     * @param jsonElement the Json to read
     * @return The Forcast from the Json, or null if it can not be read.
     */
    public Forecast getForcastfromJson(JsonElement jsonElement){


        String json = jsonElement.toString();
        assert json != null : "Json can not be null";
        assert !json.isEmpty() : "Json can not be empty";

        //returns if the String is null of empty
        if(json == null || json.isEmpty()){
            return null;
        }

        try {

            JSONObject jsonResponse = new JSONObject(json);
            Log.d("Json", jsonResponse.toString());

            //init all variables
            ArrayList<Weather> weatherArray = new ArrayList<>();
            double temp;
            int pressure;
            int humidity;
            double temp_min;
            double temp_max;
            double windSpeed;
            String location;

            JSONArray weatherArrayJson = new JSONArray(jsonResponse.get("weather").toString());


            //loop all the weathers and add them to the array
            for(int i=0; i<weatherArrayJson.length();i++){
                // get the JsonObject
                JSONObject weatherJson = weatherArrayJson.getJSONObject(i);

                //get the values
                String main = weatherJson.getString("main");
                String description = weatherJson.getString("description");

                if(main.isEmpty() || description.isEmpty()){
                    return null;
                }

                //add the weather to the Array
                weatherArray.add(new Weather(main, description));


            }
            // get the main weather
            JSONObject main = jsonResponse.getJSONObject("main");
            temp = main.getDouble("temp");
            pressure = main.getInt("pressure");
            humidity = main.getInt("humidity");
            temp_min = main.getDouble("temp_min");
            temp_max = main.getDouble("temp_max");

            //get the windspeed
            JSONObject wind = jsonResponse.getJSONObject("wind");
            windSpeed = wind.getDouble("speed");

            //get the location

            location = jsonResponse.getString("name");
            if(location.isEmpty()){
                return null;
            }

            return new Forecast(weatherArray, temp, pressure, humidity, temp_min, temp_max, windSpeed, location);



        } catch (Throwable t) {
            Log.e("Json", "Could not parse malformed JSON: \"" + json + "\"");
            return null;
        }

    }


}
