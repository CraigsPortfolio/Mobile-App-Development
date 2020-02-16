//This is our database DAO that will help us work with the database

//Defines the project our class is part of
package com.example.a1806864.willthesunshineagain;

//Imports the tools we will be using in this class
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;

//Defining our DAO name
@Dao
public interface ForecastsDAO {

    //This Insert command inserts a single forecast to the database
    @Insert
    public void insert(Forecasts forecasts);

    //This Query gets all the forecasts from the database, newest > oldest
    @Query("SELECT * FROM Forecasts ORDER BY uid DESC")
    public List<Forecasts> getForecasts();

    @Query("DELETE FROM Forecasts WHERE location LIKE :location AND time LIKE :time " )
    public void delete(String location, String time);

    //This Query searches the database for certain forecasts the user wants
    @Query("SELECT * FROM Forecasts WHERE location LIKE :location AND time LIKE :time AND humidity LIKE :humidity AND windSpeed LIKE :speed AND windBearing LIKE :direction AND summary LIKE :summary AND temperatureMax LIKE :temp")
    public Forecasts search(String location, String time, String temp, String humidity, String speed, String direction, String summary);
}
