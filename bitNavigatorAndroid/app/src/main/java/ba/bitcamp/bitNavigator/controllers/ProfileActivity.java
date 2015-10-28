package ba.bitcamp.bitNavigator.controllers;

import android.app.Activity;
import android.content.Context;
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

import java.io.InputStream;

import ba.bitcamp.bitNavigator.bitnavigator.R;
import ba.bitcamp.bitNavigator.controllers.*;
import ba.bitcamp.bitNavigator.controllers.LoginActivity;
import ba.bitcamp.bitNavigator.service.ImageHelper;

/**
 * Created by hajrudin.sehic on 27/10/15.
 */
public class ProfileActivity extends Activity{

    private ImageView mImage;
    private TextView mName;
    private TextView mEmail;
    private Button mLogout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting default screen to login.xml
        setContentView(R.layout.activity_user_profile);

        mImage = (ImageView) findViewById(R.id.imgProfilePic);
        mName = (TextView) findViewById(R.id.txtName);
        mEmail = (TextView) findViewById(R.id.txtEmail);
        mLogout = (Button) findViewById(R.id.btn_sign_out);

        final SharedPreferences preferences = getSharedPreferences("SESSION", Context.MODE_PRIVATE);
        String name = preferences.getString("name","") + " ";
        name += preferences.getString("surname", "");
        String email = preferences.getString("email","");
        mName.setText(name);
        mEmail.setText(email);
        mLogout.setText("Logout");
        mLogout.setVisibility(View.VISIBLE);
        mLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        String avatar = preferences.getString("avatar", "");
        if (!avatar.equals("")) {
            Log.d("dibag", "u ifu");
            new DownloadImageTask(mImage)
                    .execute(ImageHelper.getImage(this, avatar, 300, 300));
        }


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
                                                   Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
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

}
