package com.example.mytravels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;

// Screen to show logo of the app and change the screen after 2 seconds with thread
public class SplashActivity extends AppCompatActivity {

    // Thread handler
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler.removeCallbacks(screenUpdater);

        // Adding Runnable object and starting it in 2 seconds
        handler.postDelayed(screenUpdater, 2000);
    }

    private final Runnable screenUpdater = new Runnable() {
        @Override
        public void run() {
            // Intent to show MainActivity
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onPause() {
        // Delete Runnable Object
        handler.removeCallbacks(screenUpdater);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Adding Runnable object
        handler.postDelayed(screenUpdater, 2000);
    }
}