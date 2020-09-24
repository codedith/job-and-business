package com.xcrino.moro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.AppPreferencesShared;

import java.util.Locale;

public class UserDetailsSettingActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView navigation, edit_user;
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
        setContentView(R.layout.activity_user_details);

        getUIInit();

        navigation.setOnClickListener(this);
        edit_user.setOnClickListener(this);

    }

    private void getUIInit() {
        navigation = (ImageView) findViewById(R.id.navigation);
        edit_user = (ImageView) findViewById(R.id.edit_user);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.navigation:
                onBackPressed();
                break;

            case R.id.edit_user:
                break;
        }
    }
}
