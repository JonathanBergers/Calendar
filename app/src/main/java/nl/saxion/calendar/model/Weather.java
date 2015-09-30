package nl.saxion.calendar.model;

/**
 * Created by falco on 18-9-15.
 */
public class Weather {
    private String main;
    private String description;
    private String iconId;

    public Weather(String main, String description, String iconId){

        assert main != null : "main can not be null";
        assert !main.isEmpty() : "main can not be empty";
        assert description != null : "description can not be null";
        assert !description.isEmpty(): "description can not be empty";

        this.main = main;
        this.description = description;
        this.iconId = iconId;
    }

    @Override
    public String toString() {
        return "main = "+main+"\ndescription = "+description+"\n";
    }

    public String getDescription() {
        return description;
    }

    public String getMain() {
        return main;
    }

    public String getIconId() {
        return iconId;
    }
}
