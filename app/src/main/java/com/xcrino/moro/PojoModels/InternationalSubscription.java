package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InternationalSubscription {

    @SerializedName("plan_id")
    @Expose
    private String planId;
    @SerializedName("plan_type")
    @Expose
    private String planType;
    @SerializedName("plan_amount")
    @Expose
    private String planAmount;
    @SerializedName("plan_currency")
    @Expose
    private String planCurrency;
    @SerializedName("plan_amount_foreign")
    @Expose
    private String planAmountForeign;
    @SerializedName("plan_currency_foreign")
    @Expose
    private String planCurrencyForeign;
    @SerializedName("valid_from")
    @Expose
    private String validFrom;
    @SerializedName("valid_upto")
    @Expose
    private String validUpto;
    @SerializedName("total_applicable")
    @Expose
    private String totalApplicable;
    @SerializedName("total_consumed")
    @Expose
    private Integer totalConsumed;
    @SerializedName("total_remaining")
    @Expose
    private Integer totalRemaining;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getPlanAmount() {
        return planAmount;
    }

    public void setPlanAmount(String planAmount) {
        this.planAmount = planAmount;
    }

    public String getPlanCurrency() {
        return planCurrency;
    }

    public void setPlanCurrency(String planCurrency) {
        this.planCurrency = planCurrency;
    }

    public String getPlanAmountForeign() {
        return planAmountForeign;
    }

    public void setPlanAmountForeign(String planAmountForeign) {
        this.planAmountForeign = planAmountForeign;
    }

    public String getPlanCurrencyForeign() {
        return planCurrencyForeign;
    }

    public void setPlanCurrencyForeign(String planCurrencyForeign) {
        this.planCurrencyForeign = planCurrencyForeign;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidUpto() {
        return validUpto;
    }

    public void setValidUpto(String validUpto) {
        this.validUpto = validUpto;
    }

    public String getTotalApplicable() {
        return totalApplicable;
    }

    public void setTotalApplicable(String totalApplicable) {
        this.totalApplicable = totalApplicable;
    }

    public Integer getTotalConsumed() {
        return totalConsumed;
    }

    public void setTotalConsumed(Integer totalConsumed) {
        this.totalConsumed = totalConsumed;
    }

    public Integer getTotalRemaining() {
        return totalRemaining;
    }

    public void setTotalRemaining(Integer totalRemaining) {
        this.totalRemaining = totalRemaining;
    }

}
