package com.myqueue.myqueue.APIs;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;

/**
 * Created by DedeEko on 3/28/2016.
 */
public class BaseAPI {
    public static RestAdapter getGoogleGeocodeAPI(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://maps.googleapis.com")
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setLog(new AndroidLog("MyQueue"))
                .build();
        return restAdapter;
    }
}
