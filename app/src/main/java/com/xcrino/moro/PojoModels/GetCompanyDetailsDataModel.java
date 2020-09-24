package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCompanyDetailsDataModel {

    @SerializedName("subscription")
    @Expose
    private Subscription subscription;
    @SerializedName("company_details")
    @Expose
    private CompanyDetails companyDetails;
    @SerializedName("job_list")
    @Expose
    private List<JobDetails> jobList = null;

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public CompanyDetails getCompanyDetails() {
        return companyDetails;
    }

    public void setCompanyDetails(CompanyDetails companyDetails) {
        this.companyDetails = companyDetails;
    }

    public List<JobDetails> getJobList() {
        return jobList;
    }

    public void setJobList(List<JobDetails> jobList) {
        this.jobList = jobList;
    }
}
