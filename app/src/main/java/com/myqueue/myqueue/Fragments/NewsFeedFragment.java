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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.myqueue.myqueue.APIs.TaskFeed;
import com.myqueue.myqueue.Activities.BookActivity;
import com.myqueue.myqueue.Activities.HomeActivity;
import com.myqueue.myqueue.Activities.NewsFeedFormActivity;
import com.myqueue.myqueue.Activities.WaitingListActivity;
import com.myqueue.myqueue.Adapter.NewsFeedListAdapter;
import com.myqueue.myqueue.Models.APIFeedResponse;
import com.myqueue.myqueue.Models.Feed;
import com.myqueue.myqueue.R;

import java.util.ArrayList;
import java.util.List;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by 高橋六羽 on 2016/03/21.
 */
public class NewsFeedFragment extends Fragment implements View.OnClickListener{

    private FloatingActionButton btnAddNews;
    private ListView newsListView;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    NewsFeedListAdapter newsFeedListAdapter;

    private List<Feed> feedItems = new ArrayList<Feed>();
    private Fragment fragment;

    public static final int NEWSFEEDFORM_REQUEST_CODE = 4;
    public static final int BOOK_REQUEST_CODE_FEED = 5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_newsfeed, container, false);
        fragment = this;

        btnAddNews = (FloatingActionButton) v.findViewById(R.id.btnCreatePost);
        btnAddNews.setOnClickListener(this);

        setupActionBar();

        newsListView = (ListView) v.findViewById(R.id.listViewNews);

        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) v.findViewById(R.id.main_swipefeed);
        mWaveSwipeRefreshLayout.setWaveColor(getResources().getColor(R.color.actionBarColorARGB));

        newsFeedListAdapter = new NewsFeedListAdapter(getContext(), R.layout.item_newsfeed, feedItems);

        newsListView.setAdapter(newsFeedListAdapter);

        fetchData();


        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Do work to refresh the list here.
                fetchData();
            }

        });

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> newsitemcurrent, View v, int position,
                                    long id) {

                Intent i = new Intent(fragment.getActivity(), BookActivity.class);
                i.putExtra("Feed", (Feed) newsitemcurrent.getItemAtPosition(position));
                i.putExtra("requestcode", BOOK_REQUEST_CODE_FEED);
                startActivityForResult(i, BOOK_REQUEST_CODE_FEED);
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
        else if(requestCode==NewsFeedFragment.BOOK_REQUEST_CODE_FEED)
        {
            if(resultCode != Activity.RESULT_OK) {
                return;
            }
            else
            {
                Intent i = new Intent(getActivity(), WaitingListActivity.class);
                startActivity(i);
            }
        }
    }

    public void fetchData()
    {
        mWaveSwipeRefreshLayout.setRefreshing(true);

        TaskFeed feed = new TaskFeed(getActivity()) {

            @Override
            public void onResult(APIFeedResponse response, String statusMessage, boolean isSuccess) {

                if(isSuccess) {
                    feedItems.clear();
                    feedItems.addAll(response.getFeed());

                    newsFeedListAdapter.notifyDataSetChanged();

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


    @Override
    public void onResume() {
        super.onResume();
        fetchData();
    }


}
