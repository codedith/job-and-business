package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubscriptionDomestic {

    @SerializedName("premium_active_plan")
    @Expose
    private PremiumActivePlan premiumActivePlan;
    @SerializedName("free_international")
    @Expose
    private FreeInternational freeInternational;
    @SerializedName("free_domestic")
    @Expose
    private FreeDomestic freeDomestic;

    public PremiumActivePlan getPremiumActivePlan() {
        return premiumActivePlan;
    }

    public void setPremiumActivePlan(PremiumActivePlan premiumActivePlan) {
        this.premiumActivePlan = premiumActivePlan;
    }

    public FreeInternational getFreeInternational() {
        return freeInternational;
    }

    public void setFreeInternational(FreeInternational freeInternational) {
        this.freeInternational = freeInternational;
    }

    public FreeDomestic getFreeDomestic() {
        return freeDomestic;
    }

    public void setFreeDomestic(FreeDomestic freeDomestic) {
        this.freeDomestic = freeDomestic;
    }
}
