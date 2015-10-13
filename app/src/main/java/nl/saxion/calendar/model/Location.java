package nl.saxion.calendar.model;


import lombok.Getter;

/**
 * Created by falco on 28-9-15.
 */
public class Location {

    private @Getter double lat;
    private @Getter double lon;
    private @Getter String city;

    public Location(String city, double lat, double lon) {
        assert city != null : "city can not be null";
        assert !city.isEmpty() : "city can not be empty";
        this.city = city;
        this.lat = lat;
        this.lon = lon;
    }






}
