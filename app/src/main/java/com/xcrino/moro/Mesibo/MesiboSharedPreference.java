package com.xcrino.moro.Mesibo;

import android.content.Context;
import android.content.SharedPreferences;

public class MesiboSharedPreference {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String APP_SHARED_PREFS;

    private String appToken = "04uf0y0ixr0poxuuhjxcwh6mwlvz4eldf5yzcr2ps6cy64y2wab32eavx3arujq6";
    private String appId = "com.xcrino.chatterapp";
    private String userAccessToken;

    public MesiboSharedPreference(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        APP_SHARED_PREFS = "MoGo.com";

    }

    public void setAppToken(String appToken) {
        editor.putString("appToken", appToken);
        editor.commit();
    }

    public String getAppToken() {
        return sharedPreferences.getString("appToken", appToken);
    }

    public void setAppId(String appId) {
        editor.putString("appId", appId);
        editor.commit();
    }

    public String getAppId() {
        return sharedPreferences.getString("appId", appId);
    }

    public void setUserAccessToken(String userAccessToken) {
        editor.putString("userAccessToken", userAccessToken);
        editor.commit();
    }

    public String getUserAccessToken() {
        return sharedPreferences.getString("userAccessToken", "");
    }

}
