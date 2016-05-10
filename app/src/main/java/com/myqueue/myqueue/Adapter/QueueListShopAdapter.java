package com.myqueue.myqueue.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myqueue.myqueue.Models.QueueShop;
import com.myqueue.myqueue.Models.QueueUser;
import com.myqueue.myqueue.R;

import java.util.List;

/**
 * Created by leowirasanto on 5/9/2016.
 */
public class QueueListShopAdapter extends ArrayAdapter<QueueShop> {

    Context context;
    int resLayout;
    List<QueueShop> listQueue;

    TextView txtQueueNumber;
    ImageView imgShopLogo;

    public QueueListShopAdapter(Context context, int resLayout, List<QueueShop> listQueue){
        super(context,resLayout,listQueue);
        this.context=context;
        this.resLayout=resLayout;
        this.listQueue=listQueue;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, resLayout, null);

        txtQueueNumber = (TextView)v.findViewById(R.id.txtQueueNumber);
        imgShopLogo = (ImageView)v.findViewById(R.id.imgLogoShop);

        QueueShop navLisQueue = listQueue.get(position);

        txtQueueNumber.setText(navLisQueue.getNo());
        Glide.with(getContext()).load(navLisQueue.getUser().get(0).getProfilephoto()).placeholder(R.drawable.no_user).into(imgShopLogo);


        return v;
    }
}