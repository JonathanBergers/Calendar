package nl.saxion.calendar.model;

/**
 * Created by falco on 18-9-15.
 */
public class Weather {
    private String main;
    private String description;

    public Weather(String main, String description){
        this.main = main;
        this.description = description;
    }

    @Override
    public String toString() {
        return "main = "+main+"\ndescription = "+description+"\n";
    }
}
