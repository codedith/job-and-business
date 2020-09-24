package com.xcrino.moro.JobModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xcrino.moro.Activity.CashWithdrawalActivity;
import com.xcrino.moro.Activity.SubscriptionPlanActivity;
import com.xcrino.moro.Adapter.SubsriptionAdapter;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.RecruiterSubscriptionWallet;
import com.xcrino.moro.PojoModels.UpcomingSubscription;
import com.xcrino.moro.PojoModels.UserProfile;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.NetworkStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecruiterSubscriptionWalletActivity extends AppCompatActivity implements View.OnClickListener {

    private AppPreferencesShared appPreferencesShared;
    private Context mContext;
    private TextView toolbar_title, up_notfound, amount, no_subscription;
    private ImageView back_arrow;
    private Button upgradeYourPlan, withdrawAmount;
    private RecyclerView recycler_view_plans;
    private TextView domestic_plan_purchased_amount, domestic_total_applicable_credits, domestic_total_consumed_credits, domestic_total_remaining_credits, domestic_plan_activation_date, domestic_plan_expiry_date;
    private TextView international_plan_purchased_amount, international_total_applicable_credits, international_total_consumed_credits, international_total_remaining_credits, international_plan_activation_date, international_plan_expiry_date;
    private List<UpcomingSubscription> upcomingSubscriptions;
    private LinearLayoutManager linearLayoutManager;
    private SubsriptionAdapter subsriptionAdapter;
    private RecruiterSubscriptionWallet recruiterSubscriptionWallet;
    LinearLayout plan_layout, subscribed;
    UserProfile userProfile;

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
        setContentView(R.layout.activity_recruiter_subscription_wallet);
        mContext = this;
        getInitUi();
        toolbar_title.setText("Recruiter Subscription Wallet");

        back_arrow.setOnClickListener(this);
        upgradeYourPlan.setOnClickListener(this);
        withdrawAmount.setOnClickListener(this);

        Gson gSonUserProfile = new Gson();
        String jsonUserProfile = appPreferencesShared.getUserDetails();
        userProfile = gSonUserProfile.fromJson(jsonUserProfile, UserProfile.class);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getRecruiterSubscriptionWalletMethod();
        } else {
            Toast.makeText(mContext, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void getInitUi() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        recycler_view_plans = (RecyclerView) findViewById(R.id.recycler_view_plans);
        amount = (TextView) findViewById(R.id.amount);
        domestic_plan_purchased_amount = findViewById(R.id.domestic_plan_purchased_amount);
        domestic_total_applicable_credits = findViewById(R.id.domestic_total_applicable_credits);
        domestic_total_consumed_credits = findViewById(R.id.domestic_total_consumed_credits);
        domestic_total_remaining_credits = findViewById(R.id.domestic_total_remaining_credits);
        domestic_plan_activation_date = findViewById(R.id.domestic_plan_activation_date);
        domestic_plan_expiry_date = findViewById(R.id.domestic_plan_expiry_date);
        international_plan_purchased_amount = findViewById(R.id.international_plan_purchased_amount);
        international_total_applicable_credits = findViewById(R.id.international_total_applicable_credits);
        international_total_consumed_credits = findViewById(R.id.international_total_consumed_credits);
        international_total_remaining_credits = findViewById(R.id.international_total_remaining_credits);
        international_plan_activation_date = findViewById(R.id.international_plan_activation_date);
        international_plan_expiry_date = findViewById(R.id.international_plan_expiry_date);
        up_notfound = (TextView) findViewById(R.id.up_notfound);
        upgradeYourPlan = (Button) findViewById(R.id.upgradeYourPlan);
        withdrawAmount = (Button) findViewById(R.id.withdrawAmount);
        plan_layout = findViewById(R.id.plan_layout);
        subscribed = findViewById(R.id.subscribed);
        no_subscription = findViewById(R.id.no_subscription);
    }

    private void getRecruiterSubscriptionWalletMethod() {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<RecruiterSubscriptionWallet> RecruiterSubscriptionWalletCall = apiInterface.getRecruiterSubscriptionWalletMethod("Mogo_api/get_company_subscription_details/" + appPreferencesShared.getUserId());
        RecruiterSubscriptionWalletCall.enqueue(new Callback<RecruiterSubscriptionWallet>() {
            @Override
            public void onResponse(Call<RecruiterSubscriptionWallet> call, Response<RecruiterSubscriptionWallet> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getData() != null) {
                                subscribed.setVisibility(View.VISIBLE);
                                no_subscription.setVisibility(View.GONE);
                                if (userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("India") ||
                                        userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("india")) {
                                    amount.setText(response.body().getData().getWallet().getrWalletAmt());
                                    amount.append(" INR");
                                } else {
                                    amount.setText(response.body().getData().getWalletAmt());
                                    amount.append(" USD");
                                    amount.setText(response.body().getData().getWallet().getrWalletId());
                                    amount.append(" USD");
                                }
                                if (response.body().getData().getSubscription() == null &&
                                        response.body().getData().getInternationalSubscription() == null) {
                                    plan_layout.setVisibility(View.GONE);
                                } else {
                                    plan_layout.setVisibility(View.VISIBLE);
                                }
                                if (response.body().getData().getSubscription() != null) {
                                    if (userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("India") ||
                                            userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("india")) {
                                        domestic_plan_purchased_amount.setText(response.body().getData().getSubscription().getPlanAmount() == null ?
                                                "Purchased Amount : --" : "Purchased Amount : " + response.body().getData().getSubscription().getPlanAmount().toString());
                                        domestic_plan_purchased_amount.append(" INR");
                                    } else {
                                        domestic_plan_purchased_amount.setText(response.body().getData().getSubscription().getPlanAmount() == null ?
                                                "Purchased Amount : --" : "Purchased Amount : " + response.body().getData().getSubscription().getPlanAmount().toString());
                                        domestic_plan_purchased_amount.append(" USD");
                                    }
                                    domestic_total_applicable_credits.setText(response.body().getData().getSubscription().getTotalApplicable() == null ?
                                            "Total Credits : --" : "Total Credits : " + response.body().getData().getSubscription().getTotalApplicable().toString());
                                    domestic_total_consumed_credits.setText(response.body().getData().getSubscription().getTotalConsumed() == null ?
                                            "Consumed Credits : --" : "Consumed Credits : " + Integer.toString(response.body().getData().getSubscription().getTotalConsumed()));
                                    domestic_total_remaining_credits.setText(response.body().getData().getSubscription().getTotalRemaining() == null ?
                                            "Remaining Credits : --" : "Remaining Credits : " + Integer.toString(response.body().getData().getSubscription().getTotalRemaining()));
                                    domestic_plan_activation_date.setText("Activation Date :");
                                    domestic_plan_activation_date.append("\n");
                                    domestic_plan_expiry_date.setText("Expiry Date : ");
                                    domestic_plan_expiry_date.append("\n");

                                    String inputPattern = "yyyy-MM-dd HH:mm:ss";
                                    String outputPattern = "dd-MM-yyyy";
                                    SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                    SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

                                    Date date = null, date1 = null;
                                    String str = null, str1 = null;

                                    try {
                                        if (response.body().getData().getSubscription().getValidFrom() != null) {
                                            date = inputFormat.parse(response.body().getData().getSubscription().getValidFrom().toString());
                                            str = outputFormat.format(date);
                                            domestic_plan_activation_date.append(str);
                                        }
                                        else {
                                            domestic_plan_activation_date.append("--");
                                        }
                                        if (response.body().getData().getSubscription().getValidUpto() != null) {
                                            date1 = inputFormat.parse(response.body().getData().getSubscription().getValidUpto().toString());
                                            str1 = outputFormat.format(date1);
                                            domestic_plan_expiry_date.append(str1);
                                        }
                                        else {
                                            domestic_plan_expiry_date.append("--");
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    domestic_plan_purchased_amount.setText("Purchased Amount : --");
                                    domestic_plan_purchased_amount.append("\n");
                                    domestic_plan_purchased_amount.append("--");
                                    domestic_total_applicable_credits.setText("Total Credits : --");
                                    domestic_total_applicable_credits.append("--");
                                    domestic_total_consumed_credits.setText("Consumed Credits : --");
                                    domestic_total_consumed_credits.append("--");
                                    domestic_total_remaining_credits.setText("Remaining Credits : --");
                                    domestic_total_remaining_credits.append("--");
                                    domestic_plan_activation_date.setText("Activation Date : --");
                                    domestic_plan_activation_date.append("\n");
                                    domestic_plan_activation_date.append("--");
                                    domestic_plan_expiry_date.setText("Expiry Date : --");
                                    domestic_plan_expiry_date.append("\n");
                                    domestic_plan_expiry_date.append("--");
                                }
                                if (response.body().getData().getInternationalSubscription() != null) {
                                    if (userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("India") ||
                                            userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("india")) {
                                        international_plan_purchased_amount.setText(response.body().getData().getInternationalSubscription().getPlanAmount() == null ?
                                                "Purchased Amount : --" : "Purchased Amount : " + response.body().getData().getInternationalSubscription().getPlanAmount().toString());
                                        international_plan_purchased_amount.append(" INR");
                                    } else {
                                        international_plan_purchased_amount.setText(response.body().getData().getInternationalSubscription().getPlanAmount() == null ?
                                                "Purchased Amount : --" : "Purchased Amount : " + response.body().getData().getInternationalSubscription().getPlanAmount().toString());
                                        international_plan_purchased_amount.append(" USD");
                                    }
                                    international_total_applicable_credits.setText(response.body().getData().getInternationalSubscription().getTotalApplicable() == null ?
                                            "Total Credits : --" : "Total Credits : " + response.body().getData().getInternationalSubscription().getTotalApplicable().toString());
                                    international_total_consumed_credits.setText(response.body().getData().getInternationalSubscription().getTotalConsumed() == null ?
                                            "Consumed Credits : --" : "Consumed Credits : " + Integer.toString(response.body().getData().getInternationalSubscription().getTotalConsumed()));
                                    international_total_remaining_credits.setText(response.body().getData().getInternationalSubscription().getTotalRemaining() == null ?
                                            "Remaining Credits : --" : "Remaining Credits : " + Integer.toString(response.body().getData().getInternationalSubscription().getTotalRemaining()));
                                    international_plan_activation_date.setText("Activation Date :");
                                    international_plan_activation_date.append("\n");
                                    international_plan_expiry_date.setText("Expiry Date : ");
                                    international_plan_expiry_date.append("\n");

                                    String inputPattern = "yyyy-MM-dd HH:mm:ss";
                                    String outputPattern = "dd-MM-yyyy";
                                    SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                    SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

                                    Date date = null, date1 = null;
                                    String str = null, str1 = null;

                                    try {
                                        if (response.body().getData().getInternationalSubscription().getValidFrom() != null) {
                                            date = inputFormat.parse(response.body().getData().getInternationalSubscription().getValidFrom().toString());
                                            str = outputFormat.format(date);
                                            international_plan_activation_date.append(str);
                                        }
                                        else {
                                            international_plan_activation_date.append("--");
                                        }
                                        if (response.body().getData().getInternationalSubscription().getValidUpto() != null) {
                                            date1 = inputFormat.parse(response.body().getData().getInternationalSubscription().getValidUpto().toString());
                                            str1 = outputFormat.format(date1);
                                            international_plan_expiry_date.append(str1);
                                        }
                                        else {
                                            international_plan_expiry_date.append("--");
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    international_plan_purchased_amount.setText("Purchased Amount : ");
                                    international_plan_purchased_amount.append("\n");
                                    international_plan_purchased_amount.append("--");
                                    international_total_applicable_credits.setText("Total Credits : ");
                                    international_total_applicable_credits.append("--");
                                    international_total_consumed_credits.setText("Consumed Credits : ");
                                    international_total_consumed_credits.append("--");
                                    international_total_remaining_credits.setText("Remaining Credits : ");
                                    international_total_remaining_credits.append("--");
                                    international_plan_activation_date.setText("Activation Date : ");
                                    international_plan_activation_date.append("\n");
                                    international_plan_activation_date.append("--");
                                    international_plan_expiry_date.setText("Expiry Date : ");
                                    international_plan_expiry_date.append("\n");
                                    international_plan_expiry_date.append("--");
                                }
                                if (response.body().getData().getUpcomingSubscriptions() != null) {
                                    recycler_view_plans.setVisibility(View.VISIBLE);
                                    up_notfound.setVisibility(View.GONE);
                                    recycler_view_plans.setAdapter(new SubsriptionAdapter(mContext, response.body().getData().getUpcomingSubscriptions()));
                                    recycler_view_plans.setLayoutManager(new LinearLayoutManager(mContext));
                                    recycler_view_plans.setHasFixedSize(true);
                                }
                                else {
                                    recycler_view_plans.setVisibility(View.GONE);
                                    up_notfound.setVisibility(View.VISIBLE);
                                }
                            } else {
                                if (userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("India") ||
                                        userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("india")) {
                                    amount.setText("00 INR");
                                } else {
                                    amount.setText("00 USD");
                                }
                                subscribed.setVisibility(View.GONE);
                                no_subscription.setVisibility(View.VISIBLE);
                            }
                        } else {
                            if (userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("India") ||
                                    userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("india")) {
                                amount.setText("00 INR");
                            } else {
                                amount.setText("00 USD");
                            }
                            subscribed.setVisibility(View.GONE);
                            no_subscription.setVisibility(View.VISIBLE);
                        }
                    }

                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    subscribed.setVisibility(View.GONE);
                    no_subscription.setVisibility(View.VISIBLE);
                    Toast.makeText(mContext, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RecruiterSubscriptionWallet> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                subscribed.setVisibility(View.GONE);
                no_subscription.setVisibility(View.VISIBLE);
                Toast.makeText(mContext, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;

            case R.id.upgradeYourPlan:
                startActivity(new Intent(mContext, SubscriptionPlanActivity.class));
                appPreferencesShared.setSubscriptionDirection("Recruiter");
                break;

            case R.id.withdrawAmount:
                startActivity(new Intent(mContext, CashWithdrawalActivity.class));
//                Toast.makeText(mContext, "Withdraw Amount", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
