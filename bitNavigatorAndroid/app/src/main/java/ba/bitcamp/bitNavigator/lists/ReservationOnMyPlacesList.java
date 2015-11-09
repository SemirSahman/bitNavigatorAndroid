package ba.bitcamp.bitNavigator.lists;

import java.util.ArrayList;
import java.util.List;

import ba.bitcamp.bitNavigator.models.ReservationOnMyPlaces;


/**
 * Created by Sehic on 3.11.2015.
 */
public class ReservationOnMyPlacesList {

    private static ba.bitcamp.bitNavigator.lists.ReservationOnMyPlacesList mReservationList;

    public static ba.bitcamp.bitNavigator.lists.ReservationOnMyPlacesList getInstance() {
        if (mReservationList == null) {
            mReservationList = new ba.bitcamp.bitNavigator.lists.ReservationOnMyPlacesList();
        }
        return mReservationList;
    }

    private List<ReservationOnMyPlaces> reservationList;

    private ReservationOnMyPlacesList() {
        reservationList = new ArrayList<>();
    }

    public void add(ReservationOnMyPlaces reservation) {
        reservationList.add(reservation);
    }

    public ReservationOnMyPlaces get(int index) {
        return reservationList.get(index);
    }

    public int getSize() {
        return reservationList.size();
    }

    public void remove(ReservationOnMyPlaces reservation) {
        reservationList.remove(reservation);
    }

    public List<ReservationOnMyPlaces> getReservationList(){
        return reservationList;
    }

}
