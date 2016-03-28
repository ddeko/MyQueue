package com.myqueue.myqueue.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myqueue.myqueue.Models.Feed;
import com.myqueue.myqueue.R;

import java.util.List;

/**
 * Created by 高橋六羽 on 2016/03/22.
 */
public class NewsFeedListAdapter extends ArrayAdapter<Feed> {

    Context context;
    int resLayout;
    List<Feed> listNewsFeed;

    TextView txtShopName;
    TextView txtDescription;
    ImageView imgNewsFeed;
    ImageView imgShopLogoNews;

    public NewsFeedListAdapter(Context context, int resLayout, List<Feed> listNewsFeed){
        super(context,resLayout,listNewsFeed);
        this.context=context;
        this.resLayout=resLayout;
        this.listNewsFeed=listNewsFeed;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context,resLayout,null);

        txtShopName = (TextView)v.findViewById(R.id.tvshopname);
        txtDescription = (TextView)v.findViewById(R.id.tvdescription);
        imgNewsFeed = (ImageView)v.findViewById(R.id.shoppic);
        imgShopLogoNews = (ImageView)v.findViewById(R.id.newsfeedpic);

        Feed navLisFeed = listNewsFeed.get(position);

        txtShopName.setText(navLisFeed.getShop().get(0).getUser().get(0).getName());
        txtDescription.setText(navLisFeed.getDescription());
        Glide.with(getContext()).load(navLisFeed.getFeedpicture()).into(imgShopLogoNews);
        Glide.with(getContext()).load(navLisFeed.getShop().get(0).getUser().get(0).getProfilephoto()).into(imgNewsFeed);

        return v;
    }
}
