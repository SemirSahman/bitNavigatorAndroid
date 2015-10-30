package ba.bitcamp.bitNavigator.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ba.bitcamp.bitNavigator.bitnavigator.R;
import ba.bitcamp.bitNavigator.controllers.*;
import ba.bitcamp.bitNavigator.controllers.MapsActivity;
import ba.bitcamp.bitNavigator.lists.PlaceList;
import ba.bitcamp.bitNavigator.lists.WorkingHoursList;
import ba.bitcamp.bitNavigator.models.Place;
import ba.bitcamp.bitNavigator.models.WorkingHours;
import ba.bitcamp.bitNavigator.service.ServiceRequest;

/**
 * Created by Sehic on 26.10.2015.
 */
public class SplashScreenActivity extends Activity{

    private static int SPLASH_TIME_OUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        String url = getString(R.string.service_all_places);
        ServiceRequest.get(url, getPlaces());

        String url1 = getString(R.string.service_all_hours);
        ServiceRequest.get(url1, getHours());

        new Handler().postDelayed(new Runnable() {
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreenActivity.this, MapsActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
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
                        String description = postObj.getString("description");
                        String service = postObj.getString("service");
                        String image = postObj.getString("image");
                        Integer user_id = postObj.getInt("user_id");
                        Place place = new Place(id, name, address, longitude, latitude, description, service, image, user_id);
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

    private Callback getHours() {
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("Uso", "aaaaaaa");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    String responseJSON= response.body().string();
                    JSONArray array = new JSONArray(responseJSON);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject hourObj = array.getJSONObject(i);
                        Integer id = hourObj.getInt("id");
                        Integer place_id = hourObj.getInt("place_id");
                        Integer open1 = hourObj.getInt("open1");
                        Integer close1 = hourObj.getInt("close1");
                        Integer open2 = hourObj.getInt("open2");
                         Integer close2 = hourObj.getInt("close2");
                         Integer open3 = hourObj.getInt("open3");
                         Integer close3 = hourObj.getInt("close3");
                         Integer open4 = hourObj.getInt("open4");
                         Integer close4 = hourObj.getInt("close4");
                         Integer open5 = hourObj.getInt("open5");
                         Integer close5 = hourObj.getInt("close5");
                         Integer open6 = hourObj.getInt("open6");
                         Integer close6 = hourObj.getInt("close6");
                         Integer open7 = hourObj.getInt("open7");
                         Integer close7 = hourObj.getInt("close7");
                        Log.e("close7", close7 + " ");
                        WorkingHours hours = new WorkingHours(id, place_id, open1, close1, open2, close2, open3, close3, open4, close4, open5, close5, open6, close6, open7, close7);
                            WorkingHoursList.getInstance().add(hours);
                    }
                } catch (JSONException e) {
                    //makeToast(R.string.toast_try_again);
                    e.printStackTrace();
                }
            }
        };
    }

}
