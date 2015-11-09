package ba.bitcamp.bitNavigator.lists;

import java.util.ArrayList;
import java.util.List;

import ba.bitcamp.bitNavigator.models.Place;

/**
 * Created by semir.sahman on 22.10.15..
 */
public class PlaceList {

    private static ba.bitcamp.bitNavigator.lists.PlaceList mPlaceList;

    public static ba.bitcamp.bitNavigator.lists.PlaceList getInstance() {
        if (mPlaceList == null) {
            mPlaceList = new ba.bitcamp.bitNavigator.lists.PlaceList();
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

    public List<Place> getPlaceList(){
        return placeList;
    }

    public Place getPlace(Integer id){
        Place p;
        for(int i =0; i<mPlaceList.getSize(); i++){
            if(mPlaceList.getPlaceList().get(i).getId() == id){
                p = mPlaceList.getPlaceList().get(i);
                return p;
            }
        }
        return null;
    }
}
