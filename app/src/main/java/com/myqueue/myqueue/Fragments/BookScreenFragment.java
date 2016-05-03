package com.myqueue.myqueue.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myqueue.myqueue.Activities.BookActivity;
import com.myqueue.myqueue.Models.ShopWithUser;
import com.myqueue.myqueue.Preferences.SessionManager;
import com.myqueue.myqueue.R;

import java.util.HashMap;

/**
 * Created by 高橋六羽 on 2016/03/22.
 */
public class BookScreenFragment  extends Fragment implements View.OnClickListener {

    private Toolbar myActionBar;
    private ImageView shopProfPics;
    private RelativeLayout shopCovPics;
    private TextView ShopName;
    private TextView ShopAddress;
    private TextView numberQueueNow;
    private TextView numberYours;
    private Button bookNow;
    private Intent resultIntent;

    SessionManager sessions;
    private TextView seatAvailable;
    private TextView shopStat;
    public HashMap<String,String> userDataDetails;
    public HashMap<String,String> shopDataDetails;
    public ShopWithUser dataShop;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_book_screen, container, false);

        setupActionBar();

        sessions = new SessionManager(getContext());
        userDataDetails = sessions.getUserDetails();
        shopDataDetails = sessions.getShopDetails();
        seatAvailable = (TextView)v.findViewById(R.id.seatavailable);
        shopStat = (TextView)v.findViewById(R.id.yourNumber);

        shopProfPics=(ImageView)v.findViewById(R.id.ShopProfilePics);
        shopCovPics = (RelativeLayout)v.findViewById(R.id.layoutProfile);
        ShopName = (TextView)v.findViewById(R.id.ShopName);
        ShopName.setPaintFlags(ShopName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); // underline
        ShopAddress = (TextView)v.findViewById(R.id.ShopAddress);
        numberQueueNow = (TextView)v.findViewById(R.id.bookNumberNow);
        numberYours = (TextView)v.findViewById(R.id.getQueueNumber);
        numberYours.setPaintFlags(numberYours.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); // underline
        bookNow = (Button)v.findViewById(R.id.btnBook);

        dataShop = (ShopWithUser) getActivity().getIntent().getSerializableExtra("ShopWithUserItem");

        if(dataShop.getIsfull().toString().equals("1")){
            Full();
        }else if(dataShop.getIsfull().toString().equals("0")){
            notFull();
        }
        bookNow.setOnClickListener(this);

        return v;
    }

    private void setupActionBar() {
        BookActivity mainActivity = (BookActivity)getActivity();
        mainActivity.setDefaultActionbarIcon();
    }

    @Override
    public void onClick(View v) {
        if(v == bookNow){
            resultIntent = new Intent();
            resultIntent.putExtra("resultkey", "result");
            getActivity().setResult(Activity.RESULT_OK, resultIntent);
            getActivity().finish();
        }
    }

    private void notFull(){
        seatAvailable.setVisibility(View.VISIBLE);
        shopStat.setText("SHOP STATUS");
        numberQueueNow.setText("-");
        numberYours.setVisibility(View.GONE);
        bookNow.setVisibility(View.GONE);
    }

    private void Full(){
        seatAvailable.setVisibility(View.GONE);
        shopStat.setText("YOURS WILL BE");
        numberQueueNow.setText("27");
        numberYours.setVisibility(View.VISIBLE);
        bookNow.setVisibility(View.VISIBLE);
    }
}
