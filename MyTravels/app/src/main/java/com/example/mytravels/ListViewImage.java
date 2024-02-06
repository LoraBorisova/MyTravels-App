package com.example.mytravels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

// ListViewImage activity's function is to show all hotels in a ListView using custom adapter and
// play music while browsing
public class ListViewImage extends AppCompatActivity {

    // ListView for hotels
    ListView lv;

    // Variable to check if the music is playing
    boolean isPlay = false;

    // Button to start or stop music
    Button musicManager;

    // Variables to change text in ProductActivity when clicking on a certain list item
    String editText;
    String editRating;
    double editPrice;

    // Array of names for each hotel
    String[] name = {"Grand Hotel Paris - Paris, France", "Hotel Madrid - Madrid, Spain",
            "Leonardo London City Hotel - London, UK", "Mystery Hotel Budapest - Budapest, Hungary",
            "Elite Hotel - Berlin, Germany", "Summer Camp Hotel - Corfu, Greece", "Riverside Hotel- Vienna, Austria"};

    // Array of prices for each hotel
    double[] price = {700, 560.60, 300.40, 349, 380, 200.50, 240, 580};

    // Array of images for each hotel
    int[] image = {R.drawable.ic_hotel, R.drawable.ic_hotel_cannes, R.drawable.ic_paris, R.drawable.ic_hotel,
            R.drawable.ic_paris, R.drawable.ic_hotel_cannes, R.drawable.ic_hotel};

    // Array of ratings to show in ProductActivity based on which hotel is clicked
    String[] rating = {"Very Good 8.7", "Excellent 9.5", "Excellent 9", "Excellent 9.4", "Good 7.5", "Good 7",
            "Excellent 9.5", "Good 7.8"};

    // Array of destinations (using Destination class)
    ArrayList<Destination> myList;

    // Custom Adapter
    MyListAdapter myListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_image);

        // Finding ListView (lv) named ListView2 in activity_list_view_image.xml
        lv = findViewById(R.id.ListView2);

        //Empty ArrayList
        myList = new ArrayList<>();

        //Destination class
        Destination destination;

        // Finding button names playMusic in activity_list_view_image.xml
        musicManager = findViewById(R.id.playMusic);

        // Looping through array and creating new Destination with name, price and image from arrays name,
        // price and image, then adding each destination to myList array
        for(int i =0; i < name.length; i++){
            destination = new Destination(name[i], price[i], image[i]);
            myList.add(destination);
        }

        // Showing destinations in ListView (lv) with custom adapter
        myListAdapter = new MyListAdapter(this, myList);
        lv.setAdapter(myListAdapter);


        // When clicking on each destination in ListView...
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    // New intent to load ProductActivity
                    Intent intent = new Intent(getApplicationContext(), ProductActivity.class);

                    // using intent.putExtra() to change text in ProductActivity according to name,
                    // price and rating of each destination in the ListView
                    editText = name[position];
                    intent.putExtra("Value", editText);
                    editRating = rating[position];
                    intent.putExtra("Rating", editRating);
                    editPrice = price[position];
                    intent.putExtra("Price", editPrice);
                    startActivity(intent);
            }
        });

        // When clicking on button to play music
        musicManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if isPlay is true or false
                if(!isPlay) {
                    // When false, start playing music using PlayerService and set the icon of the button
                    startService(new Intent(ListViewImage.this, PlayerService.class));
                    musicManager.setBackgroundResource(R.drawable.ic_pause);

                }else{
                    // When true stop PlayerService and change the icon of the button
                    stopService(new Intent(ListViewImage.this, PlayerService.class));
                    musicManager.setBackgroundResource(R.drawable.ic_start);
                }
                isPlay = !isPlay;
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

        // Finding menu item with name search from new_menu.xml
        MenuItem menuItem = menu.findItem(R.id.search);

        // Returns the currently set action view for this menu item.
        SearchView searchView = (SearchView) menuItem.getActionView();
        // Setting hint for the user
        searchView.setQueryHint("Type here to search");

        // using custom Filter from MyListAdapter to search for destination by name
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                myListAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myListAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    // When selecting menu item...
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // New explicit intent when clicking each item and going to new screen
        switch (item.getItemId()){
            case R.id.home:
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

            //Goto new screen
            default: return super.onOptionsItemSelected(item);
        }
    }
}
