package me.kirkhorn.knut.assignment_7;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Knut on 11.11.2017.
 */

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
