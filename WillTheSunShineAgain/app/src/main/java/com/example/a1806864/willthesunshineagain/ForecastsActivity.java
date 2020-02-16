//This activity handles the displaying of the forecasts

//Defining the project the class belongs to
package com.example.a1806864.willthesunshineagain;

//Importing the tools we will use for this class
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.List;
import java.util.ArrayList;

public class ForecastsActivity extends AppCompatActivity implements View.OnClickListener {
    ForecastsDAO forecastsDAO; //Creating a new instance of forecast DAO
    private static final String TAG = "WillTheSunShineAgain.MainActivity"; //This will be used later to help us print to the logcat for debugging purposes
    private RecyclerAdapter adapter; //Defining our global variable 'adapter' which will help us work with the RecyclerView
    private RecyclerView recyclerView; //Creates an instance of a recycler view
    List<Forecasts> forecasts = new ArrayList<>(); //Defining a list of forecasts to hold our forecasts later
    public static String deleteLocation; //Defining a public variable deleteLocation that will be used to delete forecasts for a location, accessed by the Recycler Adapter
    public static String deleteTime; //Defining a public variable deleteLocation that will be used to delete forecasts for a time, accessed by the Recycler Adapter

    //This method handles the app-creation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //Recover state
        setContentView(R.layout.activity_forecasts); //Getting the view from the XML directory

        //Set the on-click listeners for our controls

        //Creates a new instance of the search button
        Button btnSwitch = findViewById(R.id.btn_search);
        btnSwitch.setOnClickListener(this); //Sets the on-click listener for the search button

        //Creates a new instance of the delete button
        Button btnSwitch3 = findViewById(R.id.btn_delete);
        btnSwitch3.setOnClickListener(this); //Sets the on-click listener for the delete button

        //Creates a new instance of the refresh button
        Button btnSwitch4 = findViewById(R.id.btn_refresh);
        btnSwitch4.setOnClickListener(this); //Sets the on-click listener for the refresh button

        //Getting the forecasts database
        ForecastsDatabase database = ForecastsDatabase.getDatabase(getApplicationContext());

        //Instantiating the forecasts DAO
        forecastsDAO = database.forecastsDAO();

        //Gets the intent that started the activity
        Intent started = getIntent();

        //Check if the intent contains data
        if (started == null) {
            //Intent not caused by Search Activity
            forecasts = forecastsDAO.getForecasts(); //So get all the forecasts from the database
        }else {
            //Check if the intent has extras
            if (started.hasExtra(SearchActivity.EXTRA_NAME)) {
                //Intent has extras so fetch the data from the intent
                Forecasts forecast = getIntent().getExtras().getParcelable(SearchActivity.EXTRA_NAME);
                forecasts.add(forecast); //Save forecast to our list of forecasts
            } else{
                //Intent has no attached data so get all forecasts from database.
                forecasts = forecastsDAO.getForecasts();}
        }

        //Refresh the recycler view to display the data
        refresh();
    }

    //This method handles the clicking on the activity
    public void onClick(View v) {
        //Determine which control was clicked

        //Checking if the search button was clicked
        if (v.getId() == R.id.btn_search) {
            //Search button was clicked so switch to search activity

            //Getting the activity context
            Context context = ForecastsActivity.this;

            //Setting the destination activity
            Class destinationActivity = SearchActivity.class;

            //Creating the intent to go to search activity
            Intent goto_search = new Intent(context, destinationActivity);

            // Going to the search activity
            startActivity(goto_search);
        }

        //Checking if the delete button was clicked
        if (v.getId() == R.id.btn_delete) {
            //Delete button was clicked
            deleteForecast(); //Delete the selected forecast
        }

        //Checking if the refresh button was clicked
        if (v.getId() == R.id.btn_refresh) {
            //Refresh button was clicked so refresh the screen with all the forecasts

            //Get all the forecasts from the database
            forecasts = forecastsDAO.getForecasts();

            //Refresh the screen with all the forecasts
            refresh();

            //Informs the user that the screen has refreshed
            displayToast("Display updated");
        }
    }

    //This method deletes forecasts from the database
    private void deleteForecast(){
        //Try to delete the forecast
        try {
            forecastsDAO.delete(deleteLocation, deleteTime); //Calling the DAO delete method to delete the forecast for at a time in a location
            displayToast("Forecast deleted!"); //Alerting the user of deletion success.
        }catch(Exception e){
            e.printStackTrace(); //Printing error log to logcat
            displayToast("Forecast failed to delete!"); //Alerting the user of deletion failure
        }

        //Gets the updated database
        List<Forecasts> updated = forecastsDAO.getForecasts();

        //Refreshes the display with the updated data
        adapter.setData(updated);
    }

    //This method displays toast messages to the screen
    private void displayToast(String msg)
    {
        //Display a toast
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }

    //This method refreshes the display with forecasts
    private void refresh(){

        //Getting a recycler view handle
        recyclerView = findViewById(R.id.recycler);

        //Creating a recycler view adapter
        adapter = new RecyclerAdapter(this, forecasts);

        //Connecting the adapter with the recycler view
        recyclerView.setAdapter(adapter);

        //Setting a layout manager for the recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}