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

import com.myqueue.myqueue.Activities.BookActivity;
import com.myqueue.myqueue.Activities.HomeActivity;
import com.myqueue.myqueue.Activities.WaitingListActivity;
import com.myqueue.myqueue.Adapter.ExploreListAdapter;
import com.myqueue.myqueue.Models.ExploreListItem;
import com.myqueue.myqueue.R;

import java.util.ArrayList;

/**
 * Created by 高橋六羽 on 2016/03/21.
 */
public class ExploreFragment extends Fragment {

    private ListView exploreListView;
    private Fragment fragment;

    public static final int BOOK_REQUEST_CODE = 3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_explore, container, false);
        setupActionBar();
        fragment = this;

        exploreListView = (ListView) v.findViewById(R.id.exploreList);

        ArrayList<ExploreListItem> exploreItems = new ArrayList<ExploreListItem>();

        ExploreListItem exploreListItem1 = new ExploreListItem("Pizza Hut","Jl.Babarsari Raya No 13B",0);
        ExploreListItem exploreListItem2 = new ExploreListItem("Pizza Hut","Jl.Babarsari Raya No 13B",0);
        ExploreListItem exploreListItem3 = new ExploreListItem("Pizza Hut","Jl.Babarsari Raya No 13B",0);
        ExploreListItem exploreListItem4 = new ExploreListItem("Pizza Hut","Jl.Babarsari Raya No 13B",0);

        exploreItems.add(exploreListItem1);
        exploreItems.add(exploreListItem2);
        exploreItems.add(exploreListItem3);
        exploreItems.add(exploreListItem4);

        ExploreListAdapter exploreListAdapter = new ExploreListAdapter(getContext(), R.layout.item_explore, exploreItems);

        exploreListView.setAdapter(exploreListAdapter);

        exploreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> exploreitemcurrent, View v, int position,
                                    long id) {

                Intent i = new Intent(fragment.getActivity(), BookActivity.class);
                startActivityForResult(i, BOOK_REQUEST_CODE);
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
        if(requestCode==ExploreFragment.BOOK_REQUEST_CODE)
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

}
