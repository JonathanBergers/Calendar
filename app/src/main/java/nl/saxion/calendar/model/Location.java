package nl.saxion.calendar.model;

/**
 * Created by falco on 28-9-15.
 */
public class Location {
    private double lat;
    private double lon;
    private String city;

    public Location(String city, double lat, double lon) {
        assert city != null : "city can not be null";
        assert !city.isEmpty() : "city can not be empty";
        this.city = city;
        this.lat = lat;
        this.lon = lon;
    }

    public String getCity() {
        return city;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
