package ba.bitcamp.bitNavigator.models;

import java.util.Calendar;

/**
 * Created by semir.sahman on 22.10.15..
 */
public class Place {

    private int id;
    private String title;
    private String address;
    private Double longitude;
    private Double latitude;
    private String description;
    private String service;

    public Place(int id, String title, String address, Double longitude, Double latitude, String description, String service) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
        this.service = service;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ba.bitcamp.bitNavigator.models.Place){
            ba.bitcamp.bitNavigator.models.Place p = (ba.bitcamp.bitNavigator.models.Place) o;
            return this.id == p.id;
        }
        return false;
    }
}
