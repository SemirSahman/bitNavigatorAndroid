package ba.bitcamp.bitNavigator.controllers;

        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.squareup.okhttp.Callback;
        import com.squareup.okhttp.Request;
        import com.squareup.okhttp.Response;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.IOException;

        import ba.bitcamp.bitNavigator.bitnavigator.R;
        import ba.bitcamp.bitNavigator.lists.ReservationList;
        import ba.bitcamp.bitNavigator.lists.ReservationOnMyPlacesList;
        import ba.bitcamp.bitNavigator.models.Reservation;
        import ba.bitcamp.bitNavigator.models.ReservationOnMyPlaces;
        import ba.bitcamp.bitNavigator.service.ServiceRequest;

/**
 * Created by Sehic on 3.11.2015.
 */
public class ReservationListActivity extends Activity{

    private Button mMyReservations;
    private Button mReservationsOnMyPlaces;
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservations);

        final SharedPreferences preferences = getSharedPreferences("SESSION", Context.MODE_PRIVATE);
        String email = preferences.getString("email","");
        JSONObject json = new JSONObject();
        try {
            json.put("user_email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getString(R.string.service_user_reservations);
        ServiceRequest.post(url, json.toString(), getReservations());

        JSONObject json1 = new JSONObject();
        try {
            json1.put("user_email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url1 = getString(R.string.service_user_reservations_on_my_places);
        ServiceRequest.post(url1, json1.toString(), getReservationsOnMyPlaces());

        mMyReservations = (Button) findViewById(R.id.myReservations);
        mReservationsOnMyPlaces = (Button) findViewById(R.id.reservationsOnMyPlaces);
        mText = (TextView) findViewById(R.id.textInfo);

        SharedPreferences sharedpreferences = getSharedPreferences("SESSION", Context.MODE_PRIVATE);
        if(sharedpreferences.contains("email")){
           mMyReservations.setVisibility(View.VISIBLE);
           mReservationsOnMyPlaces.setVisibility(View.VISIBLE);
        }else {
            mText.setVisibility(View.VISIBLE);
        }

        mMyReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        MyReservationsActivity.class);
                startActivity(i);
                finish();
            }
        });

        mReservationsOnMyPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        ReservationOnMyPlacesActivity.class);
                startActivity(i);
                finish();
            }
        });


        Button mLoginButton = (Button) findViewById(R.id.btnProfile);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });


        Button mReservationButton = (Button) findViewById(R.id.btnReservations);
        mReservationButton.setOnClickListener(new View.OnClickListener() {
                                                  public void onClick(View v) {
                                                      Intent i = new Intent(getApplicationContext(), ReservationListActivity.class);
                                                      startActivity(i);
                                                  }
                                              }
        );

        Button mSearchButton = (Button) findViewById(R.id.btnSearch);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
                                             public void onClick(View v) {
                                                 Intent i = new Intent(getApplicationContext(), SearchActivity.class);
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

    private Callback getReservations() {
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //makeToast(R.string.toast_try_again);
                Log.e("dibag", "hdashgdkjsa87998987111111111");
                e.getMessage();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    String responseJSON= response.body().string();
                    JSONArray array = new JSONArray(responseJSON);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject reservObj = array.getJSONObject(i);
                        Integer id = reservObj.getInt("id");
                        String place_title = reservObj.getString("place_title");
                        String status = reservObj.getString("status");
                        String date = reservObj.getString("date");
                        Reservation r = new Reservation(id, place_title, status, date);
                        if (!ReservationList.getInstance().getReservationList().contains(r)) {
                            ReservationList.getInstance().add(r);
                        }
                    }
                } catch (JSONException e) {
                    //makeToast(R.string.toast_try_again);
                    Log.e("dibag", "hdashgdkjsa87998987");
                    e.printStackTrace();
                }
            }
        };
    }

    private Callback getReservationsOnMyPlaces() {
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //makeToast(R.string.toast_try_again);
                Log.e("dibag", "hdashgdkjsa87998987111111111");
                e.getMessage();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    String responseJSON= response.body().string();
                    JSONArray array = new JSONArray(responseJSON);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject reservObj = array.getJSONObject(i);
                        Integer id = reservObj.getInt("id");
                        String place_title = reservObj.getString("place_title");
                        String status = reservObj.getString("status");
                        String date = reservObj.getString("date");
                        ReservationOnMyPlaces r = new ReservationOnMyPlaces(id, place_title, status, date);
                        if (!ReservationOnMyPlacesList.getInstance().getReservationList().contains(r)) {
                            ReservationOnMyPlacesList.getInstance().add(r);
                        }
                    }
                } catch (JSONException e) {
                    //makeToast(R.string.toast_try_again);
                    Log.e("dibag", "hdashgdkjsa87998987");
                    e.printStackTrace();
                }
            }
        };
    }

}