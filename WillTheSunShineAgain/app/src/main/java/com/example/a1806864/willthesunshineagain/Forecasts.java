//This class is the entity for our database

//Defines the project that this class belongs to
package com.example.a1806864.willthesunshineagain;

//Import the tools we will be using
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

//Setting the table name for our entity/table
@Entity(tableName = "Forecasts")
public class Forecasts implements Parcelable {

    @NonNull //Primary key cannot be NULL
    @PrimaryKey(autoGenerate = true) //Primary ID will be set automatically
    public int uid; //uid is our primary key

    //Setting up the rest of the table attributes
    private String time;
    private String summary;
    private String windSpeed;
    private String windBearing;
    private String humidity;
    private String temperatureMax;
    private String location;

    public Forecasts() {
        super();
    }

    //Creating the getters and setters for our class

    //Uid getters and setters
    @NonNull
    public int getUid() {
        return uid;
    } //Getter

    public void setUid(@NonNull int uid) {
        this.uid = uid;
    } //Setter


    //Location getters and setters
    public String getLocation() {
        return location;
    } //Getter

    public void setLocation(String location) {
        this.location = location;
    } //Setter

    //Time getters and setters
    public String getTime() {
        return time;
    } //Getter

    public void setTime(String time) {
        this.time = time;
    } //Setter

    //Summary getters and setters
    public String getSummary() {
        return summary;
    } //Getter

    public void setSummary(String summary) {
        this.summary = summary;
    } //Setter

    //Wind speed getters and setters
    public String getWindSpeed() {
        return windSpeed;
    } //Getter

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    } //Setter

    //Wind bearing getters and setters
    public String getWindBearing() {
        return windBearing;
    } //Getter

    public void setWindBearing(String windBearing) {
        this.windBearing = windBearing;
    } //Setter

    //Humidity getters and setters
    public String getHumidity() {
        return humidity;
    } //Getter

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    } //Setter

    //Temperature getters and setters
    public String getTemperatureMax() {
        return temperatureMax;
    } //Getter

    public void setTemperatureMax(String temperatureMax) {this.temperatureMax = temperatureMax; } //Setter


    //Prints the data held for an instance of the class
    @Override
    public String toString() {
        return "Forecast{" +
                "uid=" + uid +
                ", location=" + location +
                ", time='" + time + '\'' +
                ", summary=" + summary +
                ", wind speed=" + windSpeed +
                ", wind bearing=" + windBearing +
                ", humidity=" + humidity +
                ", temperatureMax=" + temperatureMax +
                '}';
    }

    //Now we will allow the forecast to be sent as a parcel for when we want to send forecasts between activities later

    /**
     * Creates a new Profile from a Parcel
     * @param in
     */

    //This method allows us to read from a parcel
    private Forecasts(Parcel in) {
        location = in.readString();
        time = in.readString();
        temperatureMax = in.readString();
        humidity = in.readString();
        windSpeed = in.readString();
        windBearing = in.readString();
        summary = in.readString();
    }

    //This method is needed for our parcel
    public int describeContents() {
        return 0;
    }
    /**
     * Writes this Profile to a Parcel
     * @param out
     * @param flags
     */

    //This method writes our class to a parcel
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(location);
        out.writeString(time);
        out.writeString(temperatureMax);
        out.writeString(humidity);
        out.writeString(windSpeed);
        out.writeString(windBearing);
        out.writeString(summary);
    }

    //Sets the parcel creator to allow us to create a parcel later
    public static final Parcelable.Creator<Forecasts> CREATOR = new Parcelable.Creator<Forecasts>() {
        public Forecasts createFromParcel(Parcel in) {
            return new Forecasts(in);
        }
        public Forecasts[] newArray(int size) {
            return new Forecasts[size];
        }
    };
}