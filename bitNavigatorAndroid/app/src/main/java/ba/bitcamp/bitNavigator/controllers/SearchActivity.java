package ba.bitcamp.bitNavigator.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ba.bitcamp.bitNavigator.bitnavigator.R;
import ba.bitcamp.bitNavigator.lists.PlaceList;
import ba.bitcamp.bitNavigator.models.Place;
import ba.bitcamp.bitNavigator.service.Navbar;


/**
 * Created by hajrudin.sehic on 27/10/15.
 */
public class SearchActivity extends Navbar {

    public static List<Place> placeList = PlaceList.getInstance().getPlaceList();

    private EditText mSearch;
    private RecyclerView recyclerView;
    private PlaceAdapter placeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        sortList();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mSearch = (EditText) findViewById(R.id.autocomplete_places);
        placeAdapter = new PlaceAdapter(placeList);
        recyclerView.setAdapter(placeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSearch.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                List<Place> list = new ArrayList<Place>();

                for (int i = 0; i < placeList.size(); i++) {
                    list.add(placeList.get(i));
                }

                placeAdapter.notifyDataSetChanged();

                if (s.length() != 0) {
                    int size = list.size();
                    int i = 0;
                    while (i < size) {
                        if (!(list.get(i).getTitle().toLowerCase().contains(s.toString().toLowerCase()))) {
                            list.remove(i);
                            size--;
                        } else {
                            i++;
                        }
                    }

                    sortList();

                    placeAdapter = new PlaceAdapter(list);
                    recyclerView.setAdapter(placeAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                } else {

                    sortList();

                    placeAdapter = new PlaceAdapter(placeList);
                    recyclerView.setAdapter(placeAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });


        navbarButtons();
    }

    private void sortList() {
        Collections.sort(placeList, new Comparator<Place>() {
            @Override
            public int compare(Place lhs, Place rhs) {
                return lhs.getTitle().compareToIgnoreCase(rhs.getTitle());
            }
        });
    }

    private class PlaceView extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mServiceImage;
        private TextView titleText;
        private TextView addressText;
        private TextView id;

        public PlaceView(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mServiceImage = (ImageView) itemView.findViewById(R.id.place_service);
            titleText = (TextView) itemView.findViewById(R.id.textView2);
            addressText = (TextView) itemView.findViewById(R.id.textView3);
            id = (TextView) itemView.findViewById(R.id.place_id);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(),
                    PlaceActivity.class);
            i.putExtra("place_id", id.getText());
            startActivity(i);
            finish();
        }
    }

    private class PlaceAdapter extends RecyclerView.Adapter<PlaceView> {

        private List<Place> placeList;

        public PlaceAdapter(List<Place> placeList) {
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
            String place_service = place.getService().toLowerCase();

            if (place_service.equals("arts&entertainment")) {
                place_service = "arts";
            } else if (place_service.equals("night life")) {
                place_service = "night_life";
            }

            int res = getResources().getIdentifier(place_service, "drawable", getPackageName());
            holder.mServiceImage.setImageResource(res);
            holder.titleText.setText(place.getTitle());
            holder.addressText.setText(place.getAddress());
            String tmp = String.valueOf(place.getId());
            holder.id.setText(tmp);
        }

        @Override
        public int getItemCount() {
            return placeList.size();
        }
    }

}
