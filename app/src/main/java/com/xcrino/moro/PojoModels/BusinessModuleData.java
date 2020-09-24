package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusinessModuleData {
    @SerializedName("subscription")
    @Expose
    private BusinessModuleSubscription subscription;
    @SerializedName("company_details")
    @Expose
    private BusinessModuleCompanyDetails companyDetails;
    @SerializedName("campaigns")
    @Expose
    private List<Campaign> campaigns = null;

    public BusinessModuleSubscription getSubscription() {
        return subscription;
    }

    public void setSubscription(BusinessModuleSubscription subscription) {
        this.subscription = subscription;
    }

    public BusinessModuleCompanyDetails getCompanyDetails() {
        return companyDetails;
    }

    public void setCompanyDetails(BusinessModuleCompanyDetails companyDetails) {
        this.companyDetails = companyDetails;
    }

    public List<Campaign> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(List<Campaign> campaigns) {
        this.campaigns = campaigns;
    }
}
