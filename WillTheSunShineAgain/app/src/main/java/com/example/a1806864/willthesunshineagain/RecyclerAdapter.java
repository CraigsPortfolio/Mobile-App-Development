//This class handles our recycler view in the forecasts activity

//Defins the project that the class belongs to
package com.example.a1806864.willthesunshineagain;

//Imports the tools we will be using
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} for the {@link ForecastsViewHolder} that will be used by a {@link RecyclerView} to display details of a {@link Forecasts}
 *
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ForecastsViewHolder> {

    //Holder for the data to be displayed
    private List<Forecasts> forecasts;

    //Will be used to inflate the views displayed by the recycler view
    private LayoutInflater inflater;

    //Creates a recycler view adapter
    public RecyclerAdapter(Context context, List<Forecasts> data){
        //Instantiating the member variables
        inflater = LayoutInflater.from(context);
        forecasts = data;
    }

    /**
     * Called when it is necessary to create a new ViewHolder.
     * @param parent
     * @param viewType
     * @return
     */

    @NonNull
    @Override
    public ForecastsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view by inflating the layout defined in recycler_item.xml
        View itemView = inflater.inflate(R.layout.recycler_item, parent, false );
        return new ForecastsViewHolder(itemView, this);
    }

    /**
     * Called when binding a {@link ForecastsViewHolder} to the item at position in this adapter's data
     * @param forecastViewHolder
     * @param position
     */

    @Override
    public void onBindViewHolder(@NonNull ForecastsViewHolder forecastViewHolder, int position) {
        //Retrieving the Forecast at position from the data list
        Forecasts currentForecast = forecasts.get(position);

        //Retrieve the TextViews from the View that forecastViewHolder is holding
        TextView summary = forecastViewHolder.forecastsItemView.findViewById(R.id.tv_summary);
        TextView location = forecastViewHolder.forecastsItemView.findViewById(R.id.tv_location);
        TextView temp = forecastViewHolder.forecastsItemView.findViewById(R.id.tv_temp);
        TextView humidity = forecastViewHolder.forecastsItemView.findViewById(R.id.tv_humidity);
        TextView speed = forecastViewHolder.forecastsItemView.findViewById(R.id.tv_wind_direction);
        TextView direction = forecastViewHolder.forecastsItemView.findViewById(R.id.tv_direction);

        //Displaying the current forecast
        summary.setText(String.format("%s", currentForecast.getSummary()));
        location.setText(String.format("%s", "Location:" + currentForecast.getLocation() + " ,Time:" + currentForecast.getTime()));
        temp.setText(String.format("%s", currentForecast.getTemperatureMax() + "°F"));
        humidity.setText(String.format("%s", currentForecast.getHumidity()   + "%"));
        speed.setText(String.format("%s", currentForecast.getWindSpeed() + "mph"));
        direction.setText(String.format("%s", currentForecast.getWindBearing() + "°"));
    }

    /**
     * Returns the number of data items held by this adapter
     */
    @Override
    public int getItemCount() {
        return forecasts.size();
    }

    /**
     * Called to set the data that is being held by this adapter
     * @param forecasts
     */
    public void setData(List<Forecasts> forecasts){
        this.forecasts = forecasts;
        //This will force the contents of the Recyclerview to be updated for the new data
        notifyDataSetChanged();
    }

    /**
     * A {@link android.support.v7.widget.RecyclerView.ViewHolder} for {@link Forecasts}s
     */
    class ForecastsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private View forecastsItemView;
        private RecyclerAdapter adapter;

        public ForecastsViewHolder(View itemView, RecyclerAdapter adapter){
            super(itemView);
            forecastsItemView = itemView;
            this.adapter = adapter;
            forecastsItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //Getting the clicked forecast's position
            int position = getAdapterPosition();

            //Getting the Forecast that was clicked
            Forecasts clickedForecastLocation = forecasts.get(position);

            //Creating a toast to display a message containing the forecast  selection
            Toast toast = Toast.makeText(v.getContext(), "You selected \nLocation:" + clickedForecastLocation.getLocation() + "\n Time:" +clickedForecastLocation.getTime(), Toast.LENGTH_LONG);
            toast.show(); //Displaying the toast

            //Setting the selected forecast location and time so that the ForecastsActivity can delete the correct forecast
            ForecastsActivity.deleteLocation = clickedForecastLocation.getLocation();
            ForecastsActivity.deleteTime   = clickedForecastLocation.getTime();
        }
    }
}
