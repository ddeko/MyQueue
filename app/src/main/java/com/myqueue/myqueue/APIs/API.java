package com.myqueue.myqueue.APIs;

import com.myqueue.myqueue.Models.APIBaseResponse;
import com.myqueue.myqueue.Models.APIExploreResponse;
import com.myqueue.myqueue.Models.APIFeedResponse;
import com.myqueue.myqueue.Models.APILoginResponse;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
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


    //USERS
    @FormUrlEncoded
    @POST("/APIv1/users/edit.php")
    public APIBaseResponse EditUser(@Field("userid") String userid,
                                    @Field("name") String name,
                                    @Field("phone") String phone);

    @FormUrlEncoded
    @POST("/APIv1/users/editwithcategory.php")
    public APIBaseResponse EditUserCategory(@Field("userid") String userid,
                                    @Field("name") String name,
                                    @Field("phone") String phone,
                                    @Field("category") String category);

    //SHOPS
    @Headers("Cache-Control: no-cache")
    @GET("/APIv1/shops/show.php")
    public APIExploreResponse Explore();

    @FormUrlEncoded
    @POST("/APIv1/shops/add.php")
    public APIBaseResponse AddShop(@Field("userid") String userid,
                                   @Field("latitude") String latitude,
                                   @Field("longitude") String longitude,
                                   @Field("address") String address,
                                   @Field("number") String number);

    @FormUrlEncoded
    @POST("/APIv1/shops/edit.php")
    public APIBaseResponse EditShop(@Field("userid") String userid,
                                    @Field("latitude") String latitude,
                                    @Field("longitude") String longitude,
                                    @Field("address") String address,
                                    @Field("number") String number,
                                    @Field("isfull") String isfull);

    //FEEDS
    @Headers("Cache-Control: no-cache")
    @GET("/APIv1/feeds/show.php")
    public APIFeedResponse Feed();

    @FormUrlEncoded
    @POST("/APIv1/feeds/add.php")
    public APIBaseResponse PostFeed(@Field("userid") String userid,
                                    @Field("urlPhoto") String urlPhoto,
                                    @Field("photoFeed") String photoFeed,
                                    @Field("description") String description);
}
