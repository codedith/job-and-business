package com.xcrino.moro.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mukesh.OtpView;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.Interface.OtpReceivedInterface;
import com.xcrino.moro.Mesibo.MesiboAPIInterface;
import com.xcrino.moro.Mesibo.MesiboApiClient;
import com.xcrino.moro.Mesibo.MesiboAppConfig;
import com.xcrino.moro.Mesibo.MesiboSharedPreference;
import com.xcrino.moro.Mesibo.mesiboModels.MesiboUserCreation;
import com.xcrino.moro.PojoModels.OtpLogin;
import com.xcrino.moro.PojoModels.UserRegistration;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.NetworkStatus;
import com.xcrino.moro.Utilities.SmsBroadcastReceiver;
import com.xcrino.moro.Utilities.Validations;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        OtpReceivedInterface, GoogleApiClient.OnConnectionFailedListener {

    Button submit_button;
    Context mContext;
    private ImageView back_arrow;
    OtpView otp_view;
    Validations validations;
    TextView resend_otp, otp_description, resend_textView;

    private AppPreferencesShared appPreferencesShared;
    private GoogleApiClient mGoogleApiClient;
    private SmsBroadcastReceiver mSmsBroadcastReceiver;
    private CountDownTimer countDownTimer;
    public static final String OTP_REGEX = "[0-9]{1,6}";

    private MesiboSharedPreference mesiboSharedPreference;
    private String token;

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
        setContentView(R.layout.activity_otp);

        mContext = this;
        getLayoutInit();

        mesiboSharedPreference = new MesiboSharedPreference(mContext);

        otp_description.setText(getResources().getString(R.string.otp_desc) + " +" + appPreferencesShared.getCountryCode().toString() + " " + appPreferencesShared.getMobileNumber().toString());

        validations = new Validations();
        back_arrow.setVisibility(View.GONE);

        submit_button.setOnClickListener(this);
        resend_otp.setOnClickListener(this);

        mSmsBroadcastReceiver = new SmsBroadcastReceiver();
        mGoogleApiClient = new GoogleApiClient.Builder(mContext).addConnectionCallbacks(this)
                .enableAutoManage(this, this).addApi(Auth.CREDENTIALS_API).build();

        mSmsBroadcastReceiver.setOnOtpListeners(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
        mContext.registerReceiver(mSmsBroadcastReceiver, intentFilter);

        token = FirebaseInstanceId.getInstance().getToken();
        Log.d("Token ", "" + FirebaseInstanceId.getInstance().getToken());
        FirebaseMessaging.getInstance().subscribeToTopic("allDevices");

        countDownTimer(40000, 1000).start();

    }

    private void getLayoutInit() {
        back_arrow = findViewById(R.id.back_arrow);
        submit_button = findViewById(R.id.submit_button);
        otp_view = findViewById(R.id.otp_view);
        resend_otp = findViewById(R.id.resend_otp);
        resend_textView = (TextView) findViewById(R.id.resend_textView);
        otp_description = findViewById(R.id.otp_description);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_button:
                if (NetworkStatus.isNetworkAvailable(mContext)) {
                    if (validations.isOTPValid(otp_view.getText().toString(), mContext)) {

//                        otp_view.getText().toString().trim();
                       /* Intent intent = new Intent(mContext, CreateProfileActivity.class);
                        startActivity(intent);*/
                        verifyPhoneNo();
//                        Intent intent = new Intent(mContext, DashboardActivity.class);
//                        appPreferencesShared.setUserId("6");
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(mContext, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.resend_otp:
                otp_view.getText().clear();
                if (NetworkStatus.isNetworkAvailable(mContext)) {
//                    sendOTP();
//                    getResendOTPMethod();
                } else {
                    Toast.makeText(mContext, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
                }

                countDownTimer(40000, 1000).start();
                break;
        }
    }

    private void verifyPhoneNo() {
        if (Integer.parseInt(otp_view.getText().toString()) == appPreferencesShared.getOtp()) {
            final ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

            ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
            Call<UserRegistration> userRegistrationCall = apiInterface.postUserRegistrationMethod(appPreferencesShared.getMobileNumber(),
                    appPreferencesShared.getCountryCode(), token);

            userRegistrationCall.enqueue(new Callback<UserRegistration>() {
                @Override
                public void onResponse(Call<UserRegistration> call, Response<UserRegistration> response) {
                    if (response.isSuccessful()) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (response.body() != null) {
                            if (response.body().getResult()) {
                                Log.d("info", response.body().getData().toString());
                                appPreferencesShared.setUserId(response.body().getData().get(0).getUserid().toString());
                                Toast.makeText(mContext, "Login Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent;
                                switch (response.body().getData().get(0).getUserExist()) {
                                    case 0:
                                        appPreferencesShared.setIsCreateProfile(1);
                                        appPreferencesShared.setIsFirstTimeLaunch(false);
//                                        createUserInMesibo();
                                        intent = new Intent(mContext, CreateProfileActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        break;

                                    case 1:
                                        appPreferencesShared.setIsCreateProfile(2);
                                        appPreferencesShared.setIsFirstTimeLaunch(false);
//                                        createUserInMesibo();
                                        intent = new Intent(mContext, DashboardActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        break;
                                }

                            } else {
                                Toast.makeText(mContext, "Something went wrong !", Toast.LENGTH_SHORT).show();
                            }
                        } else {
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
                public void onFailure(Call<UserRegistration> call, Throwable t) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
//                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(mContext, "Something went wrong !", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            Toast.makeText(mContext, "Please Enter Correct OTP !", Toast.LENGTH_SHORT).show();
        }
    }

    private void createUserInMesibo() {
        final ProgressDialog progressDialog1 = new ProgressDialog(mContext);
        progressDialog1.setMessage("Please wait...");
        progressDialog1.show();

        MesiboAPIInterface mesiboAPIInterface = MesiboApiClient.getClient().create(MesiboAPIInterface.class);
        Call<MesiboUserCreation> call = mesiboAPIInterface.createUserInMesibo(mesiboSharedPreference.getAppToken(),
                "useradd", mesiboSharedPreference.getAppId(), appPreferencesShared.getMobileNumber());

        call.enqueue(new Callback<MesiboUserCreation>() {
            @Override
            public void onResponse(Call<MesiboUserCreation> call, Response<MesiboUserCreation> response) {
               try {
                   if (progressDialog1.isShowing()) {
                       progressDialog1.dismiss();
                   }
                   if (response.isSuccessful()) {
                       if (response.body() != null) {
                           mesiboSharedPreference.setUserAccessToken(response.body().getMesiboUser().getToken().toString());
                           MesiboAppConfig.getConfig().token = response.body().getMesiboUser().getToken().toString();
                       }
                       else {

                       }
                   }
                   else {

                   }
               }
               catch (Exception e){
                   if (progressDialog1.isShowing()) {
                       progressDialog1.dismiss();
                   }
                   e.printStackTrace();
               }
            }

            @Override
            public void onFailure(Call<MesiboUserCreation> call, Throwable t) {
                if (progressDialog1.isShowing()) {
                    progressDialog1.dismiss();
                }
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onOtpReceived(String otp) {
        otp_view.setText(otp);

    }

    @Override
    public void onOtpTimeout() {

    }

    private CountDownTimer countDownTimer(int millisInFuture, int countDownInterval) {
        return new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                String seconds = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished));
                if (seconds.length() < 2) {
                    resend_textView.setText("Resend in 00:0" + seconds.toString());
                } else {
                    resend_textView.setText("Resend in 00:" + seconds.toString());
                }
                resend_otp.setVisibility(View.GONE);
                resend_textView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFinish() {
                resend_textView.setText("");
                resend_textView.setVisibility(View.GONE);
                resend_otp.setVisibility(View.VISIBLE);

            }
        };
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(mContext).registerReceiver(mSmsBroadcastReceiver,
                new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
        super.onResume();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(mSmsBroadcastReceiver);
    }

    private void getResendOTPMethod() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<OtpLogin> otpLoginCall = apiInterface.getOTPMethod(appPreferencesShared.getMobileNumber(), appPreferencesShared.getCountryCode());

        otpLoginCall.enqueue(new Callback<OtpLogin>() {
            @Override
            public void onResponse(Call<OtpLogin> call, Response<OtpLogin> response) {
                if (response.isSuccessful()) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.body() != null) {
                        if (response.body().getSuccess()) {
                            appPreferencesShared.setOtp(response.body().getOtpData().getOtp());

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
            public void onFailure(Call<OtpLogin> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}