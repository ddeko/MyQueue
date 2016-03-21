package com.myqueue.myqueue.Activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.myqueue.myqueue.Adapter.QueueListAdapter;
import com.myqueue.myqueue.Callbacks.OnActionbarListener;
import com.myqueue.myqueue.Models.QueueListItem;
import com.myqueue.myqueue.R;

import java.util.ArrayList;

public class WaitingListActivity extends BaseActivity {

    private ListView queueListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaultActionbarIcon();
        setRightIcon(0);

        queueListView = (ListView) findViewById(R.id.listQueue);

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        return R.layout.activity_waiting_list;
    }

    @Override
    public void updateUI() {

    }
}
