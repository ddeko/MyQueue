package com.myqueue.myqueue.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.myqueue.myqueue.Activities.HomeActivity;
import com.myqueue.myqueue.Activities.NewsFeedFormActivity;
import com.myqueue.myqueue.Adapter.NewsFeedListAdapter;
import com.myqueue.myqueue.Models.NewsFeedListItem;
import com.myqueue.myqueue.R;

import java.util.ArrayList;

/**
 * Created by 高橋六羽 on 2016/03/21.
 */
public class NewsFeedFragment extends Fragment implements View.OnClickListener{

    private FloatingActionButton btnAddNews;
    private ListView newsListView;

    public static final int NEWSFEEDFORM_REQUEST_CODE = 4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_newsfeed, container, false);

        btnAddNews = (FloatingActionButton) v.findViewById(R.id.btnCreatePost);
        btnAddNews.setElevation(2);
        btnAddNews.setOnClickListener(this);

        setupActionBar();

        newsListView = (ListView) v.findViewById(R.id.listViewNews);
        newsListView.setElevation(1);

        ArrayList<NewsFeedListItem> feedItems = new ArrayList<NewsFeedListItem>();

        NewsFeedListItem newsFeedListItem1 = new NewsFeedListItem(0,"Pizza Hut",0,"Special Discount");
        NewsFeedListItem newsFeedListItem2 = new NewsFeedListItem(0,"Pizza Hut",0,"Special Discount");
        NewsFeedListItem newsFeedListItem3 = new NewsFeedListItem(0,"Pizza Hut",0,"Special Discount");
        NewsFeedListItem newsFeedListItem4 = new NewsFeedListItem(0,"Pizza Hut",0,"Special Discount");

        feedItems.add(newsFeedListItem1);
        feedItems.add(newsFeedListItem2);
        feedItems.add(newsFeedListItem3);
        feedItems.add(newsFeedListItem4);

        NewsFeedListAdapter feedListAdapter = new NewsFeedListAdapter(getContext(), R.layout.item_newsfeed, feedItems);

        newsListView.setAdapter(feedListAdapter);

        return v;
    }

    private void setupActionBar() {
        HomeActivity mainActivity = (HomeActivity)getActivity();
        mainActivity.setDefaultActionbarIcon();
        mainActivity.setLeftIcon(0);
    }

    @Override
    public void onClick(View v) {
        if(v == btnAddNews)
        {
            Intent i = new Intent(this.getActivity(), NewsFeedFormActivity.class);
            startActivityForResult(i, NEWSFEEDFORM_REQUEST_CODE);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==NewsFeedFragment.NEWSFEEDFORM_REQUEST_CODE)
        {
            if(resultCode != Activity.RESULT_OK) {
                return;
            }
            else
            {

            }
        }
    }


}
