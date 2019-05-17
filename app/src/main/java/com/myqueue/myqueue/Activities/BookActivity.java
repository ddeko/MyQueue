package com.myqueue.myqueue.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.myqueue.myqueue.Callbacks.OnActionbarListener;
import com.myqueue.myqueue.Fragments.DetailShopFragment;
import com.myqueue.myqueue.Fragments.ExploreFragment;
import com.myqueue.myqueue.Fragments.NewsFeedFragment;
import com.myqueue.myqueue.Models.Feed;
import com.myqueue.myqueue.Models.ShopWithUser;
import com.myqueue.myqueue.Preferences.SessionManager;
import com.myqueue.myqueue.R;

import java.util.HashMap;

/**
 * Created by 高橋六羽 on 2016/03/21.
 */
public class BookActivity extends BaseActivity{

    public ShopWithUser responseInfo;
    public Feed responseTemp;

    private static SessionManager sessions;
    private HashMap<String,String> userdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DetailShopFragment myf = new DetailShopFragment();

        Intent i = getIntent();
        Bundle b = i.getExtras();
        int requestcode = b.getInt("requestcode");

        sessions = new SessionManager(this);
        userdata = sessions.getUserDetails();

        if(requestcode == NewsFeedFragment.BOOK_REQUEST_CODE_FEED) {
            responseTemp = (Feed) i.getSerializableExtra("Feed");
            responseInfo = responseTemp.getShop().get(0);
        }
        else if(requestcode == ExploreFragment.BOOK_REQUEST_CODE_EXPLORE)
            responseInfo = (ShopWithUser) i.getSerializableExtra("ShopWithUserItem");

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, myf);
        transaction.commit();

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
                if(userdata.get(SessionManager.KEY_USERID).toString().equals("Guest")) {
                    Toast.makeText(BookActivity.this, "Harap Login terlebih dahulu untuk melihat fitur ini", Toast.LENGTH_SHORT).show();
                }else {
                    Intent i = new Intent(BookActivity.this, WaitingListActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_book;
    }

    @Override
    public void updateUI() {

    }

    public ShopWithUser getResponseInfo() {
        return responseInfo;
    }
}
