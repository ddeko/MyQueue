package com.myqueue.myqueue.APIs;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

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

        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);

        RestAdapter getAPI = new RestAdapter.Builder()
                .setEndpoint(ROOT)
                .setClient(new OkClient(new OkHttpClient()))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        return getAPI;
    }
}