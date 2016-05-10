package com.myqueue.myqueue.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myqueue.myqueue.APIs.TaskAddDummy;
import com.myqueue.myqueue.Activities.AddDummyUserActivity;
import com.myqueue.myqueue.Models.APIAddDummyRequest;
import com.myqueue.myqueue.Models.APIBaseResponse;
import com.myqueue.myqueue.Preferences.SessionManager;
import com.myqueue.myqueue.R;

import org.w3c.dom.Text;

import java.util.HashMap;

/**
 * Created by Penopole on 5/8/2016.
 */
public class DialogDummyAdapter {

    private static DialogDummyAdapter instance = null;

    LayoutInflater li;
    View promptsView;
    AlertDialog.Builder alert;
    AlertDialog alertDialog;
    Context context;
    Button btnAddQueue;
    Button btnCancel;
    TextInputLayout nameWrapper;
    TextInputLayout telephoneWrapper;
    EditText nameed;
    EditText telephoneed;
    int holdQueuenumber;

    public SessionManager sessions;
    public HashMap<String,String> userDataDetails;
    public HashMap<String,String> shopDataDetails;

    public void initialize(Context context) {
        this.context = context;
    }

    public static DialogDummyAdapter getInstance() {
        if (instance == null) {
            instance=new DialogDummyAdapter();
        }
        return instance;
    }

    public void showDialog(int queuenumber) {
        li = LayoutInflater.from(context);
        promptsView = li.inflate(R.layout.dialog_input_dummy, null);

        alert = new AlertDialog.Builder(getContext());

        alert.setView(promptsView);

        sessions = new SessionManager(getContext());
        userDataDetails = sessions.getUserDetails();
        shopDataDetails = sessions.getShopDetails();

        btnAddQueue = (Button) promptsView.findViewById(R.id.btnAddDialog);
        btnCancel = (Button) promptsView.findViewById(R.id.btnCancelDialog);
        nameed = (EditText) promptsView.findViewById(R.id.dummyName);
        telephoneed = (EditText) promptsView.findViewById(R.id.dummyTelephone);
        nameWrapper = (TextInputLayout) promptsView.findViewById(R.id.dummyNameWrapper);
        telephoneWrapper = (TextInputLayout) promptsView.findViewById(R.id.dummyTelephoneWrapper);
        holdQueuenumber = queuenumber;

        nameed.addTextChangedListener(new MyTextWatcher(nameed));
        telephoneed.addTextChangedListener(new MyTextWatcher(telephoneed));

        btnAddQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkText()){
                    addDummy();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alert.setCancelable(false);
        // create alert dialog
        alertDialog = alert.create();

        // show it
        alertDialog.show();
    }

    public Context getContext() {
        return context;
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
                case R.id.signup_name:
                    validateName();
                    break;
                case R.id.signup_phoneNumber:
                    validatePhone();
                    break;
            }
        }
    }

    private boolean validateName() {
        if (nameed.getText().toString().trim().isEmpty()) {
            nameWrapper.setError("Please fill in the Name");
            requestFocus(nameed);
            return false;
        } else {
            nameWrapper.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePhone() {
        if (telephoneed.getText().toString().trim().isEmpty()) {
            telephoneWrapper.setError("Please fill in Telephone Number");
            requestFocus(telephoneed);
            return false;
        } else {
            telephoneWrapper.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            ((Activity) getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean checkText(){
        if (!validateName()) {
            return false;
        }

        if (!validatePhone()) {
            return false;
        }

        return true;
    }

    private void addDummy(){
        APIAddDummyRequest request = new APIAddDummyRequest();
        request.setShop_id(userDataDetails.get(SessionManager.KEY_USERID));
        request.setDummyname(nameed.getText().toString());
        request.setDummyphone(telephoneed.getText().toString());

        TaskAddDummy Total = new TaskAddDummy(getContext()) {

            @Override
            public void onResult(APIBaseResponse response, String statusMessage, boolean isSuccess) {

                if(isSuccess) {
                    Toast.makeText(getContext(), "Successfully Adding Waiting List", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                    AddDummyUserActivity.txtTotalQueue.setText("TOTAL QUEUE : " + (holdQueuenumber+1));
                    AddDummyUserActivity.btnBookDummy.setText("BOOK FOR QUEUE NUMBER : " + (holdQueuenumber+2));
                }
                else
                {
                    Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
                }
            }
        };
        Total.execute(request);
    }
}
