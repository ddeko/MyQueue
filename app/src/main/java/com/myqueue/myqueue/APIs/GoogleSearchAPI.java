package com.myqueue.myqueue.APIs;



import com.myqueue.myqueue.Models.address.ArrayofSearchResultBean;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by frensky on 8/7/15.
 */
public interface GoogleSearchAPI {

    @GET("/maps/api/geocode/json?result_type=route&key="+ "AIzaSyBW0l8K6qd9f16aaAH1Y0LMfoyJM_TDsB8")
    public ArrayofSearchResultBean getLocation(@Query("latlng") String latlng);

    @GET("/maps/api/geocode/json?result_type=route&key="+ "AIzaSyBW0l8K6qd9f16aaAH1Y0LMfoyJM_TDsB8")
    public ArrayofSearchResultBean getLatLng(@Query("address") String address);

    @GET("/maps/api/geocode/json?result_type=locality&key="+ "AIzaSyBW0l8K6qd9f16aaAH1Y0LMfoyJM_TDsB8")
    public ArrayofSearchResultBean getCity(@Query("latlng") String address);

}
