package com.xcrino.moro.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.xcrino.moro.Adapter.CampaignAdapter;
import com.xcrino.moro.BusinessModules.BusinessCreateProfileActivity;
import com.xcrino.moro.BusinessModules.CampaignModeActivity;
import com.xcrino.moro.BusinessModules.BusinessCompanyDetailsActivity;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.BusinessModuleDetailsResponse;
import com.xcrino.moro.PojoModels.Campaign;
import com.xcrino.moro.PojoModels.UserProfile;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.NetworkStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessFragment extends Fragment implements View.OnClickListener {

    Context mContext;
    private Spinner bss_type_spinner;
    private RelativeLayout parentLayout, newUsersBusiness, oldUsersBusiness;
    private LinearLayout companyName_Category;
    private Button boost_now;
    private String[] accType = {"Select", "Individual", "Business"};
    private String type = "Select";
    private AppPreferencesShared appPreferencesShared;
    private PopupWindow mPopupWindow;
    private FloatingActionButton add_campaign;
    private TextView companyName, businessCategoryName, not_available;
    private UserProfile userProfile;
    RecyclerView recycler_view;
    List<Campaign> campaigns = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        appPreferencesShared = new AppPreferencesShared(getActivity());
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
        View view = inflater.inflate(R.layout.fragment_business, container, false);
        mContext = getContext();

        parentLayout = (RelativeLayout) view.findViewById(R.id.parentLayout);
        newUsersBusiness = (RelativeLayout) view.findViewById(R.id.newUsersBusiness);
        oldUsersBusiness = (RelativeLayout) view.findViewById(R.id.oldUsersBusiness);

        boost_now = (Button) view.findViewById(R.id.boost_now);
        add_campaign = (FloatingActionButton) view.findViewById(R.id.add_campaign);
        companyName_Category = (LinearLayout) view.findViewById(R.id.companyName_Category);
        companyName = (TextView) view.findViewById(R.id.companyName);
        businessCategoryName = (TextView) view.findViewById(R.id.businessCategoryName);
        recycler_view = view.findViewById(R.id.recycler_view);
        not_available = view.findViewById(R.id.not_available);

        Gson gsonUserProfile = new Gson();
        String userDetailJson = appPreferencesShared.getUserDetails();
        userProfile = gsonUserProfile.fromJson(userDetailJson, UserProfile.class);

        if (userProfile.getUserDatum().getBusiness_profile() == 0) {
            newUsersBusiness.setVisibility(View.VISIBLE);
            oldUsersBusiness.setVisibility(View.GONE);
        }
        else {
            appPreferencesShared.setFragmentDirection("");
            newUsersBusiness.setVisibility(View.GONE);
            oldUsersBusiness.setVisibility(View.VISIBLE);
            if (NetworkStatus.isNetworkAvailable(mContext)) {
                getBusinessCompanyProfileDetails();
            } else {
                Toast.makeText(mContext, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
            }
        }

        boost_now.setOnClickListener(this);
        companyName_Category.setOnClickListener(this);
        add_campaign.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (userProfile.getUserDatum().getBusiness_profile() == 0) {
            newUsersBusiness.setVisibility(View.VISIBLE);
            oldUsersBusiness.setVisibility(View.GONE);
        }
        else {
            appPreferencesShared.setFragmentDirection("");
            newUsersBusiness.setVisibility(View.GONE);
            oldUsersBusiness.setVisibility(View.VISIBLE);
            if (NetworkStatus.isNetworkAvailable(mContext)) {
                getBusinessCompanyProfileDetails();
            } else {
                Toast.makeText(mContext, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.boost_now:
                startActivity(new Intent(mContext, BusinessCreateProfileActivity.class));
                break;

            case R.id.companyName_Category:
                startActivity(new Intent(mContext, BusinessCompanyDetailsActivity.class));
                break;

            case R.id.add_campaign:
                startActivity(new Intent(mContext, CampaignModeActivity.class));
        }
    }

    private void getBusinessCompanyProfileDetails() {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<BusinessModuleDetailsResponse> businessModuleDetailsResponseCall = apiInterface.
                getBusinessCompanyProfileDetails("Business-module-api/get-business-profile/" + appPreferencesShared.getUserId());

        businessModuleDetailsResponseCall.enqueue(new Callback<BusinessModuleDetailsResponse>() {
            @Override
            public void onResponse(Call<BusinessModuleDetailsResponse> call, Response<BusinessModuleDetailsResponse> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getBusinessModuleData().getCompanyDetails() != null) {
                                companyName.setText(response.body().getBusinessModuleData().getCompanyDetails().getUbsName() == null ? "Not Provided" : response.body().getBusinessModuleData().getCompanyDetails().getUbsName().toString());
                                businessCategoryName.setText(response.body().getBusinessModuleData().getCompanyDetails().getUbsCategory() == null ? "Not Provided" : response.body().getBusinessModuleData().getCompanyDetails().getUbsCategory().toString());
                            }

                            if (response.body().getBusinessModuleData().getCampaigns() != null) {
                                recycler_view.setVisibility(View.VISIBLE);
                                not_available.setVisibility(View.GONE);
                                campaigns = response.body().getBusinessModuleData().getCampaigns();
                                recycler_view.setAdapter(new CampaignAdapter(mContext, campaigns));
                                recycler_view.setLayoutManager(new LinearLayoutManager(mContext));
                                recycler_view.setHasFixedSize(true);
                            }
                            else {
                                recycler_view.setVisibility(View.GONE);
                                not_available.setVisibility(View.VISIBLE);
                            }
                        }
                        else {
                            Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception e) {
                    e.getMessage();
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BusinessModuleDetailsResponse> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
