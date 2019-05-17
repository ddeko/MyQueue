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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.myqueue.myqueue.APIs.TaskAddQueue;
import com.myqueue.myqueue.APIs.TaskTotalQueue;
import com.myqueue.myqueue.Activities.BookActivity;
import com.myqueue.myqueue.Models.APIAddQueueRequest;
import com.myqueue.myqueue.Models.APIBaseResponse;
import com.myqueue.myqueue.Models.APIMaxQueueRequest;
import com.myqueue.myqueue.Models.APIMaxQueueResponse;
import com.myqueue.myqueue.Models.ShopWithUser;
import com.myqueue.myqueue.Preferences.SessionManager;
import com.myqueue.myqueue.R;

import java.util.HashMap;

/**
 * Created by 高橋六羽 on 2016/03/22.
 */
public class BookScreenFragment  extends Fragment implements View.OnClickListener {
    private boolean ISFULL;

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
        bookNow = (Button)v.findViewById(R.id.btnNextQueue);

        dataShop = (ShopWithUser) getActivity().getIntent().getSerializableExtra("ShopWithUserItem");

        if(((BookActivity) getActivity()).getResponseInfo().getIsfull().equalsIgnoreCase("1")){
            Full();
        }else if(((BookActivity) getActivity()).getResponseInfo().getIsfull().equalsIgnoreCase("0")){
            notFull();
        }

        Glide.with(getActivity()).load(((BookActivity) getActivity()).getResponseInfo().getUser().get(0).getProfilephoto()).into(shopProfPics);
        ShopName.setText(((BookActivity) getActivity()).getResponseInfo().getUser().get(0).getName());
        ShopAddress.setText(((BookActivity) getActivity()).getResponseInfo().getAddress() + " " + ((BookActivity) getActivity()).getResponseInfo().getNumber());
        bookNow.setOnClickListener(this);
        getTotalQueue();
        return v;
    }

    private void setupActionBar() {
        BookActivity mainActivity = (BookActivity)getActivity();
        mainActivity.setDefaultActionbarIcon();
    }

    @Override
    public void onClick(View v) {
        if(v == bookNow){
            if(userDataDetails.get(SessionManager.KEY_USERID).toString().equals("Guest")){
                Toast.makeText(getActivity(), "Login terlebih dahulu untuk melakukan fitur ini", Toast.LENGTH_SHORT).show();
            }else{
                addQueue();
            }
        }
    }

    private void notFull(){
        seatAvailable.setVisibility(View.VISIBLE);
        shopStat.setText("SHOP STATUS");
        numberQueueNow.setText("-");
        numberYours.setVisibility(View.INVISIBLE);
        bookNow.setVisibility(View.INVISIBLE);
        ISFULL = true;
    }

    private void Full(){
        seatAvailable.setVisibility(View.INVISIBLE);
        shopStat.setText("YOURS WILL BE");
        numberQueueNow.setText("-");
        numberYours.setVisibility(View.VISIBLE);
        bookNow.setVisibility(View.VISIBLE);
        ISFULL = false;
    }

    private void addQueue(){
        APIAddQueueRequest request = new APIAddQueueRequest();
        request.setShopid(((BookActivity) getActivity()).getResponseInfo().getUser_id());
        request.setUserid(userDataDetails.get(SessionManager.KEY_USERID));
        TaskAddQueue book = new TaskAddQueue(getActivity()) {

            @Override
            public void onResult(APIBaseResponse response, String statusMessage, boolean isSuccess) {

                if(isSuccess) {
                    resultIntent = new Intent();
                    resultIntent.putExtra("resultkey", "result");
                    getActivity().setResult(Activity.RESULT_OK, resultIntent);
                    getActivity().finish();
                }
                else
                {
                    Toast.makeText(getActivity(), statusMessage, Toast.LENGTH_SHORT).show();
                }
            }


        };
        book.execute(request);
    }

    private void getTotalQueue(){
        APIMaxQueueRequest request = new APIMaxQueueRequest();
        request.setShopid(((BookActivity) getActivity()).getResponseInfo().getUser_id());

        TaskTotalQueue Total = new TaskTotalQueue(getActivity()) {

            @Override
            public void onResult(APIMaxQueueResponse response, String statusMessage, boolean isSuccess) {

                if(isSuccess) {
                    numberQueueNow.setText(response.getCurrentnumber());
                    if(!ISFULL)
                        if(response.getCurrentnumber()!=null)
                            numberYours.setText(String.valueOf(Integer.parseInt(response.getCurrentnumber())+1));

                }
                else
                {
                    Toast.makeText(getActivity(), statusMessage, Toast.LENGTH_SHORT).show();
                }
            }
        };
        Total.execute(request);
    }
}
