package com.myqueue.myqueue;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by leowirasanto on 3/14/2016.
 */
public class profileActivity extends AppCompatActivity {
    private ImageView imgcover,profilePicture;
    private TextView tv;
    private EditText edtxt;
    private RoundedImage cropCircle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        imgcover = (ImageView)findViewById(R.id.coverPicture);
        profilePicture = (ImageView)findViewById(R.id.profilepicture);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.contohpp);
        cropCircle = new RoundedImage(bm);
        profilePicture.setImageDrawable(cropCircle);

    }

}
