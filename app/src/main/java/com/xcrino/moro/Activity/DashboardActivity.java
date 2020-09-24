package com.xcrino.moro.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.xcrino.moro.Adapter.ViewPagerAdapter;
import com.xcrino.moro.BusinessModules.CampaignListForUserActivity;
import com.xcrino.moro.Fragment.BusinessFragment;
import com.xcrino.moro.Fragment.CallsFragment;
import com.xcrino.moro.Fragment.ChatFragment;
import com.xcrino.moro.Fragment.JobFragment;
import com.xcrino.moro.Fragment.StatusFragment;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.UserProfile;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.AlertDialogueManager;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.NetworkStatus;

import java.util.Locale;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout mDrawer;
    ActionBarDrawerToggle mToggle;
    Context mContext;
    Toolbar mToolbar;
    ImageView mNavigation, search_item_nearBy, search_item, dropDown_menu;
    TabLayout mTabLayout;
    ViewPager mViewpager;
    ViewPagerAdapter mViewPagerAdapter;
    TextView mToolbar_title;
    NavigationView mDesign_navigation_view;
    FabSpeedDial fab_speed_dial_menu;
    private AlertDialogueManager alertDialogueManager;
    private ImageView dark_bright_swich;
    private AppPreferencesShared appPreferencesShared;
    private LinearLayout menuProfilesHeader;
    private ConstraintLayout headerProfile;

    private ImageView user_Profile_Image, profile_Image;
    private TextView userName_tv, userEmail_Id_tv, profile_UserName;
    private Integer user_type = 0, business_profile = 0;
    Intent starterIntent;

//    private String userId;

    @SuppressLint("WrongConstant")
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

        setContentView(R.layout.activity_dashboard);

        mContext = this;
        alertDialogueManager = new AlertDialogueManager();
        appPreferencesShared = new AppPreferencesShared(mContext);
        appPreferencesShared.setIsFirstTimeLaunch(false);

