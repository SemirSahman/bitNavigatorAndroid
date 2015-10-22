package semir.bitcamp.ba.bitnavigatorandroid.models;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by semir.sahman on 22.10.15..
 */
public class Place {

    private int id;
    private String title;
    private String address;
    private Double longitude;
    private Double latitude;
    private Calendar dateAdded;

    public Place(int id, String title, String address, Double longitude, Double latitude) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
