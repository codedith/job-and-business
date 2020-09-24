package com.xcrino.moro.BusinessModules;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.xcrino.moro.Adapter.ViewPagerAdapter;
import com.xcrino.moro.Fragment.AllCampaignsFragment;
import com.xcrino.moro.Fragment.BlockedCampaignsFragment;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.AppPreferencesShared;

import java.util.Locale;

public class CampaignListForUserActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView back_arrow;
    TextView toolbar_title;
    Context mContext;
    TabLayout mTabLayout;
    ViewPager mViewpager;
    ViewPagerAdapter mViewPagerAdapter;
    private AppPreferencesShared appPreferencesShared;

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

        setContentView(R.layout.activity_campaign_list_for_user);
        mContext = this;
        getInitUi();
        toolbar_title.setText("Campaign List");
        back_arrow.setOnClickListener(this);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFragment(new AllCampaignsFragment());
        mViewPagerAdapter.addFragment(new BlockedCampaignsFragment());
        mViewpager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewpager);
        setTabTitles(2);
    }

    private void getInitUi() {
        back_arrow = findViewById(R.id.back_arrow);
        toolbar_title = findViewById(R.id.toolbar_title);
        mTabLayout = findViewById(R.id.campaign_tablayout);
        mViewpager = findViewById(R.id.campaign_viewpager);
    }

    private void setTabTitles(int i) {
        for (int y = 0; y < i; y++) {
            switch (y) {
                case 0:
                    mTabLayout.getTabAt(y).setText("All Campaigns");
                    break;

                case 1:
                    mTabLayout.getTabAt(y).setText("Blocked Campaigns");
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_arrow:
                onBackPressed();

        }
    }
}
