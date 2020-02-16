//This activity is where the user will manage their saved locations

//Defines the project that the class belongs to
package com.example.a1806864.willthesunshineagain;

//Imports the tools that we will be using
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class PreferredLocations extends AppCompatActivity implements View.OnClickListener{

    //This tag will be usedto in the LogCat to debug our code.
    private static final String TAG = "lab06.exercise1.ChildActivity";

    //This method will run when the activity is launched
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Getting the saved state
        super.onCreate(savedInstanceState);

        //Setting the layout
        setContentView(R.layout.activity_preferred_locations);

       //Telling the logcat the onCreate() method has been activated
        Log.d(TAG, "In the onCreate event handler");

        //Display fragment as main content
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new AppPreferencesFragment())
                .commit();
        PreferenceManager
                .setDefaultValues(getApplicationContext(),
                        R.xml.preferences,false);
    }

    //Handles the clicking of our widgets
    @Override
    public void onClick(View v) {

        //Getting the context of the current activity
        Context context = PreferredLocations.this;

        //Setting the destinatio activity as the main activity
        Class destinationActivity = MainActivity.class;

        //Creating an intent to start main activity
        Intent goto_home = new Intent(context, destinationActivity);

        //Starting main activity
        startActivity(goto_home);
    }
}
