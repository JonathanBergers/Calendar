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

    public ForecastSettings() {

    }

    public boolean isTemp() {
        return temp;
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

    public boolean isLocation() {
        return location;
    }

    public void setTemp(boolean temp) {
        this.temp = temp;
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

    public void setLocation(boolean location) {
        this.location = location;
    }
}