//        userId = baseInstance.getUserid();

        initializeUiLayout();

        Log.d("Token ", "" + FirebaseInstanceId.getInstance().getToken());
        FirebaseMessaging.getInstance().subscribeToTopic("allDevices");

        starterIntent = getIntent();

        setSupportActionBar(mToolbar);
        mToolbar_title.setText("MoGo");

        mToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.open_drawer, R.string.close_drawer);
        mDrawer.addDrawerListener(mToggle);
        mToggle.setDrawerIndicatorEnabled(false);
        mNavigation.setOnClickListener(this);
        mToggle.syncState();

        Drawable drawable = mToolbar.getOverflowIcon();
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable.mutate(), Color.WHITE);
            mToolbar.setOverflowIcon(drawable);
        }

        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFragment(new ChatFragment());
        mViewPagerAdapter.addFragment(new StatusFragment());
        mViewPagerAdapter.addFragment(new CallsFragment());
        mViewPagerAdapter.addFragment(new JobFragment());
        mViewPagerAdapter.addFragment(new BusinessFragment());
        mViewpager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewpager);
        mDesign_navigation_view.setNavigationItemSelectedListener(this);

        setTabTitles(5);

        View header = mDesign_navigation_view.getHeaderView(0);
        dropDown_menu = (ImageView) header.findViewById(R.id.dropDown_menu);
        menuProfilesHeader = (LinearLayout) header.findViewById(R.id.menuProfilesHeader);
        headerProfile = (ConstraintLayout) header.findViewById(R.id.headerProfile);
        user_Profile_Image = (ImageView) header.findViewById(R.id.user_Profile_Image);
        profile_Image = (ImageView) header.findViewById(R.id.profile_Image);
        userName_tv = (TextView) header.findViewById(R.id.userName_tv);
        userEmail_Id_tv = (TextView) header.findViewById(R.id.userEmail_Id_tv);
        profile_UserName = (TextView) header.findViewById(R.id.profile_UserName);

        dark_bright_swich = (ImageView) header.findViewById(R.id.dark_bright_swich);
        dark_bright_swich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appPreferencesShared.getmDayNightMode()) {
                    darkMode(false);
                } else {
                    darkMode(true);
                }
            }
        });

        headerProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuProfilesHeader.getVisibility() == View.GONE) {
                    menuProfilesHeader.setVisibility(View.VISIBLE);
                    dropDown_menu.animate().rotation(180).setDuration(0);
                } else {
                    menuProfilesHeader.setVisibility(View.GONE);
                    dropDown_menu.animate().rotation(360).setDuration(0);
                }
            }
        });

    }

    private void initializeUiLayout() {
        mToolbar = findViewById(R.id.toolbar);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawer = findViewById(R.id.drawer);
        mNavigation = findViewById(R.id.navigation);
        mViewpager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tabLayout);
        mToolbar_title = findViewById(R.id.toolbar_title);
        mDesign_navigation_view = (NavigationView) findViewById(R.id.mDesign_navigation_view);

        search_item_nearBy = (ImageView) findViewById(R.id.search_item_nearBy);
        search_item = (ImageView) findViewById(R.id.search_item);

    }

    private void darkMode(boolean b) {
        appPreferencesShared.setDayNightMode(b);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                finish();
                startActivity(new Intent(mContext, DashboardActivity.this.getClass()));

            }
        }, 500);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();
        switch (id) {
            case R.id.settings:
                startActivity(new Intent(mContext, SettingsActivity.class)); //settingActivity hogi yha
                return true;

            default:

        }
        return true;
    }

    private void getUserProfileDetailsMethod() {
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<UserProfile> userProfileCall = apiInterface.getUserProfileDetailsMethod(appPreferencesShared.getUserId());

        userProfileCall.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (response.isSuccessful()) {
                    if (progressDialog.isShowing()) {
                        progressDialog.cancel();
                    }
                    if (response.body() != null) {
                        if (response.body().getResult()) {

                            userName_tv.setText(response.body().getUserDatum().getUserProfileData().getUserFullName() == null ? "" : response.body().getUserDatum().getUserProfileData().getUserFullName());
                            profile_UserName.setText(response.body().getUserDatum().getUserProfileData().getUserFullName() == null ? "" : response.body().getUserDatum().getUserProfileData().getUserFullName());
                            mToolbar_title.setText(response.body().getUserDatum().getUserProfileData().getUserFullName() == null ? "" : response.body().getUserDatum().getUserProfileData().getUserFullName());
                            userEmail_Id_tv.setText(response.body().getUserDatum().getUserProfileData().getUserEmail() == null ? "" : response.body().getUserDatum().getUserProfileData().getUserEmail());

                            if (response.body().getUserDatum().getUserProfileData().getUserProfileImage() != null) {
                                Picasso.with(getApplicationContext()).load(response.body().getUserDatum().getUserProfileData().getUserProfileImage()).resize(185, 185).transform(new CropCircleTransformation()).placeholder(R.drawable.user_dummy).into(user_Profile_Image);
                                Picasso.with(getApplicationContext()).load(response.body().getUserDatum().getUserProfileData().getUserProfileImage()).resize(85, 85).transform(new CropCircleTransformation()).placeholder(R.drawable.user_dummy).into(profile_Image);
                            }

                            user_type = response.body().getUserDatum().getUserType();
                            business_profile = response.body().getUserDatum().getBusiness_profile();

                            UserProfile userProfile = response.body();
                            Gson gson = new Gson();
                            String json = gson.toJson(userProfile);
                            appPreferencesShared.setUserDetails(json);

                            if (appPreferencesShared.getFragmentDirection().equals("Job")) {
                                mViewpager.setCurrentItem(3);
                            }
                            if (appPreferencesShared.getFragmentDirection().equals("Business")) {
                                mViewpager.setCurrentItem(4);
                            }

                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(mContext, "Something went wrong !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(mContext, "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(mContext, "Something went wrong !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.navigation:
                mDrawer.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.business_promotions) {
            mViewpager.setCurrentItem(4);
        } else if (id == R.id.rate_app) {
            alertDialogueManager.showViewAlertDialog(mContext, "Would you mind stopping for a moment to rate us?");

        } else if (id == R.id.privacy_policy) {
            startActivity(new Intent(mContext, PrivacyPolicyActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

        } else if (id == R.id.job_DashBoard) {
            mViewpager.setCurrentItem(3);
        } else if (id == R.id.contacts) {
            startActivity(new Intent(mContext, ContactActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

        } else if (id == R.id.calls) {
            startActivity(new Intent(mContext, CallsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

        } else if (id == R.id.invite_friends) {
            startActivity(new Intent(mContext, InviteFriendsSettingActivity.class));

        } else if (id == R.id.starred_sms) {

        } else if (id == R.id.backUp_System) {

        } else if (id == R.id.campaign_list) {
            startActivity(new Intent(mContext, CampaignListForUserActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setTabTitles(int size) {
        for (int i = 0; i < size; i++) {
            switch (i) {
                case 0:
                    mTabLayout.getTabAt(i).setText("Chat");
                    break;

                case 1:
                    mTabLayout.getTabAt(i).setText("Status");
                    break;

                case 2:
                    mTabLayout.getTabAt(i).setText("Call");
                    break;

                case 3:
                    mTabLayout.getTabAt(i).setText("Job");
                    break;

                case 4:
                    mTabLayout.getTabAt(i).setText("Business");
                    break;

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getUserProfileDetailsMethod();
        } else {
            Toast.makeText(mContext, "Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }
}