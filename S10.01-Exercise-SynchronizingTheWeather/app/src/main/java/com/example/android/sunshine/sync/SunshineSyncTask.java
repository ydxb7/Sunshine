package com.example.android.sunshine.sync;


import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.example.android.sunshine.MainActivity;
import com.example.android.sunshine.data.SunshinePreferences;
import com.example.android.sunshine.data.WeatherContract;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;


import java.net.URL;

//  TODO (1) Create a class called SunshineSyncTask
public class SunshineSyncTask {
    //  TODO (2) Within SunshineSyncTask, create a synchronized public static void method called syncWeather
    public static void syncWeather(Context context) {
//      TODO (3) Within syncWeather, fetch new weather data
        URL weatherRequestUrl = NetworkUtils.getUrl(context);

        try {
            String jsonWeatherResponse = NetworkUtils
                    .getResponseFromHttpUrl(weatherRequestUrl);

            ContentValues[] weatherContentValues = OpenWeatherJsonUtils
                    .getWeatherContentValuesFromJson(context, jsonWeatherResponse);

            //      TODO (4) If we have valid results, delete the old data and insert the new
            if(weatherContentValues != null && weatherContentValues.length > 0){
                Uri uri = WeatherContract.WeatherEntry.CONTENT_URI;
                int deletedRows = context.getContentResolver().delete(uri, null, null);

                context.getContentResolver().bulkInsert(uri, weatherContentValues);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}