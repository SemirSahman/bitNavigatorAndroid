package ba.bitcamp.bitNavigator.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ba.bitcamp.bitNavigator.bitnavigator.R;
import ba.bitcamp.bitNavigator.lists.ReservationList;
import ba.bitcamp.bitNavigator.models.Reservation;

/**
 * Created by hajrudin.sehic on 30/10/15.
 */
public class MyReservationsActivity extends Activity{

    public static List<Reservation> reservationList = ReservationList.getInstance().getReservationList();

    private EditText mSearch;
    private RecyclerView recyclerView;
    private ReservationAdapter reservationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservation_list);

        Collections.sort(reservationList, new Comparator<Reservation>() {
            @Override
            public int compare(Reservation lhs, Reservation rhs) {
                return lhs.getPlace_title().compareToIgnoreCase(rhs.getPlace_title());
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mSearch = (EditText) findViewById(R.id.autocomplete_reservations);
        reservationAdapter = new ReservationAdapter(reservationList);
        recyclerView.setAdapter(reservationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSearch.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                List<Reservation> list = new ArrayList<Reservation>();

                for (int i = 0; i < reservationList.size(); i++) {
                    list.add(reservationList.get(i));
                }


                reservationAdapter.notifyDataSetChanged();


                if (s.length() != 0) {
                    int size = list.size();
                    int i = 0;
                    while (i < size) {
                        if (!(list.get(i).getPlace_title().toLowerCase().contains(s.toString().toLowerCase()))) {
                            list.remove(i);
                            size--;
                        } else {
                            i++;
                        }
                    }

                    Collections.sort(reservationList, new Comparator<Reservation>() {
                        @Override
                        public int compare(Reservation lhs, Reservation rhs) {
                            return lhs.getPlace_title().compareToIgnoreCase(rhs.getPlace_title());
                        }
                    });

                    reservationAdapter = new ReservationAdapter(list);
                    recyclerView.setAdapter(reservationAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MyReservationsActivity.this));
                } else {

                    Collections.sort(reservationList, new Comparator<Reservation>() {
                        @Override
                        public int compare(Reservation lhs, Reservation rhs) {
                            return lhs.getPlace_title().compareToIgnoreCase(rhs.getPlace_title());
                        }
                    });

                    reservationAdapter = new ReservationAdapter(reservationList);
                    recyclerView.setAdapter(reservationAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MyReservationsActivity.this));
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

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

    private class ReservationView extends RecyclerView.ViewHolder{

        private TextView titleText;
        private TextView statusText;
        private TextView dateText;

        public ReservationView(View itemView) {
            super(itemView);
            titleText = (TextView) itemView.findViewById(R.id.textView2);
            statusText = (TextView) itemView.findViewById(R.id.textView3);
            dateText = (TextView) itemView.findViewById(R.id.textView4);
        }
    }

    private class ReservationAdapter extends RecyclerView.Adapter<ReservationView> {

        private List<Reservation> reservationList;

        public ReservationAdapter(List<Reservation> reservationList) {
            this.reservationList = reservationList;
        }

        @Override
        public ReservationView onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(MyReservationsActivity.this);
            View view = layoutInflater.inflate(R.layout.reservation_layout, parent, false);
            return new ReservationView(view);
        }

        @Override
        public void onBindViewHolder(ReservationView holder, int position) {
            Reservation r = reservationList.get(position);
            holder.titleText.setText(r.getPlace_title());
            holder.statusText.setText(r.getStatus());
            holder.dateText.setText(r.getDate());
        }

        @Override
        public int getItemCount() {
            return reservationList.size();
        }
    }

}
