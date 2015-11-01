package ba.bitcamp.bitNavigator.controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import ba.bitcamp.bitNavigator.bitnavigator.R;
import ba.bitcamp.bitNavigator.controllers.*;
import ba.bitcamp.bitNavigator.controllers.LoginActivity;
import ba.bitcamp.bitNavigator.lists.ReservationList;
import ba.bitcamp.bitNavigator.models.Reservation;
import ba.bitcamp.bitNavigator.service.ImageHelper;
import ba.bitcamp.bitNavigator.service.ServiceRequest;

/**
 * Created by hajrudin.sehic on 27/10/15.
 */
public class ProfileActivity extends Activity{

    private ImageView mImage;
    private TextView mName;
    private TextView mSurname;
    private TextView mEmail;
    private Button mLogout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting default screen to login.xml
        setContentView(R.layout.activity_user_profile);

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        mImage = (ImageView) findViewById(R.id.imgProfilePic);
        mName = (TextView) findViewById(R.id.txtName);
        mSurname = (TextView) findViewById(R.id.txtSurname);
        mEmail = (TextView) findViewById(R.id.txtEmail);
        mLogout = (Button) findViewById(R.id.btn_sign_out);

        final SharedPreferences preferences = getSharedPreferences("SESSION", Context.MODE_PRIVATE);
        String name = preferences.getString("name","");
        String surname = preferences.getString("surname", "");
        String email = preferences.getString("email","");
        mName.setText("First name: " + name);
        mSurname.setText("Last name: " + surname);
        mEmail.setText("Email: " + email);
        mLogout.setText("Logout");
        mLogout.setVisibility(View.VISIBLE);

        JSONObject json = new JSONObject();
        try {
            json.put("user_email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getString(R.string.service_user_reservations);
        ServiceRequest.post(url, json.toString(), getReservations());

        mLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // Setting Dialog Title
                alertDialog.setTitle("Logout");

                // Setting Dialog Message
                alertDialog.setMessage("Are you sure you want logout?");

                // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.navbar_profileselected);

                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.clear();
                                editor.commit();
                                Intent i = new Intent(getApplicationContext(),
                                        LoginActivity.class);
                                startActivity(i);
                                finish();
                            }
                        });

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                // Showing Alert Message
                alertDialog.show();
            }
        });

        String avatar = preferences.getString("avatar", "");
        if (!avatar.equals("")) {
            new DownloadImageTask(mImage).execute(ImageHelper.getImage(this, avatar, 700, 300));
        }


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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent i = new Intent(getApplicationContext(),
                    ProfileActivity.class);
            startActivity(i);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        ImageView thumbnail;

        public DownloadImageTask(ImageView bmImage) {
            thumbnail = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Log.e("URL", urldisplay);
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            thumbnail.setImageBitmap(result);
        }
    }

    private Callback getReservations() {
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
                    e.printStackTrace();
                }
            }
        };
    }

}
