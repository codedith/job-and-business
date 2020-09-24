package com.xcrino.moro.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.UserProfile;
import com.xcrino.moro.R;
import com.xcrino.moro.SliderPageActivity.MultiSupportLanguageActivity;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.NetworkStatus;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView back_arrow, userprofileimage_collapse;
    private Toolbar toolbar_c;
    private TextView toolbar_title, user_name_none, phone_number_ctb;
    private AppPreferencesShared appPreferencesShared;
    private Menu menu;
    private CardView change_language, notificatioSounds, privacyAndSecurity, dataAndStorage,
            chatSettings, deviceId, helpsOfSettings;
    private Context mContext;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private PopupWindow mPopupWindow;
    private CoordinatorLayout settingPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appPreferencesShared = new AppPreferencesShared(this);
        if (appPreferencesShared.getmDayNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        Locale myLocale = new Locale(appPreferencesShared.getLocale());
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        setContentView(R.layout.activity_settings);

        mContext = this;
        getLayoutUIInit();

        setSupportActionBar(toolbar_c);
        toolbar_c.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedToolbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsingToolbar);
        AppBarLayout mAppBarLayout = findViewById(R.id.appbarlayout);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + i == 0) {
                    isShow = true;
//                    showOption(R.id.search_coll);

                } else if (isShow) {
                    isShow = false;
//                    showOption(R.id.search_coll);

                }
            }
        });

        change_language.setOnClickListener(this);
        notificatioSounds.setOnClickListener(this);
        privacyAndSecurity.setOnClickListener(this);
        dataAndStorage.setOnClickListener(this);
        chatSettings.setOnClickListener(this);
        deviceId.setOnClickListener(this);
        helpsOfSettings.setOnClickListener(this);

    }

    private void getLayoutUIInit() {
        toolbar_c = findViewById(R.id.toolbar_c);
        user_name_none = findViewById(R.id.user_name_none);
        phone_number_ctb = findViewById(R.id.phone_number_ctb);
        userprofileimage_collapse = findViewById(R.id.userprofileimage_collapse);
        collapsingToolbarLayout = findViewById(R.id.collapse_bar);
        notificatioSounds = (CardView) findViewById(R.id.notificatioSounds);
        privacyAndSecurity = (CardView) findViewById(R.id.privacyAndSecurity);
        dataAndStorage = (CardView) findViewById(R.id.dataAndStorage);
        chatSettings = (CardView) findViewById(R.id.chatSettings);
        deviceId = (CardView) findViewById(R.id.deviceId);
        change_language = (CardView) findViewById(R.id.change_language);
        helpsOfSettings = (CardView) findViewById(R.id.helpsOfSettings);
        settingPage = (CoordinatorLayout) findViewById(R.id.settingPage);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        // hideOption(R.id.search_coll);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.edit_profile) {
            Intent intent = new Intent(this, CreateProfileActivity.class);
            appPreferencesShared.setIsEditUserProfile(true);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.log_out) {
            appPreferencesShared.ClearPreferences();
            startActivity(new Intent(mContext, LogInActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void hideOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(false);
    }

    private void showOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_c:
                onBackPressed();
                break;

            case R.id.change_language:
                Intent cl = new Intent(mContext, MultiSupportLanguageActivity.class);
                startActivity(cl);
                break;

            case R.id.notificatioSounds:
//                startActivity(new Intent(mContext, NotificationAndSoundsActivity.class));
                break;

            case R.id.privacyAndSecurity:
//                startActivity(new Intent(mContext, PrivacyAndSecurityActivity.class));
                break;

            case R.id.dataAndStorage:
//                startActivity(new Intent(mContext, DataAndStorageActivity.class));
                break;

            case R.id.chatSettings:
//                startActivity(new Intent(mContext, ChatSettingsActivity.class));
                break;

            case R.id.deviceId:
//                startActivity(new Intent(mContext, DeviceActivity.class));
                break;

            case R.id.helpsOfSettings:
//                startActivity(new Intent(mContext, HelpsActivity.class));
                loadingBottumPopup();
                break;

        }
    }

    private void loadingBottumPopup() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.help_card_layout, null);
        mPopupWindow = new PopupWindow(customView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setTouchable(true);

        final LinearLayout askAQuestion = (LinearLayout) customView.findViewById(R.id.askAQuestion);
        final LinearLayout mogoFaq = (LinearLayout) customView.findViewById(R.id.mogoFaq);
        final LinearLayout privacyPolicy = (LinearLayout) customView.findViewById(R.id.privacyPolicy);

        askAQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "You Clicked Ask a questions", Toast.LENGTH_SHORT).show();
                mPopupWindow.dismiss();
            }
        });

        mogoFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "You Clicked Mogo FAQ", Toast.LENGTH_SHORT).show();
                mPopupWindow.dismiss();
            }
        });

        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "You Clicked Privacy Policy", Toast.LENGTH_SHORT).show();
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.showAtLocation(settingPage, Gravity.BOTTOM, 0, 0);

    }

    private void notificationSounds() {
       /* NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
        builder.setAutoCancel(true);
        builder.setContentTitle("Work Progress");
        builder.setContentText("Submit your today's work progress");
        Intent intent = new Intent(mContext, WorkStatus.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        builder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());*/

    }

    @Override
    protected void onPause() {
        super.onPause();
        Locale myLocale = new Locale(appPreferencesShared.getLocale());
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Locale myLocale = new Locale(appPreferencesShared.getLocale());
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getUserProfileDetailsMethod();
        } else {
            Toast.makeText(mContext, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Locale myLocale = new Locale(appPreferencesShared.getLocale());
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    /*  @Override
    protected void onResume() {
        super.onResume();
        String language = appPreferencesShared.getLocale();
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            conf.locale = Locale.forLanguageTag(language);
        }
        res.updateConfiguration(conf, dm);

    }*/

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
                        progressDialog.dismiss();
                    }
                    if (response.body() != null) {
                        if (response.body().getResult()) {
                            user_name_none.setText(response.body().getUserDatum().getUserProfileData().getUserFullName() == null ? "" : response.body().getUserDatum().getUserProfileData().getUserFullName());
                            collapsingToolbarLayout.setTitle(response.body().getUserDatum().getUserProfileData().getUserFullName() == null ? "" : response.body().getUserDatum().getUserProfileData().getUserFullName());
                            toolbar_c.setTitle(response.body().getUserDatum().getUserProfileData().getUserFullName() == null ? "" : response.body().getUserDatum().getUserProfileData().getUserFullName());
                            phone_number_ctb.setText(response.body().getUserDatum().getUserProfileData().getUserMobile().toString());

                            if (response.body().getUserDatum().getUserProfileData().getUserProfileImage() != null) {
                                Picasso.with(getApplicationContext()).load(response.body().getUserDatum().getUserProfileData().getUserProfileImage()).placeholder(R.drawable.user_dummy).into(userprofileimage_collapse);
                            }
                            UserProfile userProfile = response.body();
                            Gson gsonuserProfile = new Gson();
                            String jsonuserProfile = gsonuserProfile.toJson(userProfile);
                            appPreferencesShared.setUserDetails(jsonuserProfile);
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
}