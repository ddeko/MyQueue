package com.myqueue.myqueue.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myqueue.myqueue.Activities.BookActivity;
import com.myqueue.myqueue.Callbacks.OnActionbarListener;
import com.myqueue.myqueue.R;

import info.hoang8f.widget.FButton;

/**
 * Created by DedeEko on 3/29/2016.
 */
public class SetFullActivity extends BaseActivity implements View.OnClickListener{

    private Fragment fragment;

    private FButton fullButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setRightIcon(0);
        setActionBarTitle("Shop Status");

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
                fullButton.setText("E M P T Y");
                fullButton.setButtonColor(getResources().getColor(R.color.fbutton_color_emerald));
                fullButton.setShadowColor(getResources().getColor(R.color.fbutton_color_nephritis));
            }
            else{
                fullButton.setText("F U L L");
                fullButton.setButtonColor(getResources().getColor(R.color.actionbar_color));
                fullButton.setShadowColor(getResources().getColor(R.color.fbutton_color_pomegranate));
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

}
