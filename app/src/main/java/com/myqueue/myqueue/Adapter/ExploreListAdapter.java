package com.myqueue.myqueue.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myqueue.myqueue.Models.APIExploreResponse;
import com.myqueue.myqueue.Models.ShopWithUser;
import com.myqueue.myqueue.R;

import java.util.List;

/**
 * Created by leowirasanto on 3/18/2016.
 */
public class ExploreListAdapter extends ArrayAdapter<ShopWithUser> {

    Context context;
    int resLayout;
    List<ShopWithUser> listExplore;

    TextView txtExploreName;
    TextView txtExploreAddress;
    ImageView imgShopLogoExplore;

    public ExploreListAdapter(Context context, int resLayout, List<ShopWithUser> listExplore){
        super(context,resLayout,listExplore);
        this.context=context;
        this.resLayout=resLayout;
        this.listExplore=listExplore;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context,resLayout,null);

        txtExploreName = (TextView)v.findViewById(R.id.tvNama);
        txtExploreAddress = (TextView)v.findViewById(R.id.tvLokasi);
        imgShopLogoExplore = (ImageView)v.findViewById(R.id.fotoLogo);

        ShopWithUser navLisExplore = listExplore.get(position);

        txtExploreName.setText(navLisExplore.getUser().get(0).getName());
        txtExploreAddress.setText(navLisExplore.getAddress() +" "+ navLisExplore.getNumber());
        Glide.with(getContext()).load(navLisExplore.getUser().get(0).getProfilephoto())
                .placeholder(R.drawable.no_user)
                .into(imgShopLogoExplore);

        return v;
    }
}
