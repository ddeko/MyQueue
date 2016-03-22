package com.myqueue.myqueue.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myqueue.myqueue.Callbacks.OnActionbarListener;
import com.myqueue.myqueue.R;

public class NewsFeedFormActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar myActionBar;
    TextView edfeedStatus;
    ImageView imgFeed1;
    ImageView imgFeed2;
    ImageView imgFeed3;

    RelativeLayout btnAddPhoto;

    private Intent resultIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        edfeedStatus = (TextView)findViewById(R.id.feedStatus);
        btnAddPhoto = (RelativeLayout)findViewById(R.id.btnAddphoto);

        //imageview untuk nampung foto abis Add Photos
        imgFeed1 = (ImageView)findViewById(R.id.imgFeed1);
        imgFeed2 = (ImageView)findViewById(R.id.imgFeed2);
        imgFeed3 = (ImageView)findViewById(R.id.imgFeed3);

        //set imageview jadi Visible kalo mo nampilin fotonya , kalo dibuang ganti GONE (DEFAULT : GONE)
        imgFeed1.setVisibility(View.VISIBLE);

        edfeedStatus.setOnClickListener(this);
        btnAddPhoto.setOnClickListener(this);

        setDefaultActionbarIcon();
        setActionBarTitle("Post Feed");
        setRightIcon(R.drawable.submitfeed_pressed);

    }

    @Override
    public void onClick(View v) {
        if(v==edfeedStatus){
            edfeedStatus.setText("");
        }
        else if(v==btnAddPhoto){

        }
    }

    @Override
    public void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(toolbar);
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
                resultIntent = new Intent();
                resultIntent.putExtra("resultkey", "result");
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_news_feed_form;
    }

    @Override
    public void updateUI() {

    }
}
