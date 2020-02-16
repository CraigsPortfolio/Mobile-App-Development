//This activity handles the searching of forecasts in the database

//Defining the project that the class belongs to
package com.example.a1806864.willthesunshineagain;

//Importing the tools we will be using
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    //Setting a tag that will be used later to print logs to the logcat
    private static final String TAG = "WillTheSunShineAgain.MainActivity";

    //Creating an instance of the forecasts DAO
    ForecastsDAO forecastsDAO;

    //Setting the intent extra name that will be used later to send data to ForecastsActivity
    public static final String EXTRA_NAME = "SEARCH";

    //This method handles the creation of the app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //Gets the saved state
        setContentView(R.layout.activity_search); //Sets the view from the XML directory

        //Setting an instance of our search button
        Button btnSwitch = findViewById(R.id.btn_search);
        btnSwitch.setOnClickListener(this); //Setting an on-click listener for our button

        //Getting our database
        ForecastsDatabase database = ForecastsDatabase.getDatabase(getApplicationContext());

        //Instantiating our DAO
        forecastsDAO = database.forecastsDAO();
    }

    //This method handles the clicking of the activity views
    public void onClick(View v) {
        //Checks if the search button is clicked
        if (v.getId() == R.id.btn_search) {
            //Search button is clicked
            setQuery(); //Search for the forecast
        }
    }

    //This method searches for a forecast with the given data
    private void setQuery(){
        //Creating instances of our edit texts
        EditText etL = findViewById(R.id.et_location);
        EditText etTi = findViewById(R.id.et_time);
        EditText etTe= findViewById(R.id.et_temp);
        EditText etSp = findViewById(R.id.et_speed);
        EditText etD = findViewById(R.id.et_direction);
        EditText etSu = findViewById(R.id.et_summary);
        EditText etH = findViewById(R.id.et_humidity);

        //Calling the forecasts DAO search method using the edit text contents as parameters and saving the results in a forecast object
        Forecasts saved = new Forecasts();
        saved = forecastsDAO.search(etL.getText().toString(), etTi.getText().toString(), etTe.getText().toString(), etH.getText().toString(), etSp.getText().toString(), etD.getText().toString(), etSu.getText().toString());

        //Checking if any forecasts were found
        if(saved != null){
            //Forecasts were found so return to the Forecasts Activity with search results
            Context context = SearchActivity.this;

            //Setting the destination activity
            Class destinationActivity = ForecastsActivity.class;

            //Setting the intent to take us back to Forecasts Activity
            Intent intent = new Intent(context, destinationActivity);

            //Sending our search results back to the forecasts activity
            intent.putExtra(EXTRA_NAME, saved);

            //Starting the forecasts activity
            startActivity(intent);
        } else{
            //No forecasts found
            displayToast("Forecast with this information does not exist, please check the data entered and try again!");} //Display a message to the user informing them of results
        }

    //This method displays a message in the form of a toast.
    private void displayToast(String msg)
    {
        //Displaying a toast
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }
    }

