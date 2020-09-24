package com.xcrino.moro.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferencesShared {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String APP_SHARED_PREFS;

    public AppPreferencesShared(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        APP_SHARED_PREFS = "MoGo.com";

    }

    public void setCampaignId(String campaignId) {
        editor.putString("campaignId", campaignId);
        editor.commit();
    }

    public String getCampaignId() {
        return sharedPreferences.getString("campaignId", "");
    }

    public void setCreateChannelNewUser(Boolean createChannelNewUser) {
        editor.putBoolean("createChannelNewUser", createChannelNewUser);
        editor.commit();
    }

    public Boolean getCreateChannelNewUser() {
        return sharedPreferences.getBoolean("createChannelNewUser", false);
    }

    public void setJobTitle(String jobTitle) {
        editor.putString("jobTitle", jobTitle);
        editor.commit();
    }

    public String getJobTitle() {
        return sharedPreferences.getString("jobTitle", "");
    }

    public void setJobVacancies(String jobVacancies) {
        editor.putString("jobVacancies", jobVacancies);
        editor.commit();
    }

    public String getJobVacancies() {
        return sharedPreferences.getString("jobVacancies", "");
    }

    public void setJobRole(String jobRole) {
        editor.putString("jobRole", jobRole);
        editor.commit();
    }

    public String getJobRole() {
        return sharedPreferences.getString("jobRole", "");
    }

    public void setJobDescription(String jobDescription) {
        editor.putString("jobDescription", jobDescription);
        editor.commit();
    }

    public String getJobDescription() {
        return sharedPreferences.getString("jobDescription", "");
    }

    public void setStateSpinner(String stateSpinner) {
        editor.putString("stateSpinner", stateSpinner);
        editor.commit();
    }

    public String getStateSpinner() {
        return sharedPreferences.getString("stateSpinner", "");
    }

    public void setQualificationSpinner(String qualificationSpinner) {
        editor.putString("qualificationSpinner", qualificationSpinner);
        editor.commit();
    }

    public String getQualificationSpinner() {
        return sharedPreferences.getString("qualificationSpinner", "");
    }

    public void setSkillsSpinner(String skillsSpinner) {
        editor.putString("skillsSpinner", skillsSpinner);
        editor.commit();
    }

    public String getSkillsSpinner() {
        return sharedPreferences.getString("skillsSpinner", "");
    }

    public void setJobExperience(String jobExperience) {
        editor.putString("jobExperience", jobExperience);
        editor.commit();
    }

    public String getJobExperience() {
        return sharedPreferences.getString("jobExperience", "");
    }

    public void setSalaryExpectation(String salaryExpectation) {
        editor.putString("salaryExpectation", salaryExpectation);
        editor.commit();
    }

    public String getSalaryExpectation() {
        return sharedPreferences.getString("salaryExpectation", "");
    }

    public void setEmployment(String employment) {
        editor.putString("employment", employment);
        editor.commit();
    }

    public String getEmployment() {
        return sharedPreferences.getString("employment", "");
    }

    public void setIndustryId(String industryId) {
        editor.putString("industryId", industryId);
        editor.commit();
    }

    public String getIndustryId() {
        return sharedPreferences.getString("industryId", "");
    }

    public void setRecruiterName(String recruiterName) {
        editor.putString("recruiterName", recruiterName);
        editor.commit();
    }

    public String getRecruiterName() {
        return sharedPreferences.getString("recruiterName", "");
    }

    public void setRecruiterContactNo(String recruiterContactNo) {
        editor.putString("recruiterContactNo", recruiterContactNo);
        editor.commit();
    }

    public String getRecruiterContactNo() {
        return sharedPreferences.getString("recruiterContactNo", "");
    }

    public void setRecruiterCompanyName(String recruiterCompanyName) {
        editor.putString("recruiterCompanyName", recruiterCompanyName);
        editor.commit();
    }

    public String getRecruiterCompanyName() {
        return sharedPreferences.getString("recruiterCompanyName", "");
    }

    public void isRecruiterPaymentSuccessful(Boolean isRecruiterPaymentSuccessful) {
        editor.putBoolean("isRecruiterPaymentSuccessful", isRecruiterPaymentSuccessful);
        editor.commit();
    }

    public Boolean getRecruiterPaymentSuccessful() {
        return sharedPreferences.getBoolean("isRecruiterPaymentSuccessful", false);
    }

    // final AppPreferences Created by Akshita Sharma 17-03-2020
    public void setUserDetails(String userDetails) {
        editor.putString("userDetails", userDetails);
        editor.commit();
    }

    public String getUserDetails() {
        return sharedPreferences.getString("userDetails", "");
    }

    public void setPageDirection(String page) {
        editor.putString("employeePage", page);
        editor.commit();
    }

    public String getPageDirection() {
        return sharedPreferences.getString("employeePage", "");
    }

    public void setCreateCompanyProfile(Boolean createCompanyProfile) {
        editor.putBoolean("createCompanyProfile", createCompanyProfile);
        editor.commit();
    }

    public Boolean getCreateCompanyProfile() {
        return sharedPreferences.getBoolean("createCompanyProfile", false);
    }

    public void setCandidateDetails(String candidateDetails) {
        editor.putString("candidateDetails", candidateDetails);
        editor.commit();
    }

    public String getCandidateDetails() {
        return sharedPreferences.getString("candidateDetails", "");
    }

    public void setMobileNumber(String mobileNumber) {
        editor.putString("mobileNumber", mobileNumber);
        editor.commit();
    }

    public String getMobileNumber() {
        return sharedPreferences.getString("mobileNumber", "");
    }

    public void setCountryCode(Integer countryCode) {
        editor.putInt("countryCode", countryCode);
        editor.commit();
    }

    public Integer getCountryCode() {
        return sharedPreferences.getInt("countryCode", 91);
    }

    public void setOtp(Integer otp) {
        editor.putInt("otp", otp);
        editor.commit();
    }

    public Integer getOtp() {
        return sharedPreferences.getInt("otp", 0);
    }

    public void setIsFirstTimeLaunch(Boolean isFirstTimeLaunch) {
        editor.putBoolean("isFirstTimeLaunch", isFirstTimeLaunch);
        editor.commit();
    }

    public Boolean getIsFirstTimeLaunch() {
        return sharedPreferences.getBoolean("isFirstTimeLaunch", true);
    }

    public void setIsCreateProfile(Integer isCreateProfile) {
        editor.putInt("isCreateProfile", isCreateProfile);
        editor.commit();
    }

    public Integer getIsCreateProfile() {
        return sharedPreferences.getInt("isCreateProfile", 0);
    }

    public void setUserId(String userId) {
        editor.putString("verificationUserId", userId);
        editor.commit();
    }

    public String getUserId() {
        return sharedPreferences.getString("verificationUserId", null);
    }

    public void setAccessTokenString(String mAccessTokenString) {
        editor.putString("mAccessTokenString", mAccessTokenString);
        editor.commit();
    }

    public String getAccessTokenString() {
        return sharedPreferences.getString("mAccessTokenString", "");
    }

    public void setTermsAndConditions(Boolean TermsAndConditions) {
        editor.putBoolean("isTermsAndConditions", TermsAndConditions);
        editor.commit();
    }

    public Boolean getTermsAndConditions() {
        return sharedPreferences.getBoolean("isTermsAndConditions", false);
    }

    public void setDayNightMode(Boolean mDayNightMode) {
        editor.putBoolean("mDayNightMode", mDayNightMode);
        editor.commit();
    }

    public Boolean getmDayNightMode() {
        return sharedPreferences.getBoolean("mDayNightMode", false);
    }

    public String getLocale() {
        return sharedPreferences.getString("locale", "en");
    }

    public void setLocale(String locale) {
        editor.putString("locale", locale);
        editor.commit();
    }

    public void setIsEditUserProfile(Boolean isEditUserProfile) {
        editor.putBoolean("isEditUserProfile", isEditUserProfile);
        editor.commit();
    }

    public Boolean getIsEdituserProfile() {
        return sharedPreferences.getBoolean("isEditUserProfile", false);
    }

    public void setSavedStatus(Boolean savedStatus) {
        editor.putBoolean("savedStatus", savedStatus);
        editor.commit();
    }

    public Boolean getSavedStatus() {
        return sharedPreferences.getBoolean("savedStatus", false);
    }

    public void setCandidateId(String candidateId) {
        editor.putString("candidateId", candidateId);
        editor.commit();
    }

    public String getCandidateId() {
        return sharedPreferences.getString("candidateId", null);
    }

    public void setIsCandidateProfileEdit(Boolean isCandidateProfileEdit) {
        editor.putBoolean("isCandidateProfileEdit", isCandidateProfileEdit);
        editor.commit();
    }

    public Boolean getIsCandidateProfileEdit() {
        return sharedPreferences.getBoolean("isCandidateProfileEdit", false);
    }

    public void setSearchedList(String searchedList) {
        editor.putString("searchedList", searchedList);
        editor.commit();
    }

    public String getSearchedList() {
        return sharedPreferences.getString("searchedList", null);
    }

    public void setRecruiterAddUpdateJob(Boolean recruiterAddUpdateJob) {
        editor.putBoolean("recruiterAddUpdateJob", recruiterAddUpdateJob);
        editor.commit();
    }

    public Boolean getRecruiterAddUpdateJob() {
        return sharedPreferences.getBoolean("recruiterAddUpdateJob", false);
    }

    public void setRecentSearchName(String recentSearchName) {
        editor.putString("recentSearchName", recentSearchName);
        editor.commit();
    }

    public String getRecentSearchName() {
        return sharedPreferences.getString("recentSearchName", null);
    }

    public void setRecentSearchLocation(String recentSearchLocation) {
        editor.putString("recentSearchLocation", recentSearchLocation);
        editor.commit();
    }

    public String getRecentSearchLocation() {
        return sharedPreferences.getString("recentSearchLocation", null);
    }

    public void setJobId(String jobId) {
        editor.putString("jobId", jobId);
        editor.commit();
    }

    public String getJobId() {
        return sharedPreferences.getString("jobId", null);
    }

    public void setIndustryName(String industryName) {
        editor.putString("industryName", industryName);
        editor.commit();
    }

    public String getIndustryName() {
        return sharedPreferences.getString("industryName", null);
    }

    public void setSubscriptionDirection(String subscriptionDirection) {
        editor.putString("subscriptionDirection", subscriptionDirection);
        editor.commit();
    }

    public String getSubscriptionDirection() {
        return sharedPreferences.getString("subscriptionDirection", "");
    }

    // AppPrefernce for Business Module
    public void setBusinessCreateCompanyProfile(Boolean businessCreateCompanyProfile) {
        editor.putBoolean("businessCreateCompanyProfile", businessCreateCompanyProfile);
        editor.commit();
    }

    public Boolean getBusinessCreateCompanyProfile() {
        return sharedPreferences.getBoolean("businessCreateCompanyProfile", false);
    }

    public void setBusinessModuleDetails(String businessModule) {
        editor.putString("businessModule", businessModule);
        editor.commit();
    }

    public String getBusinessModuleDetails() {
        return sharedPreferences.getString("businessModule", "");
    }

    public void setFilterDirection(String filterDirection) {
        editor.putString("filterDirection", filterDirection);
        editor.commit();
    }

    public String getFilterDirection() {
        return sharedPreferences.getString("filterDirection", "");
    }

    public void setCompanyDetails(String companyDetails) {
        editor.putString("companyDetails", companyDetails);
        editor.commit();
    }

    public String getCompanyDetails() {
        return sharedPreferences.getString("companyDetails", "");
    }

    public void setJobDetails(String jobDetails) {
        editor.putString("jobDetails", jobDetails);
        editor.commit();
    }

    public String getJobDetails() {
        return sharedPreferences.getString("jobDetails", "");
    }

    public void setFragmentDirection(String fragmentDirection) {
        editor.putString("fragmentDirection", fragmentDirection);
        editor.commit();
    }

    public String getFragmentDirection() {
        return sharedPreferences.getString("fragmentDirection", "Chat");
    }

    public void setPlanDetails(String planDetails) {
        editor.putString("planDetails", planDetails);
        editor.commit();
    }

    public String getPlanDetails() {
        return sharedPreferences.getString("planDetails", "");
    }

    public void ClearPreferences() {
        editor.clear().commit();
    }


}