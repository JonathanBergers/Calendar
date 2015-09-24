package nl.saxion.calendar.model;

/**
 * Created by jonathan on 24-9-15.
 */
public class ForecastSettings {



    private boolean temp, tempMin, tempMax, pressure, humidity, windspeed, location;


    public ForecastSettings(boolean temp, boolean tempMin, boolean tempMax, boolean pressure, boolean humidity, boolean windspeed, boolean location) {
        this.temp = temp;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windspeed = windspeed;
        this.location = location;
    }


}
