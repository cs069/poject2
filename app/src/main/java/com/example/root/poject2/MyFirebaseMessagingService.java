package com.example.root.poject2;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

import static com.example.root.poject2.R.raw.correct;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    //private static final String TAG = "dfdsf";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //if the msg contain data payload
        //it si a map cusotm keyvalues
        //we cam read it easilu
       System.out.println(remoteMessage);

        //handle the data message her
        // Create Notification
      Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1410,
                intent, PendingIntent.FLAG_ONE_SHOT);
        long[] pattern = {200, 300, 400, 500,100,500};
        String  longtext="quiZOL-->New Message";

        NotificationCompat.Builder notificationBuilder = new
                NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.logo)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.lol))
                .setTicker("Alert new message")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(longtext))
                .setContentTitle(remoteMessage.getNotification().getBody())
                .setContentText(remoteMessage.getNotification().getTitle())
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(this,R.color.pinkAccent))
                .setVibrate(pattern)
                .setContentIntent(pendingIntent);


        NotificationManager notificationManager =
                (NotificationManager)
                        getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1410, notificationBuilder.build());


    }
}