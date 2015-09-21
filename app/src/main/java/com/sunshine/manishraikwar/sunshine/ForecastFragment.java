package com.sunshine.manishraikwar.sunshine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ForecastFragment extends Fragment {

        private ArrayAdapter<String> mForecastAdapter;

        public ForecastFragment(){}
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView =inflater.inflate(R.layout.fragment_main, container, false);

            String[] forecastArray = {
                    "Today -  Sunny - 88/63",
                    "Tomorrow - Foggy - 70/40",
                    "Wed - Cloudy - 72/63",
                    "Thurs - Asteroids - 75/65",
                    "Fri - Heavy Rain",
                    "Sat - Help Trapped In weatherStation - 60/51",
                    "Sun - Sunny - 80/68",

            };
            List<String> weekForecast = new ArrayList<String>(Arrays.asList(forecastArray));

            mForecastAdapter =
                    new ArrayAdapter<String>(getActivity(),
                            R.layout.list_item_forecast,
                            R.id.list_item_forecast_textview, weekForecast);
            ListView listView =(ListView) rootView.findViewById(R.id.listview_forecast);
            listView.setAdapter(mForecastAdapter);
            HttpURLConnection urlConnection =null;
            BufferedReader reader = null;
            //will contsin the raw JSON response as a string.
            String forecastJsonStr = null;
            try{
             // construct the url for the OpenWeatherMap query
                //Possible parameter are available at OWM's forecast API page,at
                //http:openweathermap.org/API#forecast

                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7");
            //create the request to OpenweatherMap , and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            //read the input stream into a String

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null)
            { // Nothing to do.
               forecastJsonStr = null;
            }
        reader = new BufferedReader(new InputStreamReader(inputStream));
    String line;
    while((line =reader.readLine())!=null)
        {
        //Since its JSON. adding a newline isn't necessary(it won't affect parsing)
        // But it does make debugging a *lot* easier if you print out the completed
        // buffer for debugging.
    buffer.append(line +"\n");

        }
    if(buffer.length() == 0) {
        //String was empty. No point in parsing.

        forecastJsonStr = null;
        }
        forecastJsonStr =buffer.toString();
    }
    catch(IOException e)
    {
        Log.e("PlaceholderFragment","Error",e);
        //if the code didn't succesfully get the weather data, there's no point in attemping
        // to parse it.
          forecastJsonStr = null;

        } finally
    {
        if (urlConnection != null)
        {
            urlConnection.disconnect();
        }


    if (reader !=null) {
        try {
            reader.close();
        }
        catch (final IOException e)
        {
            Log.e("PlaceholderFragment", "Error closing stream", e);
        }
    }}
        return rootView;
        }
        }