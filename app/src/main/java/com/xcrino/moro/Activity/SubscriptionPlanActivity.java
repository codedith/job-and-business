package com.xcrino.moro.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.Adapter.SubscriptionPlanAdapter;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.SubscriptionPlan;
import com.xcrino.moro.PojoModels.SubscriptionPlanResponse;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.NetworkStatus;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriptionPlanActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    RecyclerView plan_list;
    private ImageView back_arrow;
    private TextView toolbar_title, plan_listNotFound;
    private AppPreferencesShared appPreferencesShared;
    private SubscriptionPlanAdapter subscriptionPlanAdapter;
    private List<SubscriptionPlan> subscriptionPlanList;

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
        setContentView(R.layout.activity_subscription_plan);
        mContext = this;

        getUIInit();

        toolbar_title.setText("Subscription Plan");

        back_arrow.setOnClickListener(this);
    }

    private void getBusinessModulePlans() {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<SubscriptionPlanResponse> subscriptionPlanResponseCall = apiInterface.getBusinessModulePlans();
        subscriptionPlanResponseCall.enqueue(new Callback<SubscriptionPlanResponse>() {
            @Override
            public void onResponse(Call<SubscriptionPlanResponse> call, Response<SubscriptionPlanResponse> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body().getData() != null) {
                            subscriptionPlanList = response.body().getData();
                            plan_list.setVisibility(View.VISIBLE);
                            plan_listNotFound.setVisibility(View.GONE);
                            setSubscriptionPlanDataLayout();
                        } else {
                            plan_list.setVisibility(View.GONE);
                            plan_listNotFound.setVisibility(View.VISIBLE);
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        plan_list.setVisibility(View.GONE);
                        plan_listNotFound.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<SubscriptionPlanResponse> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getEmployeeSubscriptionPlans() {
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<SubscriptionPlanResponse> subscriptionPlanResponseCall = apiInterface.getEmployeeSubscriptionPlans("Mogo_api/get_employee_subscription_plans/"
                + appPreferencesShared.getUserId());

        subscriptionPlanResponseCall.enqueue(new Callback<SubscriptionPlanResponse>() {
            @Override
            public void onResponse(Call<SubscriptionPlanResponse> call, Response<SubscriptionPlanResponse> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body().getData() != null) {
                            subscriptionPlanList = response.body().getData();
                            plan_list.setVisibility(View.VISIBLE);
                            plan_listNotFound.setVisibility(View.GONE);
                            setSubscriptionPlanDataLayout();
                        } else {
                            plan_list.setVisibility(View.GONE);
                            plan_listNotFound.setVisibility(View.VISIBLE);
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        plan_list.setVisibility(View.GONE);
                        plan_listNotFound.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<SubscriptionPlanResponse> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void getSubscriptionPlanMethod() {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<SubscriptionPlanResponse> stringCall = apiInterface.getSubscriptionPlanMethod("Mogo_api/get_subscription_plan_employers/"
                + appPreferencesShared.getUserId().toString());
        stringCall.enqueue(new Callback<SubscriptionPlanResponse>() {
            @Override
            public void onResponse(Call<SubscriptionPlanResponse> call, Response<SubscriptionPlanResponse> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body().getData() != null) {
                            subscriptionPlanList = response.body().getData();
                            plan_list.setVisibility(View.VISIBLE);
                            plan_listNotFound.setVisibility(View.GONE);
                            setSubscriptionPlanDataLayout();
                        } else {
                            plan_list.setVisibility(View.GONE);
                            plan_listNotFound.setVisibility(View.VISIBLE);
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        plan_list.setVisibility(View.GONE);
                        plan_listNotFound.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<SubscriptionPlanResponse> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUIInit() {
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        plan_listNotFound = (TextView) findViewById(R.id.plan_listNotFound);
        plan_list = (RecyclerView) findViewById(R.id.plan_list);
    }

    private void setSubscriptionPlanDataLayout() {
        subscriptionPlanAdapter = new SubscriptionPlanAdapter(mContext, subscriptionPlanList);
        LinearLayoutManager linearHorizontal = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        plan_list.setLayoutManager(linearHorizontal);
        plan_list.setAdapter(subscriptionPlanAdapter);
        plan_list.setHasFixedSize(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkStatus.isNetworkAvailable(mContext)) {
            switch (appPreferencesShared.getSubscriptionDirection()) {
                case "Employee":
                    getEmployeeSubscriptionPlans();
                    break;

                case "Recruiter":
                    getSubscriptionPlanMethod();
                    break;

                case "Business":
                    getBusinessModulePlans();
                    break;
            }
        } else {
            Toast.makeText(mContext, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }
}