package com.myqueue.myqueue.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.myqueue.myqueue.APIs.TaskGetUser;
import com.myqueue.myqueue.Models.APILoginRequest;
import com.myqueue.myqueue.Models.APILoginResponse;
import com.myqueue.myqueue.Models.Shop;
import com.myqueue.myqueue.Models.User;
import com.myqueue.myqueue.Preferences.SessionManager;
import com.myqueue.myqueue.R;

import java.util.HashMap;


/**
 * Created by 高橋六羽 on 2016/03/10.
 */
public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 0;
    private SessionManager sessions;
    User loginuser;
    Shop loginshopdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sessions = new SessionManager(this);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if(sessions.isLoggedIn()==false) {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                }
                else
                {
                    getNewData();
                }

            }
        }, SPLASH_TIME_OUT);
    }

    public void getNewData()
    {
        HashMap<String,String> userdata = sessions.getUserDetails();

        APILoginRequest request = new APILoginRequest();
        request.setEmail(userdata.get(SessionManager.KEY_EMAIL));
        request.setPassword(userdata.get(SessionManager.KEY_PASSWORD));

        TaskGetUser getUser = new TaskGetUser(this) {

            @Override
            public void onResult(APILoginResponse response, String statusMessage, boolean isSuccess) {

                if(isSuccess)
                {
                    loginuser = response.getUser().get(0);
                    if(response.getShop().size()!=0)

                        loginshopdata = response.getShop().get(0);

                        sessions.createLoginSession(loginuser);
                        if(loginuser.getIsowner().equalsIgnoreCase("1"))
                            sessions.setShopData(loginshopdata);


                        Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(i);

                        // close this activity
                        finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), statusMessage, Toast.LENGTH_SHORT).show();
                }

            }
        };
        getUser.execute(request);
    }

}
