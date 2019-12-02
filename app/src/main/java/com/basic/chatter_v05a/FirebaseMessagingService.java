package com.basic.chatter_v05a;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Build;

import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;




public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    public static final String CHANNEL_1_ID = "channel1";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);





        String click_action = remoteMessage.getData().get("click_action");
        final String toUser = remoteMessage.getData().get("user_id");
        String fromUser = remoteMessage.getData().get("from_user_id");

        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");








        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel(CHANNEL_1_ID,
                    "channel1", NotificationManager.IMPORTANCE_HIGH));
        }

        Intent resultIntent = new Intent(click_action);
        resultIntent.putExtra("user_id",fromUser);

        Log.d("From User ",fromUser);
        Log.d("To User ",toUser);
        Log.d("Title",title);


        PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(),0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);




        NotificationCompat.Builder notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_account)
                .setContentTitle(title)
                .setContentText(body)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        notification.setContentIntent(resultPendingIntent);

        int mNotificationId =  (int) System.currentTimeMillis();


            notificationManager.notify(mNotificationId,notification.build());


    }

}
