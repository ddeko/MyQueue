package com.myqueue.myqueue.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.myqueue.myqueue.Callbacks.OnActionbarListener;
import com.myqueue.myqueue.R;

/**
 * Created by 高橋六羽 on 2016/03/22.
 */
public class StoreScreenActivity extends BaseActivity implements View.OnClickListener{

    private Button updatebtn;

    private Intent resultIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaultActionbarIcon();
        setRightIcon(0);

        updatebtn = (Button)findViewById(R.id.btnUpdateProfile);

        updatebtn.setOnClickListener(this);
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
        return R.layout.activity_store_location;
    }

    @Override
    public void updateUI() {

    }

    @Override
    public void onClick(View v) {
        if(v == updatebtn)
        {
            resultIntent = new Intent();
            resultIntent.putExtra("resultkey", "result");
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
    }
}
