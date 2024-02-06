package com.example.mytravels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

// Activity to show profile (username, password and log out)
public class ProfileActivity extends AppCompatActivity {

    // TextView for username and password
    TextView username, password;

    // Buttons to show password and log out
    Button passBtn, logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Finding username and password TextView in activity_profile.xml
        username = findViewById(R.id.usernameProfile);
        password = findViewById(R.id.passProfile);

        // Finding button to show password and log out button in activity_profile.xml
        passBtn = findViewById(R.id.seePass);
        logOut = findViewById(R.id.btnLogOut);

        // Using SharedPreferences to set text of TextView - username and password (Getting the text from LoginActivity)
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        String user = sharedPreferences.getString("username","");
        String pass = sharedPreferences.getString("password","");
        username.setText(user);
        password.setText(pass);

        // When passBtn is clicked...
        passBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            // Make password visible
            public void onClick(View v) {
                password.setVisibility(View.VISIBLE);
            }
        });

        // When logOut button is clicked...
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to LoginActivity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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