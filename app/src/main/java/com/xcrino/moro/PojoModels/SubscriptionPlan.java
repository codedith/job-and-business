package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubscriptionPlan {
    // Recruiter Modules
    @SerializedName("subscription_plan_id")
    @Expose
    private String subscriptionPlanId;
    @SerializedName("plans_name")
    @Expose
    private String plansName;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("days")
    @Expose
    private String days;
    @SerializedName("post_job")
    @Expose
    private String postJob;
    @SerializedName("created_plan")
    @Expose
    private String createdPlan;
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
    @SerializedName("foreign_amt")
    @Expose
    private String foreignAmt;
    @SerializedName("foreign_cur")
    @Expose
    private String foreignCur;

    //Business Modules
    @SerializedName("bms_id")
    @Expose
    private String bmsId;
    @SerializedName("bms_credit")
    @Expose
    private String bmsCredit;
    @SerializedName("bms_validity")
    @Expose
    private String bmsValidity;
    @SerializedName("bms_amt_inr")
    @Expose
    private String bmsAmtInr;
    @SerializedName("bms_amt_usd")
    @Expose
    private String bmsAmtUsd;
    @SerializedName("bms_amt_cur")
    @Expose
    private String bmsAmtCur;
    @SerializedName("bms_amt_foreign_cur")
    @Expose
    private String bmsAmtForeignCur;

    // Employee Modules
    @SerializedName("es_id")
    @Expose
    private String esId;
    @SerializedName("es_type")
    @Expose
    private String esType;
    @SerializedName("es_amount")
    @Expose
    private String esAmount;
    @SerializedName("es_currency")
    @Expose
    private String esCurrency;
    @SerializedName("es_foreign_amt")
    @Expose
    private String esForeignAmt;
    @SerializedName("es_foreign_cur")
    @Expose
    private String esForeignCur;
    @SerializedName("es_credits")
    @Expose
    private String esCredits;
    @SerializedName("es_date_created")
    @Expose
    private String esDateCreated;

    // Business/Employee/Recruiter Modules
    @SerializedName("consumed_credits")
    @Expose
    private Integer consumedCredits;
    @SerializedName("current_credits")
    @Expose
    private String currentCredits;

    public String getSubscriptionPlanId() {
        return subscriptionPlanId;
    }

    public void setSubscriptionPlanId(String subscriptionPlanId) {
        this.subscriptionPlanId = subscriptionPlanId;
    }

    public String getPlansName() {
        return plansName;
    }

    public void setPlansName(String plansName) {
        this.plansName = plansName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getPostJob() {
        return postJob;
    }

    public void setPostJob(String postJob) {
        this.postJob = postJob;
    }

    public String getCreatedPlan() {
        return createdPlan;
    }

    public void setCreatedPlan(String createdPlan) {
        this.createdPlan = createdPlan;
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

    public String getForeignAmt() {
        return foreignAmt;
    }

    public void setForeignAmt(String foreignAmt) {
        this.foreignAmt = foreignAmt;
    }

    public String getForeignCur() {
        return foreignCur;
    }

    public void setForeignCur(String foreignCur) {
        this.foreignCur = foreignCur;
    }

    public String getBmsId() {
        return bmsId;
    }

    public void setBmsId(String bmsId) {
        this.bmsId = bmsId;
    }

    public String getBmsCredit() {
        return bmsCredit;
    }

    public void setBmsCredit(String bmsCredit) {
        this.bmsCredit = bmsCredit;
    }

    public String getBmsValidity() {
        return bmsValidity;
    }

    public void setBmsValidity(String bmsValidity) {
        this.bmsValidity = bmsValidity;
    }

    public String getBmsAmtInr() {
        return bmsAmtInr;
    }

    public void setBmsAmtInr(String bmsAmtInr) {
        this.bmsAmtInr = bmsAmtInr;
    }

    public String getBmsAmtUsd() {
        return bmsAmtUsd;
    }

    public void setBmsAmtUsd(String bmsAmtUsd) {
        this.bmsAmtUsd = bmsAmtUsd;
    }

    public String getEsId() {
        return esId;
    }

    public void setEsId(String esId) {
        this.esId = esId;
    }

    public String getEsType() {
        return esType;
    }

    public void setEsType(String esType) {
        this.esType = esType;
    }

    public String getEsAmount() {
        return esAmount;
    }

    public void setEsAmount(String esAmount) {
        this.esAmount = esAmount;
    }

    public String getEsCurrency() {
        return esCurrency;
    }

    public void setEsCurrency(String esCurrency) {
        this.esCurrency = esCurrency;
    }

    public String getEsForeignAmt() {
        return esForeignAmt;
    }

    public void setEsForeignAmt(String esForeignAmt) {
        this.esForeignAmt = esForeignAmt;
    }

    public String getEsForeignCur() {
        return esForeignCur;
    }

    public void setEsForeignCur(String esForeignCur) {
        this.esForeignCur = esForeignCur;
    }

    public String getEsCredits() {
        return esCredits;
    }

    public void setEsCredits(String esCredits) {
        this.esCredits = esCredits;
    }

    public String getEsDateCreated() {
        return esDateCreated;
    }

    public void setEsDateCreated(String esDateCreated) {
        this.esDateCreated = esDateCreated;
    }

    public Integer getConsumedCredits() {
        return consumedCredits;
    }

    public void setConsumedCredits(Integer consumedCredits) {
        this.consumedCredits = consumedCredits;
    }

    public String getCurrentCredits() {
        return currentCredits;
    }

    public void setCurrentCredits(String currentCredits) {
        this.currentCredits = currentCredits;
    }

    public String getBmsAmtCur() {
        return bmsAmtCur;
    }

    public void setBmsAmtCur(String bmsAmtCur) {
        this.bmsAmtCur = bmsAmtCur;
    }

    public String getBmsAmtForeignCur() {
        return bmsAmtForeignCur;
    }

    public void setBmsAmtForeignCur(String bmsAmtForeignCur) {
        this.bmsAmtForeignCur = bmsAmtForeignCur;
    }
}