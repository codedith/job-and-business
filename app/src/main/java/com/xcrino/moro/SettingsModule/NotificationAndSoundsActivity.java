package com.xcrino.moro.SettingsModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.AppPreferencesShared;

import java.util.Locale;

public class NotificationAndSoundsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mToolbar_title;
    private ImageView back_arrow;
    private LinearLayout vibrate, ringTone, enabledBadgeCounter, includeBadge, countUnReadMsg, inAppSounds, inAppVibrate,
            inAppPreview, InAppSounds, importance, eventsContacts, pinnedMsg, keepAlive, backgroundConnection, repeatNotice,
            resetAllNotice;
    private Switch enabledSwitch, includeMutedChatsSwitch, countUnreadMsgSwitch, inAppSoundsSwitch, inAppVibrateSwitch, inAppPreviewSwitch,
            viewInAppSoundsSwitch, importanceSwitch, eventsContactsSwitch, pinnedMsgSwitch, keepAliveSwitch, backgroundConnectionSwitch;
    private AppPreferencesShared appPreferencesShared;
    private Context mContext;

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
        setContentView(R.layout.activity_notification_and_sounds);
        mContext = this;

        getLayoutUIInit();

        mToolbar_title.setText("Notifications and Sounds");
        back_arrow.setOnClickListener(this);
    }

    private void getLayoutUIInit() {
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        mToolbar_title = findViewById(R.id.toolbar_title);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;

        }
    }
}
