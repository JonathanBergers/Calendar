package nl.saxion.calendar.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by falco on 17-9-15.
 */

public class JsonConverterWeather {

    public Forcast getForcastfromJson(String json){
        try {

            JSONObject jsonResponse = new JSONObject(json);
            Log.d("My App", jsonResponse.toString());

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


            return new Forcast(weatherArray, temp, pressure, humidity, temp_min, temp_max, windSpeed, location);



        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
        }
        return null;
    }


}
