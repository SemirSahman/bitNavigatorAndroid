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
    private String image;
    private Integer user_id;
    private Double rating;
    private Boolean isReservable;

    public Place(int id, String title, String address, Double longitude, Double latitude, String description, String service, String image, Integer user_id, Double rating, Boolean isReservable) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
        this.service = service;
        this.image = image;
        this.user_id = user_id;
        this.rating = rating;
        this.isReservable = isReservable;
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

    public Integer getUser_id() {
        return user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Boolean getIsReservable() {
        return isReservable;
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
