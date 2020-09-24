package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserDatum {

    @SerializedName("user_profile")
    @Expose
    private UserProfileData userProfileData;
    @SerializedName("user_bc")
    @Expose
    private List<UserBusinessDatum> userBc = null;
    @SerializedName("user_type")
    @Expose
    private Integer userType;
    @SerializedName("business_profile")
    @Expose
    private Integer business_profile;
    @SerializedName("business_profile_data")
    @Expose
    private BusinessModuleCompanyDetails businessModuleCompanyDetails;

    public UserProfileData getUserProfileData() {
        return userProfileData;
    }

    public void setUserProfileData(UserProfileData userProfileData) {
        this.userProfileData = userProfileData;
    }

    public List<UserBusinessDatum> getUserBc() {
        return userBc;
    }

    public void setUserBc(List<UserBusinessDatum> userBc) {
        this.userBc = userBc;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getBusiness_profile() {
        return business_profile;
    }

    public void setBusiness_profile(Integer business_profile) {
        this.business_profile = business_profile;
    }

    public BusinessModuleCompanyDetails getBusinessModuleCompanyDetails() {
        return businessModuleCompanyDetails;
    }

    public void setBusinessModuleCompanyDetails(BusinessModuleCompanyDetails businessModuleCompanyDetails) {
        this.businessModuleCompanyDetails = businessModuleCompanyDetails;
    }
}
