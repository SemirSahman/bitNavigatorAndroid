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
import android.support.v4.util.LruCache;
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
import java.net.URL;
import java.net.URLConnection;

import ba.bitcamp.bitNavigator.bitnavigator.R;
import ba.bitcamp.bitNavigator.controllers.*;
import ba.bitcamp.bitNavigator.controllers.LoginActivity;
import ba.bitcamp.bitNavigator.lists.ReservationList;
import ba.bitcamp.bitNavigator.models.Reservation;
import ba.bitcamp.bitNavigator.service.Cache;
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
        mName.setText(name);
        mSurname.setText(surname);
        mEmail.setText(email);
        mLogout.setText("Logout");
        mLogout.setVisibility(View.VISIBLE);

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
            if (getBitmapFromMemCache(ImageHelper.getThumbnail(avatar)) == null) {
                new DownloadImageTask(mImage).execute(ImageHelper.getThumbnail(avatar));
            } else {
                mImage.setImageBitmap(getBitmapFromMemCache(ImageHelper.getThumbnail(avatar)));
            }
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

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            Cache.getInstance().getLruCache().put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return Cache.getInstance().getLruCache().get(key);
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
            Log.i("URL", urldisplay);
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                addBitmapToMemoryCache(urldisplay, mIcon11);
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
}
