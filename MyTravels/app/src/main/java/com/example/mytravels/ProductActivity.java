package com.example.mytravels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

// Activity to show each product
public class ProductActivity extends AppCompatActivity {

    // ViewPager for photos
    ViewPager mViewPager;

    // TextView for name (name), price (price) and rating (rat)
    TextView name;
    TextView rat;
    TextView price;

    // Button for reservation
    Button resBtn;

    // String to set name, price and rating of an item
    String nm;
    String rating;
    double pr;

    // Array of images
    int[] images = {R.drawable.ic_paris, R.drawable.ic_hotel_cannes, R.drawable.ic_hotel};

    // Creating Object of ViewPagerAdapter
    ViewerPageAdapter mViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        // Finding ViewPager (viewPagerProduct) in activity_product.xml
        mViewPager = findViewById(R.id.viewPagerProduct);

        // Adding images to ViewPager with custom adapter
        mViewPagerAdapter = new ViewerPageAdapter(ProductActivity.this, images);
        mViewPager.setAdapter(mViewPagerAdapter);

        // Getting intent
        Intent intent = getIntent();

        //Finding textView (name) in activity_product.xml and setting the value from ListViewImage
        name = findViewById(R.id.name);
        nm = intent.getExtras().getString("Value");
        name.setText(nm);

        //Finding textView (rat) in activity_product.xml and setting the value from ListViewImage
        rat = findViewById(R.id.rating);
        rating = intent.getExtras().getString("Rating");
        rat.setText(rating);

        //Finding textView (price) in activity_product.xml and setting the value from ListViewImage
        price = findViewById(R.id.price);
        pr = intent.getDoubleExtra("Price", 0);
        price.setText(String.valueOf(pr));

        // Finding reservation button in activity_product.xml
        resBtn = findViewById(R.id.reservation);

        // When resBtn is clicked...
        resBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Going to ReservationActivity and using intent to change textView in ReservationActivity
                Intent intent = new Intent(getApplicationContext(), ReservationActivity.class);
                intent.putExtra("HotelName", nm);
                intent.putExtra("PricePerNight", pr);
                startActivity(intent);
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

}
