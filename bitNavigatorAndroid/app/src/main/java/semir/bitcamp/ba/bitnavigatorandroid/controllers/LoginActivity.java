package semir.bitcamp.ba.bitnavigatorandroid.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import semir.bitcamp.ba.bitnavigatorandroid.R;
import semir.bitcamp.ba.bitnavigatorandroid.lists.PlaceList;
import semir.bitcamp.ba.bitnavigatorandroid.models.Place;
import semir.bitcamp.ba.bitnavigatorandroid.service.ServiceRequest;


public class LoginActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting default screen to login.xml
        setContentView(R.layout.activity_login);

        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
        TextView homeScreen = (TextView) findViewById(R.id.link_to_homepage);

        ServiceRequest.get(getString(R.string.service_all_places), getPlaces());

        // Listening to register new account link
        registerScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });

        // Listening to Map Screen link
        homeScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Closing registration screen
                // Switching to Login Screen/closing register screen
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(i);

                finish();
            }
        });
    }

    private Callback getPlaces() {
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //makeToast(R.string.toast_try_again);
                Log.d("dibag", "hdashgdkjsa87998987");
            }

            @Override
            public void onResponse(Response response) throws IOException {

                try {
                    String responseJSON= response.body().string();
                    JSONArray array = new JSONArray(responseJSON);
                    for (int i = 0; i < array.length(); i++) {

                        JSONObject postObj = array.getJSONObject(i);
                        Integer id = postObj.getInt("id");
                        String name = postObj.getString("title");
                        String address = postObj.getString("address");
                        Double longitude = postObj.getDouble("longitude");
                        Double latitude = postObj.getDouble("latitude");

                        Place place = new Place(id, name, address, longitude, latitude);
                        if (!PlaceList.getInstance().getPlaceList().contains(place)) {
                            PlaceList.getInstance().add(place);
                        }
                        Log.d("dibag", address);

                    }
                } catch (JSONException e) {
                    //makeToast(R.string.toast_try_again);
                    e.printStackTrace();
                }
            }
        };
    }

}
