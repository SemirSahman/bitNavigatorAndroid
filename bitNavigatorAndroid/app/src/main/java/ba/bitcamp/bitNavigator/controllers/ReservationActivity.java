package ba.bitcamp.bitNavigator.controllers;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ba.bitcamp.bitNavigator.bitnavigator.R;
import ba.bitcamp.bitNavigator.lists.WorkingHoursList;
import ba.bitcamp.bitNavigator.models.WorkingHours;
import ba.bitcamp.bitNavigator.service.ServiceRequest;

/**
 * Created by hajrudin.sehic on 29/10/15.
 */
public class ReservationActivity extends Activity{

    private Button btnCalendar;
    private Button btnTimePicker;
    private Button btnSubmit;

    private EditText txtDate;
    private EditText txtTime;
    private EditText txtMessage;

    private TextView monFrom;
    private TextView tueFrom;
    private TextView wedFrom;
    private TextView thuFrom;
    private TextView friFrom;
    private TextView satFrom;
    private TextView sunFrom;

    private TextView monTo;
    private TextView tueTo;
    private TextView wedTo;
    private TextView thuTo;
    private TextView friTo;
    private TextView satTo;
    private TextView sunTo;



    public Date date;
    public int selectedDay;
    public String id;
    public String message;

    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        id = getIntent().getStringExtra("place_id");
        final Integer place_id = Integer.parseInt(id);

        final WorkingHours hours = WorkingHoursList.getInstance().getByPlaceId(place_id);


        monFrom = (TextView) findViewById(R.id.monFrom);
        monFrom.setText(hours.getFormatedTime(hours.getOpen1()));
        tueFrom = (TextView) findViewById(R.id.tueFrom);
        tueFrom.setText(hours.getFormatedTime(hours.getOpen2()));
        wedFrom = (TextView) findViewById(R.id.wedFrom);
        wedFrom.setText(hours.getFormatedTime(hours.getOpen3()));
        thuFrom = (TextView) findViewById(R.id.thuFrom);
        thuFrom.setText(hours.getFormatedTime(hours.getOpen4()));
        friFrom = (TextView) findViewById(R.id.friFrom);
        friFrom.setText(hours.getFormatedTime(hours.getOpen5()));
        satFrom = (TextView) findViewById(R.id.satFrom);
        satFrom.setText(hours.getFormatedTime(hours.getOpen6()));
        sunFrom = (TextView) findViewById(R.id.sunFrom);
        sunFrom.setText(hours.getFormatedTime(hours.getOpen7()));

        monTo = (TextView) findViewById(R.id.monTo);
        monTo.setText(hours.getFormatedTime(hours.getClose1()));
        tueTo = (TextView) findViewById(R.id.tueTo);
        tueTo.setText(hours.getFormatedTime(hours.getClose2()));
        wedTo = (TextView) findViewById(R.id.wedTo);
        wedTo.setText(hours.getFormatedTime(hours.getClose3()));
        thuTo = (TextView) findViewById(R.id.thuTo);
        thuTo.setText(hours.getFormatedTime(hours.getClose4()));
        friTo = (TextView) findViewById(R.id.friTo);
        friTo.setText(hours.getFormatedTime(hours.getClose5()));
        satTo = (TextView) findViewById(R.id.satTo);
        satTo.setText(hours.getFormatedTime(hours.getClose6()));
        sunTo = (TextView) findViewById(R.id.sunTo);
        sunTo.setText(hours.getFormatedTime(hours.getClose7()));

        btnSubmit = (Button) findViewById(R.id.btn_submit_reservation);
        btnCalendar = (Button) findViewById(R.id.btnCalendar);
        btnTimePicker = (Button) findViewById(R.id.btnTimePicker);
        txtDate = (EditText) findViewById(R.id.txtDate);
        txtTime = (EditText) findViewById(R.id.txtTime);
        txtMessage = (EditText) findViewById(R.id.txtMessage);

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Process to get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                // Launch Date Picker Dialog
                DatePickerDialog dpd;
                dpd = new DatePickerDialog(ReservationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                txtDate.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);
                                Calendar c = new GregorianCalendar(year,monthOfYear,dayOfMonth);
                                Calendar today = Calendar.getInstance();
                                selectedDay = c.get(Calendar.DAY_OF_WEEK) - 1;
                                if(selectedDay == 0){
                                    selectedDay = 7;
                                }
                                if(hours.getIsWorking(selectedDay) == null || c.before(today)){
                                    btnTimePicker.setEnabled(false);
                                    txtDate.setFocusable(true);
                                    txtDate.setError("Please select valid date!");
                                    txtDate.requestFocus();
                                }else {
                                    txtDate.setError(null);
                                    btnTimePicker.setEnabled(true);
                                }
                            }
                        }, mYear, mMonth, mDay);
                dpd.getDatePicker().setMinDate(new GregorianCalendar().getTimeInMillis()+86400000);
                dpd.show();
            }
        });


        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog tpd = new TimePickerDialog(ReservationActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // Display Selected time in textbox
                                txtTime.setText(hourOfDay + ":" + minute);
                                if (validateTime(hourOfDay, minute, hours)) {
                                    txtTime.setError(null);
                                    txtMessage.setEnabled(true);
                                    btnSubmit.setEnabled(true);
                                } else {
                                    txtTime.setFocusable(true);
                                    txtTime.setError("Please select valid time!");
                                    txtTime.requestFocus();
                                    txtMessage.setEnabled(false);
                                }
                            }
                        }, mHour, mMinute, false);
                tpd.show();
            }
        });


        SharedPreferences sharedpreferences = getSharedPreferences("SESSION", Context.MODE_PRIVATE);
        final String email = sharedpreferences.getString("email", "");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = txtMessage.getText().toString();
                if(message.length() < 1){
                    txtMessage.setError("Can't send an empty message!");
                }else{
                    txtMessage.setError(null);


                JSONObject json = new JSONObject();
                try {
                    json.put("place_id", place_id);
                    json.put("user_email", email);
                    json.put("day", txtDate.getText());
                    json.put("time", txtTime.getText());
                    json.put("message",message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String url = getString(R.string.service_submit_reservation);
                ServiceRequest.post(url, json.toString(), submitReservation());
            }    }
        });


        Button mLoginButton = (Button) findViewById(R.id.btnProfile);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });


//        Button mRegisterButton = (Button) findViewById(R.id.btnReservations);
//        mRegisterButton.setOnClickListener(new View.OnClickListener() {
//                                               public void onClick(View v) {
//                                                   Intent i = new Intent(getApplicationContext(), LoginActivity.class);
//                                                   startActivity(i);
//                                               }
//                                           }
//        );

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

    public boolean validateTime(int h, int m, WorkingHours hours){
        String selected = h + "" +m;
        String work = hours.getIsWorking(selectedDay);
        String split[] = work.split(" ");
        int sel = Integer.parseInt(selected);
        int op = Integer.parseInt(split[0]);
        String p = String.format("%02d%02d", op / 60, op % 60);
        op = Integer.parseInt(p);
        int cl = Integer.parseInt(split[1]);
        String c = String.format("%02d%02d", cl / 60, cl % 60);
        cl = Integer.parseInt(c);
        if(sel >= op && sel <= cl){
            return true;
        }
        return false;
    }

    private Callback submitReservation() {
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.getMessage();
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                goToPlace();
            }
        };
    }

    public void goToPlace() {
        Intent i = new Intent(this, PlaceActivity.class);
        i.putExtra("place_id", id);
        startActivity(i);
    }

}
