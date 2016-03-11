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

import com.myqueue.myqueue.APIs.TaskLogin;
import com.myqueue.myqueue.Models.APILoginRequest;
import com.myqueue.myqueue.Models.APILoginResponse;
import com.myqueue.myqueue.Preferences.SessionManager;
import com.myqueue.myqueue.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 高橋六羽 on 2016/03/10.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText loginEmail;
    EditText loginPassword;
    LinearLayout loginButton;
    TextView signupButton;
    TextView forgotButton;
    SessionManager sessions;

    private static final int SIGNUP_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessions = new SessionManager(this);

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
            if(formValidation()) {

                APILoginRequest request = new APILoginRequest();
                request.setEmail(loginEmail.getText().toString());
                request.setPassword(loginPassword.getText().toString());

                TaskLogin login = new TaskLogin(this) {

                    @Override
                    public void onResult(APILoginResponse response, String statusMessage, boolean isSuccess) {

                        if(isSuccess)
                        {
                            sessions.createLoginSession(response.getUser().get(0));
                            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),statusMessage,Toast.LENGTH_SHORT).show();
                        }

                    }
                };
                login.execute(request);
            }
        }
        else if(v == signupButton)
        {
            Intent i = new Intent(LoginActivity.this, SignupActivity.class);
            startActivityForResult(i, SIGNUP_REQUEST_CODE);
        }
        else if(v == forgotButton)
        {
            Toast.makeText(getApplicationContext(),"Implement Method",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean formValidation()
    {
        if(loginEmail.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please fill in your Email Address", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!isEmailValid(loginEmail.getText().toString()))
        {
            Toast.makeText(getApplicationContext(), "Please input a correct Email format", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(loginPassword.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(),"Please fill in your Password",Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SIGNUP_REQUEST_CODE)
        {
            if(resultCode != Activity.RESULT_OK) {
                return;
            }
            else
            {
                if(data != null) {
                    Bundle extras = data.getExtras();
                    String Email = extras.get("email").toString();
                    loginEmail.setText(Email);
                    loginPassword.setText("");
                }
            }
        }
    }
}
