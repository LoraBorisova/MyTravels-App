package com.example.mytravels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

// ConditionsActivity's function is to show Terms and Conditions using JSON
public class ConditionsActivity extends AppCompatActivity {

        private final String TAG = ConditionsActivity.class.getSimpleName();

        // Progress dialog to show that the user should wait a few seconds
        private ProgressDialog pDialog;

        // ListView item
        private ListView lv;

        // Creating list to store conditions
        ArrayList<HashMap<String, String>> conditionsList;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_conditions);

            conditionsList = new ArrayList<>();

            // Finding ListView with id conditionsList in activity_conditions.xml
            lv = findViewById(R.id.conditionsList);

            new GetConditions().execute();
        }

        private class GetConditions extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Showing progress dialog when loading
                pDialog = new ProgressDialog(ConditionsActivity.this);
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected Void doInBackground(Void... arg0) {
                // Using HttpHandler class
                HttpHandler sh = new HttpHandler();

                // Url of json (using firebase)
                String url = "https://conditions-e873e-default-rtdb.europe-west1.firebasedatabase.app/conditions.json";

                // Making a request to url and getting response
                String jsonStr = sh.makeServiceCall(url);

                // Showing response from url
                Log.e(TAG, "Response from url: " + jsonStr);

                // jsonStr should not be empty!
                if (jsonStr != null) {
                    try {
                        JSONArray jsonarray = new JSONArray(jsonStr);

                        // looping through all conditions
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject c = jsonarray.getJSONObject(i);

                            String id = c.getString("id");
                            String name = c.getString("name");
                            String desc = c.getString("desc");
                            String date = c.getString("date");

                            // tmp hash map for single contact
                            HashMap<String, String> condition = new HashMap<>();

                            // adding each child node to HashMap key => value
                            condition.put("id", id);
                            condition.put("name", name);
                            condition.put("desc", desc);
                            condition.put("date", date);

                            // Adding condition to list of conditions
                            conditionsList.add(condition);

                        }
                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                        // Parsing error exception
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        });

                    }
                } else {
                    Log.e(TAG, "Couldn't get json from server.");

                    // Cannot get json (wrong url, uncertified server) - Empty jsonStr
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Couldn't get json from server. Check LogCat for possible errors!",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);

                // Dismiss the progress dialog when the conditions are shown
                if (pDialog.isShowing())
                    pDialog.dismiss();

                // Showing each condition in ListView (lv) using ListAdapter
                ListAdapter adapter = new SimpleAdapter(
                        ConditionsActivity.this, conditionsList,
                        R.layout.condition, new String[]{"name", "id", "desc",
                        "date"}, new int[]{R.id.nameOfCondition, R.id.idOfCondition,
                        R.id.descOfCondition, R.id.dateOfCondition});

                lv.setAdapter(adapter);
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