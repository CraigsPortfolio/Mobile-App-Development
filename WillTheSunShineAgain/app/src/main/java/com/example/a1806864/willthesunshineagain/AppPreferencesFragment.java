//This class acts as an interface between the XML preferences and the activity
//By 'inflating' the XML

//Defines the project that this fragment is part of
package com.example.a1806864.willthesunshineagain;

//Importing the tools we will be using
import android.support.v7.preference.PreferenceFragmentCompat;
import android.os.Bundle;

public class AppPreferencesFragment extends PreferenceFragmentCompat {
    public void onCreatePreferences(Bundle bundle, String rootKey){
        //Sets the preferences from the XML file
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
