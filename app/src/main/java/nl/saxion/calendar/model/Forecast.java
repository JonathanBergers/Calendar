package nl.saxion.calendar.model;

import java.util.ArrayList;

/**
 * Created by falco on 17-9-15.
 */
public class Forecast {

    //difference between Kelvin and Celsius
    private final double DIFFERENCE = 273.15;
    private ArrayList<Weather> weather;
    private double temp;
    private int pressure;
    private int humidity;
    private double temp_min;
    private double temp_max;
    private double windSpeed;
    private Location location;


    public Forecast(ArrayList<Weather> weather, double temp, int pressure, int humidity, double temp_min, double temp_max, double windSpeed, Location location) {

        assert weather != null : "weather can not be null";
        assert location != null : "location can not be null";


        this.humidity = humidity;
        this.location = location;
        this.pressure = pressure;
        this.temp = makeCelsius(temp);
        this.temp_max = makeCelsius(temp_max);
        this.temp_min = makeCelsius(temp_min);
        this.windSpeed = windSpeed;
        this.weather = weather;


    }

    //sets temp from Kelvin to Celsius and rounds it off
    private double makeCelsius(double d) {
        double value = d - DIFFERENCE;
        double celsius = Math.round(value * 100) / 100;

        return celsius;
    }

    @Override
    public String toString() {
        String s = "The forecast is:\n";
        for (Weather w : weather) {
            s += w.toString();
        }
        s += "\n";
        s += "temperature = " + temp + "\n";
        s += "pressure = " + pressure + "\n";
        s += "humidity = " + humidity + "\n";
        s += "minimal temperature = " + temp_min + "\n";
        s += "maximal temperature = " + temp_max + "\n";
        s += "windspeed = " + windSpeed + "\n";
        s += "location = " + location;


        return s;
    }

    public int getHumidity() {
        return humidity;
    }

    public Location getLocation() {
        return location;
    }

    public int getPressure() {
        return pressure;
    }

    public double getTemp() {
        return temp;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

}
