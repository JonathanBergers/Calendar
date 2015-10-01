package nl.saxion.calendar.model;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.google.gson.JsonObject;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
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

    ForecastSettings setting = new ForecastSettings(true,true,true,true,true,true,true);

    private List<Location> locations = new ArrayList<>();
    private Map<String, Forecast> locationForecasts = new TreeMap<>();

    public List<Location> getLocations() {
        return locations;
    }

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




        UInotifiyObservers();
    }


    @UiThread
    public void UInotifiyObservers(){

        setChanged();
        notifyObservers();

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

    public ForecastSettings getSettings() {
        return setting;
    }


    @Background
    public void searchCity(String city, Context c){
        JsonObject result = openweatherClient.recieveCurrentWeather(city);
        if(result!=null){

            JsonObject coord = result.getAsJsonObject("coord");
            double resultLon = coord.get("lon").getAsDouble();
            double resultLat = coord.get("lat").getAsDouble();
            String resultCity = result.get("name").getAsString();

            Location resultLocation = new Location(resultCity, resultLat, resultLon);

            getRightCity(city, resultLocation, c);
        } else {
            //give error
        }
        return;
    }
    @UiThread
    public void getRightCity(String searedCity, final Location resultLocation, Context c){
        if(searedCity.equalsIgnoreCase(resultLocation.getCity())){
            addLocation(resultLocation);
        } else {
            //geef gebruiker keuze
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            addLocation(resultLocation);
                            UInotifiyObservers();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            //do nothing for now
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(c);
            builder.setMessage("u zocht op: "+searedCity+"\nBedoelt u: "+resultLocation.getCity()+"?").setPositiveButton("Ja", dialogClickListener)
                    .setNegativeButton("Nee", dialogClickListener).show();
        }
        return;
    }


}



