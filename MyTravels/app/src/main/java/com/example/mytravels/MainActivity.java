package com.example.mytravels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


// Activity for registration
public class MainActivity extends AppCompatActivity {

    // EditText for username, pass and repassword (write password again)
    EditText username, password, repassword;

    // Button for registration or log in
    Button signUp, signIn;

    // Using DBHelper to store data in the database
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Finding username, password and repassword EditText in activity_main.xml
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);

        // Find btnSignUp and btnSignIn buttons in activity_main.xml
        signUp = findViewById(R.id.btnSignUp);
        signIn = findViewById(R.id.btnSignIn);

        DB = new DBHelper(this);

        // When clicking signUp button
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Getting Strings of username, password and repassword
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                // Checking if those Strings are empty
                if(user.equals("")||pass.equals("")||repass.equals(""))
                    Toast.makeText(MainActivity.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                else{
                    // If not, checking if pass is equal to repass
                    if(pass.equals(repass)){
                        Boolean checkUser = DB.checkUsername(user);
                        // Checking for existing username
                        if(!checkUser){
                            // If there is no such username, make a registration and store data in database
                            Boolean insert = DB.insertData(user, pass);
                            if(insert){
                                Toast.makeText(MainActivity.this, "Registration was successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(MainActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(MainActivity.this, "Username already exists.", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this, "Passwords not matching.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // When clicking signIn button
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start an intent to go to LoginActivity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}