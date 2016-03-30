package com.myqueue.myqueue.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.myqueue.myqueue.APIs.TaskExplore;
import com.myqueue.myqueue.Activities.BookActivity;
import com.myqueue.myqueue.Activities.HomeActivity;
import com.myqueue.myqueue.Activities.WaitingListActivity;
import com.myqueue.myqueue.Adapter.ExploreListAdapter;
import com.myqueue.myqueue.Models.APIExploreResponse;
import com.myqueue.myqueue.Models.ShopWithUser;
import com.myqueue.myqueue.R;

import java.util.ArrayList;
import java.util.List;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by 高橋六羽 on 2016/03/21.
 */
public class ExploreFragment extends Fragment {

    private ListView exploreListView;
    private Fragment fragment;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    ExploreListAdapter exploreListAdapter;

    private List<ShopWithUser> exploreItems = new ArrayList<ShopWithUser>();

    public static final int BOOK_REQUEST_CODE_EXPLORE = 3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_explore, container, false);
        setupActionBar();
        fragment = this;

        exploreListView = (ListView) v.findViewById(R.id.exploreList);

        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) v.findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setWaveColor(getResources().getColor(R.color.actionBarColorARGB));

        exploreListAdapter = new ExploreListAdapter(getContext(), R.layout.item_explore, exploreItems);

        exploreListView.setAdapter(exploreListAdapter);

        fetchData();

        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                // Do work to refresh the list here.
                fetchData();
            }

        });

        exploreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> exploreitemcurrent, View v, int position,
                                    long id) {

                Intent i = new Intent(fragment.getActivity(), BookActivity.class);
                i.putExtra("ShopWithUserItem",(ShopWithUser) exploreitemcurrent.getItemAtPosition(position));
                i.putExtra("requestcode", BOOK_REQUEST_CODE_EXPLORE);
                startActivityForResult(i, BOOK_REQUEST_CODE_EXPLORE);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==ExploreFragment.BOOK_REQUEST_CODE_EXPLORE)
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
        TaskExplore explore = new TaskExplore(getActivity()) {

            @Override
            public void onResult(APIExploreResponse response, String statusMessage, boolean isSuccess) {

                if(isSuccess) {
                    exploreItems.clear();
                    exploreItems.addAll(response.getShop());

                    exploreListAdapter.notifyDataSetChanged();

                    mWaveSwipeRefreshLayout.setRefreshing(false);
                }
                else
                {
                    Toast.makeText(getActivity(), statusMessage, Toast.LENGTH_SHORT).show();

                    mWaveSwipeRefreshLayout.setRefreshing(false);
                }

            }
        };
        explore.execute();

    }

    @Override
    public void onResume() {
        super.onResume();
        mWaveSwipeRefreshLayout.setRefreshing(true);
        mWaveSwipeRefreshLayout.setRefreshing(false);
    }
}
