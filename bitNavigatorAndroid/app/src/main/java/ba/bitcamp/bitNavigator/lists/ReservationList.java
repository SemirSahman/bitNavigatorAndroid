package ba.bitcamp.bitNavigator.lists;


import java.util.ArrayList;
import java.util.List;

import ba.bitcamp.bitNavigator.models.Reservation;

/**
 * Created by hajrudin.sehic on 30/10/15.
 */
public class ReservationList{

    private static ba.bitcamp.bitNavigator.lists.ReservationList mReservationList;

    public static ba.bitcamp.bitNavigator.lists.ReservationList getInstance() {
        if (mReservationList == null) {
            mReservationList = new ba.bitcamp.bitNavigator.lists.ReservationList();
        }
        return mReservationList;
    }

    private List<Reservation> reservationList;

    private ReservationList() {
        reservationList = new ArrayList<>();
    }

    public void add(Reservation reservation) {
        reservationList.add(reservation);
    }

    public Reservation get(int index) {
        return reservationList.get(index);
    }

    public int getSize() {
        return reservationList.size();
    }

    public void remove(Reservation reservation) {
        reservationList.remove(reservation);
    }

    public List<Reservation> getReservationList(){
        return reservationList;
    }

    public Reservation getReservation(Integer id){
        Reservation r;
        for(int i =0; i<mReservationList.getSize(); i++){
            if(mReservationList.getReservationList().get(i).getId() == id){
                r = mReservationList.getReservationList().get(i);
                return r;
            }
        }
        return null;
    }

}
