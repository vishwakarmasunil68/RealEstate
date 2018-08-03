package com.appentus.realestate.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.appentus.realestate.R;
import com.appentus.realestate.activity.HomeActivity;
import com.appentus.realestate.activity.SplashActivity;
import com.appentus.realestate.utils.StringUtils;
import com.appentus.realestate.utils.TagUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

/**
 * Created by sunil on 18-08-2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        try {
            String notification = remoteMessage.getData().toString();
//            String success = remoteMessage.getData().get("success");
            String result = remoteMessage.getData().get("result");
//            String title = remoteMessage.getData().get("title");
//            String description = remoteMessage.getData().get("description");
            String type = remoteMessage.getData().get("type");

            Log.d(TagUtils.getTag(), "notification:-" + notification);
//            Log.d(TagUtils.getTag(), "success:-" + success);
            Log.d(TagUtils.getTag(), "result:-" + result);
//            Log.d(TagUtils.getTag(), "title:-" + title);
//            Log.d(TagUtils.getTag(), "description:-" + description);
            Log.d(TagUtils.getTag(), "type:-" + type);

            checkType(type, result);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            try {
                Log.d(TAG, "From: " + remoteMessage.getFrom());
                Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
            } catch (Exception e1) {
                Log.d(TAG, e1.toString());
            }
        }
//        }
    }


    public void checkType(String type, String result) {
        if(type.equalsIgnoreCase("chat")) {
            try {
                sendChatNotification("chat", new JSONObject(result).optJSONObject("data").optString("msg"));
                updateChatActivity(getApplicationContext(),result);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public void sendLiveNot(String title, String message) {
        try {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendChatNotification(String title,String message){
        try {
            Intent intent = new Intent(this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            String channelId = "channel-01";
            String channelName = "Channel Name";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        channelId, channelName, importance);
                notificationManager.createNotificationChannel(mChannel);
            }

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(message);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
            stackBuilder.addNextIntent(intent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
            mBuilder.setContentIntent(resultPendingIntent);

            notificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateChatActivity(Context context, String message) {
        Intent intent = new Intent(StringUtils.CHAT_CLASS);

        //put whatever data you want to send, if any
        intent.putExtra("message", message);

        //send broadcast
        context.sendBroadcast(intent);
    }

}