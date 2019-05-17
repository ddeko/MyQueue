package com.myqueue.myqueue.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myqueue.myqueue.APIs.TaskSignup;
import com.myqueue.myqueue.Callbacks.OnActionbarListener;
import com.myqueue.myqueue.Models.APIBaseResponse;
import com.myqueue.myqueue.Models.APISignupRequest;
import com.myqueue.myqueue.Preferences.SessionManager;
import com.myqueue.myqueue.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 高橋六羽 on 2016/03/10.
 */
public class SignupActivity extends BaseActivity implements View.OnClickListener {

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
    TextInputLayout signupEmailInput;
    TextInputLayout signupPasswordInput;
    TextInputLayout signupConfirmPasswordInput;
    TextInputLayout signupNameInput;
    TextInputLayout signupPhoneInput;
    Toolbar toolbar;

    private static View activityRoot;

    private final int USER_STATE =1;
    private final int OWNER_STATE =2;

    int signupState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessions = new SessionManager(this);
        activityRoot = findViewById(android.R.id.content);

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
        signupEmailInput = (TextInputLayout)findViewById(R.id.emailWrapper);
        signupPasswordInput = (TextInputLayout)findViewById(R.id.passwordWrapper);
        signupConfirmPasswordInput = (TextInputLayout)findViewById(R.id.confirmPasswordWrapper);
        signupNameInput = (TextInputLayout)findViewById(R.id.shopNameWrapper);
        signupPhoneInput = (TextInputLayout)findViewById(R.id.phoneWrapper);

        signupEmail.addTextChangedListener(new MyTextWatcher(signupEmail));
        signupPassword.addTextChangedListener(new MyTextWatcher(signupPassword));
        signupConfirmPassword.addTextChangedListener(new MyTextWatcher(signupConfirmPassword));
        signupName.addTextChangedListener(new MyTextWatcher(signupName));
        signupPhone.addTextChangedListener(new MyTextWatcher(signupPhone));

        signupButton.setOnClickListener(this);
        stateButton.setOnClickListener(this);

        startUserState();

        setDefaultActionbarIcon();
        setActionBarTitleCenter("Sign Up");
        setRightIcon(0);
        setActionBarColor(R.color.transparent);

    }

    @Override
    public void onClick(View v) {

        if(v == signupButton)
        {
            if(submitForm()) {

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

    private boolean submitForm() {

        if (!validateEmail()) {
            return false;
        }

        if (!validatePassword()) {
            return false;
        }

        if (!validateConfirmationPassword()) {
            return false;
        }

        if (!validateName()) {
            return false;
        }

        if (!validatePhone()) {
            return false;
        }

        return true;
    }


    private boolean validateEmail() {
        if (signupEmail.getText().toString().trim().isEmpty()) {
            signupEmailInput.setError("Please fill in your Email Address");
            requestFocus(signupEmail);
            return false;
        }
        else if(!isEmailValid(signupEmail.getText().toString())) {
            signupEmailInput.setError("Please input a correct Email format");
            requestFocus(signupEmail);
            return false;
        } else {
            signupEmailInput.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (signupPassword.getText().toString().trim().isEmpty()) {
            signupPasswordInput.setError("Please fill in your Password");
            requestFocus(signupPassword);
            return false;
        } else {
            signupPasswordInput.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateConfirmationPassword() {
        if (signupConfirmPassword.getText().toString().trim().isEmpty()) {
            signupConfirmPasswordInput.setError("Please fill in your Confirmation Password");
            requestFocus(signupConfirmPassword);
            return false;
        }
        else if(!signupPassword.getText().toString().equals(signupConfirmPassword.getText().toString()))
        {
            signupConfirmPasswordInput.setError("Password and Confirmation Password did not match");
            requestFocus(signupConfirmPassword);
            return false;
        }
        else {
            signupConfirmPasswordInput.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateName() {
        if (signupName.getText().toString().trim().isEmpty()) {
            signupNameInput.setError("Please fill in your Name");
            requestFocus(signupName);
            return false;
        } else {
            signupNameInput.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePhone() {
        if (signupPhone.getText().toString().trim().isEmpty()) {
            signupPhoneInput.setError("Please fill in your Phone Number");
            requestFocus(signupPhone);
            return false;
        } else {
            signupPhoneInput.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void setUICallbacks() {
        setActionbarListener(new OnActionbarListener() {
            @Override
            public void onLeftIconClick() {
                onBackPressed();
            }
            @Override
            public void onRightIconClick() {

            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_signup;
    }

    @Override
    public void updateUI() {
        activityRoot.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // if more than 100 pixels, its probably a keyboard
                int heightDiff = activityRoot.getRootView().getHeight() - activityRoot.getHeight();
                if(heightDiff > 100) {
                    toolbar.setVisibility(View.GONE);
                }
                else
                {
                    toolbar.setVisibility(View.VISIBLE);
                }
            }
        });

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
                case R.id.signup_email:
                    validateEmail();
                    break;
                case R.id.signup_password:
                    validatePassword();
                    break;
                case R.id.signup_confirm_pass:
                    validateConfirmationPassword();
                    break;
                case R.id.signup_name:
                    validateName();
                    break;
                case R.id.signup_phoneNumber:
                    validatePhone();
                    break;
            }
        }
    }
}
