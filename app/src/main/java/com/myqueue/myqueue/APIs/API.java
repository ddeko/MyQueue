package com.myqueue.myqueue.APIs;

import com.myqueue.myqueue.Models.APIBaseResponse;
import com.myqueue.myqueue.Models.APILoginResponse;
import com.myqueue.myqueue.Models.User;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
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
}
