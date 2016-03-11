package com.myqueue.myqueue.APIs;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by 高橋六羽 on 2016/03/11.
 */
public class RestClient {

    private static String ROOT =
            "http://hnwtvc.com/myqueue";

    static {
        setupRestClient();
    }

    private RestClient() {}

    public static RestAdapter setupRestClient() {
        RestAdapter getAPI = new RestAdapter.Builder()
                .setEndpoint(ROOT)
                .setClient(new OkClient(new OkHttpClient()))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        return getAPI;
    }
}