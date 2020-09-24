package com.xcrino.moro.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.hbb20.CountryCodePicker;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.Interface.OtpReceivedInterface;
import com.xcrino.moro.PojoModels.OtpLogin;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.NetworkStatus;
import com.xcrino.moro.Utilities.SmsBroadcastReceiver;
import com.xcrino.moro.Utilities.Validations;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, OtpReceivedInterface {

    private Button next_button;
    private Context mContext;
    private ImageView back_arrow;
    private Validations validations;
    private EditText mobile_number;
    private CountryCodePicker ccp;
    private LinearLayout faceBook_Login, gmail_Login, login;

    private String mobileNumber;
    private Integer countryCode = 91;
    private GoogleApiClient mGoogleApiClient;
    private int RESOLVE_HINT = 2;
    private SmsBroadcastReceiver mSmsBroadcastReceiver;
    private AppPreferencesShared appPreferencesShared;

    private boolean TermsAndConditions = false;
    private final String BROADCAST_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    private static final int PERMISSION_REQUEST_ID = 100;
    private static final String TAG = LogInActivity.class.getSimpleName();


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
        setContentView(R.layout.activity_login);

        mContext = this;
        appPreferencesShared = new AppPreferencesShared(mContext);
        TermsAndConditions = appPreferencesShared.getTermsAndConditions();

        getLayoutInit();
        loadingPopup();

        validations = new Validations();
        back_arrow.setVisibility(View.GONE);
        next_button.setOnClickListener(this);

        // init broadcast receiver
        mSmsBroadcastReceiver = new SmsBroadcastReceiver();
        mGoogleApiClient = new GoogleApiClient.Builder(mContext).addConnectionCallbacks(this).enableAutoManage(this,
                this).addApi(Auth.CREDENTIALS_API).build();

        mSmsBroadcastReceiver.setOnOtpListeners(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
        mContext.registerReceiver(mSmsBroadcastReceiver, intentFilter);

        getHintPhoneNumber();

        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countryCode = Integer.valueOf(ccp.getSelectedCountryCode());

            }
        });

    }

    private void loadingPopup() {
        if (!TermsAndConditions) {
            new AlertDialog.Builder(this).setTitle("Terms & Conditions")
                    .setMessage("Lorem Ipsum is very cute Dummy text for Android Studio")
                    .setCancelable(false)
                    .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            appPreferencesShared.setTermsAndConditions(false);
                            finish();
                            System.exit(0);
                        }
                    })
                    .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            appPreferencesShared.setTermsAndConditions(true);
                            requestRuntimePermissions(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS);

                        }
                    }).show();

        }

       /* ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.privacy_policy_popup, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();*/

    }

    private void getHintPhoneNumber() {
        HintRequest hintRequest = new HintRequest.Builder().setPhoneNumberIdentifierSupported(true).build();
        PendingIntent mIntent = Auth.CredentialsApi.getHintPickerIntent(mGoogleApiClient, hintRequest);
        try {
            startIntentSenderForResult(mIntent.getIntentSender(), RESOLVE_HINT, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    private void getLayoutInit() {
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        next_button = (Button) findViewById(R.id.next_button);
        mobile_number = (EditText) findViewById(R.id.mobile_number);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        login = (LinearLayout) findViewById(R.id.login);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_button:
//                appPreferencesShared.setMobileNumber(mobile_number.getText().toString());
                if (validations.isMobileValid(mobile_number.getText().toString(), mobile_number)) {
                    appPreferencesShared.setMobileNumber(mobile_number.getText().toString());
                    appPreferencesShared.setCountryCode(countryCode);
                   /* objLoginWithOTPModel.deviceId = "dshfdhbfdhbfdbfdjdfkjfmkdfngdk1655484815bhjfbhjfdbhjdfhf454857576576576776557655767"
                    objLoginWithOTPModel.deviceId = appPreferencesShared.deviceTokenId;
                    objLoginWithOTPModel.deviceType = "Android";*/
                    if (NetworkStatus.isNetworkAvailable(mContext)) {
                        startSMSListener();
//                        sendOTP();

                    } else {
                        Toast.makeText(mContext, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }

    public void startSMSListener() {
        SmsRetrieverClient mClient = SmsRetriever.getClient(this);
        Task<Void> mTask = mClient.startSmsRetriever();
        mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (NetworkStatus.isNetworkAvailable(mContext)) {
                    sendOTP();
//                    baseInstance.setOtp(123456);
//                    Intent intent = new Intent(mContext, OTPActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
                } else {
                    Toast.makeText(mContext, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mContext, "Error", Toast.LENGTH_LONG).show();
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
//        Toast.makeText(this, "Otp Received " + otp, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onOtpTimeout() {
//        Toast.makeText(mContext, "Timeout Please try again !", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESOLVE_HINT) {
            if (resultCode == Activity.RESULT_OK) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                mobile_number.setText(credential.getId());
                mobile_number.setSelection(mobile_number.getText().toString().length());
            }
        }
    }

    private void sendOTP() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<OtpLogin> otpLoginCall = apiInterface.getOTPMethod(appPreferencesShared.getMobileNumber(), countryCode);

        otpLoginCall.enqueue(new Callback<OtpLogin>() {
            @Override
            public void onResponse(Call<OtpLogin> call, Response<OtpLogin> response) {
                if (response.isSuccessful()) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.body() != null) {
                        if (response.body().getSuccess()) {
                            appPreferencesShared.setIsFirstTimeLaunch(false);
                            appPreferencesShared.setOtp(response.body().getOtpData().getOtp());
                            Intent intent = new Intent(mContext, OTPActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

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

    private void requestRuntimePermissions(String... permissions) {
        for (String perm : permissions) {

            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{perm}, PERMISSION_REQUEST_ID);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_ID) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                getHintPhoneNumber();
//                sendOTP();


                // permission was granted
               /* mSmsBroadcastReceiver.bindListener(new SmsBroadcastReceiver() {
                    @Override
                    public void messageReceived(String sender, String messageText) {
                        Log.e("Text", messageText);
                        Toast.makeText(mContext, "Message: " + messageText, Toast.LENGTH_LONG).show();
                    }
                });*/

            } else {
                Log.e(TAG, "Permission not granted");


            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mSmsBroadcastReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
//        Log.e("LogInActivity", "Registered receiver");

    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(mSmsBroadcastReceiver);
//        Log.e("LogInActivity", "Unregistered receiver");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}