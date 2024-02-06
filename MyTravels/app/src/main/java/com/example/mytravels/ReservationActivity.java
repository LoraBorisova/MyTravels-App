package com.example.mytravels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.preference.PreferenceManager;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

// Activity to make a reservation
public class ReservationActivity extends AppCompatActivity {

    // TextView for first day and last day of vacation, price per night, name of hotel
    // and final price of vacation
    TextView firstDay, secondDay, price, name, pricePerNight;

    CheckBox checkBox;

    // String to save days when rotating screen
    String firstD, secondD;

    // Variable for all days
    int days;

    // Variables for price per night and final price
    double pr, priceValue;

    // Name of hotel and username
    String nm, un;

    // Dialog to pick date
    DatePickerDialog datePickerDialog;

    // Buttons for reservation and calculating price
    Button save_btn, reservation;

    // Calendars
    Calendar c1,c2;

    // Using DBHelperReservation to add information of reservations in database
    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_reservation);

        // Find TextView for first day selected (firstDay), last day selected (secondDay), price
        // and name of hotel in activity_reservation.xml
        firstDay = findViewById (R.id.firstDateSelected); // First day selected in calendar dialog
        secondDay = findViewById (R.id.secondDateSelected); // Second day selected in calendar dialog
        price = findViewById (R.id.finalPrice); // Final price of vacation
        pricePerNight =  findViewById(R.id.pricePerNight); // Price per night of vacation
        name = findViewById(R.id.nameOfHotel); // Name of the hotel
        checkBox = findViewById(R.id.simpleCheckBox); // Checkbox for terms and conditions

        // Finding button for calculating price and button for making reservation in activity_reservation.xml
        save_btn = findViewById (R.id.reservationBtn); // Button for calculating price
        reservation = findViewById(R.id.finalBtn); // Button for finishing reservation

        // Final price is 0
        priceValue = 0.0;

        // Getting intent
        Intent intent = getIntent();
        myDB = new DBHelper(this);

        // Using SharedPreferences to get username from LoginActivity
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        un = sharedPreferences.getString("username","");

        // Getting price per night and hotel name from ProductActivity and setting the text in TextView
        pr = intent.getDoubleExtra("PricePerNight", 0);
        pricePerNight.setText(String.valueOf(pr));
        nm = intent.getExtras().getString("HotelName");
        name.setText(nm);

        // Saving dates and final price when rotating the screen
        if (savedInstanceState != null) {
            priceValue = savedInstanceState.getDouble("finalPrice");
            price.setText(String.valueOf(priceValue));
            firstD = savedInstanceState.getString("firstDay");
            firstDay.setText(firstD);
            secondD = savedInstanceState.getString("secondDay");
            secondDay.setText(secondD);
        }


        // When save_btn (calculating price) is clicked..
        save_btn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                try {
                    // Creating String for first and last day of vacation
                    String d1 = firstDay.getText().toString();
                    String d2 = secondDay.getText().toString();

                    // Setting date format
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                    Date date1 = simpleDateFormat.parse(d1);
                    Date date2 = simpleDateFormat.parse(d2);
                    // dates should not be null
                    if(date1 != null && date2 != null) {
                        if (date1.getTime() > date2.getTime()) {
                            Toast.makeText(ReservationActivity.this, "Wrong dates.", Toast.LENGTH_SHORT).show();
                        } else {

                            try {
                            } catch (NumberFormatException nfe) {
                                System.out.println("Could not parse " + nfe);
                            }

                            // calculating days from first day (firstDay) to last day (secondDay)
                            long difference = Math.abs(date1.getTime() - date2.getTime());
                            long difftDays = difference / (24 * 60 * 60 * 1000);

                            // days is equal to Integer of difftDays, because it is used in calculating final price
                            days = (int) difftDays;

                            // final price is price per night * days (from first day to last day)
                            priceValue = pr * days;

                            // setting textView of final price
                            price.setText(String.valueOf(priceValue));
                        }
                    }else{
                        Toast.makeText(ReservationActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        // Clicking of TextView for first day
        firstDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Getting month, year and day
                c1 = Calendar.getInstance();
                int mYear = c1.get(Calendar.YEAR);
                int mMonth = c1.get(Calendar.MONTH);
                int mDay = c1.get(Calendar.DAY_OF_MONTH);

                // Dialog to choose date
                datePickerDialog = new DatePickerDialog(ReservationActivity.this,new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        // String of months, used to show months by name
                        String[] MONTHS = {"January", "February", "March", "April", "May", "June",
                                "July", "August", "September", "October", "November", "December"};
                        String mon=MONTHS[monthOfYear];

                        // Setting text of TextView
                        firstD = dayOfMonth + "-" + (mon) + "-" + year;
                        firstDay.setText(firstD);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        secondDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c2 = Calendar.getInstance();
                int mYear = c2.get(Calendar.YEAR);
                int mMonth = c2.get(Calendar.MONTH);
                int mDay = c2.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(ReservationActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July",
                                "August", "September", "October", "November", "December"};
                        String mon=MONTHS[monthOfYear];

                        secondD = dayOfMonth + "-" + (mon) + "-" + year;
                        secondDay.setText(secondD);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        // Clicking reservation button
        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if price is calculated
                if(priceValue > 0 && checkBox.isChecked()) {
                    // adding username, name of hotel and final price of reservation to database
                    boolean insertData = myDB.insertData(un, nm, priceValue);
                    // If inserted successfully
                    if(insertData){
                        Toast.makeText(ReservationActivity.this, "You have made a reservation.", Toast.LENGTH_LONG).show();
                        // Go to reservations
                        Intent intent = new Intent(getApplicationContext(), ShowReservationsActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(ReservationActivity.this, "Something went wrong.", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(ReservationActivity.this, "Check dates and price again or read terms and conditions!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Adding menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_menu, menu);

        // removing name of the app from Action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        return true;
    }

    // When selecting menu item...
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // New explicit intent when clicking each item and going to new screen
        switch (item.getItemId()){
            case R.id.home:
            case R.id.search:
                Intent intent = new Intent(getApplicationContext(), ListViewImage.class);
                startActivity(intent);
                return true;
            case R.id.reservations:
                Intent intent1 = new Intent(getApplicationContext(), ShowReservationsActivity.class);
                startActivity(intent1);
                return true;
            case R.id.profile:
                Intent intent2 = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent2);
                return true;
            case R.id.settings:
                Intent intent3 = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent3);
                return true;
            case R.id.conditions:
                Intent intent4 = new Intent(getApplicationContext(), ConditionsActivity.class);
                startActivity(intent4);
                return true;

            //GOTO NEW SCREEN
            default: return super.onOptionsItemSelected(item);
        }
    }

    // Save value of final price, first day and last day, when rotating screen
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("finalPrice", priceValue);
        outState.putString("firstDay", firstD);
        outState.putString("secondDay", secondD);
    }
}

