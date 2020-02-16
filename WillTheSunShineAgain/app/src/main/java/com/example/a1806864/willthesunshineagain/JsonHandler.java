//This class parsers the downloaded JSON for the data we need and packages it in a form the user will understand
//Defining what project this class belongs to
package com.example.a1806864.willthesunshineagain;

//Importing everything we will need
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JsonHandler {
    //This tag will be referenced in the Logcat to help us debug our code
    private static final String TAG = "WillTheSunShineAgain.JsonHandler";

    //Function that will be called to parse the JSON
    public static List<Forecasts> processJson(String s){
        //This list will hold all the parsed forecasts
        List<Forecasts> locations = new ArrayList<Forecasts>();

        try {
            //Creates a new JSON object that will be used to hold the weeks forecasts
            JSONObject locationsObj = new JSONObject(s);

            //Sets the object to the week's worth of forecasts
            JSONObject locationObj = locationsObj.getJSONObject("daily");

            //Creates a JSON array that will be used to hold the days forecasts pulled from the week
            JSONArray locationArray = locationObj.getJSONArray("data");

            Log.d(TAG, "Value =" + Integer.toString(locationArray.length()));

            //We will loop through the array and process each day every iteration
            for (int i = 0, j = locationArray.length(); i < j; i++){
                //Creates a new instance of the Forecasts class
                Forecasts forecasts = new Forecasts();

                //Sets the JSON object to the day from the array
                JSONObject location = locationArray.getJSONObject(i);

                //We will now begin pulling the data from the object

                //Gets the summary from the object
                String summary = location.getString("summary");
                forecasts.setSummary(summary); //Sets the summary for our current forecast as the summary fetched

                //Gets the wind speed from the object
                String windSpeed = location.getString("windSpeed");
                forecasts.setWindSpeed(windSpeed); //Sets the wind speed for our current forecast as the wind speed fetched

                //Gets the wind direction from the object
                String windDirection = location.getString("windBearing");
                forecasts.setWindBearing(windDirection); //Sets the wind bearing for our current forecast as the wind bearing fetched

                //Gets the max temperature from the object
                String temperature = location.getString("temperatureMax");
                forecasts.setTemperatureMax(temperature); //Sets the temperature for our current forecast as the temperature fetched

                //Gets the humidity from the object
                String humidity = location.getString("humidity");
                forecasts.setHumidity(Double.toString (Double.parseDouble(humidity)* 100)); //Sets the humidity for our current forecast as the humidity fetched

                //Gets the time from the object
                String time = location.getString("time");
                Long time2 = Long.parseLong(time); //Converts time to a long data type
                Date date = new java.util.Date(time2*1000L); //Converts the time to a date

                //Formatting the date
                SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");

                //Give a timezone reference for formatting
                sdf.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));

                //Converting date into a string
                String formattedDate = sdf.format(date);

                forecasts.setTime(formattedDate); //Sets the time for our current forecast as the time fetched

                //Adds the processed forecast to the array
                locations.add(forecasts);
            }
        } catch (JSONException e) { e.printStackTrace(); } //Prints an error message to the log if the process fails

        Log.d(TAG, "Location: " + locations);
        //Returns the processed forecast locations to the main activity
        return locations;
    }
}