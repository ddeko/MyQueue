package com.myqueue.myqueue.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myqueue.myqueue.R;

public class NewsFeedFormActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar myActionBar;
    TextView edfeedStatus;
    ImageView imgFeed1;
    ImageView imgFeed2;
    ImageView imgFeed3;
    ImageButton btnSubmit;

    RelativeLayout btnAddPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed_form);

        myActionBar = (Toolbar) findViewById(R.id.toolbar_withoutalarm);
        setSupportActionBar(myActionBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(getSupportActionBar()!= null){
            getSupportActionBar().setElevation(0);
        }
        edfeedStatus = (TextView)findViewById(R.id.feedStatus);
        btnAddPhoto = (RelativeLayout)findViewById(R.id.btnAddphoto);
        btnSubmit = (ImageButton)findViewById(R.id.btnSubmitFeed);

        //imageview untuk nampung foto abis Add Photos
        imgFeed1 = (ImageView)findViewById(R.id.imgFeed1);
        imgFeed2 = (ImageView)findViewById(R.id.imgFeed2);
        imgFeed3 = (ImageView)findViewById(R.id.imgFeed3);

        //set imageview jadi Visible kalo mo nampilin fotonya , kalo dibuang ganti GONE (DEFAULT : GONE)
        imgFeed1.setVisibility(View.VISIBLE);

        edfeedStatus.setOnClickListener(this);
        btnAddPhoto.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==edfeedStatus){
            edfeedStatus.setText("");
        }
        else if(v==btnAddPhoto){
            startActivity(new Intent(this,BookScreenActivity.class));
        }
        else if(v==btnSubmit){
            startActivity(new Intent(this,WaitingListActivity.class));
        }
    }
}
