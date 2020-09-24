package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CandidateCurrentSubscription {

    @SerializedName("plan_type")
    @Expose
    private String planType;
    @SerializedName("plan_subscription_date")
    @Expose
    private Object planSubscriptionDate;
    @SerializedName("total_applicable")
    @Expose
    private Integer totalApplicable;
    @SerializedName("consumed_credits")
    @Expose
    private Integer consumedCredits;
    @SerializedName("current_credits")
    @Expose
    private Integer currentCredits;
    @SerializedName("current_free_international_credits_total")
    @Expose
    private Integer currentFreeInternationalCreditsTotal;
    @SerializedName("current_free_international_credits_remaining")
    @Expose
    private Integer currentFreeInternationalCreditsRemaining;
    @SerializedName("current_free_international_credits_used")
    @Expose
    private Integer currentFreeInternationalCreditsUsed;
    @SerializedName("current_free_domestic_credits_total")
    @Expose
    private Integer currentFreeDomesticCreditsTotal;
    @SerializedName("current_free_domestic_credits_remaining")
    @Expose
    private Integer currentFreeDomesticCreditsRemaining;
    @SerializedName("current_free_domestic_credits_used")
    @Expose
    private Integer currentFreeDomesticCreditsUsed;

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public Object getPlanSubscriptionDate() {
        return planSubscriptionDate;
    }

    public void setPlanSubscriptionDate(Object planSubscriptionDate) {
        this.planSubscriptionDate = planSubscriptionDate;
    }

    public Integer getTotalApplicable() {
        return totalApplicable;
    }

    public void setTotalApplicable(Integer totalApplicable) {
        this.totalApplicable = totalApplicable;
    }

    public Integer getConsumedCredits() {
        return consumedCredits;
    }

    public void setConsumedCredits(Integer consumedCredits) {
        this.consumedCredits = consumedCredits;
    }

    public Integer getCurrentCredits() {
        return currentCredits;
    }

    public void setCurrentCredits(Integer currentCredits) {
        this.currentCredits = currentCredits;
    }

    public Integer getCurrentFreeInternationalCreditsTotal() {
        return currentFreeInternationalCreditsTotal;
    }

    public void setCurrentFreeInternationalCreditsTotal(Integer currentFreeInternationalCreditsTotal) {
        this.currentFreeInternationalCreditsTotal = currentFreeInternationalCreditsTotal;
    }

    public Integer getCurrentFreeInternationalCreditsRemaining() {
        return currentFreeInternationalCreditsRemaining;
    }

    public void setCurrentFreeInternationalCreditsRemaining(Integer currentFreeInternationalCreditsRemaining) {
        this.currentFreeInternationalCreditsRemaining = currentFreeInternationalCreditsRemaining;
    }

    public Integer getCurrentFreeInternationalCreditsUsed() {
        return currentFreeInternationalCreditsUsed;
    }

    public void setCurrentFreeInternationalCreditsUsed(Integer currentFreeInternationalCreditsUsed) {
        this.currentFreeInternationalCreditsUsed = currentFreeInternationalCreditsUsed;
    }

    public Integer getCurrentFreeDomesticCreditsTotal() {
        return currentFreeDomesticCreditsTotal;
    }

    public void setCurrentFreeDomesticCreditsTotal(Integer currentFreeDomesticCreditsTotal) {
        this.currentFreeDomesticCreditsTotal = currentFreeDomesticCreditsTotal;
    }

    public Integer getCurrentFreeDomesticCreditsRemaining() {
        return currentFreeDomesticCreditsRemaining;
    }

    public void setCurrentFreeDomesticCreditsRemaining(Integer currentFreeDomesticCreditsRemaining) {
        this.currentFreeDomesticCreditsRemaining = currentFreeDomesticCreditsRemaining;
    }

    public Integer getCurrentFreeDomesticCreditsUsed() {
        return currentFreeDomesticCreditsUsed;
    }

    public void setCurrentFreeDomesticCreditsUsed(Integer currentFreeDomesticCreditsUsed) {
        this.currentFreeDomesticCreditsUsed = currentFreeDomesticCreditsUsed;
    }

}
