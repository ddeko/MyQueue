package com.myqueue.myqueue.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myqueue.myqueue.R;

/**
 * Created by 高橋六羽 on 2016/03/10.
 */
public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    EditText signupEmail;
    EditText signupPassword;
    EditText signupConfirmPassword;
    EditText signupName;
    EditText signupPhone;
    LinearLayout signupButton;
    LinearLayout stateButton;
    TextView signupTitle;
    TextView signupText;
    TextView signupStateText;

    private final int USER_STATE =1;
    private final int OWNER_STATE =2;

    int signupState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupEmail = (EditText) findViewById(R.id.signup_email);
        signupPassword = (EditText) findViewById(R.id.signup_password);
        signupConfirmPassword = (EditText) findViewById(R.id.signup_confirm_pass);
        signupName = (EditText) findViewById(R.id.signup_name);
        signupPhone = (EditText) findViewById(R.id.signup_phoneNumber);
        signupButton = (LinearLayout) findViewById(R.id.signup_button);
        stateButton = (LinearLayout) findViewById(R.id.signup_state_button);
        signupTitle = (TextView) findViewById(R.id.signup_title);
        signupText = (TextView) findViewById(R.id.signup_text);
        signupStateText = (TextView) findViewById(R.id.signup_state_text);


        signupButton.setOnClickListener(this);
        stateButton.setOnClickListener(this);

        startUserState();

    }

    @Override
    public void onClick(View v) {

        if(v == signupButton)
        {
            if(signupState==USER_STATE) {
                Intent i = new Intent(SignupActivity.this, HomeActivity.class);
                startActivity(i);
            }
            else if(signupState==OWNER_STATE)
            {
                Intent i = new Intent(SignupActivity.this, HomeActivity.class);
                startActivity(i);
            }
        }
        else if(v == stateButton)
        {
            if(signupState==USER_STATE) {
                startOwnerState();
            }
            else if(signupState==OWNER_STATE)
            {
                startUserState();
            }
        }
    }

    private void startUserState()
    {
        signupState=USER_STATE;
        signupTitle.setText("I N S E R T  D A T A");
        signupText.setText("Sign Up Customer");
        signupStateText.setText("I'm a Shop Owner!");
        signupButton.setBackground(getResources().getDrawable(R.drawable.selector_button_rectangle));
        stateButton.setBackground(getResources().getDrawable(R.drawable.selector_button_rectangle_grey));
    }

    private void startOwnerState()
    {
        signupState=OWNER_STATE;
        signupTitle.setText("I N S E R T  S H O P  D A T A");
        signupText.setText("Sign Up Shop");
        signupStateText.setText("I'm a Customer!");
        signupButton.setBackground(getResources().getDrawable(R.drawable.selector_button_rectangle_grey));
        stateButton.setBackground(getResources().getDrawable(R.drawable.selector_button_rectangle));
    }
}
