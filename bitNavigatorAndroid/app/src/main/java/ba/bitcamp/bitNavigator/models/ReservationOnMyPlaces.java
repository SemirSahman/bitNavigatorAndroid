package ba.bitcamp.bitNavigator.models;

/**
 * Created by hajrudin.sehic on 30/10/15.
 */
public class ReservationOnMyPlaces {

    private Integer id;
    private String place_title;
    private String status;
    private String date;

    public ReservationOnMyPlaces(Integer id, String place_title, String status, String date) {
        this.id = id;
        this.place_title = place_title;
        this.status = status;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlace_title() {
        return place_title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        ReservationOnMyPlaces r = (ReservationOnMyPlaces) o;
        return this.id == r.id;
    }
}
