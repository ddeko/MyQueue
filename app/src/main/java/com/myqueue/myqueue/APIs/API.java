package com.myqueue.myqueue.APIs;

import com.myqueue.myqueue.Models.APIBaseResponse;
import com.myqueue.myqueue.Models.APIExploreResponse;
import com.myqueue.myqueue.Models.APIFeedResponse;
import com.myqueue.myqueue.Models.APILoginResponse;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by 高橋六羽 on 2016/03/11.
 */
public interface API {

    //AUTHENTICATION
    @FormUrlEncoded
    @POST("/APIv1/auth/signup.php")
    public APIBaseResponse Signup(@Field("email") String email,
                                    @Field("password") String password,
                                    @Field("name") String name,
                                    @Field("phone") String phone,
                                    @Field("isowner") String isowner);

    @FormUrlEncoded
    @POST("/APIv1/auth/login.php")
    public APILoginResponse Login(@Field("email") String email,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("/APIv1/auth/confirmation.php")
    public APIBaseResponse Confirm(@Field("userid") String userid,
                                  @Field("confirmationcode") String confirmationcode);

    @FormUrlEncoded
    @POST("/APIv1/auth/resend.php")
    public APIBaseResponse Resend(@Field("email") String email);

    @FormUrlEncoded
    @POST("/APIv1/auth/forgot.php")
    public APIBaseResponse Forgot(@Field("email") String email);


    //SHOPS
    @GET("/APIv1/shops/show.php")
    public APIExploreResponse Explore();

    //FEEDS
    @GET("/APIv1/feeds/show.php")
    public APIFeedResponse Feed();
}
