package com.xcrino.moro.Mesibo.fcm;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.xcrino.moro.Utilities.MainApplication;

public class MesiboGcmListenerService extends FirebaseMessagingService {

    private static final String TAG = "FcmListenerService";


    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

            Bundle data = new Bundle();
            data.putString("body","newPushNotification");


            MesiboRegistrationIntentService.sendMessageToListener( false);

            Intent intent = new Intent("com.mesibo.someintent");
            intent.putExtras(data);
            MesiboJobIntentService.enqueueWork(MainApplication.getAppContext(), intent);

    }


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("newToken", s);
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fb", s).apply();

    }

    public static String getToken(Context context) {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fb", "empty");
    }
}
