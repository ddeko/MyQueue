package com.myqueue.myqueue.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.myqueue.myqueue.APIs.TaskDeleteQueue;
import com.myqueue.myqueue.APIs.TaskQueueShop;
import com.myqueue.myqueue.APIs.TaskQueueUser;
import com.myqueue.myqueue.Adapter.QueueListAdapter;
import com.myqueue.myqueue.Adapter.QueueListShopAdapter;
import com.myqueue.myqueue.Callbacks.OnActionbarListener;
import com.myqueue.myqueue.Models.APIBaseResponse;
import com.myqueue.myqueue.Models.APIDeleteQueueRequest;
import com.myqueue.myqueue.Models.APIQueueShopRequest;
import com.myqueue.myqueue.Models.APIQueueShopResponse;
import com.myqueue.myqueue.Models.APIQueueUserRequest;
import com.myqueue.myqueue.Models.APIQueueUserResponse;
import com.myqueue.myqueue.Models.Feed;
import com.myqueue.myqueue.Models.QueueShop;
import com.myqueue.myqueue.Models.QueueUser;
import com.myqueue.myqueue.Preferences.SessionManager;
import com.myqueue.myqueue.R;
import com.myqueue.myqueue.Views.RoundedImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WaitingListActivity extends BaseActivity implements View.OnClickListener{

    private ListView queueListView;
    private ImageView profilewait;
    private ImageView coverwait;
    private RoundedImage cropCircle;
    private Button btnDeleteBook;
    private List<QueueUser> queueUsersItems = new ArrayList<QueueUser>();
    private List<QueueShop> queueShopsItems = new ArrayList<QueueShop>();
    private QueueListAdapter queueListAdapter;
    private QueueListShopAdapter queueListAdapter2;

    private LinearLayout changeStatusButton;

    private LinearLayout AddUserDummy;

    public boolean isOwner = false;


    public SessionManager sessions;
    public HashMap<String,String> userdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaultActionbarIcon();
        setRightIcon(0);
        setActionBarTitle("Waiting List");



        sessions = new SessionManager(this);



        fetchData();

        userdata = sessions.getUserDetails();

        if(userdata.get(SessionManager.KEY_ISOWNER).equalsIgnoreCase("1"))
        {
            isOwner = true;
        }

        if(userdata.get(SessionManager.KEY_ISOWNER).equals("0")){
            changeStatusButton.setVisibility(View.GONE);
            AddUserDummy.setVisibility(View.GONE);
            btnDeleteBook.setVisibility(View.GONE);
        }else if(userdata.get(SessionManager.KEY_ISOWNER).equals("1")){
            changeStatusButton.setVisibility(View.VISIBLE);
            AddUserDummy.setVisibility(View.VISIBLE);
            btnDeleteBook.setVisibility(View.VISIBLE);
        }


    }

    public void deleteBook(){
        APIDeleteQueueRequest request = new APIDeleteQueueRequest();
        request.setUserid(this.userdata.get(SessionManager.KEY_USERID));

        TaskDeleteQueue deleteQueue= new TaskDeleteQueue(this) {

            @Override
            public void onResult(APIBaseResponse response, String statusMessage, boolean isSuccess) {

                if(isSuccess) {
                    fetchData();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), statusMessage, Toast.LENGTH_SHORT).show();
                }
            }
        };
        deleteQueue.execute(request);

    }

    @Override
    public void initView() {

        changeStatusButton = (LinearLayout) findViewById(R.id.changeStatusBtn);
        AddUserDummy = (LinearLayout) findViewById(R.id.addUserDummy);
        btnDeleteBook = (Button) findViewById(R.id.btnNextQueue);
        queueListView = (ListView) findViewById(R.id.listQueue);
        profilewait = (ImageView) findViewById(R.id.profileWait);
        coverwait = (ImageView) findViewById(R.id.coverWait);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        btnDeleteBook.setOnClickListener(this);
        changeStatusButton.setOnClickListener(this);
        AddUserDummy.setOnClickListener(this);

        queueListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> newsitemcurrent, View v, int position,
                                    long id) {
                if(isOwner==false)
                {
                    Intent i = new Intent(WaitingListActivity.this, UserInformationActivity.class);
                    i.putExtra("queue", (QueueUser) newsitemcurrent.getItemAtPosition(position));
                    i.putExtra("isowner", isOwner);
                    startActivity(i);
                }
                else if(isOwner==true)
                {
                    Intent i = new Intent(WaitingListActivity.this, UserInformationActivity.class);
                    i.putExtra("queue", (QueueShop) newsitemcurrent.getItemAtPosition(position));
                    i.putExtra("isowner", isOwner);
                    startActivity(i);
                }

            }
        });
    }

    @Override
    public void setUICallbacks() {
        setActionbarListener(new OnActionbarListener() {
            @Override
            public void onLeftIconClick() {

                if (isTaskRoot()) {
                    Intent i = new Intent(WaitingListActivity.this, HomeActivity.class);
                    startActivity(i);
                } else
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

        if(this.userdata.get(SessionManager.KEY_ISOWNER).equalsIgnoreCase("0")){
            queueListAdapter = new QueueListAdapter(getApplicationContext(), R.layout.item_queue, queueUsersItems);
            queueListView.setAdapter(queueListAdapter);
        }
        else {
            queueListAdapter2 = new QueueListShopAdapter(getApplicationContext(), R.layout.item_queue, queueShopsItems);
            queueListView.setAdapter(queueListAdapter2);
        }

        if(this.userdata.get(SessionManager.KEY_ISOWNER).equalsIgnoreCase("0")){
            getQueueUser();
        }
        else {
            getQueueShop();
        }

    }

    private void getQueueUser(){
        APIQueueUserRequest request = new APIQueueUserRequest();
        request.setUser(this.userdata.get(SessionManager.KEY_USERID));

        TaskQueueUser QueueUser = new TaskQueueUser(this) {

            @Override
            public void onResult(APIQueueUserResponse response, String statusMessage, boolean isSuccess) {

                if(isSuccess) {
                    queueUsersItems.clear();
                    List<QueueUser> queueUsers = response.getQueue();

                    for(QueueUser queueUser : queueUsers)
                    {
                        queueUsersItems.add(queueUser);
                        queueListAdapter.notifyDataSetChanged();
                    }
                    queueListAdapter.notifyDataSetChanged();

                }
                else
                {
                    Toast.makeText(getApplicationContext(), statusMessage, Toast.LENGTH_SHORT).show();
                }
            }
        };
        QueueUser.execute(request);
    }

    private void getQueueShop(){
        APIQueueShopRequest request = new APIQueueShopRequest();
        request.setShopid(this.userdata.get(SessionManager.KEY_USERID));

        TaskQueueShop QueueShop = new TaskQueueShop(this) {

            @Override
            public void onResult(APIQueueShopResponse response, String statusMessage, boolean isSuccess) {

                if(isSuccess) {
                    queueShopsItems.clear();
                    List<QueueShop> queueShops = response.getQueue();

                    for(QueueShop queueShop: queueShops)
                    {
                        queueShopsItems.add(queueShop);
                        queueListAdapter2.notifyDataSetChanged();
                    }
                    queueListAdapter2.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), statusMessage, Toast.LENGTH_SHORT).show();
                }
            }
        };
        QueueShop.execute(request);
    }

    @Override
    public void onClick(View v) {
        if(v==changeStatusButton)
        {
            Intent i = new Intent(this, SetFullActivity.class);
            startActivity(i);
        }else if(v==AddUserDummy){
            startActivity(new Intent(this,AddDummyUserActivity.class));
        }
        else if(v==btnDeleteBook){
            deleteBook();
        }
    }
}
