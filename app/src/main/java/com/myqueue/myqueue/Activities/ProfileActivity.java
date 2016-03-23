package com.myqueue.myqueue.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.myqueue.myqueue.Callbacks.OnActionbarListener;
import com.myqueue.myqueue.Fragments.ProfileFragment;
import com.myqueue.myqueue.Models.User;
import com.myqueue.myqueue.Preferences.SessionManager;
import com.myqueue.myqueue.R;

/**
 * Created by 高橋六羽 on 2016/03/22.
 */
public class ProfileActivity extends BaseActivity{

    private static SessionManager sessions;

    public User userActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessions = new SessionManager(this);

        setDefaultActionbarIcon();
        setRightIcon(0);

        ProfileFragment myf = new ProfileFragment();

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
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_profile;
    }

    @Override
    public void updateUI() {

    }
}
