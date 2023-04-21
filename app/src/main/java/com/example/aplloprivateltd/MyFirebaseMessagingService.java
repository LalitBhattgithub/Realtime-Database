package com.example.aplloprivateltd;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String CHANAL_ID="my channel";
    private static final int NOTICATION_ID=100;
    private static final int REUEST_CODE=101;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        System.out.println("From: " + remoteMessage.getFrom());


        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            // System.out.println("Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getFrom(), remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

    }



    private void sendNotification(String title, String body) {

        //getting notification manager
        NotificationManager nm=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //making notification variable
        Notification notification;

        // getting intent classs
        Intent inotify= new Intent(getApplicationContext(),MainActivity.class);
        inotify.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingInten= PendingIntent.getActivity(this,REUEST_CODE,inotify,PendingIntent.FLAG_UPDATE_CURRENT);






        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification=new Notification.Builder(this)
                    .setSmallIcon(null)
                    .setContentText(title)
                    .setSubText(body)
                    .setChannelId(CHANAL_ID)
                    .setContentIntent(pendingInten)
                    .build();
            nm.createNotificationChannel(new NotificationChannel(CHANAL_ID,"my channel",NotificationManager.IMPORTANCE_HIGH));
        }
        else
        {
            notification=new Notification.Builder(this)
                    //  .setLargeIcon(large icon)
                    .setContentText(title)
                    .setSubText(body)
                    .setContentIntent(pendingInten)

                    .build();
        }
        if(nm!=null)
        {
            nm.notify(NOTICATION_ID,notification);

        }

    }
}
