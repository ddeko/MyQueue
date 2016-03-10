package com.myqueue.myqueue;



import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import net.yanzm.mth.MaterialTabHost;

import java.util.List;
import java.util.Locale;
/**
 * Created by leowirasanto on 3/6/2016.
 */
public class tabActivity extends ActionBarActivity {
    private Toolbar myActionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_host);
        myActionBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myActionBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(getSupportActionBar()!= null){
            getSupportActionBar().setElevation(0);
        }



        final MaterialTabHost tabHost = (MaterialTabHost)findViewById(android.R.id.tabhost);
        tabHost.setType(MaterialTabHost.Type.FullScreenWidth);

        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        for(int i = 0 ; i < pagerAdapter.getCount();i++){
            tabHost.addTab(pagerAdapter.getPageTitle(i));
        }






        Log.d("Testestes","Test");

        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(tabHost);

        tabHost.setOnTabChangeListener(new MaterialTabHost.OnTabChangeListener() {
            @Override
            public void onTabSelected(int position) {
                    viewPager.setCurrentItem(position);
            }
        });
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


            if(getArguments().getInt(ARG_SECTION_NUMBER)>1)
            {
                btnAddNews.setVisibility(View.INVISIBLE);

                newsFeedList.setVisibility(View.INVISIBLE);
            }


        btnAddNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar sb = Snackbar.make(relativeLayout,"News Feed Success",Snackbar.LENGTH_LONG);
                sb.show();
            }
        });

            tv.setText("Here is the page " + getArguments().getInt(ARG_SECTION_NUMBER));
            return rootView;

        }
    }
}
