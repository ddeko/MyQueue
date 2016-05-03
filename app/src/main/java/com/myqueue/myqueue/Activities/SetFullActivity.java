package com.myqueue.myqueue.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.myqueue.myqueue.APIs.TaskChangeStatusShop;
import com.myqueue.myqueue.Activities.BookActivity;
import com.myqueue.myqueue.Callbacks.OnActionbarListener;
import com.myqueue.myqueue.Models.APIBaseResponse;
import com.myqueue.myqueue.Models.APIChangeStatusShopRequest;
import com.myqueue.myqueue.Preferences.SessionManager;
import com.myqueue.myqueue.R;

import java.util.HashMap;

import info.hoang8f.widget.FButton;

/**
 * Created by DedeEko on 3/29/2016.
 */
public class SetFullActivity extends BaseActivity implements View.OnClickListener{

    private Fragment fragment;

    private FButton fullButton;

    SessionManager sessions;
    public HashMap<String,String> userDataDetails;
    public HashMap<String,String> shopDataDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setRightIcon(0);
        setActionBarTitle("Shop Status");

        sessions = new SessionManager(this);
        userDataDetails = sessions.getUserDetails();
        shopDataDetails = sessions.getShopDetails();

        if(shopDataDetails.get(SessionManager.KEY_ISFULL).toString().equals("1")){
            changeToFull();
        }else if(shopDataDetails.get(SessionManager.KEY_ISFULL).toString().equals("0")){
            changeToEmpty();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        if(v==fullButton)
        {
            if(fullButton.getText().toString()=="F U L L") {
                changeToEmpty(); //DR EDHO
                changeStatusShop(0); //DR EDHO
            }
            else if(fullButton.getText().toString() == "E M P T Y"){
                changeToFull(); //DR EDHO
                changeStatusShop(1); //DR EDHO
            }
        }
    }

    @Override
    public void initView() {

        fullButton = (FButton) findViewById(R.id.fullBtn);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        fullButton.setOnClickListener(this);
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
        return R.layout.activity_setshop_full;
    }

    @Override
    public void updateUI() {

    }

    //DR EDHO KEBAWAH-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private void changeStatusShop(final int isFull){
        APIChangeStatusShopRequest request = new APIChangeStatusShopRequest();
        request.setUserid(userDataDetails.get(SessionManager.KEY_USERID));
        request.setIsfull(isFull);

        TaskChangeStatusShop confirm = new TaskChangeStatusShop(this) {

            @Override
            public void onResult(APIBaseResponse response, String statusMessage, boolean isSuccess) {

                if(isSuccess) {
                    Toast.makeText(getApplicationContext(), "Shop Status Successfully Changed", Toast.LENGTH_SHORT).show();
                    sessions.setIsFull(String.valueOf(isFull));
                }
                else
                {
                    Toast.makeText(getApplicationContext(), statusMessage, Toast.LENGTH_SHORT).show();
                }
            }
        };
        confirm.execute(request);
    }

    private void changeToFull(){
        fullButton.setText("F U L L");
        fullButton.setButtonColor(getResources().getColor(R.color.actionbar_color));
        fullButton.setShadowColor(getResources().getColor(R.color.fbutton_color_pomegranate));
    }

    private void changeToEmpty(){
        fullButton.setText("E M P T Y");
        fullButton.setButtonColor(getResources().getColor(R.color.fbutton_color_emerald));
        fullButton.setShadowColor(getResources().getColor(R.color.fbutton_color_nephritis));
    }
}


