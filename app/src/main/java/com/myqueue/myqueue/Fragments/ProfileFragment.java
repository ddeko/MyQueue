package com.myqueue.myqueue.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.myqueue.myqueue.Activities.ProfileActivity;
import com.myqueue.myqueue.R;
import com.myqueue.myqueue.Views.RoundedImage;

/**
 * Created by 高橋六羽 on 2016/03/21.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener{

    private LinearLayout customerContainer;
    private LinearLayout shopOwnerContainer;

    private ImageView imgcover,profilePicture;
    private TextInputLayout txtStore;
    private RoundedImage cropCircle;
    private Button updatebtn;

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
        txtStore = (TextInputLayout)v.findViewById(R.id.storeaddwrapper);

        updatebtn.setOnClickListener(this);
        txtStore.setOnClickListener(this);

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
            Toast.makeText(getActivity(), "Profile Updated", Toast.LENGTH_SHORT).show();
        }
        else if(v == txtStore)
        {
            StoreLocationFragment myf = new StoreLocationFragment();

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, myf);
            transaction.addToBackStack("Store");
            transaction.commit();
        }
    }

}
