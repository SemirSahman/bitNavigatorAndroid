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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.InputStream;

import ba.bitcamp.bitNavigator.bitnavigator.R;
import ba.bitcamp.bitNavigator.lists.PlaceList;
import ba.bitcamp.bitNavigator.models.Place;
import ba.bitcamp.bitNavigator.service.ImageHelper;

/**
 * Created by hajrudin.sehic on 28/10/15.
 */
public class PlaceActivity extends Activity{

    private ImageView mImage;
    private TextView mTitle;
    private TextView mAddress;
    private TextView mDescription;
    private Button mReservation;
    private RatingBar mRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        final String id = getIntent().getStringExtra("place_id");
        final Integer place_id = Integer.parseInt(id);

        final Place place = PlaceList.getInstance().getPlace(place_id);

        mImage = (ImageView) findViewById(R.id.imgProfilePic);
        mTitle = (TextView) findViewById(R.id.txtTitle);
        mAddress = (TextView) findViewById(R.id.txtAddress);
        mDescription = (TextView) findViewById(R.id.txtDescription);
        mReservation = (Button) findViewById(R.id.btn_reservation);
        mRatingBar = (RatingBar) findViewById(R.id.ratingBar);

        int res = getResources().getIdentifier(place.getService().toLowerCase(),"drawable",getPackageName());

        mTitle.setText(place.getTitle());
        mAddress.setText(place.getAddress());
        mDescription.setText(place.getDescription());
        mRatingBar.setRating(place.getRating().floatValue());

        mAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("place_id", place_id);
                startActivity(intent);
                finish();
            }
        });

        SharedPreferences sharedpreferences = getSharedPreferences("SESSION", Context.MODE_PRIVATE);
        if(sharedpreferences.contains("email") && place.getIsReservable()) {
            Integer user_id = sharedpreferences.getInt("id", 0);
            if (!user_id.equals(place.getUser_id())) {
                mReservation.setVisibility(View.VISIBLE);
                mReservation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(),
                                ReservationActivity.class);
                        i.putExtra("place_id", id);
                        startActivity(i);
                        finish();
                    }
                });
            }
        }

        String avatar = place.getImage();
        if (!avatar.equals("")) {
            new DownloadImageTask(mImage).execute(ImageHelper.getImage(this, avatar, 400, 400));
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
