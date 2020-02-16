//This is the main activity for the application. It will be the activity seen when the application is launched and the other activities will be accessed from this one.
//This activity will allow us to download the forecasts for each of our locations.

//Defines what project this activity is a part of
package com.example.a1806864.willthesunshineagain;

//Importing the tools we will need for this class.
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ForecastsDAO forecastsDAO;

    //Setting the tag which will be used to print errors to the Logcat to help with debugging.
    private static final String TAG = "WillTheSunShineAgain.MainActivity";

    //A set of co-ordinates will be appended to this string later in order to request data from the Dark Sky API.
    private  String JSON_WEB_SERVICE_ADDRESS = "https://api.darksky.net/forecast/8785c1badc7ab3be0e0603fed739a14d/";

    //Defining a new array of strings which will hold all of the web addresses to make our API requests
    ArrayList<String> locations=new ArrayList<String>();

    //Progress bar spinner to indicate working in the background
    private ProgressBar progressBar;


    @Override
    //This method will be called when the application is created (started-up).
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        //Prints a message to Logcat to know which method the program is currently running.
        Log.d(TAG, "In the onCreate event handler");

        //Creating new instances of buttons so we can listen for button clicks within our activity
        //Setting our button on-click listeners to allow us to work with our buttons.

        //Sets a new instance of the location preferences button
        Button btnSwitch = findViewById(R.id.btn_location_preferences);
        btnSwitch.setOnClickListener(this); //Sets the on-click listener for the location preferences button

        //Sets a new instance of the download button
        Button btnSwitch2 = findViewById(R.id.btn_download);
        btnSwitch2.setOnClickListener(this); //Sets the on-click listener for the download button

        //Sets a new instance of the view forecasts button.
        Button btnSwitch4 = findViewById(R.id.btn_view_forecasts);
        btnSwitch4.setOnClickListener(this); //Sets the on-click listener for the view forecasts button

        progressBar = findViewById(R.id.progressBar);

        //Getting the forecasts database that holds our saved forecasts.
        ForecastsDatabase database = ForecastsDatabase.getDatabase(getApplicationContext());

        //Instantiating our Forecasts DAO.
        forecastsDAO = database.forecastsDAO();

        //Fetching our saved location preferences
        //We will iterate for each preference and con-catenate them with the JSON Web Service Address
        //Then store them in our locations array so we can access our API later.
        for(int i=1; i<=10; i++) {
            String x = Integer.toString(i); //Gets a string version of the index called 'x' so we can search for our preference name
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()); //Gets our preferences and stores them in a variable called 'sharePref'.
            String location = sharedPref.getString("saved_location" + x, ""); //Searches for each of our preferences withing our 'sharedPref' variable and stores them in our 'location' variable.
            locations.add(JSON_WEB_SERVICE_ADDRESS + location); //Adds our web address to the array which will be used later.
        }
    }

    @Override
    //This method is called when we click a view in our activity.
    public void onClick(View v) {
        //We will check if the user clicked any of our buttons.

        //Checks if the user clicked 'Location Preferences'.
        if (v.getId() == R.id.btn_location_preferences) {
            //'Location Preferences' button was clicked so we will carry out the steps to switch to the 'Preferred Locations' activity.

            //Gets our activity context
            Context context = MainActivity.this;

            //Defining a class object called 'destinationActivity' that will be set to our PreferredLocations class.
            Class destinationActivity = PreferredLocations.class;

            //Creating an intent to go to preferred locations activity.
            Intent goto_preferred_locations = new Intent(context, destinationActivity);

            //Starting preferred locations activity.
            startActivity(goto_preferred_locations);
        }

        //Checks if the user clicked 'Download'.
        if (v.getId() == R.id.btn_download) {
            //'Download' button was clicked so we will carry out our download.
            Boolean failed = false;
            //Iterates for each of our ten preferences
            for(int i=0; i<=9; i++) {
                //Creates a new instance of our HttpDownloaderTask.
                HttpDownloaderTask task = new HttpDownloaderTask();



                //Attempt to download the data from the API.
                try {
                    //Sets a URL variable called 'downloadURL' to the address for the location in this iteration
                    URL downloadURL = new URL(Uri.parse(locations.get(i)).toString());

                    //Downloads the data from the API.
                    task.execute(downloadURL);

                } catch (MalformedURLException e) {
                    //Catch handles the event that a download fails.

                    //Print an error message to the log.
                    e.printStackTrace();
                    failed = true;
                }
            }

            //Inform the user of download outcome
            if(failed) {
                //Display a toast to the user to alert them that the download has failed.
                displayToast("Forecasts failed to download.");
            }else{
                //Displays a toast to alert the user of a successful download.
                displayToast("Forecasts downloaded successfully!");
            }
        }

        //Checks if the 'View Forecasts' button was clicked.
        if (v.getId() == R.id.btn_view_forecasts) {
            //'View Forecasts' button was clicked so we will begin switching to the 'View Forecasts' activity.

            //Get the activity context.
            Context context = MainActivity.this;

            //Setting our destination as 'Forecasts Activity'.
            Class destinationActivity = ForecastsActivity.class;

            //Creating an intent to start 'Forecasts Activity'.
            Intent goto_preferred_locations = new Intent(context, destinationActivity);

            //Starting the Forecasts Activity.
            startActivity(goto_preferred_locations);
        }
        }

    //This class will handle the downloaded data from the API.
    class HttpDownloaderTask extends AsyncTask<URL, Void, List<Forecasts>> {

        @Override
        protected void onPreExecute() {
            //Show the progress bar to show something is happening
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(List<Forecasts> forecasts) {
            //This method occurs after the data is downloaded.
            super.onPostExecute(forecasts);

            //Prints a message to the Logcat so we know which method the program is currently executing.
            Log.d(TAG, String.format("onPostExecute %s", forecasts));

            //Hide the progress spinner as data downloaded
            progressBar.setVisibility(View.INVISIBLE);


            //Here we will save our forecasts to the database

            //This will be used to help us get the correct preference.
            Integer i = 1;

            //We will loop through each of our forecasts for the current location
            for (Forecasts fl : forecasts){
               Log.d(TAG, "fl===" + fl.toString());

               //Converting the iteration number to a string so we can use it to recieve our preferences.
               String x = Integer.toString(i);

               //Fetching our preferences into the 'sharedPref' variable.
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                //Fetching our current preference from the 'sharedPref' variable.
                String location = sharedPref.getString("saved_location" + x, "");
                Log.d(TAG, location);

                //Setting the location attribute for our current forecast.
               fl.setLocation(location);

               //Saving our forecast to the database.
               addForecast(fl);
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
           Log.d(TAG, "onProgressUpdate");
        }


        @Override
        //This method will handle the downloading of our data from the API.
        protected List<Forecasts> doInBackground(URL... urls) {
            //Printing a message to the Logcat so we know which method we are in when debugging.
            Log.d(TAG, "doInBackground");

            //Assuming there is a URL in the parameters.
            URL downloadUrl = urls[0];

            //Declaring and initiating our 'downloaded' variable which will hold our downloaded JSON.
            String downloaded = null;

            //Attempt to download the data.
            try {
                //Getting the JSON from the API and storing it in our 'downloaded' variable.
                downloaded = HttpDownloader.getResponseFromHttpUrl(downloadUrl);
            } catch (IOException ioe) {
                //The catch statement handles the event that the data fails to download.

                //Prints a message to the error log containing details on the fault.
                ioe.printStackTrace();
            }

            //Creating an array of objects to hold our processed forecasts.
            List<Forecasts> forecasts = null;

            //Checks to make sure downloaded data exists.
            if (downloaded != null) {
                //Calling the 'processJson' method from our 'JSON Handler' class to parse and process our forecasts for user digestion.
                forecasts = JsonHandler.processJson(downloaded);
            }

            //Returning our packaged forecasts to the calling method.
            return forecasts;

        }
    }

    //This method inserts the forecasts to the database.
    private void addForecast(Forecasts forecasts) {
        //Calling the 'insert' method in the Forecasts DAO class to insert the forecasts.
        forecastsDAO.insert(forecasts);
    }

    //This method displays a message in the form of a toast.
    private void displayToast(String msg)
    {
        //Displaying a toast
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }
    }







