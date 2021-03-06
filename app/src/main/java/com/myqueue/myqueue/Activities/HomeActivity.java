package com.myqueue.myqueue.Activities;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.myqueue.myqueue.Callbacks.OnActionbarListener;
import com.myqueue.myqueue.Fragments.ExploreFragment;
import com.myqueue.myqueue.Fragments.NewsFeedFragment;
import com.myqueue.myqueue.Preferences.SessionManager;
import com.myqueue.myqueue.R;

import net.yanzm.mth.MaterialTabHost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by leowirasanto on 3/6/2016.
 */
public class HomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener{

    private static SessionManager sessions;
    private Drawer result = null;
    private AccountHeader headerResult = null;

    private MaterialTabHost tabHost;
    private ViewPager viewPager;

    private Bundle savedInstanceState;
    private HashMap<String,String> userdata;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessions = new SessionManager(this);
        this.savedInstanceState = savedInstanceState;

        //START TAB HOST CODE

        tabHost = (MaterialTabHost)findViewById(android.R.id.tabhost);
        tabHost.setType(MaterialTabHost.Type.FullScreenWidth);
        tabHost.setup();

        String[] tabnames = {"NEWSFEED", "EXPLORE"};

        for(int i=0; i<tabnames.length; i++) {
            TabHost.TabSpec tabSpec;
            tabSpec = tabHost.newTabSpec(tabnames[i]);
            tabSpec.setIndicator(tabnames[i]);
            tabSpec.setContent(new FakeContent(this));
            tabHost.addTab(tabSpec);
        }

        tabHost.setOnTabChangedListener(this);

        List<Fragment> listFragments = new ArrayList<Fragment>();
        listFragments.add(new NewsFeedFragment());
        listFragments.add(new ExploreFragment());

