package com.myqueue.myqueue.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myqueue.myqueue.Models.QueueListItem;
import com.myqueue.myqueue.R;

import java.util.List;


/**
 * Created by 遥か連 on 3/16/2016.
 */
public class QueueListAdapter extends ArrayAdapter<QueueListItem> {

    Context context;
    int resLayout;
    List<QueueListItem> listQueue;

    TextView txtQueueNumber;
    ImageView imgShopLogo;

    public QueueListAdapter(Context context, int resLayout, List<QueueListItem> listQueue){
        super(context,resLayout,listQueue);
        this.context=context;
        this.resLayout=resLayout;
        this.listQueue=listQueue;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context,resLayout,null);

        txtQueueNumber = (TextView)v.findViewById(R.id.txtQueueNumber);
        imgShopLogo = (ImageView)v.findViewById(R.id.imgLogoShop);

        QueueListItem navLisQueue = listQueue.get(position);

        txtQueueNumber.setText(navLisQueue.getTxtNumberQueue());
        imgShopLogo.setImageResource(navLisQueue.getImgLogoShop());

        return v;
    }
}
