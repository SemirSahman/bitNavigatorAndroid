package semir.bitcamp.ba.bitnavigatorandroid.models;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by semir.sahman on 22.10.15..
 */
public class Place {

    private UUID id;
    private String name;
    private String address;
    private Date dateAdded;

    public Place(String name, String address){
        id = UUID.randomUUID();
        this.name = name;
        this.address = address;
        dateAdded = Calendar.getInstance().getTime();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public UUID getId() {
        return id;
    }
}
