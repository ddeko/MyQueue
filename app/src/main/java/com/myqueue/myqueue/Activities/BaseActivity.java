package com.myqueue.myqueue.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myqueue.myqueue.Base.ActivityInterface;
import com.myqueue.myqueue.Callbacks.OnActionbarListener;
import com.myqueue.myqueue.R;

public abstract class BaseActivity extends ActionBarActivity implements ActivityInterface {

    private final Handler handler = new Handler();

    protected Context context;
    private View actionBarView;

    private OnActionbarListener actionbarListener;
    private TextView tvActionBarTitle;
    private TextView tvActionBarTitleCenter;
    private TextView tvBadge;
    private ImageView leftIcon, rightIcon;
    protected RelativeLayout toolbarBackground;

    private RelativeLayout actionRightContainer;

    private LinearLayout primaryLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        setContentView(getLayout());
        initView();
        setUICallbacks();
        showCustomActionBar();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void showCustomActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null) {
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            actionBarView = inflater.inflate(R.layout.view_custom_actionbar, null, false);
            actionBarClickListener();
            tvActionBarTitle = (TextView) actionBarView.findViewById(R.id.tv_title);
            tvActionBarTitleCenter = (TextView) actionBarView.findViewById(R.id.tv_title_2);
            tvBadge = (TextView) actionBarView.findViewById(R.id.cart_badge);
            leftIcon = (ImageView) actionBarView.findViewById(R.id.iv_action_left);
            rightIcon = (ImageView) actionBarView.findViewById(R.id.iv_action_right);
            toolbarBackground = (RelativeLayout) actionBarView.findViewById(R.id.toolbar_background);
            primaryLogo = (LinearLayout) actionBarView.findViewById(R.id.tv_primary_logo);
            actionRightContainer = (RelativeLayout) actionBarView.findViewById(R.id.action_right_container);


            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setCustomView(actionBarView);
            actionBar.show();
        }
    }

    private void actionBarClickListener() {
        View actionLeft = actionBarView.findViewById(R.id.left_icon_container);
        View actionRight = actionBarView.findViewById(R.id.right_icon_container);

        actionLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionbarListener != null)
                    actionbarListener.onLeftIconClick();
            }
        });

        actionRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionbarListener != null)
                    actionbarListener.onRightIconClick();
            }
        });
    }

    public void setActionBarColor(int resColor) {
        actionBarView.setBackgroundResource(resColor);
    }

    public void setActionbarListener(OnActionbarListener actionbarListener) {
        this.actionbarListener = actionbarListener;
    }

    public void setLeftIcon(int drawableRes) {
        if(leftIcon != null) {
            leftIcon.setVisibility(drawableRes == 0 ? View.GONE : View.VISIBLE);

            leftIcon.setImageResource(drawableRes);
        }
    }

    @Override
    public void onBackPressed() {
        int stackCount = getFragmentManager().getBackStackEntryCount();

        if(stackCount > 0)
            getFragmentManager().popBackStack();
        else
            super.onBackPressed();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    public void setRightIcon(int drawableRes) {
        if(rightIcon != null) {
            rightIcon.setVisibility(drawableRes == 0 ? View.GONE : View.VISIBLE);
            actionRightContainer.setVisibility(drawableRes == 0 ? View.GONE : View.VISIBLE);
            rightIcon.setImageResource(drawableRes);
        }
    }

    public void setActionBarTitle(String title) {
        if(tvActionBarTitle != null) {
            tvActionBarTitleCenter.setVisibility(View.GONE);
            tvBadge.setVisibility(View.GONE);
            tvActionBarTitle.setText(title);
            tvActionBarTitle.setVisibility(View.VISIBLE);
            primaryLogo.setVisibility(View.GONE);
        }
    }

    public void setActionBarTitleCenter(String title) {
        if(tvActionBarTitleCenter != null) {
            tvActionBarTitle.setVisibility(View.GONE);
            tvActionBarTitleCenter.setText(title);
            tvActionBarTitleCenter.setVisibility(View.VISIBLE);
            primaryLogo.setVisibility(View.GONE);
        }
    }

    public void setActionBarCartBadge(String badge) {
        tvBadge.setText(badge);
        tvBadge.setVisibility(View.VISIBLE);
    }

    public void setDefaultActionbarIcon() {
        rightIcon.setVisibility(View.VISIBLE);
        actionRightContainer.setVisibility(View.VISIBLE);
        primaryLogo.setVisibility(View.VISIBLE);
        tvActionBarTitle.setVisibility(View.GONE);
        tvActionBarTitleCenter.setVisibility(View.GONE);
        setRightIcon(R.drawable.logoclock);
        tvBadge.setVisibility(View.VISIBLE);
    }



    public int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    public int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public DisplayMetrics getDensityScreen(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    public int getDPISize(int value, float scale) {
        return (int) (value * scale + 0.5f);
    }

    public void clearBackStack() {
        FragmentManager fm = getFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }


}
