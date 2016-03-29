package com.myqueue.myqueue.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.myqueue.myqueue.Callbacks.OnActionbarListener;
import com.myqueue.myqueue.Fragments.ProfileFragment;
import com.myqueue.myqueue.Models.User;
import com.myqueue.myqueue.Preferences.SessionManager;
import com.myqueue.myqueue.R;

import java.util.HashMap;

/**
 * Created by 高橋六羽 on 2016/03/22.
 */
public class ProfileActivity extends BaseActivity{

    public static SessionManager sessions;

    public HashMap<String,String> userData;
    public HashMap<String,String> shopData;

    public boolean isOwner = false;

    ProfileFragment profileFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessions = new SessionManager(this);

        userData = sessions.getUserDetails();
        shopData = sessions.getShopDetails();

        if(userData.get(SessionManager.KEY_ISOWNER).equalsIgnoreCase("1"))
        {
            isOwner = true;
        }

        String Email = userData.get(SessionManager.KEY_EMAIL);

        setDefaultActionbarIcon();
        setRightIcon(0);

        profileFragment = new ProfileFragment();

        replaceFragment(R.id.fragment_container, profileFragment, false);

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

    public boolean getIsOwner() {
        return isOwner;
    }

}
