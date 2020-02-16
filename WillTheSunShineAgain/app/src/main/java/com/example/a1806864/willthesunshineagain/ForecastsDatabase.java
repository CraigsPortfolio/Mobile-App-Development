//This class contains our Room database that holds our saved forecasts.

//Defines what project the class belongs to.
package com.example.a1806864.willthesunshineagain;

//Importing the tools needed for this class.
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import android.content.Context;

@Database(entities = {Forecasts.class}, version = 1) //Adding our Forecasts entity (table) to the database

public abstract class ForecastsDatabase extends RoomDatabase {

    //Connecting our DAO to the database
    public abstract ForecastsDAO forecastsDAO();

    //Creates an instance of our database
    private static ForecastsDatabase INSTANCE;

    //Setting up our database
    public static ForecastsDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (ForecastsDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            ForecastsDatabase.class,
                            "forecasts_database")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
