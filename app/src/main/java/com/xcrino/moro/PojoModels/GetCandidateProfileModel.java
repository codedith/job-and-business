package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCandidateProfileModel {
    @SerializedName("details")
    @Expose
    private GetCandidateProfileModelList details;
    @SerializedName("skills")
    @Expose
    private List<String> skills = null;
    @SerializedName("interested_candidate")
    @Expose
    private Boolean interested_candidate;
    @SerializedName("subscription_domestic")
    @Expose
    private SubscriptionDomestic subscriptionDomestic;
    @SerializedName("subscription_international")
    @Expose
    private SubscriptionInternational subscriptionInternational;

    public GetCandidateProfileModelList getDetails() {
        return details;
    }

    public void setDetails(GetCandidateProfileModelList details) {
        this.details = details;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public Boolean getInterested_candidate() {
        return interested_candidate;
    }

    public void setInterested_candidate(Boolean interested_candidate) {
        this.interested_candidate = interested_candidate;
    }

    public SubscriptionDomestic getSubscriptionDomestic() {
        return subscriptionDomestic;
    }

    public void setSubscriptionDomestic(SubscriptionDomestic subscriptionDomestic) {
        this.subscriptionDomestic = subscriptionDomestic;
    }

    public SubscriptionInternational getSubscriptionInternational() {
        return subscriptionInternational;
    }

    public void setSubscriptionInternational(SubscriptionInternational subscriptionInternational) {
        this.subscriptionInternational = subscriptionInternational;
    }
}
