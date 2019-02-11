package com.drife.digitaf.Firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Module.Notification.NotificationActivity;
import com.drife.digitaf.Module.SplashScreen.Activity.SplashScreenActivity;
import com.drife.digitaf.R;
import com.drife.digitaf.UIModel.UserSession;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;
    String title, message, submission_id, lead_id;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        if (s != null || s.equals("")) {
            storeRegIdInPref(s);
        }
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
//            handleNotification(remoteMessage.getNotification().getBody());
        }

        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payloadsss: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData());
                Log.e(TAG, "Firebase Response: " + json.toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exceptionsssss: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
        }else{

        }
    }

    private void handleDataMessage(JSONObject json) {

        Log.e(TAG, "push json: " + json.toString());

        try {

            title = json.getString("title_message");
            submission_id = json.getString("submission_id");
            message = json.getString("content_message");
            lead_id = json.getString("lead_id");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "submission_id: " + submission_id);
            Log.e(TAG, "Lead_id: " + lead_id);
            displayCustomNotificationForOrders(title,message, lead_id);

        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }


    private void displayCustomNotificationForOrders(String titles, String description, String lead_id) {
        NotificationManager notifManager = null;
        if (notifManager == null) {
            notifManager = (NotificationManager) getSystemService
                    (Context.NOTIFICATION_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = null;
            NotificationCompat.Builder builder;
//            UserSession userSession = SharedPreferenceUtils.getUserSession(getApplicationContext());
//            if(userSession != null){
//                    intent = new Intent(this, NotificationActivity.class);
//            }else {
                intent = new Intent(this, SplashScreenActivity.class);
                intent.putExtra("notif", title);
                intent.putExtra("lead_id", lead_id);
//            }

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent;

            String channelId = getApplicationContext().getString(R.string.default_notification_channel_id);
            NotificationChannel channel = new NotificationChannel(channelId,   title, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);
            notifManager.createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(this, channelId);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_logo);
            pendingIntent = PendingIntent.getActivity(this, 1251, intent, PendingIntent.FLAG_ONE_SHOT);
            builder.setContentTitle(titles)
                    .setSmallIcon(R.drawable.ic_logo) // required
                    .setContentText(description)  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
//                    .setWhen(getTimeMilliSec(timestamp))
//                    .setLargeIcon(bitmap)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setSound(RingtoneManager.getDefaultUri
                            (RingtoneManager.TYPE_NOTIFICATION));
            Notification notification = builder.build();
            notifManager.notify(0, notification);
        } else {
            Intent intent = null;
//            UserSession userSession = SharedPreferenceUtils.getUserSession(getApplicationContext());
//            if(userSession != null){
//                intent = new Intent(this, NotificationActivity.class);
//            }else {
                intent = new Intent(this, SplashScreenActivity.class);
                intent.putExtra("notif", title);
                intent.putExtra("lead_id", lead_id);
//            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = null;

            pendingIntent = PendingIntent.getActivity(this, 1251, intent, PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_logo);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setContentTitle(title)
                    .setContentText(description)
                    .setAutoCancel(true)
                    .setColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary))
                    .setSound(defaultSoundUri)
//                    .setWhen(getTimeMilliSec(timestamp))
//                    .setLargeIcon(bitmap)
                    .setSmallIcon(R.drawable.ic_logo)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1251, notificationBuilder.build());
        }
    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.commit();
    }
}