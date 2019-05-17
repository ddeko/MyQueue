package com.myqueue.myqueue.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.myqueue.myqueue.Models.Feed;
import com.myqueue.myqueue.Preferences.SessionManager;
import com.myqueue.myqueue.R;
import com.myqueue.myqueue.Views.RoundedImage;

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
    RoundedImage ProfPics;

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

        Bitmap bm = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.no_user);
        ProfPics = new RoundedImage(bm);
        imgNewsFeed.setImageDrawable(ProfPics);

        Feed navLisFeed = listNewsFeed.get(position);

        txtShopName.setText(navLisFeed.getShop().get(0).getUser().get(0).getName());
        txtDescription.setText(navLisFeed.getDescription());
        Glide.with(getContext()).load(navLisFeed.getFeedpicture()).into(imgShopLogoNews);

        Glide.with(getContext()).load(navLisFeed.getShop().get(0).getUser().get(0).getProfilephoto()).asBitmap().placeholder(R.drawable.no_user)
                .into(new SimpleTarget<Bitmap>(40, 40) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        ProfPics = new RoundedImage(resource);
                        imgNewsFeed.setImageDrawable(ProfPics);
                    }
                });
        return v;
    }
}
