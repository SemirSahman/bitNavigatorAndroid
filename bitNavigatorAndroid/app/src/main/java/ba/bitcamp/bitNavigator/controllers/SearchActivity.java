package ba.bitcamp.bitNavigator.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import ba.bitcamp.bitNavigator.bitnavigator.R;
import ba.bitcamp.bitNavigator.lists.PlaceList;
import ba.bitcamp.bitNavigator.models.Place;


/**
 * Created by hajrudin.sehic on 27/10/15.
 */
public class SearchActivity extends Activity implements SearchView.OnQueryTextListener{

    public static PlaceList placeList = PlaceList.getInstance();

    private SearchView mSearch;
    private RecyclerView recyclerView;
    private PlaceAdapter placeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearch = (SearchView) findViewById(R.id.search_view);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        placeAdapter = new PlaceAdapter(placeList);
        recyclerView.setAdapter(placeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


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


    public boolean onQueryTextChange(String newText) {
        PlaceList placeList1 = null;
        for(int i = 0; i<placeList.getSize(); i++){
            if(placeList.get(i).getTitle().contains(newText)){
                placeList1.add(placeList.get(i));
            }
        }
        placeAdapter = new PlaceAdapter(placeList1);
        recyclerView.setAdapter(placeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        PlaceList placeList1 = null;
        for(int i = 0; i<placeList.getSize(); i++){
            if(placeList.get(i).getTitle().contains(query)){
                placeList1.add(placeList.get(i));
            }
        }
        placeAdapter = new PlaceAdapter(placeList1);
        recyclerView.setAdapter(placeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        return true;
    }

    private class PlaceView extends RecyclerView.ViewHolder {

        private TextView titleText;
        private TextView addressText;
        private TextView dateText;

        public PlaceView(View itemView) {
            super(itemView);
            titleText = (TextView) itemView.findViewById(R.id.textView2);
            addressText = (TextView) itemView.findViewById(R.id.textView3);
        }
    }

    private class PlaceAdapter extends RecyclerView.Adapter<PlaceView> {

        private PlaceList placeList;

        public PlaceAdapter(PlaceList placeList) {
            this.placeList = placeList;
        }

        @Override
        public PlaceView onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(SearchActivity.this);
            View view = layoutInflater.inflate(R.layout.place_layout, parent, false);
            return new PlaceView(view);
        }

        @Override
        public void onBindViewHolder(PlaceView holder, int position) {
            Place place = placeList.get(position);
            holder.titleText.setText(place.getTitle());
            holder.addressText.setText(place.getAddress());
        }

        @Override
        public int getItemCount() {
            return placeList.getSize();
        }
    }

}
