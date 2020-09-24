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
import com.xcrino.moro.Activity.SubscriptionPlanActivity;
import com.xcrino.moro.Adapter.SubsriptionAdapter;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.EmployeeSubscriptionWallet;
import com.xcrino.moro.PojoModels.UserProfile;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.NetworkStatus;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeSubscriptionActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView toolbar_title;
    private ImageView back_arrow;
    private Context mContext;
    private AppPreferencesShared appPreferencesShared;
    private Button upgradePlan_button;
    private RecyclerView recycler_view_plans;
    private TextView preDomesticPurchasedAmount, preDomesticApplicableCredits, preDomesticConsumedCredits, preDomesticRemainingCredits,
            preDomesticActivationDate, preInternationalPurchasedAmount, preInternationalApplicableCredit, preInternationalConsumedCredit,
            preInternationalRemainingCredit, preInternationalActivationDate;
    private TextView fdp_applicablecredits, fdp_consumedcredits, fdp_remainingcredits,
            fip_totalapplicablecredits, fip_consumedcredits, fip_remainingcredits, not_available;
    private LinearLayout premium_layout, free_layout;
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
        setContentView(R.layout.activity_employee_subscription);

        mContext = this;
        getLayoutUiId();

        toolbar_title.setText("Employee Subscription Plan");
        back_arrow.setOnClickListener(this);
        upgradePlan_button.setOnClickListener(this);

        Gson gsonUserProfile = new Gson();
        String json = appPreferencesShared.getUserDetails();
        userProfile = gsonUserProfile.fromJson(json, UserProfile.class);
    }

    private void getLayoutUiId() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        upgradePlan_button = (Button) findViewById(R.id.upgradePlan_button);
        recycler_view_plans = (RecyclerView) findViewById(R.id.recycler_view_plans);
        fdp_applicablecredits = (TextView) findViewById(R.id.fdp_applicablecredits);
        fdp_consumedcredits = (TextView) findViewById(R.id.fdp_consumedcredits);
        fdp_remainingcredits = (TextView) findViewById(R.id.fdp_remainingcredits);
        fip_totalapplicablecredits = (TextView) findViewById(R.id.fip_totalapplicablecredits);
        fip_consumedcredits = (TextView) findViewById(R.id.fip_consumedcredits);
        fip_remainingcredits = (TextView) findViewById(R.id.fip_remainingcredits);
        preDomesticPurchasedAmount = (TextView) findViewById(R.id.preDomesticPurchasedAmount);
        preDomesticApplicableCredits = (TextView) findViewById(R.id.preDomesticApplicableCredits);
        preDomesticConsumedCredits = (TextView) findViewById(R.id.preDomesticConsumedCredits);
        preDomesticRemainingCredits = (TextView) findViewById(R.id.preDomesticRemainingCredits);
        preDomesticActivationDate = (TextView) findViewById(R.id.preDomesticActivationDate);
        preInternationalPurchasedAmount = (TextView) findViewById(R.id.preInternationalPurchasedAmount);
        preInternationalApplicableCredit = (TextView) findViewById(R.id.preInternationalApplicableCredit);
        preInternationalConsumedCredit = (TextView) findViewById(R.id.preInternationalConsumedCredit);
        preInternationalRemainingCredit = (TextView) findViewById(R.id.preInternationalRemainingCredit);
        preInternationalActivationDate = (TextView) findViewById(R.id.preInternationalActivationDate);
        not_available = findViewById(R.id.not_available);
        premium_layout = findViewById(R.id.premium_layout);
        free_layout = findViewById(R.id.free_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getEmployeeSubscriptionWalletMethod();
        } else {
            Toast.makeText(mContext, "Please Check Your Internet Connection ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;

            case R.id.upgradePlan_button:
                startActivity(new Intent(mContext, SubscriptionPlanActivity.class));
                appPreferencesShared.setSubscriptionDirection("Employee");
                break;

        }
    }

    private void getEmployeeSubscriptionWalletMethod() {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<EmployeeSubscriptionWallet> EmployeeSubscriptionWalletCall = apiInterface.getEmployeeSubscriptionWalletMethod("Mogo_api/get_employee_subscription_details/" + appPreferencesShared.getUserId());
        EmployeeSubscriptionWalletCall.enqueue(new Callback<EmployeeSubscriptionWallet>() {
            @Override
            public void onResponse(Call<EmployeeSubscriptionWallet> call, Response<EmployeeSubscriptionWallet> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body().getData() != null) {
                            if (response.body().getData().getSubscriptionDomestic().getPremiumActivePlan().getSubscriptionType() != null ||
                                    response.body().getData().getSubscriptionInternational().getPremiumActivePlan().getSubscriptionType() != null) {
                                premium_layout.setVisibility(View.VISIBLE);
                            }
                            else {
                                premium_layout.setVisibility(View.GONE);
                            }
                            if (response.body().getData().getSubscriptionDomestic().getFreeDomestic().getCurrentFreeDomesticCreditsRemaining() == 0 &&
                                response.body().getData().getSubscriptionInternational().getFreeInternational().getCurrentFreeInternationalCreditsRemaining() == 0) {
                                free_layout.setVisibility(View.GONE);
                            }
                            else {
                                free_layout.setVisibility(View.VISIBLE);
                            }
                            if (response.body().getData().getSubscriptionDomestic().getPremiumActivePlan().getSubscriptionType() != null) {
                                if (userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("India") ||
                                        userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("india")) {
                                    preDomesticPurchasedAmount.setText(response.body().getData().getSubscriptionDomestic().getPremiumActivePlan().getSubscriptionAmt()==null?
                                            "Purchased Amount : --" : "Purchased Amount : " + response.body().getData().getSubscriptionDomestic().getPremiumActivePlan().getSubscriptionAmt().toString());
                                    preDomesticPurchasedAmount.append(" INR");
                                }
                                else {
                                    preDomesticPurchasedAmount.setText(response.body().getData().getSubscriptionDomestic().getPremiumActivePlan().getSubscriptionAmtForeign()==null?
                                            "Purchased Amount : --" : "Purchased Amount : " + response.body().getData().getSubscriptionDomestic().getPremiumActivePlan().getSubscriptionAmtForeign().toString());
                                    preDomesticPurchasedAmount.append(" USD");
                                }
                                preDomesticApplicableCredits.setText(response.body().getData().getSubscriptionDomestic().getPremiumActivePlan().getTotalApplicable()==null?
                                        "Total Credits : --" : "Total Credits : " + Integer.toString(response.body().getData().getSubscriptionDomestic().getPremiumActivePlan().getTotalApplicable()));
                                preDomesticConsumedCredits.setText(response.body().getData().getSubscriptionDomestic().getPremiumActivePlan().getConsumedCredits()==null?
                                        "Consumed Credits : --" : "Consumed Credits : " + Integer.toString(response.body().getData().getSubscriptionDomestic().getPremiumActivePlan().getConsumedCredits()));
                                preDomesticRemainingCredits.setText(response.body().getData().getSubscriptionDomestic().getPremiumActivePlan().getCurrentCredits()==null?
                                        "Remaining Credits : --" : "Remaining Credits : " + Integer.toString(response.body().getData().getSubscriptionDomestic().getPremiumActivePlan().getCurrentCredits()));
                                preDomesticActivationDate.setText("Activation Date :");
                                preDomesticActivationDate.append("\n");
                                preDomesticActivationDate.append(response.body().getData().getSubscriptionDomestic().getPremiumActivePlan().getPlanSubscriptionDate()==null?
                                        "--" : response.body().getData().getSubscriptionDomestic().getPremiumActivePlan().getPlanSubscriptionDate());
                            }
                            else {
                                preDomesticPurchasedAmount.setText("Purchased Amount : --");
                                preDomesticApplicableCredits.setText("Total Credits : --");
                                preDomesticConsumedCredits.setText("Consumed Credits : --");
                                preDomesticRemainingCredits.setText("Remaining Credits : --");
                                preDomesticActivationDate.setText("Activation Date : --");
                            }
                            if (response.body().getData().getSubscriptionInternational().getPremiumActivePlan().getSubscriptionType() != null) {
                                if (userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("India") ||
                                        userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("india")) {
                                    preInternationalPurchasedAmount.setText(response.body().getData().getSubscriptionInternational().getPremiumActivePlan().getSubscriptionAmt()==null?
                                            "Purchased Amount : --" : "Purchased Amount : " + response.body().getData().getSubscriptionInternational().getPremiumActivePlan().getSubscriptionAmt().toString());
                                    preInternationalPurchasedAmount.append(" INR");
                                }
                                else {
                                    preInternationalPurchasedAmount.setText(response.body().getData().getSubscriptionInternational().getPremiumActivePlan().getSubscriptionAmtForeign()==null?
                                            "Purchased Amount : --" : "Purchased Amount : " + response.body().getData().getSubscriptionInternational().getPremiumActivePlan().getSubscriptionAmtForeign().toString());
                                    preInternationalPurchasedAmount.append(" USD");
                                }
                                preInternationalApplicableCredit.setText(response.body().getData().getSubscriptionInternational().getPremiumActivePlan().getTotalApplicable()==null?
                                        "Total Credits : --" : "Total Credits : " + Integer.toString(response.body().getData().getSubscriptionInternational().getPremiumActivePlan().getTotalApplicable()));
                                preInternationalConsumedCredit.setText(response.body().getData().getSubscriptionInternational().getPremiumActivePlan().getConsumedCredits()==null?
                                        "Consumed Credits : --" : "Consumed Credits : " + Integer.toString(response.body().getData().getSubscriptionInternational().getPremiumActivePlan().getConsumedCredits()));
                                preInternationalRemainingCredit.setText(response.body().getData().getSubscriptionInternational().getPremiumActivePlan().getCurrentCredits()==null?
                                        "Remaining Credits : --" : "Remaining Credits : " + Integer.toString(response.body().getData().getSubscriptionInternational().getPremiumActivePlan().getCurrentCredits()));
                                preInternationalActivationDate.setText("Activation Date :");
                                preInternationalActivationDate.append("\n");
                                preInternationalActivationDate.append(response.body().getData().getSubscriptionInternational().getPremiumActivePlan().getPlanSubscriptionDate()==null?
                                        "--" : response.body().getData().getSubscriptionInternational().getPremiumActivePlan().getPlanSubscriptionDate());
                            }
                            else {
                                preInternationalPurchasedAmount.setText("Purchased Amount : --");
                                preInternationalApplicableCredit.setText("Total Credits : --");
                                preInternationalConsumedCredit.setText("Consumed Credits : --");
                                preInternationalRemainingCredit.setText("Remaining Credits : --");
                                preInternationalActivationDate.setText("Activation Date : --");
                            }
                            if (response.body().getData().getSubscriptionDomestic().getFreeDomestic() != null) {
                                fdp_applicablecredits.setText(response.body().getData().getSubscriptionDomestic().getFreeDomestic().getCurrentFreeDomesticCreditsTotal() == null ? "Total Credits : --" : "Total Credits:" + response.body().getData().getSubscriptionDomestic().getFreeDomestic().getCurrentFreeDomesticCreditsTotal().toString());
                                fdp_consumedcredits.setText(response.body().getData().getSubscriptionDomestic().getFreeDomestic().getCurrentFreeDomesticCreditsUsed() == null ? "Consumed Credits : --" : "Consumed Credits:" + response.body().getData().getSubscriptionDomestic().getFreeDomestic().getCurrentFreeDomesticCreditsUsed().toString());
                                fdp_remainingcredits.setText(response.body().getData().getSubscriptionDomestic().getFreeDomestic().getCurrentFreeDomesticCreditsRemaining() == null ? "Remaining Credits :--" : "Remaining Credits:" + response.body().getData().getSubscriptionDomestic().getFreeDomestic().getCurrentFreeDomesticCreditsRemaining().toString());
                            } else {
                                fdp_applicablecredits.setText("Total Credits: --");
                                fdp_consumedcredits.setText(" Consumed Credits: --");
                                fdp_remainingcredits.setText("Remaining Credits: -- ");
                            }
                            if (response.body().getData().getSubscriptionInternational().getFreeInternational() != null) {
                                fip_totalapplicablecredits.setText(response.body().getData().getSubscriptionInternational().getFreeInternational().getCurrentFreeInternationalCreditsTotal() == null ? "Total Credits: --" : "Total Credits:" + response.body().getData().getSubscriptionInternational().getFreeInternational().getCurrentFreeInternationalCreditsTotal().toString());
                                fip_consumedcredits.setText(response.body().getData().getSubscriptionInternational().getFreeInternational().getCurrentFreeInternationalCreditsUsed() == null ? "Consumed Credits: --" : "Consumed Credits:" + response.body().getData().getSubscriptionInternational().getFreeInternational().getCurrentFreeInternationalCreditsUsed().toString());
                                fip_remainingcredits.setText(response.body().getData().getSubscriptionInternational().getFreeInternational().getCurrentFreeInternationalCreditsRemaining() == null ? "Remaining Credits: --" : "Remaining Credits:" + response.body().getData().getSubscriptionInternational().getFreeInternational().getCurrentFreeInternationalCreditsRemaining().toString());
                            } else {
                                fip_totalapplicablecredits.setText("Total Credits: --");
                                fip_consumedcredits.setText("Consumed Credits: --");
                                fip_remainingcredits.setText("Remaining Credits: --");
                            }
                            if (response.body().getData().getUpcomingSubscriptions() != null) {
                                recycler_view_plans.setVisibility(View.VISIBLE);
                                not_available.setVisibility(View.GONE);
                                recycler_view_plans.setAdapter(new SubsriptionAdapter(mContext, response.body().getData().getUpcomingSubscriptions()));
                                recycler_view_plans.setLayoutManager(new LinearLayoutManager(mContext));
                                recycler_view_plans.setHasFixedSize(true);
                            }
                            else {
                                recycler_view_plans.setVisibility(View.GONE);
                                not_available.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(mContext, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EmployeeSubscriptionWallet> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(mContext, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
