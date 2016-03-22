package com.myqueue.myqueue.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myqueue.myqueue.Models.ExploreListItem;
import com.myqueue.myqueue.R;

import java.util.List;

/**
 * Created by leowirasanto on 3/18/2016.
 */
public class ExploreListAdapter extends ArrayAdapter<ExploreListItem> {

    Context context;
    int resLayout;
    List<ExploreListItem> listExplore;

    TextView txtExploreName;
    TextView txtExploreAddress;
    ImageView imgShopLogoExplore;

    public ExploreListAdapter(Context context, int resLayout, List<ExploreListItem> listExplore){
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

        ExploreListItem navLisExplore = listExplore.get(position);

        txtExploreName.setText(navLisExplore.getShopName());
        txtExploreAddress.setText(navLisExplore.getShopAddress());
        imgShopLogoExplore.setImageResource(navLisExplore.getPicture());

        return v;
    }
}
