package com.myqueue.myqueue.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.myqueue.myqueue.APIs.TaskConfirm;
import com.myqueue.myqueue.Models.APIBaseResponse;
import com.myqueue.myqueue.Models.APIConfirmRequest;
import com.myqueue.myqueue.Models.User;
import com.myqueue.myqueue.Preferences.SessionManager;
import com.myqueue.myqueue.R;

/**
 * Created by 高橋六羽 on 2016/03/22.
 */
public class ConfirmationActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout sendBtn;
    private EditText confirmationText;

    private Intent resultIntent;

    private User userinfo;
    SessionManager sessions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmationcode);

        sessions = new SessionManager(this);

        sendBtn = (LinearLayout)findViewById(R.id.send_button);
        confirmationText = (EditText)findViewById(R.id.tvConfirm);

        sendBtn.setOnClickListener(this);

        Intent i = getIntent();
        userinfo = (User)i.getSerializableExtra("User");
    }

    @Override
    public void onClick(View v) {
        if(v == sendBtn)
        {
            APIConfirmRequest request = new APIConfirmRequest();
            request.setConfirmationcode(confirmationText.getText().toString());
            request.setUserid(userinfo.getUser_id());

            TaskConfirm confirm = new TaskConfirm(this) {

                @Override
                public void onResult(APIBaseResponse response, String statusMessage, boolean isSuccess) {

                    if(isSuccess) {
                        sessions.createLoginSession(userinfo);
                        resultIntent = new Intent();
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), statusMessage, Toast.LENGTH_SHORT).show();
                    }
                }
            };
            confirm.execute(request);
        }
    }
}
