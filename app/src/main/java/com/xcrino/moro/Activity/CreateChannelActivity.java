package com.xcrino.moro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.AppPreferencesShared;

import java.util.Locale;

public class CreateChannelActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout brief_desc, new_channel_layout, channel_settings_layout;
    private Button create_channel_button, next_button, settings_next_button;
    private TextView toolbar_title;
    private ImageView back_arrow;
    private AppPreferencesShared appPreferencesShared;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appPreferencesShared= new AppPreferencesShared(this);
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
        setContentView(R.layout.activity_create_channel);

        getLayoutInit();

        context = this;
        appPreferencesShared = new AppPreferencesShared(context);

        if (appPreferencesShared.getCreateChannelNewUser()) {
            brief_desc.setVisibility(View.VISIBLE);
        } else {
            brief_desc.setVisibility(View.GONE);
            new_channel_layout.setVisibility(View.VISIBLE);
        }

        create_channel_button.setOnClickListener(this);
        next_button.setOnClickListener(this);
        settings_next_button.setOnClickListener(this);
        back_arrow.setOnClickListener(this);
    }

    private void getLayoutInit() {
        toolbar_title = findViewById(R.id.toolbar_title);
        brief_desc = findViewById(R.id.brief_desc);
        create_channel_button = findViewById(R.id.create_channel_button);
        new_channel_layout = findViewById(R.id.new_channel_layout);
        next_button = findViewById(R.id.next_button);
        channel_settings_layout = findViewById(R.id.channel_settings_layout);
        settings_next_button = findViewById(R.id.settings_next_button);
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_channel_button:
                appPreferencesShared.setCreateChannelNewUser(false);
                brief_desc.setVisibility(View.GONE);
                new_channel_layout.setVisibility(View.VISIBLE);
                channel_settings_layout.setVisibility(View.GONE);
                toolbar_title.setText("New Channel");
                break;

            case R.id.next_button:
                brief_desc.setVisibility(View.GONE);
                new_channel_layout.setVisibility(View.GONE);
                channel_settings_layout.setVisibility(View.VISIBLE);
                toolbar_title.setText("Channel Settings");
                break;

            case R.id.settings_next_button:
                startActivity(new Intent(context, NewSecretChatActivity.class));
                finish();
                break;

            case R.id.back_arrow:
                onBackPressed();
                finish();
        }
    }
}
