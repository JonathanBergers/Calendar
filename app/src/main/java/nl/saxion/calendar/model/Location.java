package nl.saxion.calendar.model;


import lombok.Getter;

/**
 * Created by falco on 28-9-15.
 */
public class Location {

    private
    @Getter
    double lat;
    private
    @Getter
    double lon;
    private
    @Getter
    String city;

    public Location(String city, double lat, double lon) {
        assert city != null : "city can not be null";
        assert !city.isEmpty() : "city can not be empty";
        this.city = city;
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "City: " + getCity() + " | lon: " + getLon() + " | lat: " + getLat();
    }

    @Override
    public int hashCode() {
        return getCity().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        return !(getCity() != null ? !getCity().equals(location.getCity()) : location.getCity() != null);

    }
}
