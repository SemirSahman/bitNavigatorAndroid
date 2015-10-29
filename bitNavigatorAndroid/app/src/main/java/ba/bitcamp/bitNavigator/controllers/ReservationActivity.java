package ba.bitcamp.bitNavigator.controllers;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ba.bitcamp.bitNavigator.bitnavigator.R;
import ba.bitcamp.bitNavigator.lists.WorkingHoursList;
import ba.bitcamp.bitNavigator.models.WorkingHours;

/**
 * Created by hajrudin.sehic on 29/10/15.
 */
public class ReservationActivity extends Activity{

    private Button btnCalendar;
    private Button btnTimePicker;

    private EditText txtDate;
    private EditText txtTime;

    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        final String id = getIntent().getStringExtra("place_id");
        Integer place_id = Integer.parseInt(id);

        Log.e("place", id);
        final WorkingHours hours = WorkingHoursList.getInstance().getByPlaceId(place_id);

        Log.e("Hours", hours.getClose7()+ " ");

        btnCalendar = (Button) findViewById(R.id.btnCalendar);
        btnTimePicker = (Button) findViewById(R.id.btnTimePicker);
        txtDate = (EditText) findViewById(R.id.txtDate);
        txtTime = (EditText) findViewById(R.id.txtTime);

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
                                SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                                Date date = new Date(year, monthOfYear, dayOfMonth-1);
                                String dayOfWeek = simpledateformat.format(date);
                                Log.e("day", "."+dayOfWeek+".");
                                int tmp = getIntDay(dayOfWeek);
                                Log.e("tmp", tmp + "");
                                Log.e("aaaa", getIsWorking(tmp, hours));
                                if(getIsWorking(tmp, hours).equals("1")){
                                    txtDate.setError("Please select valid date");
                                    btnTimePicker.setEnabled(false);
                                    return;
                                }else {
                                    btnTimePicker.setEnabled(true);
                                }
                            }
                        }, mYear, mMonth, mDay);
                dpd.getDatePicker().setMinDate(new GregorianCalendar().getTimeInMillis()+86400000);
                dpd.show();
            }
        });


        btnTimePicker.setOnClickListener(new View.OnClickListener(){
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
                            }
                        }, mHour, mMinute, false);
                tpd.show();
            }
        });


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

    public int getIntDay(String dayOfWeek){
        switch(dayOfWeek){
            case "Monday":return 1;
            case "Tuesday":return 2;
            case "Wednesday":return 3;
            case "Thursday":return 4;
            case "Friday":return 5;
            case "Saturday":return 6;
            default: return 7;
        }
    }

    public String getIsWorking(int day, WorkingHours hours){
        switch (day){
            case 1: {
                if (hours.getOpen1() == -1) {
                    return "1";
                } else {
                    return hours.getOpen1() + " " + hours.getClose1();
                }
            }
            case 2: {
                if (hours.getOpen2() == -1) {
                    return "1";
                } else {
                    return hours.getOpen2() + " " + hours.getClose2();
                }
            }
            case 3: {
                if (hours.getOpen3() == -1) {
                    return "1";
                } else {
                    return hours.getOpen3() + " " + hours.getClose3();
                }
            }
            case 4: {
                if (hours.getOpen4() == -1) {
                    return "1";
                } else {
                    return hours.getOpen4() + " " + hours.getClose4();
                }
            }
            case 5: {
                if (hours.getOpen5() == -1) {
                    return "1";
                } else {
                    return hours.getOpen5() + " " + hours.getClose5();
                }
            }
            case 6: {
                if (hours.getOpen6() == -1) {
                    return "1";
                } else {
                    return hours.getOpen6() + " " + hours.getClose6();
                }
            }
            case 7: {
                if (hours.getOpen7().equals(-1)) {
                    return "1";
                } else {
                    return hours.getOpen7() + " " + hours.getClose7();
                }
            }
            default: return "2";
        }
    }

}
