package com.myqueue.myqueue.Activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myqueue.myqueue.R;

public class BookScreenActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar myActionBar;
    ImageView shopProfPics;
    RelativeLayout shopCovPics;
    TextView ShopName;
    TextView ShopAddress;
    TextView numberQueueNow;
    TextView numberYours;
    Button bookNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_screen);

        myActionBar = (Toolbar) findViewById(R.id.toolbarBook);
        setSupportActionBar(myActionBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(getSupportActionBar()!= null){
            getSupportActionBar().setElevation(0);
        }

        shopProfPics=(ImageView)findViewById(R.id.ShopProfilePics);
        shopCovPics = (RelativeLayout)findViewById(R.id.layoutProfile);
        ShopName = (TextView)findViewById(R.id.ShopName);
        ShopName.setPaintFlags(ShopName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); // underline
        ShopAddress = (TextView)findViewById(R.id.ShopAddress);
        numberQueueNow = (TextView)findViewById(R.id.bookNumberNow);
        numberYours = (TextView)findViewById(R.id.getQueueNumber);
        numberYours.setPaintFlags(numberYours.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); // underline
        bookNow = (Button)findViewById(R.id.btnBook);

        bookNow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == bookNow){
            Toast.makeText(getApplicationContext(),"Booked",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,NewsFeedFormActivity.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
