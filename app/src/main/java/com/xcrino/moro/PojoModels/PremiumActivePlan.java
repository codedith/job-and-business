package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PremiumActivePlan {

    @SerializedName("subscription_id")
    @Expose
    private String subscriptionId;
    @SerializedName("subscription_type")
    @Expose
    private String subscriptionType;
    @SerializedName("subscription_amt")
    @Expose
    private String subscriptionAmt;
    @SerializedName("subscription_cur")
    @Expose
    private String subscriptionCur;
    @SerializedName("subscription_cur_foreign")
    @Expose
    private String subscriptionCurForeign;
    @SerializedName("subscription_amt_foreign")
    @Expose
    private String subscriptionAmtForeign;
    @SerializedName("plan_type")
    @Expose
    private String planType;
    @SerializedName("plan_subscription_date")
    @Expose
    private String planSubscriptionDate;
    @SerializedName("total_applicable")
    @Expose
    private Integer totalApplicable;
    @SerializedName("consumed_credits")
    @Expose
    private Integer consumedCredits;
    @SerializedName("current_credits")
    @Expose
    private Integer currentCredits;

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public String getSubscriptionAmt() {
        return subscriptionAmt;
    }

    public void setSubscriptionAmt(String subscriptionAmt) {
        this.subscriptionAmt = subscriptionAmt;
    }

    public String getSubscriptionCur() {
        return subscriptionCur;
    }

    public void setSubscriptionCur(String subscriptionCur) {
        this.subscriptionCur = subscriptionCur;
    }

    public String getSubscriptionCurForeign() {
        return subscriptionCurForeign;
    }

    public void setSubscriptionCurForeign(String subscriptionCurForeign) {
        this.subscriptionCurForeign = subscriptionCurForeign;
    }

    public String getSubscriptionAmtForeign() {
        return subscriptionAmtForeign;
    }

    public void setSubscriptionAmtForeign(String subscriptionAmtForeign) {
        this.subscriptionAmtForeign = subscriptionAmtForeign;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getPlanSubscriptionDate() {
        return planSubscriptionDate;
    }

    public void setPlanSubscriptionDate(String planSubscriptionDate) {
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
}
