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
import android.widget.Toast;

import com.myqueue.myqueue.APIs.TaskFeed;
import com.myqueue.myqueue.Activities.HomeActivity;
import com.myqueue.myqueue.Activities.NewsFeedFormActivity;
import com.myqueue.myqueue.Adapter.NewsFeedListAdapter;
import com.myqueue.myqueue.Models.APIFeedResponse;
import com.myqueue.myqueue.Models.Feed;
import com.myqueue.myqueue.R;

import java.util.List;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by 高橋六羽 on 2016/03/21.
 */
public class NewsFeedFragment extends Fragment implements View.OnClickListener{

    private FloatingActionButton btnAddNews;
    private ListView newsListView;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;

    private List<Feed> feedItems;

    public static final int NEWSFEEDFORM_REQUEST_CODE = 4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_newsfeed, container, false);

        btnAddNews = (FloatingActionButton) v.findViewById(R.id.btnCreatePost);
        btnAddNews.setOnClickListener(this);

        setupActionBar();

        newsListView = (ListView) v.findViewById(R.id.listViewNews);

        fetchData();

        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) v.findViewById(R.id.main_swipefeed);
        mWaveSwipeRefreshLayout.setWaveColor(getResources().getColor(R.color.actionBarColorARGB));
        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Do work to refresh the list here.
                fetchData();
            }

        });

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

    public void fetchData()
    {
        TaskFeed feed = new TaskFeed(getActivity()) {

            @Override
            public void onResult(APIFeedResponse response, String statusMessage, boolean isSuccess) {

                if(isSuccess) {
                    feedItems = response.getFeed();

                    NewsFeedListAdapter newsFeedListAdapter = new NewsFeedListAdapter(getContext(), R.layout.item_newsfeed, feedItems);

                    newsListView.setAdapter(newsFeedListAdapter);

                    mWaveSwipeRefreshLayout.setRefreshing(false);
                }
                else
                {
                    Toast.makeText(getActivity(), statusMessage, Toast.LENGTH_SHORT).show();

                    mWaveSwipeRefreshLayout.setRefreshing(false);
                }

            }
        };
        feed.execute();

    }


}
