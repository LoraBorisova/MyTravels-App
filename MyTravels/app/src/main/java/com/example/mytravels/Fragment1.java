package com.example.mytravels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


// Preference Fragment to change settings
public class Fragment1 extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        final SwitchPreferenceCompat notificationsRef = findPreference("notifications");
        // Notifications preferences
        if (notificationsRef != null) {
            notificationsRef.setSummaryProvider(new Preference.SummaryProvider<SwitchPreferenceCompat>() {
                @Override
                public CharSequence provideSummary(SwitchPreferenceCompat preference) {
                    if (notificationsRef.isChecked()) {

                        return "Active";
                    } else {
                        return "Inactive";
                    }
                }
            });
        }
        // Feedback using implicit intent to change the screen when clicking on feedback
        Preference feedbackPref = findPreference("feedback");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://write-box.appspot.com/"));
        if (feedbackPref != null) {
            feedbackPref.setIntent(intent);
        }
    }

}