package com.xcrino.moro.BusinessModules;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

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

import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.AppPreferencesShared;

import java.util.Locale;

public class CampaignModeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView toolbar_title;
    private ImageView back_arrow;
    private Context mContext;
    private AppPreferencesShared appPreferencesShared;
    private LinearLayout putimagehere, puttexthere, Mix, video, share_via_socialmedia, select_previous_campaign;

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
        setContentView(R.layout.activity_compaign_mode);

        mContext = this;

        getLayoutUiId();
        toolbar_title.setText("Select Campaign Mode");
        back_arrow.setOnClickListener(this);
        putimagehere.setOnClickListener(this);
        puttexthere.setOnClickListener(this);
        Mix.setOnClickListener(this);
        video.setOnClickListener(this);
        share_via_socialmedia.setOnClickListener(this);
        select_previous_campaign.setOnClickListener(this);
    }

    private void getLayoutUiId() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        putimagehere = (LinearLayout) findViewById(R.id.putimagehere);
        puttexthere = (LinearLayout) findViewById(R.id.puttexthere);
        Mix = (LinearLayout) findViewById(R.id.Mix);
        video = (LinearLayout) findViewById(R.id.video);
        share_via_socialmedia = (LinearLayout) findViewById(R.id.share_via_socialmedia);
        select_previous_campaign = findViewById(R.id.select_previous_campaign);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;
            case R.id.putimagehere:
                Intent intent = new Intent(mContext, CampaignImageUploadActivity.class);
                startActivity(intent);
                break;
            case R.id.puttexthere:
                Intent intent1 = new Intent(mContext, CampaignTextActivity.class);
                startActivity(intent1);
                break;
            case R.id.Mix:
                Intent intent2 = new Intent(mContext, CampaignMixActivity.class);
                startActivity(intent2);
                break;
            case R.id.video:
                Intent intent3 = new Intent(mContext, CampaignVideoActivity.class);
                startActivity(intent3);
                break;
            case R.id.share_via_socialmedia:
                Intent intent4 = new Intent(mContext, SharedActivity.class);
                startActivity(intent4);
                break;
            case R.id.select_previous_campaign:
                Intent intent5 = new Intent(mContext, PreviousSelectionActivity.class);
                startActivity(intent5);
                break;
        }
    }
}