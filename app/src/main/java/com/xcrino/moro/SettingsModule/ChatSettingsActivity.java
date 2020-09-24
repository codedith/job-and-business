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

public class ChatSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mToolbar_title, distanceUnitsText, stickerAndMasksTextOnly;
    private ImageView back_arrow;
    private LinearLayout autoNightMode, inAppBrowser, directShare, enavleAnimations, largeEmoji, raiseToSpeak, sendByEnter,
            saveToGallery, distanceUnits;
    private Switch autoNightModeSwitch, inAppBrowserSwitch, directShareSwitch, enavleAnimationsSwitch, largeEmojiSwitch,
            raiseToSpeakSwitch, sendByEnterSwitch, saveToGallerySwitch;
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
        setContentView(R.layout.activity_chat_settings);

        mContext = this;
        getLayoutUIInit();

        mToolbar_title.setText("Chat Settings");

        back_arrow.setOnClickListener(this);
        autoNightMode.setOnClickListener(this);
        inAppBrowser.setOnClickListener(this);
        directShare.setOnClickListener(this);
        enavleAnimations.setOnClickListener(this);
        largeEmoji.setOnClickListener(this);
        raiseToSpeak.setOnClickListener(this);
        sendByEnter.setOnClickListener(this);
        saveToGallery.setOnClickListener(this);
        distanceUnits.setOnClickListener(this);
        distanceUnitsText.setOnClickListener(this);
        stickerAndMasksTextOnly.setOnClickListener(this);

    }

    private void getLayoutUIInit() {
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        mToolbar_title = (TextView) findViewById(R.id.toolbar_title);
        distanceUnitsText = (TextView) findViewById(R.id.distanceUnitsText);
        stickerAndMasksTextOnly = (TextView) findViewById(R.id.stickerAndMasksTextOnly);
        autoNightMode = (LinearLayout) findViewById(R.id.autoNightMode);
        inAppBrowser = (LinearLayout) findViewById(R.id.inAppBrowser);
        directShare = (LinearLayout) findViewById(R.id.directShare);
        enavleAnimations = (LinearLayout) findViewById(R.id.enavleAnimations);
        largeEmoji = (LinearLayout) findViewById(R.id.largeEmoji);
        raiseToSpeak = (LinearLayout) findViewById(R.id.raiseToSpeak);
        sendByEnter = (LinearLayout) findViewById(R.id.sendByEnter);
        saveToGallery = (LinearLayout) findViewById(R.id.saveToGallery);
        distanceUnits = (LinearLayout) findViewById(R.id.distanceUnits);
        autoNightModeSwitch = (Switch) findViewById(R.id.autoNightModeSwitch);
        inAppBrowserSwitch = (Switch) findViewById(R.id.inAppBrowserSwitch);
        directShareSwitch = (Switch) findViewById(R.id.directShareSwitch);
        enavleAnimationsSwitch = (Switch) findViewById(R.id.enavleAnimationsSwitch);
        largeEmojiSwitch = (Switch) findViewById(R.id.largeEmojiSwitch);
        raiseToSpeakSwitch = (Switch) findViewById(R.id.raiseToSpeakSwitch);
        sendByEnterSwitch = (Switch) findViewById(R.id.sendByEnterSwitch);
        saveToGallerySwitch = (Switch) findViewById(R.id.saveToGallerySwitch);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;

            case R.id.autoNightMode:
                break;

            case R.id.inAppBrowser:
                break;

            case R.id.directShare:
                break;

            case R.id.enavleAnimations:
                break;

            case R.id.largeEmoji:
                break;

            case R.id.raiseToSpeak:
                break;

            case R.id.sendByEnter:
                break;

            case R.id.saveToGallery:
                break;

            case R.id.distanceUnits:
                break;

            case R.id.distanceUnitsText:
                break;

            case R.id.stickerAndMasksTextOnly:
                break;

        }
    }
}