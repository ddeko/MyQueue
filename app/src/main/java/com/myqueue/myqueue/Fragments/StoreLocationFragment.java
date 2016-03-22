package com.myqueue.myqueue.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.myqueue.myqueue.Activities.ProfileActivity;
import com.myqueue.myqueue.R;

/**
 * Created by 高橋六羽 on 2016/03/22.
 */
public class StoreLocationFragment extends Fragment implements View.OnClickListener{

    private Button updatebtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_store_location, container, false);

        setupActionBar();

        updatebtn = (Button)v.findViewById(R.id.btnUpdateProfile);

        updatebtn.setOnClickListener(this);

        return v;
    }

    private void setupActionBar() {
        ProfileActivity mainActivity = (ProfileActivity)getActivity();
        mainActivity.setDefaultActionbarIcon();
        mainActivity.setRightIcon(0);
    }

    @Override
    public void onClick(View v) {
        if(v == updatebtn)
        {
            Toast.makeText(getActivity(), "StoreScreen Updated", Toast.LENGTH_SHORT).show();
        }
    }
}
