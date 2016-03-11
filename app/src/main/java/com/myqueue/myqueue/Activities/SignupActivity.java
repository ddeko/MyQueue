package com.myqueue.myqueue.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myqueue.myqueue.APIs.TaskSignup;
import com.myqueue.myqueue.Models.APIBaseResponse;
import com.myqueue.myqueue.Models.APISignupRequest;
import com.myqueue.myqueue.Preferences.SessionManager;
import com.myqueue.myqueue.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    SessionManager sessions;
    Intent resultIntent;

    private final int USER_STATE =1;
    private final int OWNER_STATE =2;

    int signupState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        sessions = new SessionManager(this);

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
            if(formValidation()) {

                APISignupRequest request = new APISignupRequest();
                request.setEmail(signupEmail.getText().toString());
                request.setPassword(signupPassword.getText().toString());
                request.setName(signupName.getText().toString());
                request.setPhone(signupPhone.getText().toString());

                if (signupState == USER_STATE) {
                    request.setIsowner("0");
                } else if (signupState == OWNER_STATE) {
                    request.setIsowner("1");
                }

                TaskSignup signup = new TaskSignup(this) {

                    @Override
                    public void onResult(APIBaseResponse response, String statusMessage, boolean isSuccess) {

                        if(isSuccess)
                        {
                            resultIntent = new Intent();
                            resultIntent.putExtra("email", signupEmail.getText().toString());
                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),statusMessage,Toast.LENGTH_SHORT).show();
                        }

                    }
                };
                signup.execute(request);
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

    private boolean formValidation()
    {
        if(signupEmail.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please fill in your Email Address", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!isEmailValid(signupEmail.getText().toString()))
        {
            Toast.makeText(getApplicationContext(), "Please input a correct Email format", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(signupPassword.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(),"Please fill in your Password",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(signupConfirmPassword.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(),"Please fill in your Confirmation Password",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(signupName.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(),"Please fill in your Name",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(signupPhone.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(),"Please fill in your Phone Number",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!signupConfirmPassword.getText().toString().equals(signupPassword.getText().toString())) {
            Toast.makeText(getApplicationContext(),"Password and Confirmation Password did not match",Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;
    }

    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}
