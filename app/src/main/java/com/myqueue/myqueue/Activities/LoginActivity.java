package com.myqueue.myqueue.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myqueue.myqueue.R;

/**
 * Created by 高橋六羽 on 2016/03/10.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText loginEmail;
    EditText loginPassword;
    LinearLayout loginButton;
    TextView signupButton;
    TextView forgotButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail = (EditText) findViewById(R.id.login_email);
        loginPassword = (EditText) findViewById(R.id.login_password);
        loginButton = (LinearLayout) findViewById(R.id.login_button);
        signupButton = (TextView) findViewById(R.id.signUp_button);
        forgotButton = (TextView) findViewById(R.id.forgotPass_button);

        loginButton.setOnClickListener(this);
        signupButton.setOnClickListener(this);
        forgotButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == loginButton)
        {
            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(i);
        }
        else if(v == signupButton)
        {
            Intent i = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(i);
        }
        else if(v == forgotButton)
        {
            Toast.makeText(getApplicationContext(),"Implement Method",Toast.LENGTH_SHORT).show();
        }

    }
}
