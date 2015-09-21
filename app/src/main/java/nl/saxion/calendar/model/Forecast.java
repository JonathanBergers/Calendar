package nl.saxion.calendar.model;

import java.util.ArrayList;

/**
 * Created by falco on 17-9-15.
 */
public class Forecast {

    private ArrayList<Weather> weather;

    private double temp;
    private int pressure;
    private int humidity;
    private double temp_min;
    private double temp_max;
    private double windSpeed;
    private String location;


    public Forecast(ArrayList<Weather> weather, double temp, int pressure, int humidity, double temp_min, double temp_max, double windSpeed, String location) {
        this.humidity = humidity;
        this.location = location;
        this.pressure = pressure;
        this.temp = temp;
        this.temp_max = temp_max;
        this.temp_min = temp_min;
        this.windSpeed = windSpeed;
        this.weather = weather;

    }

    @Override
    public String toString() {
        String s = "The forcast is:\n";
        for(Weather w : weather){
            s += w.toString();
        }
        s+="\n";
        s+= "temperature = "+temp+"\n";
        s+= "pressure = "+pressure+"\n";
        s+= "humidity = "+humidity+"\n";
        s+= "minimal temperature = "+temp_min+"\n";
        s+= "maximal temperature = "+temp_max+"\n";
        s+= "windspeed = "+windSpeed+"\n";
        s+= "location = "+location;


        return s;
    }
}
