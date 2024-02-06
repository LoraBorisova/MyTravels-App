package com.example.mytravels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// Activity's function is to check if the user has registration
public class LoginActivity extends AppCompatActivity {

    // EditText to write username and password
    EditText username, password;

    // Log in button
    Button btnLogIn;

    // Strings for username and password (transfer to other activities)
    String st, st1;

    // Using DBHelper to check data in the database
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Finding username and password EditText in activity_login.xml
        username = findViewById(R.id.username1);
        password = findViewById(R.id.password1);

        // Finding log in button in activity_login.xml
        btnLogIn = findViewById(R.id.btnSignIn1);

        DB = new DBHelper(this);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Getting username and password String from EditText
                String user = username.getText().toString();
                String pass = password.getText().toString();

                // Checking if they are empty
                if(user.equals("")||pass.equals("")){
                    Toast.makeText(LoginActivity.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                }else{
                    // If not, chek for existing username and password
                    Boolean checkUserPass = DB.checkUsernamePassword(user, pass);
                    if(checkUserPass){
                        // Log in if these username and password exist
                        Toast.makeText(LoginActivity.this, "Sign in successfully.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ListViewImage.class);

                        // Use SharedPreferences to pass data to other activities
                        SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("username", user);
                        editor.putString("password", pass);
                        editor.apply();

                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Invalid Credentials.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}