package com.myqueue.myqueue.Activities;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
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
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialize.util.UIUtils;
import com.myqueue.myqueue.Callbacks.OnActionbarListener;
import com.myqueue.myqueue.Preferences.SessionManager;
import com.myqueue.myqueue.R;

import net.yanzm.mth.MaterialTabHost;

import java.util.HashMap;
import java.util.Locale;
/**
 * Created by leowirasanto on 3/6/2016.
 */
public class HomeActivity extends BaseActivity {

    private static SessionManager sessions;
    private Drawer result = null;
    private AccountHeader headerResult = null;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessions = new SessionManager(this);

        if(getSupportActionBar()!= null){
            getSupportActionBar().setElevation(0);
        }

        final MaterialTabHost tabHost = (MaterialTabHost)findViewById(android.R.id.tabhost);
        tabHost.setType(MaterialTabHost.Type.FullScreenWidth);

        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        for(int i = 0 ; i < pagerAdapter.getCount();i++){
            tabHost.addTab(pagerAdapter.getPageTitle(i));
        }

        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(tabHost);

        tabHost.setOnTabChangeListener(new MaterialTabHost.OnTabChangeListener() {
            @Override
            public void onTabSelected(int position) {
                    viewPager.setCurrentItem(position);
            }
        });

        final IProfile profile = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(R.drawable.profile);

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
                .withSavedInstance(savedInstanceState)
                .build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_free_play).withIcon(FontAwesome.Icon.faw_gamepad).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_custom).withIcon(FontAwesome.Icon.faw_eye).withIdentifier(3),
                        new SectionDrawerItem().withName(R.string.drawer_item_section_header),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog).withIdentifier(4),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_question).withIdentifier(5),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_github).withIdentifier(6),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_bullhorn).withIdentifier(7)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {
                                intent = new Intent(HomeActivity.this, BookScreenActivity.class);
                                HomeActivity.this.startActivity(intent);
                                Toast.makeText(HomeActivity.this, ((Nameable) drawerItem).getName().getText(HomeActivity.this), Toast.LENGTH_SHORT).show();
                            } else if (drawerItem.getIdentifier() == 2) {
                                intent = new Intent(HomeActivity.this, NewsFeedFormActivity.class);
                                HomeActivity.this.startActivity(intent);
                            }
                            if (intent != null) {
                                HomeActivity.this.startActivity(intent);
                            }
                        }
                        return false;
                    }
                })
                .withSelectedItem(-1)
                .withSavedInstance(savedInstanceState)
                .build();

        //set the back arrow in the toolbar

        // setup default activity action bar


    }

    @Override
    public void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
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
                onBackPressed();
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.tab_host;
    }

    @Override
    public void updateUI() {

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
        Toast.makeText(this, "aaaaaaa", Toast.LENGTH_LONG).show();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm){
            super(fm);
        }



        @Override
        public Fragment getItem(int position) {
            return PlaceHolderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position){
                case 0:
                    return getString(R.string.tab_session1).toUpperCase(l);
                case 1 :
                    return getString(R.string.tab_session2).toUpperCase(l);
                case 2 :
                    return getString(R.string.tab_session3).toUpperCase(l);
            }
            return null;
        }
    }

    public static class PlaceHolderFragment extends Fragment{
        private static final String ARG_SECTION_NUMBER = "section_number";
        private FloatingActionButton btnAddNews;
        private RelativeLayout relativeLayout;
        private ListView newsFeedList;

        public static PlaceHolderFragment newInstance(int sectionNumber){
            PlaceHolderFragment fragment = new PlaceHolderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceHolderFragment(){}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_sample,container,false);
            TextView tv = (TextView) rootView.findViewById(R.id.section_label);
            btnAddNews = (FloatingActionButton) rootView.findViewById(R.id.btnCreatePost);
            relativeLayout = (RelativeLayout) rootView.findViewById(R.id.tab1screen);
            newsFeedList = (ListView) rootView.findViewById(R.id.listNewsFeed);

            setupActionBar();


            if(getArguments().getInt(ARG_SECTION_NUMBER)>1)
            {
                btnAddNews.setVisibility(View.INVISIBLE);

                newsFeedList.setVisibility(View.INVISIBLE);
            }


        btnAddNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar sb = Snackbar.make(relativeLayout, "News Feed Success", Snackbar.LENGTH_LONG);
                sb.show();

                //LOGOUT USER
                sessions.logoutUser();

            }
        });

            //HOW TO FETCH LOGGED IN USER DATA FROM SHARED PREFERENCE
            SessionManager session = new SessionManager(getContext());
            HashMap<String, String> user = session.getUserDetails();

            tv.setText(user.get(SessionManager.KEY_NAME));


            return rootView;

        }

        private void setupActionBar() {
            HomeActivity mainActivity = (HomeActivity)getActivity();
            mainActivity.setDefaultActionbarIcon();
        }
    }
}
