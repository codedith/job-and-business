package com.xcrino.moro.BusinessModules;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.logging.Handler;

public class UploadImageApacheHttp {

    public static final String TAG = "Upload Image Apache";

    public void doFileUpload(final String url, final Bitmap bmp, final Handler handler){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG,"Starting Upload...");
                //final ArrayList<Nam> nameValuePairs = new ArrayList<NameValuePair>();
                //nameValuePairs.
            }
        });
    }
}
