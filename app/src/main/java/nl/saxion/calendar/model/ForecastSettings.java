package nl.saxion.calendar.model;

/**
 * Created by jonathan on 24-9-15.
 */
public class ForecastSettings {



    private boolean tempMin, tempMax, pressure, humidity, windspeed;


    public ForecastSettings(boolean tempMin, boolean tempMax, boolean pressure, boolean humidity, boolean windspeed) {
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windspeed = windspeed;
    }

    public ForecastSettings() {

    }

    public boolean isTempMin() {
        return tempMin;
    }

    public boolean isTempMax() {
        return tempMax;
    }

    public boolean isPressure() {
        return pressure;
    }

    public boolean isHumidity() {
        return humidity;
    }

    public boolean isWindspeed() {
        return windspeed;
    }

    public void setTempMin(boolean tempMin) {
        this.tempMin = tempMin;
    }

    public void setTempMax(boolean tempMax) {
        this.tempMax = tempMax;
    }

    public void setPressure(boolean pressure) {
        this.pressure = pressure;
    }

    public void setHumidity(boolean humidity) {
        this.humidity = humidity;
    }

    public void setWindspeed(boolean windspeed) {
        this.windspeed = windspeed;
    }

}
