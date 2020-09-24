package com.xcrino.moro.Utilities;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.mesibo.api.Mesibo;
import com.mesibo.calls.MesiboCall;
import com.xcrino.moro.Mesibo.MesiboAppConfig;
import com.xcrino.moro.Mesibo.MesiboSampleAPI;
import com.xcrino.moro.SliderPageActivity.SliderImageScreenActivity;

public class MainApplication extends Application implements Mesibo.RestartListener {

    public static final String TAG = "MesiboSampleApplication";
    private static Context mContext = null;
    private static MesiboCall mCall = null;
    private static MesiboAppConfig mConfig = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Mesibo.setRestartListener(this);
        mConfig = new MesiboAppConfig(this);
        MesiboSampleAPI.init(getApplicationContext());

        mCall = MesiboCall.getInstance();
        mCall.init(this);
    }

    public static String getRestartIntent() {
        return "com.mesibo.sampleapp.restart";
    }

    public static Context getAppContext() {
        return mContext;
    }

    @Override
    public void Mesibo_onRestart() {
        Log.d(TAG, "OnRestart");
        SliderImageScreenActivity.newInstance(this, true);
    }
}
