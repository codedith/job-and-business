package com.xcrino.moro.Mesibo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.mesibo.api.Mesibo;
import com.xcrino.moro.R;
import com.xcrino.moro.SliderPageActivity.SliderImageScreenActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MesiboNotifyUser {
    public static final int TYPE_MESSAGE=0;
    public static final int TYPE_OTHER=0;
    public static int mCount = 0;

    public static class NotificationContent {
        public String title = null;
        public String content = null;
        public String subContent = null;
        public RemoteViews v = null;
        public NotificationCompat.Style style = null;
    }

    private List<NotificationContent> mNotificationContentList = new ArrayList<NotificationContent>();
    private Context mContxt = null;
    private String mPackageName = null;
    private TimerTask mTimerTask = null;
    private Timer mTimer = null;
    private Handler mUiHandler = new Handler();

    public static final String NOTIFYMESSAGE_CHANNEL_ID = "MESSAGE_CHANNEL";
    private static final String NOTIFYMESSAGE_CHANNEL_NAME = "Messages";

    public static final String NOTIFYCALL_CHANNEL_ID = "CALL_CHANNEL";
    private static final String NOTIFYCALL_CHANNEL_NAME = "Calls";

    public MesiboNotifyUser(Context context) {
        mContxt = context;
        mPackageName = context.getPackageName();
        createNotificationChannels();
        Mesibo.addListener(this);
    }

    private void createNotificationChannel(String id, String name, int important) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }

        NotificationChannel nchannel = new NotificationChannel(id, name, important);

        // Sets whether notifications posted to this channel should display notification lights
        nchannel.enableLights(true);
        // Sets whether notification posted to this channel should vibrate.
        nchannel.enableVibration(true);
        // Sets the notification light color for notifications posted to this channel
        nchannel.setLightColor(Color.GREEN);
        // Sets whether notifications posted to this channel appear on the lockscreen or not
        nchannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        NotificationManager notificationManager =
                (NotificationManager) mContxt.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.createNotificationChannel(nchannel);
    }

    private void createNotificationChannels() {
        createNotificationChannel(NOTIFYMESSAGE_CHANNEL_ID, NOTIFYMESSAGE_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        createNotificationChannel(NOTIFYCALL_CHANNEL_ID, NOTIFYCALL_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
    }

    public void sendNotification(String channelId, int id, PendingIntent intent, NotificationContent n) {

        // Use NotificationCompat.Builder to set up our notification.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContxt, channelId);

        if(null != n.title)
            builder.setContentTitle(n.title);
        if(null != n.content)
            builder.setContentText(n.content);

        // The subtext, which appears under the text on newer devices.
        // This will show-up in the devices with Android 4.2 and above only
        if(!TextUtils.isEmpty(n.subContent))
            builder.setSubText(n.subContent);

        //icon appears in device notification bar and right hand corner of notification
        //https://clevertap.com/blog/fixing-notification-icon-for-android-lollipop-and-above/
        builder.setSmallIcon(R.drawable.ic_launcher); //R.drawable.ic_launcher

        // Set the intent that will fire when the user taps the notification.
        builder.setContentIntent(intent);

        // Large icon appears on the left of the notification
        builder.setLargeIcon(BitmapFactory.decodeResource(mContxt.getResources(), R.drawable.ic_launcher));

        if(null != n.v) {
            builder.setCustomContentView(n.v);
        }

        if(null == n.style && null != n.content)
            n.style = new NotificationCompat.BigTextStyle().bigText(n.content);

        builder.setStyle(n.style);

        // clears on click
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setPriority(Notification.PRIORITY_MAX);

        NotificationManager notificationManager = (NotificationManager) mContxt.getSystemService(Context.NOTIFICATION_SERVICE);

        // Will display the notification in the notification bar
        notificationManager.notify(id, builder.build());
    }

    public synchronized void  clearNotification() {

        NotificationManager notificationManager = (NotificationManager) mContxt.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationContentList.clear();
        // Will display the notification in the notification bar
        notificationManager.cancel(TYPE_MESSAGE);
        mCount = 0;
        mNotificationContentList.clear();
        //notificationManager.cancel(TYPE_OTHER);
    }

    private PendingIntent getDefaultIntent() {
        // This intent is fired when notification is clicked
        Intent intent = new Intent(mContxt, SliderImageScreenActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        return PendingIntent.getActivity(mContxt, 0, intent, 0);
    }


    public void sendNotification(String channelid, int id, String title, String content) {
        NotificationContent n = new NotificationContent();
        n.title = title;
        n.content = content;

        sendNotification(channelid, id, getDefaultIntent(), n);
    }

    private synchronized void notifyMessages() {
        if(0 == mNotificationContentList.size())
            return;

        NotificationContent notify= mNotificationContentList.get(mNotificationContentList.size()-1);

        String title = "New Message from " + notify.title;
        NotificationCompat.InboxStyle inboxStyle = null;
        if(mNotificationContentList.size() > 1) {

            inboxStyle = new NotificationCompat.InboxStyle();
            Iterator iterator = mNotificationContentList.iterator();

            while(iterator.hasNext()) {
                NotificationContent n = (NotificationContent) iterator.next();
                inboxStyle.addLine(n.title + " : " + n.content);
            }

            inboxStyle.setBigContentTitle(title);
            inboxStyle.setSummaryText(mCount + " new messages");
            notify.style = inboxStyle;
            sendNotification(MesiboNotifyUser.NOTIFYMESSAGE_CHANNEL_ID, TYPE_MESSAGE, getDefaultIntent(), notify);
            return;
        }

        // Don't use notify object else we it will overwrite title
        sendNotification(MesiboNotifyUser.NOTIFYMESSAGE_CHANNEL_ID, TYPE_MESSAGE, title, notify.content);
    }

    private Runnable mNotifyRunnable = new Runnable() {
        @Override
        public void run() {
            notifyMessages();
        }
    };

    public synchronized void sendNotificationInList(String title, String message) {


        // it is also possible to limit only latest notification from a user by adding params and checking for duplicate
        NotificationContent notify = new NotificationContent();
        notify.title = title;
        notify.content = message;

        // inboxStyle can only have max 5 messages
        if(mNotificationContentList.size() >= 5)
            mNotificationContentList.remove(0);

        mNotificationContentList.add(notify);
        mCount++;

        // if more realtime messages in thread queue, just add into our list and return
        // TBD, this may have adverse effect if next realtime message is the one we reading

        //if(Mesibo.isMoreRealtimeMessages(params.ts))
        //  return;

        if(null != mTimer) {
            mTimer.cancel();
            mTimer = null;
        }

        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                mUiHandler.post(mNotifyRunnable);
            }
        };

        mTimer.schedule(mTimerTask, 1000);

        return;
    }
}
