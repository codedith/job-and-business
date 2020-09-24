package com.xcrino.moro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.CommonResponseModel;
import com.xcrino.moro.PojoModels.SubscriptionPlan;
import com.xcrino.moro.PojoModels.UserProfile;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.NetworkStatus;

import org.json.JSONObject;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener, PaymentResultListener {

    private ImageView back_arrow;
    private Context mContext;
    private TextView toolbar_title, plans_name, amountInInrUsd, daysforValidity, applicable_credits;
    private AppPreferencesShared appPreferencesShared;
    private Button checkout_button;
    private static final String TAG = PaymentActivity.class.getSimpleName();
    UserProfile userProfile;
    SubscriptionPlan subscriptionPlan;

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
        setContentView(R.layout.activity_payment);

        setLayoutUiInit();

        mContext = this;
        toolbar_title.setText("Payment");

        Gson gSonUserProfile = new Gson();
        String jsonUserProfile = appPreferencesShared.getUserDetails();
        userProfile = gSonUserProfile.fromJson(jsonUserProfile, UserProfile.class);

        Gson gsonPlan = new Gson();
        String json = appPreferencesShared.getPlanDetails();
        subscriptionPlan = gsonPlan.fromJson(json, SubscriptionPlan.class);

        back_arrow.setOnClickListener(this);
        checkout_button.setOnClickListener(this);

        Checkout.clearUserData(mContext);
        Checkout.preload(mContext);

        switch (appPreferencesShared.getSubscriptionDirection()) {
            case "Employee":
                plans_name.setText("Plan Name: Employee ");
                plans_name.append(subscriptionPlan.getEsType());
                plans_name.append(" Plan");
                amountInInrUsd.setText("Plan Amount : ");
                if (userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("india") ||
                        userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("India")) {
                    amountInInrUsd.append(subscriptionPlan.getEsAmount());
                    amountInInrUsd.append(" ");
                    amountInInrUsd.append(subscriptionPlan.getEsCurrency());
                } else {
                    amountInInrUsd.append(subscriptionPlan.getEsForeignAmt());
                    amountInInrUsd.append(" ");
                    amountInInrUsd.append(subscriptionPlan.getEsForeignCur());
                }
                daysforValidity.setText("Validity : -NA-");
                applicable_credits.setText("Applicable Credits : ");
                applicable_credits.append(subscriptionPlan.getEsCredits());
                break;

            case "Recruiter":
                plans_name.setText("Plan Name: Recruiter ");
                plans_name.append(subscriptionPlan.getPlansName());
                plans_name.append(" Plan");
                amountInInrUsd.setText("Plan Amount : ");
                if (userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("india") ||
                        userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("India")) {
                    amountInInrUsd.append(subscriptionPlan.getAmount());
                    amountInInrUsd.append(" ");
                    amountInInrUsd.append(subscriptionPlan.getCurrency());
                } else {
                    amountInInrUsd.append(subscriptionPlan.getForeignAmt());
                    amountInInrUsd.append(" ");
                    amountInInrUsd.append(subscriptionPlan.getForeignCur());
                }
                daysforValidity.setText("Validity : ");
                daysforValidity.append(subscriptionPlan.getDays());
                daysforValidity.append(" Days");
                applicable_credits.setText("Applicable Credits : ");
                applicable_credits.append(subscriptionPlan.getPostJob());
                break;

            case "Business":
                plans_name.setText("Business Plan");
                amountInInrUsd.setText("Plan Amount : ");
                if (userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("india") ||
                        userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("India")) {
                    amountInInrUsd.append(subscriptionPlan.getBmsAmtInr());
                    amountInInrUsd.append(" ");
                    amountInInrUsd.append(subscriptionPlan.getBmsAmtCur());
                } else {
                    amountInInrUsd.append(subscriptionPlan.getBmsAmtUsd());
                    amountInInrUsd.append(" ");
                    amountInInrUsd.append(subscriptionPlan.getBmsAmtForeignCur());
                }
                daysforValidity.setText("Validity : ");
                daysforValidity.append(subscriptionPlan.getBmsValidity());
                daysforValidity.append(" Days");
                applicable_credits.setText("Applicable Credits : ");
                applicable_credits.append(subscriptionPlan.getBmsCredit());
                break;
        }
    }

    private void setLayoutUiInit() {
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        plans_name = (TextView) findViewById(R.id.plans_name);
        amountInInrUsd = (TextView) findViewById(R.id.amountInInrUsd);
        daysforValidity = (TextView) findViewById(R.id.daysforValidity);
        applicable_credits = (TextView) findViewById(R.id.applicable_credits);
        checkout_button = (Button) findViewById(R.id.checkout_button);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                finish();

            case R.id.checkout_button:
                if (NetworkStatus.isNetworkAvailable(mContext)) {
                    switch (appPreferencesShared.getSubscriptionDirection()) {
                        case "Employee":
                            if (userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("india") ||
                                    userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("India")) {
                                startPayment(subscriptionPlan.getEsAmount());
                            } else {
                                startPayment(subscriptionPlan.getEsForeignAmt());
                            }
                            break;

                        case "Recruiter":
                            if (userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("india") ||
                                    userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("India")) {
                                startPayment(subscriptionPlan.getAmount());
                            } else {
                                startPayment(subscriptionPlan.getForeignAmt());
                            }
                            break;

                        case "Business":
                            if (userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("india") ||
                                    userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("India")) {
                                startPayment(subscriptionPlan.getBmsAmtInr());
                            } else {
                                startPayment(subscriptionPlan.getBmsAmtUsd());
                            }
                            break;
                    }
                } else {
                    Toast.makeText(mContext, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void startPayment(String planAmount) {
        final Activity activity = this;
        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "MoRo");
            options.put("description", "");
            if (userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("india") ||
                    userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("India")) {
                options.put("currency", "INR");
            } else {
                options.put("currency", "USD");
            }
            int amount = (Integer.parseInt(planAmount)) * 100;
            options.put("amount", Integer.toString(amount));

            JSONObject preFill = new JSONObject();
            preFill.put("email", userProfile.getUserDatum().getUserProfileData().getUserEmail().toString());
            preFill.put("contact", appPreferencesShared.getMobileNumber());

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();

        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        if (NetworkStatus.isNetworkAvailable(mContext)) {
            switch (appPreferencesShared.getSubscriptionDirection()) {
                case "Employee":
                    try {
                        applyEmployeePlanMethod(razorpayPaymentID);
                        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e(TAG, "Exception in onPaymentSuccess", e);
                    }
                    break;

                case "Recruiter":
                    try {
                        postAddSubscriptionMethod(razorpayPaymentID);
                        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e(TAG, "Exception in onPaymentSuccess", e);
                    }
                    break;

                case "Business":
                    try {
                        applyBusinessModulePlanMethod(razorpayPaymentID);
                        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e(TAG, "Exception in onPaymentSuccess", e);
                    }
                    break;
            }
        } else {
            Toast.makeText(mContext, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }

    private void applyBusinessModulePlanMethod(String razorpayPaymentID) {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<CommonResponseModel> planCall = apiInterface.applyBusinessModulePlanMethod(appPreferencesShared.getUserId(),
                subscriptionPlan.getBmsId(), razorpayPaymentID);

        planCall.enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(mContext, "Subscribed Successfully", Toast.LENGTH_SHORT).show();
                        appPreferencesShared.isRecruiterPaymentSuccessful(true);
                        finish();
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void applyEmployeePlanMethod(String razorpayPaymentID) {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<CommonResponseModel> subscriptionPlanCall = apiInterface.applyEmployeePlanMethod(appPreferencesShared.getUserId(),
                subscriptionPlan.getEsId(), razorpayPaymentID);
        subscriptionPlanCall.enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(mContext, "Subscribed Successfully", Toast.LENGTH_SHORT).show();
                        appPreferencesShared.isRecruiterPaymentSuccessful(true);
                        finish();

                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    e.getMessage();
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postAddSubscriptionMethod(String razorpayPaymentID) {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<CommonResponseModel> addSubscriptionPlanCall = apiInterface.postAddSubscriptionMethod(appPreferencesShared.getUserId(),
                subscriptionPlan.getSubscriptionPlanId(), razorpayPaymentID);

        addSubscriptionPlanCall.enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(mContext, "Subscribed Successfully", Toast.LENGTH_SHORT).show();
                        appPreferencesShared.isRecruiterPaymentSuccessful(true);
                        finish();
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    e.getMessage();
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}