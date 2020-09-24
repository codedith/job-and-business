package com.xcrino.moro.SliderPageActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.xcrino.moro.Activity.DashboardActivity;
import com.xcrino.moro.Activity.LogInActivity;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.AppPreferencesShared;

import java.util.List;
import java.util.Locale;

public class SplashScreenActivity extends AppCompatActivity {

    String mAccessTokenString;
    Handler mDelayHandler;
    int SPLASH_DELAY = 0;
    AppPreferencesShared mAppPreferences;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mContext = this;
        mAppPreferences = new AppPreferencesShared(mContext);

        Locale myLocale = new Locale(mAppPreferences.getLocale());
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        mDelayHandler = new Handler();
        mDelayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAppPreferences.getIsFirstTimeLaunch()) {
                    mAppPreferences.setIsFirstTimeLaunch(true);
                    Intent intent = new Intent(mContext, SliderImageScreenActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    mAppPreferences.setIsFirstTimeLaunch(false);
                    if (mAccessTokenString.isEmpty()) {
                        Intent intent = new Intent(mContext, LogInActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(mContext, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }, SPLASH_DELAY);
        mAccessTokenString = mAppPreferences.getAccessTokenString();

//        requestStoragePermission();
//        goToNextActivity();

    }

    private void requestStoragePermission() {
        Dexter.withActivity(this).withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.SEND_SMS).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()) {
//                            Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
//                    goToNextActivity();
                }
                // check for permanent denial of any permission
                if (report.isAnyPermissionPermanentlyDenied()) {
                    // show alert dialog navigating to Settings
                    Toast.makeText(mContext, "Please check permissions", Toast.LENGTH_SHORT).show();
//                    showSettingsDialog();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
            }
        }).onSameThread().check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
//                openSettings();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);

    }

    private void goToNextActivity() {
        mDelayHandler = new Handler();
        mDelayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAppPreferences.getIsFirstTimeLaunch()) {
                    mAppPreferences.setIsFirstTimeLaunch(true);
                    Intent intent = new Intent(mContext, SliderImageScreenActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    mAppPreferences.setIsFirstTimeLaunch(false);
                    if (mAccessTokenString.isEmpty()) {
                        Intent intent = new Intent(mContext, LogInActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(mContext, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }, SPLASH_DELAY);
    }
}