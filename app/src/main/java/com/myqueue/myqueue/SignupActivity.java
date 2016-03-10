package com.myqueue.myqueue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout signUpButton;
    private LinearLayout customerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_customer);



    }

    @Override
    public void onClick(View v) {

    }
}
