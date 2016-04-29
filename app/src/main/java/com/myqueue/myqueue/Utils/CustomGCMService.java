package com.myqueue.myqueue.Utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.myqueue.myqueue.Activities.WaitingListActivity;
import com.myqueue.myqueue.Preferences.SessionManager;
import com.myqueue.myqueue.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import co.mobiwise.fastgcm.GCMListenerService;

/**
 * Created by 高橋六羽 on 4/13/2016.
 */
public class CustomGCMService extends GCMListenerService {

    private SessionManager sessions;
    private HashMap<String,String> userdata;
    private HashMap<String,String> shopdata;

    @Override
    public void onMessageReceived(String from, Bundle bundle) {
        super.onMessageReceived(from, bundle);

        //Here is called even app is not running.
        //create your notification here.

        sessions = new SessionManager(this);
        userdata = sessions.getUserDetails();

        if(userdata.get(SessionManager.KEY_USERID)!=null) {
            if(userdata.get(SessionManager.KEY_USERID).equals(bundle.getString("useridtarget"))) {
                if (userdata.get(SessionManager.KEY_ISOWNER).equalsIgnoreCase("0")) {
                    Intent notificationIntent = new Intent(this, WaitingListActivity.class);
                    // set intent so it does not start a new activity
                    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    PendingIntent intent =
                            PendingIntent.getActivity(this, 0, notificationIntent, 0);

                    final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                            .setVibrate(new long[]{1000, 1000})
                            .setLights(Color.RED, 3000, 3000)
                            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                            .setAutoCancel(true)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setColor(Color.argb(1, 212, 48, 55))
                            .setLargeIcon(getBitmapFromURL(bundle.getString("largeIcon")))
                            .setContentIntent(intent)
                            .setContentTitle("Queue Notification")
                            .setContentText(bundle.getString("subtitle"));

                    final int notificationId = 1;
                    NotificationManager nm = (NotificationManager) getApplicationContext()
                            .getSystemService(Context.NOTIFICATION_SERVICE);
                    nm.notify(notificationId, mBuilder.build());

                } else {
                    Intent notificationIntent = new Intent(this, WaitingListActivity.class);
                    // set intent so it does not start a new activity
                    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    PendingIntent intent =
                            PendingIntent.getActivity(this, 0, notificationIntent, 0);

                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                            .setVibrate(new long[]{1000, 1000})
                            .setLights(Color.RED, 3000, 3000)
                            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                            .setAutoCancel(true)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setColor(Color.argb(1, 212, 48, 55))
                            .setLargeIcon(getBitmapFromURL(bundle.getString("largeIcon")))
                            .setContentIntent(intent)
                            .setContentTitle("Queue Notification")
                            .setContentText(bundle.getString("subtitle"));

                    final int notificationId = 1;
                    NotificationManager nm = (NotificationManager) getApplicationContext()
                            .getSystemService(Context.NOTIFICATION_SERVICE);
                    nm.notify(notificationId, mBuilder.build());
                }
            }
        }
    }

    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}