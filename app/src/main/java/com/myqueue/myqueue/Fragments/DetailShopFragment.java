package com.myqueue.myqueue.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myqueue.myqueue.Activities.BookActivity;
import com.myqueue.myqueue.R;

/**
 * Created by 高橋六羽 on 2016/03/22.
 */
public class DetailShopFragment extends Fragment implements View.OnClickListener{

    private FloatingActionButton toBookbtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_detailshop, container, false);

        setupActionBar();

        toBookbtn = (FloatingActionButton)v.findViewById(R.id.btnBook);
        toBookbtn.setOnClickListener(this);

        return v;
    }

    private void setupActionBar() {
        BookActivity mainActivity = (BookActivity)getActivity();
        mainActivity.setDefaultActionbarIcon();
    }

    @Override
    public void onClick(View v) {
        if(v==toBookbtn)
        {
            BookScreenFragment myf = new BookScreenFragment();

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, myf);
            transaction.addToBackStack("Detail");
            transaction.commit();

        }
    }
}
