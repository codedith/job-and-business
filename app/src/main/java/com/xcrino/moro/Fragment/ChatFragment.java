package com.xcrino.moro.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mesibo.calls.MesiboCall;
import com.xcrino.moro.Activity.CreateChannelActivity;
import com.xcrino.moro.Activity.CreateNewGroupActivity;
import com.xcrino.moro.Activity.NewSecretChatActivity;
import com.xcrino.moro.Activity.StartChatActivity;
import com.xcrino.moro.Adapter.ChatAdapter;
import com.xcrino.moro.Mesibo.MesiboAppConfig;
import com.xcrino.moro.Mesibo.MesiboSampleAPI;
import com.xcrino.moro.Mesibo.MesiboSharedPreference;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.AppPreferencesShared;

import java.util.Locale;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class ChatFragment extends Fragment implements View.OnClickListener {

    RecyclerView mRecyclerView;
    Context mContext;
    private FabSpeedDial fab_speed_dial_chat;
    private AppPreferencesShared appPreferencesShared;
    MesiboSharedPreference mesiboSharedPreference;
    private static MesiboCall mCall = null;
    private static MesiboAppConfig mConfig = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        appPreferencesShared= new AppPreferencesShared(getActivity());
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
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        mContext = getContext();

        mesiboSharedPreference = new MesiboSharedPreference(mContext);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        fab_speed_dial_chat = (FabSpeedDial) view.findViewById(R.id.fab_speed_dial_chat);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new ChatAdapter(mContext));


        fab_speed_dial_chat.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.new_group:
                        startActivity(new Intent(mContext, CreateNewGroupActivity.class));
                        break;

                    case R.id.new_channel:
                        startActivity(new Intent(mContext, CreateChannelActivity.class));
                        break;

                    case R.id.new_chat:
                        startActivity(new Intent(mContext, StartChatActivity.class));
                        break;

                    /*case R.id.business_promotion:
                        break;*/

                    /*case R.id.add_jobs:
                        break;*/

                    case R.id.secret_chat:
                        startActivity(new Intent(mContext, NewSecretChatActivity.class));
                        break;

                }
                return true;
            }
        });

        mesiboIntegration();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_chat:
                Intent intent = new Intent(mContext, StartChatActivity.class);
                startActivity(intent);
        }
    }

    private void mesiboIntegration() {
        mConfig = new MesiboAppConfig(mContext);
        MesiboAppConfig.getConfig().token = "2a0b63812684a9e90ad3a3714982529903aafbda85dfcecc11f1b5";
        MesiboSampleAPI.init(mContext);

        mCall = MesiboCall.getInstance();
        mCall.init(mContext);
    }
}
