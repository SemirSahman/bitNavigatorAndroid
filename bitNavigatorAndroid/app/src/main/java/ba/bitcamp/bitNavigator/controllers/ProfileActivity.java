package ba.bitcamp.bitNavigator.controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ba.bitcamp.bitNavigator.bitnavigator.R;
import ba.bitcamp.bitNavigator.controllers.*;
import ba.bitcamp.bitNavigator.controllers.LoginActivity;

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

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

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

                // Setting Dialog Title
                alertDialog.setTitle("Logout");

                // Setting Dialog Message
                alertDialog.setMessage("Are you sure you want logout?");

                // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.navbar_profilenormal);

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

}
