package ba.bitcamp.bitNavigator.controllers;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ba.bitcamp.bitNavigator.bitnavigator.R;
import ba.bitcamp.bitNavigator.models.Place;

/**
 * Created by ognje on 28-Oct-15.
 */
public class LiveSightInfo extends Fragment {

    private Place mPlace;

    private TextView mTitle;

    public void setPlace(Place mPlace) {
        this.mPlace = mPlace;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mTitle = (TextView) getView().findViewById(R.id.livesight_info_text);
        mTitle.setText(mPlace.getTitle());
        Log.d("dibag", "kreiro");
        return inflater.inflate(R.layout.livesight_info_view, null);
    }
}
