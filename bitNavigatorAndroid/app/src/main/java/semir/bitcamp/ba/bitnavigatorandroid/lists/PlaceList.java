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

    private List<Place> placeList;

    public PlaceList() {
        placeList = new ArrayList<>();
    }

    public void addPlace(Editable name, Editable address) {
        placeList.add(new Place(name.toString(), address.toString()));
    }

    public Place getPlace(int index) {
        return placeList.get(index);
    }

    public int getPlaceListSize() {
        return placeList.size();
    }

    public void removePlace(UUID id) {
        Iterator<Place> it = placeList.iterator();
        while (it.hasNext()) {
            if(it.next().getId().equals(id)) {
                it.remove();
                return;
            }
        }
    }

}
