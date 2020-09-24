package com.xcrino.moro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.AppPreferencesShared;

import java.util.Locale;

public class WalletActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    Toolbar mToolbar;
    TextView mToolbar_title;
    private ImageView back_arrow;
    private AppPreferencesShared appPreferencesShared;

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
        setContentView(R.layout.activity_wallet);
        mContext = this;

        getUIInit();

        mToolbar_title.setText("Wallet");
        back_arrow.setOnClickListener(this);
    }

    private void getUIInit() {
        mToolbar = findViewById(R.id.toolbar);
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
