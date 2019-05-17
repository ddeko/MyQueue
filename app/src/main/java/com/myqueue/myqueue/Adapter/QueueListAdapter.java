package com.myqueue.myqueue.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myqueue.myqueue.Activities.BookActivity;
import com.myqueue.myqueue.Models.QueueListItem;
import com.myqueue.myqueue.Models.QueueUser;
import com.myqueue.myqueue.R;

import java.util.List;


/**
 * Created by 遥か連 on 3/16/2016.
 */
public class QueueListAdapter extends ArrayAdapter<QueueUser> {

    Context context;
    int resLayout;
    List<QueueUser> listQueue;

    TextView txtQueueNumber;
    ImageView imgShopLogo;

    public QueueListAdapter(Context context, int resLayout, List<QueueUser> listQueue){
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

        QueueUser navLisQueue = listQueue.get(position);

        txtQueueNumber.setText(navLisQueue.getNo());
        Glide.with(getContext()).load(navLisQueue.getShop().get(0).getUser().get(0).getProfilephoto()).placeholder(R.drawable.no_user).into(imgShopLogo);


        return v;
    }
}
