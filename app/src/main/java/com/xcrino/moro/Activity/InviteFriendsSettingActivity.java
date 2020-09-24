package com.xcrino.moro.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.BuildConfig;
import com.google.gson.Gson;
import com.xcrino.moro.PojoModels.UserProfile;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.AppPreferencesShared;

import java.util.Locale;

public class InviteFriendsSettingActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private ImageView back_arrow;
    private TextView toolbar_title;
    private AppPreferencesShared appPreferencesShared;
    private LinearLayout line_Share;
    private UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appPreferencesShared = new AppPreferencesShared(this);
        Locale myLocale = new Locale(appPreferencesShared.getLocale());
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        if (appPreferencesShared.getmDayNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        setContentView(R.layout.activity_invite_friends_setting);

        mContext = this;
        getUIInit();

        toolbar_title.setText("Invite Friends");
        back_arrow.setOnClickListener(this);
        line_Share.setOnClickListener(this);

        Gson gsonUserProfile = new Gson();
        String json = appPreferencesShared.getUserDetails();
        userProfile = gsonUserProfile.fromJson(json, UserProfile.class);


    }

    private void getUIInit() {
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        line_Share = (LinearLayout) findViewById(R.id.line_Share);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;

            case R.id.line_Share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                if (userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("india") ||
                        userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("India")) {
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);

                } else {
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                }

                sendIntent.setType("text/plain");
//                sendIntent.setPackage("com.facebook.orca");
                try {
                    startActivity(sendIntent);
                } catch (android.content.ActivityNotFoundException ex) {
//                    Toast.makeText(mContext, "Please install facebook messenger.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
