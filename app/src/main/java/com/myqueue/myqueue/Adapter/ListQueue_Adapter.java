package com.myqueue.myqueue.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myqueue.myqueue.Model.ListQueue;
import com.myqueue.myqueue.R;

import java.util.List;


/**
 * Created by 遥か連 on 3/16/2016.
 */
public class ListQueue_Adapter extends ArrayAdapter<ListQueue> {

    Context context;
    int resLayout;
    List<ListQueue> listQueue;

    TextView txtQueueNumber;
    ImageView imgShopLogo;

    public ListQueue_Adapter(Context context,int resLayout,List<ListQueue> listQueue){
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

        ListQueue navLisQueue = listQueue.get(position);

        txtQueueNumber.setText(navLisQueue.getTxtNumberQueue());
        imgShopLogo.setImageBitmap(navLisQueue.getImgLogoShop());

        return v;
    }
}
