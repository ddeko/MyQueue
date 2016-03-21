package com.myqueue.myqueue.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myqueue.myqueue.Models.NewsFeedListItem;
import com.myqueue.myqueue.R;

import java.util.List;

/**
 * Created by 高橋六羽 on 2016/03/22.
 */
public class NewsFeedListAdapter extends ArrayAdapter<NewsFeedListItem> {

    Context context;
    int resLayout;
    List<NewsFeedListItem> listNewsFeed;

    TextView txtShopName;
    TextView txtDescription;
    ImageView imgNewsFeed;
    ImageView imgShopLogoNews;

    public NewsFeedListAdapter(Context context, int resLayout, List<NewsFeedListItem> listNewsFeed){
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

        NewsFeedListItem navLisExplore = listNewsFeed.get(position);

        txtShopName.setText(navLisExplore.getShopName());
        txtDescription.setText(navLisExplore.getDescription());
        imgNewsFeed.setImageResource(navLisExplore.getNewsPic());
        imgShopLogoNews.setImageResource(navLisExplore.getShopPic());

        return v;
    }
}
