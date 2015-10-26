package nl.saxion.calendar.model;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.gson.JsonObject;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import lombok.Getter;
import lombok.Setter;
import nl.saxion.calendar.client.GoogleCalendarClient;
import nl.saxion.calendar.client.OpenweatherClient;
import nl.saxion.calendar.utils.Updatable;

/**
 * Created by jonathan on 24-9-15.
 */
@EBean(scope = EBean.Scope.Singleton)
public class Model{



    @RestService
    OpenweatherClient openweatherClient;

    @Bean
    JsonConverterWeather forecastConverter;

    @Bean
    GoogleCalendarClient calendarClient;

    private @Getter @Setter ForecastSettings viewSettings = new ForecastSettings(true,true,true,true,true,true,true);
    private @Getter @Setter GoogleAccountCredential credentials;
    private @Getter @Setter List<EventWrapper> events  = new ArrayList<EventWrapper>();
    private Set<Location> locations = new HashSet<>();
    private Map<String, Forecast> locationForecasts = new TreeMap<>();
    private @Getter @Setter Location standardLocation;
    private @Getter @Setter Location currentLocation;
    private @Getter @Setter CalendarListEntry weatherAgenda = null;


    public List<Location> getLocations() {
        List<Location> locationsCopy = new ArrayList<>();
        locationsCopy.addAll(locations);

        Location currentLocation = getCurrentLocation();
        Location standardLocation = getStandardLocation();

        if(currentLocation!= null && !locationsCopy.contains(currentLocation)) locationsCopy.add(currentLocation);
        if(standardLocation != null && !locationsCopy.contains(standardLocation)) locationsCopy.add(standardLocation);

        return locationsCopy;
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




    @Background
    public void retrieveEvents(Updatable updatableCallBack){
        calendarClient.retrieveEvents(updatableCallBack);

    }



    /**retrieves the forecasts for the current locations
     *
     */
    @Background
    public void retrieveForecasts(Updatable<List<Forecast>> callBack){


        HashSet<Location> locationsCopy = new HashSet<>();
        locationsCopy.addAll(getLocations());
        if(getCurrentLocation()!= null) locationsCopy.add(getCurrentLocation());
        if(getStandardLocation()!= null) locationsCopy.add(getStandardLocation());

        for(Location l: locationsCopy){
            Log.d("MODEL", l.toString());
            locationForecasts.put(l.getCity(), forecastConverter.fromJsonObject(openweatherClient.recieveCurrentWeather(l.getCity())));


        }


        invokeCallBack(callBack, getLocationForecasts());



    }
    /**retrieves the forecasts for the current locations
     *
     */
    @Background
    public void retrieveForecastLocation(Updatable<Forecast> callBack, Location l){

        Forecast f = forecastConverter.fromJsonObject(openweatherClient.recieveCurrentWeather(l.getCity()));
        invokeCallBack(callBack, f);



    }
    /**retrieves the forecasts for the current locations
     *
     */
    @Background
    public void retrieveForecastLocationLatLong(Updatable<Forecast> callBack, Location l){

        Forecast f = forecastConverter.fromJsonObject(openweatherClient.recieveCurrentWeather(l.getLat(), l.getLon()));
        invokeCallBack(callBack, f);



    }


    @Background
    public void retrieveForecastsZip(Updatable<List<Forecast>> callBack, String... zip){


        List<Forecast> forecasts = new ArrayList<>();


        for(String l: zip){

            Log.d("MODEL", l.toString());
            forecasts.add(forecastConverter.fromJsonObject(openweatherClient.recieveCurrentWeatherZIP(l)));


        }


        invokeCallBack(callBack, forecasts);



    }


    @UiThread
    protected  <T> void invokeCallBack(Updatable<T> callBack, T input) {
        callBack.update(input);
    }





    @Background
    public void searchCity(String city, Context c){
        JsonObject result = openweatherClient.recieveCurrentWeather(city);
        if(result!=null){


            JsonObject coord = result.getAsJsonObject("coord");

            if(coord!=null) {
                double resultLon = coord.get("lon").getAsDouble();
                double resultLat = coord.get("lat").getAsDouble();
                String resultCity = result.get("name").getAsString();

                Location resultLocation = new Location(resultCity, resultLat, resultLon);

                getRightCity(city, resultLocation, c);

            }else{
                // give error
            }
        } else {
            //give error
        }

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
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            //do nothing for now
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(c);
            builder.setMessage("u zocht op: "+ searedCity+"\nBedoelt u: "+resultLocation.getCity()+"?").setPositiveButton("Ja", dialogClickListener)
                    .setNegativeButton("Nee", dialogClickListener).show();
        }
        return;
    }

    public void selectWeatherAgenda(Updatable callback){
        // get all agenda's
        calendarClient.getAgendas(callback);
        // choose agenda

        // set agenda
        System.out.println("SELECT AGENDA");
    }
    public void makeWeatherAgenda(Calendar agenda){
        // make new agenda
        calendarClient.makeAgenda(agenda);
        // set agenda
        System.out.println("MAKE AGENDA");
    }

    public CalendarListEntry getWeatherAgenda() {
        return weatherAgenda;
    }

    public void setWeatherAgenda(CalendarListEntry weatherAgenda) {
        this.weatherAgenda = weatherAgenda;
    }

    /**
     *
     * @return if the weather is exported
     */


    public boolean exportWeatherToAgenda(){


        if(standardLocation == null){
            return false;
        }

        if(weatherAgenda==null){
            return false;
        }
        getAndExportForecasts();
        return true;
    }
    @Background
    public void getAndExportForecasts(){

        calendarClient.exportForecasts(forecastConverter.getForecastsFromJson(openweatherClient.recieveDailyForcasts(standardLocation.getLat(), standardLocation.getLon(), 10)));

    }
}



