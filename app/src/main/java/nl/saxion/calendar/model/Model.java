package nl.saxion.calendar.model;

import org.androidannotations.annotations.EBean;

/**
 * Created by Alec on 21-9-2015.
 */
@EBean (scope = EBean.Scope.Singleton)
public class Model {

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



