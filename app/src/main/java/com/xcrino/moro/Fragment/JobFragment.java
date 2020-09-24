package com.xcrino.moro.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.xcrino.moro.JobModule.ApplyJobActivity;
import com.xcrino.moro.JobModule.CandidateSearchActivity;
import com.xcrino.moro.JobModule.EmployeeAppliedActivity;
import com.xcrino.moro.JobModule.EmployeeEditProfileActivity;
import com.xcrino.moro.JobModule.EmployeeProfileDetailsActivity;
import com.xcrino.moro.JobModule.EmployeeRecruiterInterestActivity;
import com.xcrino.moro.JobModule.EmployeeSavedJobActivity;
import com.xcrino.moro.JobModule.HotJobActivity;
import com.xcrino.moro.JobModule.NewJobSearchActivity;
import com.xcrino.moro.JobModule.RecommendedJobActivity;
import com.xcrino.moro.JobModule.RecruiterAddJobActivity;
import com.xcrino.moro.JobModule.RecruiterAppReceivedActivity;
import com.xcrino.moro.JobModule.RecruiterCompanyProfileActivity;
import com.xcrino.moro.JobModule.RecruiterPostedJobActivity;
import com.xcrino.moro.JobModule.RecruiterProfileDetailsActivity;
import com.xcrino.moro.JobModule.RecruiterSavedCandidateActivity;
import com.xcrino.moro.PojoModels.GetCompanyDetailsModel;
import com.xcrino.moro.PojoModels.UserProfile;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.AppPreferencesShared;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class JobFragment extends Fragment implements View.OnClickListener {

    Context mContext;
    TextView new_apply_job_btn, recommended_jobs, saved_jobs, applied_jobs, recruiter_interest,
            new_post_job_btn, add_jobs, saved_candidates, posted_jobs, applications_received;
    AppPreferencesShared appPreferencesShared;
    FloatingActionButton job_search, candidate_search;
    LinearLayout candidate_info, company_info, new_user_layout, apply_jobs, hot_jobs;
    RelativeLayout old_user_employee, old_user_employer;
    TextView candidate_name, candidate_company, candidate_designation_experience, candidate_location_dob, company_name, company_location;
    ImageView candidate_img;
    ImageView company_logo_oldemployer;
    UserProfile userProfile;
    private GetCompanyDetailsModel companyDetailsModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job, container, false);

        mContext = getContext();
        // Inflate the layout for this fragment
        appPreferencesShared = new AppPreferencesShared(mContext);
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

        getLayoutInitUI(view);
        Gson gsonUserProfile = new Gson();
        String json = appPreferencesShared.getUserDetails();
        userProfile = gsonUserProfile.fromJson(json, UserProfile.class);

        Gson gson = new Gson();
        String json1 = appPreferencesShared.getCompanyDetails();
        companyDetailsModel = gson.fromJson(json1, GetCompanyDetailsModel.class);

        CheckEmpOrRecruiter();

        new_apply_job_btn.setOnClickListener(this);
        recommended_jobs.setOnClickListener(this);
        saved_jobs.setOnClickListener(this);
        job_search.setOnClickListener(this);
        applied_jobs.setOnClickListener(this);
        recruiter_interest.setOnClickListener(this);
        candidate_info.setOnClickListener(this);
        new_post_job_btn.setOnClickListener(this);
        company_info.setOnClickListener(this);
        add_jobs.setOnClickListener(this);
        saved_candidates.setOnClickListener(this);
        posted_jobs.setOnClickListener(this);
        applications_received.setOnClickListener(this);
        candidate_search.setOnClickListener(this);
        hot_jobs.setOnClickListener(this);
        apply_jobs.setOnClickListener(this);

        return view;
    }

    private void getLayoutInitUI(View view) {
        new_apply_job_btn = view.findViewById(R.id.new_apply_job_btn);
        recommended_jobs = view.findViewById(R.id.recommended_jobs);
        saved_jobs = view.findViewById(R.id.saved_jobs);
        job_search = view.findViewById(R.id.job_search);
        applied_jobs = view.findViewById(R.id.applied_jobs);
        recruiter_interest = view.findViewById(R.id.recruiter_interest);
        candidate_info = view.findViewById(R.id.candidate_info);
        new_post_job_btn = view.findViewById(R.id.new_post_job_btn);
        company_info = view.findViewById(R.id.company_info);
        add_jobs = view.findViewById(R.id.add_jobs);
        saved_candidates = view.findViewById(R.id.saved_candidates);
        posted_jobs = view.findViewById(R.id.posted_jobs);
        applications_received = view.findViewById(R.id.applications_received);
        candidate_search = view.findViewById(R.id.candidate_search);
        new_user_layout = view.findViewById(R.id.new_user_layout);
        old_user_employee = view.findViewById(R.id.old_user_employee);
        old_user_employer = view.findViewById(R.id.old_user_employer);
        apply_jobs = view.findViewById(R.id.apply_jobs);
        hot_jobs = view.findViewById(R.id.hot_jobs);
        candidate_name = view.findViewById(R.id.candidate_name);
        candidate_company = view.findViewById(R.id.candidate_company);
        candidate_designation_experience = view.findViewById(R.id.candidate_designation_experience);
        candidate_location_dob = view.findViewById(R.id.candidate_location_dob);
        candidate_img = view.findViewById(R.id.candidate_img);
        company_name = view.findViewById(R.id.company_name);
        company_location = view.findViewById(R.id.company_location);
        company_logo_oldemployer = view.findViewById(R.id.company_logo_oldemployer);


    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.new_apply_job_btn:
                intent = new Intent(mContext, EmployeeEditProfileActivity.class);
                startActivity(intent);
                ((Activity) mContext).finish();
                break;

            case R.id.job_search:
                appPreferencesShared.setPageDirection("Job Search");
                intent = new Intent(mContext, NewJobSearchActivity.class);
                startActivity(intent);
                break;

            case R.id.recommended_jobs:
//                appPreferencesShared.setPageDirection("Recommended Jobs");
                intent = new Intent(mContext, RecommendedJobActivity.class);
                startActivity(intent);
                break;

            case R.id.saved_jobs:
                appPreferencesShared.setPageDirection("Saved Jobs");
                intent = new Intent(mContext, EmployeeSavedJobActivity.class);
                startActivity(intent);
                break;

            case R.id.apply_jobs:
//                appPreferencesShared.setPageDirection("Apply Jobs");
                intent = new Intent(mContext, ApplyJobActivity.class);
                startActivity(intent);
                break;

            case R.id.hot_jobs:
                // appPreferencesShared.setPageDirection("Hot Jobs");
                intent = new Intent(mContext, HotJobActivity.class);
                startActivity(intent);
                break;

            case R.id.applied_jobs:
                appPreferencesShared.setPageDirection("Applied Job");
                intent = new Intent(mContext, EmployeeAppliedActivity.class);
                startActivity(intent);
                break;

            case R.id.recruiter_interest:
                appPreferencesShared.setPageDirection("Recommended");
                intent = new Intent(mContext, EmployeeRecruiterInterestActivity.class);
                startActivity(intent);
                break;

            case R.id.candidate_info:
                intent = new Intent(mContext, EmployeeProfileDetailsActivity.class);
                startActivity(intent);
                break;

            case R.id.new_post_job_btn:
//                appPreferencesShared.setFragmentDirection("Job");
                intent = new Intent(mContext, RecruiterCompanyProfileActivity.class);
                appPreferencesShared.setCreateCompanyProfile(false);
                startActivity(intent);
                ((Activity) mContext).finish();
                break;

            case R.id.company_info:
                intent = new Intent(mContext, RecruiterProfileDetailsActivity.class);
                startActivity(intent);
                break;

            case R.id.add_jobs:
                intent = new Intent(mContext, RecruiterAddJobActivity.class);
                appPreferencesShared.setRecruiterAddUpdateJob(false);
                startActivity(intent);
                break;

            case R.id.saved_candidates:
                intent = new Intent(mContext, RecruiterSavedCandidateActivity.class);
                startActivity(intent);
                break;

            case R.id.posted_jobs:
                appPreferencesShared.setPageDirection("Posted Jobs");
                intent = new Intent(mContext, RecruiterPostedJobActivity.class);
                startActivity(intent);
                break;

            case R.id.applications_received:
                intent = new Intent(mContext, RecruiterAppReceivedActivity.class);
                startActivity(intent);
                break;

            case R.id.candidate_search:
               appPreferencesShared.setPageDirection("Candidate Search");
                intent = new Intent(mContext, CandidateSearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void CheckEmpOrRecruiter() {
        switch (userProfile.getUserDatum().getUserType()) {
            case 0:
                new_user_layout.setVisibility(View.VISIBLE);
                old_user_employee.setVisibility(View.GONE);
                old_user_employer.setVisibility(View.GONE);
                break;

            case 1:
                appPreferencesShared.setFragmentDirection("");
                new_user_layout.setVisibility(View.GONE);
                old_user_employee.setVisibility(View.GONE);
                old_user_employer.setVisibility(View.VISIBLE);
                company_name.setText(userProfile.getUserDatum().getUserProfileData().getCompanyName() == null ?
                        "Not Provided" : userProfile.getUserDatum().getUserProfileData().getCompanyName().toString());
                company_location.setText(userProfile.getUserDatum().getUserProfileData().getCountry() == null ? "Not Provided" : userProfile.getUserDatum().getUserProfileData().getCountry().toString());
                company_location.append(", ");
                company_location.append(userProfile.getUserDatum().getUserProfileData().getState() == null ? "Not Provided" : userProfile.getUserDatum().getUserProfileData().getState().toString());
                if (userProfile.getUserDatum().getUserProfileData().getCompanyLogo() != null) {
                    Picasso.with(getContext()).load(userProfile.getUserDatum().getUserProfileData().getCompanyLogo().toString()).resize(185, 185).transform(new CropCircleTransformation()).placeholder(R.drawable.user_dummy).into(company_logo_oldemployer);
                }

                break;

            case 2:
                appPreferencesShared.setFragmentDirection("");
                new_user_layout.setVisibility(View.GONE);
                old_user_employee.setVisibility(View.VISIBLE);
                old_user_employer.setVisibility(View.GONE);
                candidate_name.setText(userProfile.getUserDatum().getUserProfileData().getEmployeeName() == null ? "Not Provided" : userProfile.getUserDatum().getUserProfileData().getEmployeeName().toString());
                candidate_company.setText(userProfile.getUserDatum().getUserProfileData().getEmployeeLastCompanyName() == null ? "Not Provided" : userProfile.getUserDatum().getUserProfileData().getEmployeeLastCompanyName().toString());
                candidate_designation_experience.setText(userProfile.getUserDatum().getUserProfileData().getEmployeeDesignation() == null ? "Not Provided" : userProfile.getUserDatum().getUserProfileData().getEmployeeDesignation().toString());
                candidate_designation_experience.append(", ");
                candidate_designation_experience.append(userProfile.getUserDatum().getUserProfileData().getEmployeeJobExp() == null ? "" : userProfile.getUserDatum().getUserProfileData().getEmployeeJobExp().toString());
                candidate_location_dob.setText(userProfile.getUserDatum().getUserProfileData().getEmployeeCountry() == null ? "Not Provided" : userProfile.getUserDatum().getUserProfileData().getEmployeeCountry().toString());
                candidate_location_dob.append(", ");
                candidate_location_dob.append(userProfile.getUserDatum().getUserProfileData().getEmployeeState() == null ? "" : userProfile.getUserDatum().getUserProfileData().getEmployeeState().toString());
                candidate_location_dob.append(", ");

                if (userProfile.getUserDatum().getUserProfileData().getEmployeeDob() != null) {
                    String inputPattern = "yyyy-MM-dd";
                    String outputPattern = "dd-MM-yyyy";
                    SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                    SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

                    Date date = null;
                    String str = null;

                    try {
                        date = inputFormat.parse(userProfile.getUserDatum().getUserProfileData().getEmployeeDob().toString());
                        str = outputFormat.format(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    candidate_location_dob.append(str.toString());
                }

                if (userProfile.getUserDatum().getUserProfileData().getUserProfileImage() != null) {
                    Picasso.with(getContext()).load(userProfile.getUserDatum().getUserProfileData().getUserProfileImage()).resize(185, 185).transform(new CropCircleTransformation()).placeholder(R.drawable.user_dummy).into(candidate_img);
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Gson gsonUserProfile = new Gson();
        String json = appPreferencesShared.getUserDetails();
        userProfile = gsonUserProfile.fromJson(json, UserProfile.class);
        CheckEmpOrRecruiter();
    }

    @Override
    public void onStart() {
        super.onStart();
        Gson gsonUserProfile = new Gson();
        String json = appPreferencesShared.getUserDetails();
        userProfile = gsonUserProfile.fromJson(json, UserProfile.class);
        CheckEmpOrRecruiter();
    }

    @Override
    public void onPause() {
        super.onPause();
        Gson gsonUserProfile = new Gson();
        String json = appPreferencesShared.getUserDetails();
        userProfile = gsonUserProfile.fromJson(json, UserProfile.class);
        CheckEmpOrRecruiter();
    }
}
