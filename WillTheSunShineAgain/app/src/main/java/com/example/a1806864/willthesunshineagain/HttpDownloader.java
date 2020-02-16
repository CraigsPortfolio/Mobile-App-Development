package com.example.a1806864.willthesunshineagain;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HttpDownloader {

    /**#
     * This method returns the entire result from the HTTP response.*
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */

    //This method downloads the data from the API
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        //Connecting to the DarkSky API
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            //Gets the data from the connection
            InputStream in = urlConnection.getInputStream();

            //Creates a new scanner to scan our download for data
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            //Creates a variable to check if the download was a success
            boolean hasInput = scanner.hasNext();

            if (hasInput) {
                //Downloaded content contains data
                return scanner.next(); //Returning data
            } else {
                //Downloaded content contains nothing
                return null; //Returning nothing
            }
        } finally {
            //Disconnect from DarkSky API
            urlConnection.disconnect();
        }
    }
}
