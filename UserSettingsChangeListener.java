package com.example.mido.fileiosharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by Mina on 5/15/2017.
 */

public class UserSettingsChangeListener implements SharedPreferences.OnSharedPreferenceChangeListener {
    private Context context;
    public UserSettingsChangeListener(Context context) {
        this.context=context;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Toast.makeText(context,s+" Value changed:",Toast.LENGTH_SHORT).show();
    }
}