        viewPager = (ViewPager)findViewById(R.id.pager);
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), listFragments);
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.setOnPageChangeListener(tabHost);



        int selectedItem  = tabHost.getCurrentTab();

        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            View v = tabHost.getTabWidget().getChildAt(i);

            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextSize(14);

            if(i==selectedItem)
                tv.setTextColor(getResources().getColor(R.color.white));
            else
                tv.setTextColor(getResources().getColor(R.color.actionbar_dark_color));
        }

        //END TAB HOST CCODE

        userdata = sessions.getUserDetails();

        //initialize and create the image loader logic
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Glide.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Glide.clear(imageView);
            }
        });

        setNavigationBar();

    }

    @Override
    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.setBackgroundColor(getResources().getColor(R.color.actionbar_color));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void setUICallbacks() {
        setActionbarListener(new OnActionbarListener() {
            @Override
            public void onLeftIconClick() {

            }

            @Override
            public void onRightIconClick() {
                if(userdata.get(SessionManager.KEY_USERID).toString().equals("Guest")) {
                    Toast.makeText(HomeActivity.this, "Harap Login terlebih dahulu untuk melihat fitur ini", Toast.LENGTH_SHORT).show();
                }else {
                    Intent i = new Intent(HomeActivity.this, WaitingListActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.tab_host;
    }

    @Override
    public void updateUI() {
        setNavigationBar();
        registerGCM();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String tabId) {
        int selectedItem  = tabHost.getCurrentTab();

        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            View v = tabHost.getTabWidget().getChildAt(i);

            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);

            if(i==selectedItem)
                tv.setTextColor(getResources().getColor(R.color.white));
            else
                tv.setTextColor(getResources().getColor(R.color.actionbar_dark_color));
        }

        viewPager.setCurrentItem(selectedItem);
    }

    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> listFragment;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> listFragment) {
            super(fm);
            this.listFragment = listFragment;
        }

        @Override
        public Fragment getItem(int position) {
            return listFragment.get(position);
        }

        @Override
        public int getCount() {
            return listFragment.size();
        }
    }


    public class FakeContent implements TabHost.TabContentFactory
    {
        Context context;
        public FakeContent(Context context)
        {
            this.context = context;
        }


        @Override
        public View createTabContent(String s) {
            View fakeView = new View(context);
            fakeView.setMinimumHeight(0);
            fakeView.setMinimumWidth(0);
            return fakeView;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    public void setNavigationBar()
    {
        if(result!=null)
            result.removeAllItems();

        final IProfile profile;

        if(userdata.get(SessionManager.KEY_PROFILEPHOTO) != null)
            profile = new ProfileDrawerItem().withName(userdata.get(SessionManager.KEY_NAME)).withEmail(userdata.get(SessionManager.KEY_EMAIL))
                .withIcon(userdata.get(SessionManager.KEY_PROFILEPHOTO));
        else
            profile = new ProfileDrawerItem().withName(userdata.get(SessionManager.KEY_NAME)).withEmail(userdata.get(SessionManager.KEY_EMAIL))
                    .withIcon(R.drawable.no_user);

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(true)
                .withHeightDp(90)
                .withPaddingBelowHeader(true)
                .withHeaderBackground(R.color.actionbar_color)
                .addProfiles(
                        profile,
                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                        new ProfileSettingDrawerItem().withName("Manage Account").withIcon(GoogleMaterial.Icon.gmd_settings)
                )
                .withProfileImagesClickable(true)
                .withSavedInstance(savedInstanceState)
                .withOnAccountHeaderProfileImageListener(new AccountHeader.OnAccountHeaderProfileImageListener() {
                    @Override
                    public boolean onProfileImageClick(View view, IProfile profile, boolean current) {
                        Intent i = new Intent(HomeActivity.this, ProfileActivity.class);
                        startActivity(i);
                        return false;
                    }

                    @Override
                    public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {
                        Intent i = new Intent(HomeActivity.this, ProfileActivity.class);
                        startActivity(i);
                        return false;
                    }
                })
                .build();

        if(userdata.get(SessionManager.KEY_USERID).toString().equals("Guest")) {


            result = new DrawerBuilder()
                    .withActivity(this)
                    .withTranslucentStatusBar(true)
                    .withAccountHeader(headerResult)
                    .withToolbar(toolbar)
                    .addDrawerItems(
                            new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withIdentifier(1),
                            //new PrimaryDrawerItem().withName(R.string.drawer_item_custom).withIcon(FontAwesome.Icon.faw_eye).withIdentifier(3),
                            new SectionDrawerItem().withName("Others"),
                            new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog).withIdentifier(4),
                            new SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_question).withIdentifier(5),
                            //new SecondaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_github).withIdentifier(6),
                            new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_bullhorn).withIdentifier(7),
                            new SecondaryDrawerItem().withName("Login as User").withIcon(FontAwesome.Icon.faw_sign_out).withIdentifier(8)
                    )
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            if (drawerItem != null) {
                                Intent intent = null;
                                if (drawerItem.getIdentifier() == 8) {
                                    sessions.logoutUser();
                                    unregisterGCM();
                                }
                                if (intent != null) {

                                }
                            }
                            return false;
                        }
                    })
                    .withSelectedItem(-1)
                    .withSavedInstance(savedInstanceState)
                    .build();
        }
        else{


            result = new DrawerBuilder()
                    .withActivity(this)
                    .withTranslucentStatusBar(true)
                    .withAccountHeader(headerResult)
                    .withToolbar(toolbar)
                    .addDrawerItems(
                            new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withIdentifier(1),
                            new PrimaryDrawerItem().withName("Profile").withIcon(FontAwesome.Icon.faw_user).withIdentifier(2),
                            //new PrimaryDrawerItem().withName(R.string.drawer_item_custom).withIcon(FontAwesome.Icon.faw_eye).withIdentifier(3),
                            new SectionDrawerItem().withName("Others"),
                            new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog).withIdentifier(4),
                            new SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_question).withIdentifier(5),
                            //new SecondaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_github).withIdentifier(6),
                            new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_bullhorn).withIdentifier(7),
                            new SecondaryDrawerItem().withName("Logout").withIcon(FontAwesome.Icon.faw_sign_out).withIdentifier(8)
                    )
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            if (drawerItem != null) {
                                Intent intent = null;
                                if (drawerItem.getIdentifier() == 2) {
                                    Intent i = new Intent(HomeActivity.this, ProfileActivity.class);
                                    startActivity(i);
                                } else if (drawerItem.getIdentifier() == 8) {
                                    sessions.logoutUser();
                                    unregisterGCM();
                                }
                                if (intent != null) {

                                }
                            }
                            return false;
                        }
                    })
                    .withSelectedItem(-1)
                    .withSavedInstance(savedInstanceState)
                    .build();
        }

    }



}
