package semir.bitcamp.ba.bitnavigatorandroid.lists;

import android.text.Editable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import semir.bitcamp.ba.bitnavigatorandroid.models.Place;

/**
 * Created by semir.sahman on 22.10.15..
 */
public class PlaceList {

    private static PlaceList mPlaceList;

    public static PlaceList getInstance() {
        if (mPlaceList == null) {
            mPlaceList = new PlaceList();
        }
        return mPlaceList;
    }

    private List<Place> placeList;

    private PlaceList() {
        placeList = new ArrayList<>();
    }

    public void add(Place place) {
        placeList.add(place);
    }

    public Place get(int index) {
        return placeList.get(index);
    }

    public int getSize() {
        return placeList.size();
    }

    public void remove(Place place) {
        placeList.remove(place);
    }

}
