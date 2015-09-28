package nl.saxion.calendar.model;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.rest.RestService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;

import nl.saxion.calendar.client.OpenweatherClient;

/**
 * Created by jonathan on 24-9-15.
 */
@EBean(scope = EBean.Scope.Singleton)
public class Model extends Observable{



    @RestService
    OpenweatherClient openweatherClient;

    @Bean
    JsonConverterWeather forecastConverter;


    private List<Location> locations = new ArrayList<>();
    private Map<String, Forecast> locationForecasts = new TreeMap<>();


    public List<Forecast> getLocationForecasts(){

        List<Forecast> l = new ArrayList<>();
        for(String s: locationForecasts.keySet()){
            l.add(locationForecasts.get(s));
        }
        return l;

    }

    public void addLocation(Location location){
        locations.add(location);
        System.out.println("Location added");
    }

    /**
     *
     * @param city the city nam eof the location
     * @return the location as an object or null if not found
     */
    public Location getLocationByCity(String city){
        for(Location l : locations){
            if(l.getCity().equalsIgnoreCase(city)){
                return l;
            }
        }
        return null;
    }


    @Background
    public void retrieveForecasts(Location... city){


        for(Location l: city){

            locationForecasts.put(l.getCity(), forecastConverter.fromJsonObject(openweatherClient.recieveCurrentWeather(l.getCity())));


        }


    }


    private Double latitude;
    private Double longitude;

    public void setLatitude(Double latitude) {

        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {

        this.longitude = longitude;
    }

    public Double getLatitude() {

        return latitude;
    }

    public Double getLongitude() {

        return longitude;
    }
}



