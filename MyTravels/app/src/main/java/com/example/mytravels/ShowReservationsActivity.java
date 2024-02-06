package com.example.mytravels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;


// ShowReservationsActivity's function is to show each reservation of the logged in user
// The information is stored in database
public class ShowReservationsActivity extends AppCompatActivity {

    // Using DBHelperReservation database
    DBHelper myDB;

    // ListView item
    ListView listView;

    // TextView item
    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reservations);

        // Finding ListView names listView in activity_show_reservations.xml
        listView = findViewById(R.id.listView);

        // Finding textView named text in activity_show_reservations.xml
        tv = findViewById(R.id.text);

        myDB = new DBHelper(this);

        // Getting username from LoginActivity
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        String st = sharedPreferences.getString("username","");

        // Populate an ArrayList<String> from the database
        ArrayList<String> theList = new ArrayList<>();

        // Check which user has logged in (st) and show only their data
        Cursor data = myDB.read_all_data(st);
        if(data.getCount() == 0){
            // If there are no reservations with this username, show text
            Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
        }else{
            // Show the reservations from database in ListView using ListAdapter
            while(data.moveToNext()){
                theList.add(data.getString(2) + " - " + data.getString(3));
                ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
                listView.setAdapter(listAdapter);
            }
        }
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
}
