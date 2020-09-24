package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FreeDomestic {
    @SerializedName("plan_type")
    @Expose
    private String planType;
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
