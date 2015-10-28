package ba.bitcamp.bitNavigator.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ba.bitcamp.bitNavigator.bitnavigator.R;
import ba.bitcamp.bitNavigator.lists.PlaceList;
import ba.bitcamp.bitNavigator.models.Place;

/**
 * Created by hajrudin.sehic on 28/10/15.
 */
public class PlaceActivity extends Activity{

    private ImageView mImage;
    private ImageView mService;
    private TextView mTitle;
    private TextView mAddress;
    private TextView mDescription;
    private Button mReservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        String id = getIntent().getStringExtra("place_id");
        Integer place_id = Integer.parseInt(id);

        Place place = PlaceList.getInstance().getPlace(place_id);

        mImage = (ImageView) findViewById(R.id.imgProfilePic);
        mService = (ImageView) findViewById(R.id.service_img);
        mTitle = (TextView) findViewById(R.id.txtTitle);
        mAddress = (TextView) findViewById(R.id.txtAddress);
        mDescription = (TextView) findViewById(R.id.txtDescription);
        mReservation = (Button) findViewById(R.id.btn_reservation);

        int res = getResources().getIdentifier(place.getService().toLowerCase(),"drawable",getPackageName());

        mService.setImageResource(res);
        mTitle.setText(place.getTitle());
        mAddress.setText("   " + place.getAddress());
        mDescription.setText(place.getDescription());

        SharedPreferences sharedpreferences = getSharedPreferences("SESSION", Context.MODE_PRIVATE);
        if(sharedpreferences.contains("email")){
            mReservation.setVisibility(View.VISIBLE);
        }




        Button mLoginButton = (Button) findViewById(R.id.btnProfile);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });


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
}
