package com.myqueue.myqueue.Activities;

import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.myqueue.myqueue.APIs.TaskAddDummy;
import com.myqueue.myqueue.APIs.TaskTotalQueue;
import com.myqueue.myqueue.Adapter.DialogDummyAdapter;
import com.myqueue.myqueue.Callbacks.OnActionbarListener;
import com.myqueue.myqueue.Models.APIMaxQueueRequest;
import com.myqueue.myqueue.Models.APIMaxQueueResponse;
import com.myqueue.myqueue.Preferences.SessionManager;
import com.myqueue.myqueue.R;
import com.myqueue.myqueue.Views.RoundedImage;

import java.util.HashMap;

public class AddDummyUserActivity extends BaseActivity implements View.OnClickListener{

    public static TextView txtTotalQueue;
    public static Button btnBookDummy;
    ImageView imgProfile;
    TextView txtShopName;
    RoundedImage profPics;
    int queuenumber=0;

    SessionManager session;

    public HashMap<String,String> userDataDetails;
    public HashMap<String,String> shopDataDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRightIcon(0);
        setActionBarTitle("Book Directly");

        session = new SessionManager(this);
        userDataDetails = session.getUserDetails();
        shopDataDetails = session.getShopDetails();
        DialogDummyAdapter.getInstance().initialize(this);

        if(shopDataDetails.get(SessionManager.KEY_ISFULL).toString().equals("1")){
            changeToFull();
        }else if(shopDataDetails.get(SessionManager.KEY_ISFULL).toString().equals("0")){
            changeToEmpty();
        }

        txtShopName.setText(userDataDetails.get(SessionManager.KEY_NAME).toString());
        Glide.with(context).load(userDataDetails.get(SessionManager.KEY_PROFILEPHOTO)).asBitmap()
                .into(new SimpleTarget<Bitmap>(150, 150) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        profPics = new RoundedImage(resource);
                        imgProfile.setImageDrawable(profPics);
                    }
                });
    }

    @Override
    public void initView() {

        txtShopName = (TextView)findViewById(R.id.shopNametxt);
        imgProfile = (ImageView)findViewById(R.id.profilePhoto);
        btnBookDummy = (Button)findViewById(R.id.bookDummy);
        txtTotalQueue = (TextView)findViewById(R.id.totalQueue);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        btnBookDummy.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==btnBookDummy){
            DialogDummyAdapter.getInstance().showDialog(queuenumber);
        }
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
        return R.layout.activity_add_dummy_user;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void updateUI() {
    }

    private void changeToEmpty() {
        txtTotalQueue.setText("SLOT AVAILBLE(NO NEED TO BOOK)");
        btnBookDummy.setVisibility(View.GONE);
    }

    private void changeToFull(){
        getTotalQueue();
        btnBookDummy.setVisibility(View.VISIBLE);
    }

    public void getTotalQueue(){
        APIMaxQueueRequest request = new APIMaxQueueRequest();
        request.setShopid(userDataDetails.get(SessionManager.KEY_USERID));

        TaskTotalQueue Total = new TaskTotalQueue(this) {

            @Override
            public void onResult(APIMaxQueueResponse response, String statusMessage, boolean isSuccess) {

                if(isSuccess) {
                    if(response.getCurrentnumber() == null){
                        txtTotalQueue.setText("TOTAL QUEUE : " + 0);
                        btnBookDummy.setText("BOOK FOR QUEUE NUMBER : " + 1);
                    }else {
                        txtTotalQueue.setText("TOTAL QUEUE : " + response.getCurrentnumber());
                        btnBookDummy.setText("BOOK FOR QUEUE NUMBER : " + (Integer.parseInt(response.getCurrentnumber()) + 1));
                        queuenumber = Integer.parseInt(response.getCurrentnumber());
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), statusMessage, Toast.LENGTH_SHORT).show();
                }
            }
        };
        Total.execute(request);
    }
}
