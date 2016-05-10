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
import android.widget.TextView;
import android.widget.Toast;

import com.myqueue.myqueue.APIs.TaskExplore;
import com.myqueue.myqueue.APIs.TaskFilterCategory;
import com.myqueue.myqueue.APIs.TaskFilterName;
import com.myqueue.myqueue.Activities.BaseActivity;
import com.myqueue.myqueue.Activities.BookActivity;
import com.myqueue.myqueue.Activities.HomeActivity;
import com.myqueue.myqueue.Activities.WaitingListActivity;
import com.myqueue.myqueue.Adapter.ExploreListAdapter;
import com.myqueue.myqueue.Fragments.Dialogs.FilterDialog;
import com.myqueue.myqueue.Models.APIExploreResponse;
import com.myqueue.myqueue.Models.APIFilterCategoryRequest;
import com.myqueue.myqueue.Models.APIFilterNameRequest;
import com.myqueue.myqueue.Models.ShopWithUser;
import com.myqueue.myqueue.R;

import java.util.ArrayList;
import java.util.List;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by 高橋六羽 on 2016/03/21.
 */
public class ExploreFragment extends Fragment implements View.OnClickListener{

    private ListView exploreListView;
    private TextView filterBtn;
<<<<<<< Updated upstream
    private TextView resetBtn;
=======
>>>>>>> Stashed changes
    private Fragment fragment;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    ExploreListAdapter exploreListAdapter;

    public String filterCat;
    public String filterName;

    public int refreshState = 0;

    private List<ShopWithUser> exploreItems = new ArrayList<ShopWithUser>();

    public static final int BOOK_REQUEST_CODE_EXPLORE = 3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_explore, container, false);
        setupActionBar();
        fragment = this;

        exploreListView = (ListView) v.findViewById(R.id.exploreList);
        filterBtn = (TextView) v.findViewById(R.id.filter_btn);
<<<<<<< Updated upstream
        resetBtn = (TextView) v.findViewById(R.id.resetBtn);

        filterBtn.setOnClickListener(this);
        resetBtn.setOnClickListener(this);
=======

        filterBtn.setOnClickListener(this);
>>>>>>> Stashed changes

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
        if(refreshState == 0) {
            mWaveSwipeRefreshLayout.setRefreshing(true);
            TaskExplore explore = new TaskExplore(getActivity()) {

                @Override
                public void onResult(APIExploreResponse response, String statusMessage, boolean isSuccess) {

                    if (isSuccess) {
                        exploreItems.clear();
                        List<ShopWithUser> exploreResponseItems = response.getShop();

                        for (ShopWithUser exploreResponseItem : exploreResponseItems) {
                            exploreItems.add(exploreResponseItem);
                            exploreListAdapter.notifyDataSetChanged();
                        }

                        mWaveSwipeRefreshLayout.setRefreshing(false);
                    } else {
                        Toast.makeText(getActivity(), statusMessage, Toast.LENGTH_SHORT).show();

                        mWaveSwipeRefreshLayout.setRefreshing(false);
                    }

                }
            };
            explore.execute();
        }
        else if(refreshState == 1)
        {
            mWaveSwipeRefreshLayout.setRefreshing(true);
            APIFilterCategoryRequest request = new APIFilterCategoryRequest();
            request.setCategory_name(filterCat);

<<<<<<< Updated upstream
=======
            Toast.makeText(getActivity(), filterCat +" sda "+ request.getCategory_name()  , Toast.LENGTH_SHORT).show();
>>>>>>> Stashed changes

            TaskFilterCategory filterCategory = new TaskFilterCategory(getActivity()) {
                @Override
                public void onResult(APIExploreResponse response, String statusMessage, boolean isSuccess) {
                    if (isSuccess) {
                        exploreItems.clear();
                        List<ShopWithUser> exploreResponseItems = response.getShop();

                        for (ShopWithUser exploreResponseItem : exploreResponseItems) {
                            exploreItems.add(exploreResponseItem);
                            exploreListAdapter.notifyDataSetChanged();
                        }

                        mWaveSwipeRefreshLayout.setRefreshing(false);
                    } else {
                        Toast.makeText(getActivity(), statusMessage, Toast.LENGTH_SHORT).show();

                        mWaveSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            };
<<<<<<< Updated upstream
            filterCategory.execute(request);
=======
            filterCategory.execute();
>>>>>>> Stashed changes
        }
        else
        {
            mWaveSwipeRefreshLayout.setRefreshing(true);
            APIFilterNameRequest request = new APIFilterNameRequest();
            request.setShop_name(filterName);

            TaskFilterName filterName = new TaskFilterName(getActivity()) {
                @Override
                public void onResult(APIExploreResponse response, String statusMessage, boolean isSuccess) {
                    if (isSuccess) {
                        exploreItems.clear();
                        List<ShopWithUser> exploreResponseItems = response.getShop();

                        for (ShopWithUser exploreResponseItem : exploreResponseItems) {
                            exploreItems.add(exploreResponseItem);
                            exploreListAdapter.notifyDataSetChanged();
                        }

                        mWaveSwipeRefreshLayout.setRefreshing(false);
                    } else {
                        Toast.makeText(getActivity(), statusMessage, Toast.LENGTH_SHORT).show();

                        mWaveSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            };
<<<<<<< Updated upstream
            filterName.execute(request);
=======
            filterName.execute();
>>>>>>> Stashed changes
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mWaveSwipeRefreshLayout.setRefreshing(true);
        mWaveSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View v) {
        if(v==filterBtn)
        {
            FilterDialog filterDialog = new FilterDialog(this);
            filterDialog.show(getActivity().getSupportFragmentManager(), null);
        }
<<<<<<< Updated upstream
        else if(v == resetBtn)
        {
            setRefreshState(0);
            fetchData();
        }
=======
>>>>>>> Stashed changes
    }

    public void setFilterCategory(String category)
    {
        this.filterCat = "";
        filterCat = category;
    }

    public void setFilterName(String category)
    {
        this.filterName = "";
        filterName = category;
    }

    public void setRefreshState(int state)
    {
        this.refreshState = state;
    }

}
