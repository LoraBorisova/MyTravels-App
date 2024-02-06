package com.example.mytravels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

// Settings Activity
public class SettingsActivity extends AppCompatActivity {

    // EditText for username, old password and new one
    EditText username, password, newPassword;

    // button to change password
    Button changePass;

    // Database where the username and password are stored
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Adding Fragment for settings (notifications and feedback)
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_container, new Fragment1())
                .commit();

        // Finding EditText for username, password and new password in activity_settings.xml
        username = findViewById(R.id.username1);
        password = findViewById(R.id.password1);
        newPassword = findViewById(R.id.passwordNew);

        // Finding changePassBtn in activity_settings.xml
        changePass = findViewById(R.id.changePassBtn);

        DB = new DBHelper(this);

        // When changePass is clicked...
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Getting username, pass and new pass from EditText
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String newPass = newPassword.getText().toString();

                // Check if there is an empty editText
                if(user.equals("")||pass.equals("")||newPass.equals("")){
                    Toast.makeText(SettingsActivity.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                }else {
                    // If not, check if the new password is the same as the old one
                    if (!(pass.equals(newPass))) {
                        // Check if the username is correct
                        Boolean checkUserPass = DB.checkUsernamePassword(user, pass);
                        if (checkUserPass) {
                            Boolean insert = DB.updatePassword(user, newPass);
                            // Update password in database
                            if(insert) {
                                Toast.makeText(SettingsActivity.this, "Password changed successfully.", Toast.LENGTH_SHORT).show();
                                // Go to loginActivity to check new password
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(SettingsActivity.this, "ERROR.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SettingsActivity.this, "Invalid Credentials.", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(SettingsActivity.this, "New password is the same as the old password.", Toast.LENGTH_SHORT).show();
                    }
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

}