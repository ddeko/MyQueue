package com.myqueue.myqueue.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.myqueue.myqueue.Adapter.QueueListAdapter;
import com.myqueue.myqueue.Callbacks.OnActionbarListener;
import com.myqueue.myqueue.Models.QueueListItem;
import com.myqueue.myqueue.Preferences.SessionManager;
import com.myqueue.myqueue.R;
import com.myqueue.myqueue.Views.RoundedImage;

import java.util.ArrayList;
import java.util.HashMap;

public class WaitingListActivity extends BaseActivity implements View.OnClickListener{

    private ListView queueListView;
    private ImageView profilewait;
    private ImageView coverwait;
    private RoundedImage cropCircle;

    private LinearLayout changeStatusButton;

    public SessionManager sessions;
    public HashMap<String,String> userdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaultActionbarIcon();
        setRightIcon(0);
        setActionBarTitle("Waiting List");

        sessions = new SessionManager(this);

        queueListView = (ListView) findViewById(R.id.listQueue);
        profilewait = (ImageView) findViewById(R.id.profileWait);
        coverwait = (ImageView) findViewById(R.id.coverWait);

        fetchData();

        ArrayList<QueueListItem> queueItems = new ArrayList<QueueListItem>();

        QueueListItem queueListItem1 = new QueueListItem(0,"13");
        QueueListItem queueListItem2 = new QueueListItem(0,"13");
        QueueListItem queueListItem3 = new QueueListItem(0,"13");
        QueueListItem queueListItem4 = new QueueListItem(0,"13");


        queueItems.add(queueListItem1);
        queueItems.add(queueListItem2);
        queueItems.add(queueListItem3);
        queueItems.add(queueListItem4);

        QueueListAdapter queueListAdapter = new QueueListAdapter(getApplicationContext(), R.layout.item_queue, queueItems);

        queueListView.setAdapter(queueListAdapter);
    }

    @Override
    public void initView() {

        changeStatusButton = (LinearLayout) findViewById(R.id.changeStatusBtn);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        changeStatusButton.setOnClickListener(this);
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
        return R.layout.activity_waiting_list;
    }

    @Override
    public void updateUI() {
        fetchData();
    }

    public void fetchData()
    {
        userdata = sessions.getUserDetails();
        Glide.with(this).load(this.userdata.get(SessionManager.KEY_COVERPHOTO)).placeholder(R.drawable.coverpics).into(coverwait);
        Glide.with(this).load(this.userdata.get(SessionManager.KEY_PROFILEPHOTO)).asBitmap()
                .into(new SimpleTarget<Bitmap>(256, 256) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        cropCircle = new RoundedImage(resource);
                        profilewait.setImageDrawable(cropCircle);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v==changeStatusButton)
        {
            Intent i = new Intent(this, SetFullActivity.class);
            startActivity(i);
        }
    }
}
