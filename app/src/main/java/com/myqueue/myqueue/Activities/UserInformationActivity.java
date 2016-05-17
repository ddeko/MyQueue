package com.myqueue.myqueue.Activities;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import com.myqueue.myqueue.Callbacks.OnActionbarListener;
import com.myqueue.myqueue.Models.QueueShop;
import com.myqueue.myqueue.Models.QueueUser;
import com.myqueue.myqueue.Models.ShopWithUser;
import com.myqueue.myqueue.Models.User;
import com.myqueue.myqueue.Preferences.SessionManager;
import com.myqueue.myqueue.R;
import com.myqueue.myqueue.Views.RoundedImage;


/**
 * Created by DedeEko on 5/17/2016.
 */
public class UserInformationActivity extends BaseActivity{

    private LinearLayout customerContainer;
    private LinearLayout shopOwnerContainer;

    private ImageView imgcover, profilePicture;

    private EditText storeAddress;
    private EditText storePhone;
    private EditText storeEmail;
    private EditText storeCategory;
    private EditText storeName;

    private EditText userPhone;
    private EditText userEmail;
    private EditText userName;

    public boolean isOwner;

    User userData;
    ShopWithUser shopData;

    public QueueShop queueShop;
    public QueueUser queueUser;

    private RoundedImage cropCircle;
    private Button updatebtn;

    ProfileActivity activity;

    LinearLayout btnChangeProfile;
    LinearLayout btnChangeCover;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setDefaultActionbarIcon();
        setRightIcon(0);
        setActionBarTitle("User Information");

        Intent i = getIntent();
        Bundle b = i.getExtras();
        isOwner  = b.getBoolean("isowner");

        if(isOwner==false)
        {
            queueUser = (QueueUser) i.getSerializableExtra("queue");
            shopData = queueUser.getShop().get(0);
            userData = shopData.getUser().get(0);
        }
        else if(isOwner==true)
        {
            queueShop = (QueueShop) i.getSerializableExtra("queue");
            userData = queueShop.getUser().get(0);
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


    private void fetchData()
    {
        if(userData.getCoverphoto()!= null && userData.getProfilephoto().toString() != null)
        {
            Glide.with(this).load(userData.getCoverphoto().toString()).placeholder(R.drawable.coverpics).into(imgcover);
            Glide.with(this).load(userData.getProfilephoto().toString()).asBitmap()
                    .into(new SimpleTarget<Bitmap>(256, 256) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            Bitmap dstBmp;
                            if (resource.getWidth() >= resource.getHeight()) {
                                dstBmp = Bitmap.createBitmap(
                                        resource,
                                        resource.getWidth() / 2 - resource.getHeight() / 2,
                                        0,
                                        resource.getHeight(),
                                        resource.getHeight()
                                );
                            } else {
                                dstBmp = Bitmap.createBitmap(
                                        resource,
                                        0,
                                        resource.getHeight() / 2 - resource.getWidth() / 2,
                                        resource.getWidth(),
                                        resource.getWidth()
                                );
                            }

                            cropCircle = new RoundedImage(dstBmp);
                            profilePicture.setImageDrawable(cropCircle);
                        }
                    });

        }
        if(isOwner==false)
        {
            storeName.setText(userData.getName());
            storePhone.setText(userData.getPhone());
            storeEmail.setText(userData.getEmail());
            storeCategory.setText(shopData.getCategory());
            if(activity.shopData.get(SessionManager.KEY_ADDRESS)!=null)
                storeAddress.setText(shopData.getAddress() +" "+ shopData.getNumber());

        }
        else
        {
            if(queueShop.getUser_id().equalsIgnoreCase("1"))
            {
                userName.setText(queueShop.getDummyname());
                userPhone.setText(queueShop.getDummyphone());
                userEmail.setText(userData.getEmail());
            }else {
                userName.setText(userData.getName());
                userPhone.setText(userData.getPhone());
                userEmail.setText(userData.getEmail());
            }
        }
    }

    @Override
    public void initView() {

        imgcover = (ImageView) findViewById(R.id.coverPicture);
        profilePicture = (ImageView) findViewById(R.id.profilepicture);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.no_user);
        cropCircle = new RoundedImage(bm);
        profilePicture.setImageDrawable(cropCircle);
        updatebtn = (Button) findViewById(R.id.btnUpdateProfile);

        storeName = (EditText) findViewById(R.id.txtNamaProfileShop);
        storePhone = (EditText) findViewById(R.id.txtPhoneNumberShop);
        storeEmail = (EditText) findViewById(R.id.txtEmailShop);
        storeCategory = (EditText) findViewById(R.id.txtCategoryShop);
        storeAddress = (EditText) findViewById(R.id.txtStoreAdd);

        userName = (EditText) findViewById(R.id.txtNamaProfile);
        userPhone = (EditText) findViewById(R.id.txtPhoneNumber);
        userEmail = (EditText) findViewById(R.id.txtEmail);

        customerContainer = (LinearLayout) findViewById(R.id.customer_data_container);
        shopOwnerContainer = (LinearLayout) findViewById(R.id.shop_data_container);

        btnChangeProfile = (LinearLayout) findViewById(R.id.bagianTextChangePP);
        btnChangeCover = (LinearLayout) findViewById(R.id.bagianTextChangeCover);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

    }

    @Override
    public void setUICallbacks() {
        setActionbarListener(new OnActionbarListener() {
            @Override
            public void onLeftIconClick() {
                onBackPressed();
            }

            @Override
            public void onRightIconClick() {

            }
        });

    }

    @Override
    public int getLayout() {
        return R.layout.activity_user_information;
    }

    @Override
    public void updateUI() {

        if(isOwner==false)
        {
            shopOwnerContainer.setVisibility(View.VISIBLE);
            customerContainer.setVisibility(View.GONE);

        } else {
            customerContainer.setVisibility(View.VISIBLE);
            shopOwnerContainer.setVisibility(View.GONE);

        }

        fetchData();
    }

}


