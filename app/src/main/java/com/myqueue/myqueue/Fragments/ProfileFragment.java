package com.myqueue.myqueue.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.myqueue.myqueue.Activities.HomeActivity;
import com.myqueue.myqueue.Activities.StoreScreenActivity;
import com.myqueue.myqueue.R;
import com.myqueue.myqueue.Views.RoundedImage;

/**
 * Created by 高橋六羽 on 2016/03/21.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener{

    private ImageView imgcover,profilePicture;
    private EditText txtStore;
    private RoundedImage cropCircle;
    private Button updatebtn;

    public static final int SHOPSCREEN_REQUEST_CODE = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        setupActionBar();

        imgcover = (ImageView)v.findViewById(R.id.coverPicture);
        profilePicture = (ImageView)v.findViewById(R.id.profilepicture);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.contohpp);
        cropCircle = new RoundedImage(bm);
        profilePicture.setImageDrawable(cropCircle);
        updatebtn = (Button)v.findViewById(R.id.btnUpdateProfile);
        txtStore = (EditText)v.findViewById(R.id.txtStoreAdd);

        updatebtn.setOnClickListener(this);
        txtStore.setOnClickListener(this);

        return v;
    }

    private void setupActionBar() {
        HomeActivity mainActivity = (HomeActivity)getActivity();
        mainActivity.setDefaultActionbarIcon();
        mainActivity.setLeftIcon(0);
    }

    @Override
    public void onClick(View v) {
        if(v == updatebtn)
        {
            Toast.makeText(getActivity(), "Profile Updated", Toast.LENGTH_SHORT).show();
        }
        else if(v == txtStore)
        {
            Intent i = new Intent(getActivity(), StoreScreenActivity.class);
            startActivityForResult(i, SHOPSCREEN_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode== ProfileFragment.SHOPSCREEN_REQUEST_CODE)
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
