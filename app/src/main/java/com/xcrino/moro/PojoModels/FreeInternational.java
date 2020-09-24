package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FreeInternational {
    @SerializedName("plan_type")
    @Expose
    private String planType;
    @SerializedName("current_free_international_credits_total")
    @Expose
    private Integer currentFreeInternationalCreditsTotal;
    @SerializedName("current_free_international_credits_remaining")
    @Expose
    private Integer currentFreeInternationalCreditsRemaining;
    @SerializedName("current_free_international_credits_used")
    @Expose
    private Integer currentFreeInternationalCreditsUsed;

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
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

}
