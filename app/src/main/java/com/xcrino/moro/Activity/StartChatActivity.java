package com.xcrino.moro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xcrino.moro.Adapter.StartChatAdapter;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.AppPreferencesShared;

import java.util.Locale;

public class StartChatActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    RecyclerView mRecyclerView;
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
        setContentView(R.layout.activity_start_chat);
        mContext = this;

        mToolbar_title = findViewById(R.id.toolbar_title);
        mToolbar_title.setText("Select Contact");
        back_arrow = findViewById(R.id.back_arrow);

        back_arrow.setOnClickListener(this);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new StartChatAdapter(mContext));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_arrow:
                onBackPressed();
                break;
        }
    }
}
