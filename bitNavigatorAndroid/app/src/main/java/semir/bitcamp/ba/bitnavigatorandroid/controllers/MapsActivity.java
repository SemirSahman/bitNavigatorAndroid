package semir.bitcamp.ba.bitnavigatorandroid.controllers;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button mLoginButton = (Button) findViewById(R.id.btnProfile);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {
                                                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                                startActivity(i);
                                            }
                                        }
        );

        Button mRegisterButton = (Button) findViewById(R.id.btnReservations);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
                                               public void onClick(View v) {
                                                   Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                                   startActivity(i);
                                               }
                                           }
        );

        Button mSearchButton = (Button) findViewById(R.id.btnSearch);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
                                             public void onClick(View v) {
                                                 Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                                                 startActivity(i);
                                             }
                                         }
        );

        Button mMapButton = (Button) findViewById(R.id.btnMap);
        mMapButton.setOnClickListener(new View.OnClickListener() {
                                          public void onClick(View v) {
                                              Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                                              startActivity(i);
                                          }
                                      }
        );

    }

    public void drawMarkers(){
        for(Place place: PlaceList.getInstance().getPlaceList()){
            LatLng ius = new LatLng(place.getLatitude(),place.getLongitude());
            mMap.addMarker(new MarkerOptions().position(ius).title(place.getTitle()));
        }
    }





    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(PlaceList.getInstance().getPlaceList().size() == 0) {
            ServiceRequest.get(getString(R.string.service_all_places), getPlaces());
        }

        drawMarkers();
        
        // Add a marker in Sydney and move the camera

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(43.821, 18.3088)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.821, 18.3088), 13.0f));
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
