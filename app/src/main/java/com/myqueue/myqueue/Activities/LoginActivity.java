package com.myqueue.myqueue.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myqueue.myqueue.APIs.TaskForgot;
import com.myqueue.myqueue.APIs.TaskLogin;
import com.myqueue.myqueue.Models.APIBaseResponse;
import com.myqueue.myqueue.Models.APILoginRequest;
import com.myqueue.myqueue.Models.APILoginResponse;
import com.myqueue.myqueue.Models.Shop;
import com.myqueue.myqueue.Models.User;
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
    TextInputLayout loginEmailInput;
    TextInputLayout loginPasswordInput;
    SessionManager sessions;
    User loginuser;
    Shop loginshopdata;

    private static final int SIGNUP_REQUEST_CODE = 1;
    private static final int CONFIRMATION_REQUEST_CODE = 5;

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
        loginEmailInput = (TextInputLayout)findViewById(R.id.emailWrapper);
        loginPasswordInput = (TextInputLayout)findViewById(R.id.passwordWrapper);

        loginEmail.addTextChangedListener(new MyTextWatcher(loginEmail));
        loginPassword.addTextChangedListener(new MyTextWatcher(loginPassword));

        loginButton.setElevation(1);

        loginButton.setOnClickListener(this);
        signupButton.setOnClickListener(this);
        forgotButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == loginButton)
        {
            submitForm();
            if(submitForm()) {

                APILoginRequest request = new APILoginRequest();
                request.setEmail(loginEmail.getText().toString());
                request.setPassword(loginPassword.getText().toString());

                TaskLogin login = new TaskLogin(this) {

                    @Override
                    public void onResult(APILoginResponse response, String statusMessage, boolean isSuccess) {

                        if(isSuccess)
                        {
                            loginuser = response.getUser().get(0);
                            if(response.getShop().size()!=0)
                                loginshopdata = response.getShop().get(0);

                            if(loginuser.getIsverified().equalsIgnoreCase("1")) {
                                sessions.createLoginSession(loginuser);
                                if(loginuser.getIsowner().equalsIgnoreCase("1"))
                                    sessions.setShopData(loginshopdata);

                                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
                            }
                            else if(response.getUser().get(0).getIsverified().equalsIgnoreCase("0"))
                            {
                                Intent i = new Intent(LoginActivity.this, ConfirmationActivity.class);
                                i.putExtra("Response",response);
                                startActivityForResult(i, CONFIRMATION_REQUEST_CODE);
                            }
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
            if(validateEmail()) {
                TaskForgot forgot = new TaskForgot(this) {

                    @Override
                    public void onResult(APIBaseResponse response, String statusMessage, boolean isSuccess) {

                        if (isSuccess) {
                            Toast.makeText(getApplicationContext(), statusMessage, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), statusMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                forgot.execute(loginEmail.getText().toString());
            }
        }
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
        else if(requestCode==CONFIRMATION_REQUEST_CODE)
        {
            if(resultCode != Activity.RESULT_OK) {
                return;
            }
            else
            {
                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        }
    }

    private boolean submitForm() {

        if (!validateEmail()) {
            return false;
        }

        if (!validatePassword()) {
            return false;
        }

        return true;
    }


    private boolean validateEmail() {
        if (loginEmail.getText().toString().trim().isEmpty()) {
            loginEmailInput.setError("Please fill in your Email Address");
            requestFocus(loginEmail);
            return false;
        }
        else if(!isEmailValid(loginEmail.getText().toString())) {
            loginEmailInput.setError("Please input a correct Email format");
            requestFocus(loginEmail);
            return false;
        } else {
            loginEmailInput.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (loginPassword.getText().toString().trim().isEmpty()) {
            loginPasswordInput.setError("Please fill in your Password");
            requestFocus(loginPassword);
            return false;
        } else {
            loginPasswordInput.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.login_email:
                    validateEmail();
                    break;
                case R.id.login_password:
                    validatePassword();
                    break;
            }
        }
    }
}
