package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpcomingSubscription {

    @SerializedName("us_id")
    @Expose
    private String usId;
    @SerializedName("us_user_id")
    @Expose
    private String usUserId;
    @SerializedName("us_days_validity")
    @Expose
    private String usDaysValidity;
    @SerializedName("us_expire_date")
    @Expose
    private String usExpireDate;
    @SerializedName("us_type")
    @Expose
    private String usType;
    @SerializedName("us_amount")
    @Expose
    private String usAmount;
    @SerializedName("us_currency")
    @Expose
    private String usCurrency;
    @SerializedName("us_amount_foreign")
    @Expose
    private String usAmountForeign;
    @SerializedName("us_for_currency_foreign")
    @Expose
    private String usForCurrencyForeign;
    @SerializedName("us_jobs_allowed")
    @Expose
    private String usJobsAllowed;
    @SerializedName("us_date")
    @Expose
    private String usDate;
    @SerializedName("us_status")
    @Expose
    private String usStatus;
    @SerializedName("us_transaction_id")
    @Expose
    private String usTransactionId;
    @SerializedName("us_consumed_jobs")
    @Expose
    private String usConsumedJobs;
    @SerializedName("us_active_date")
    @Expose
    private String usActiveDate;
    @SerializedName("eas_id")
    @Expose
    private String easId;
    @SerializedName("eas_plan_user_id")
    @Expose
    private String easPlanUserId;
    @SerializedName("eas_credits_rec")
    @Expose
    private String easCreditsRec;
    @SerializedName("eas_credits_used")
    @Expose
    private String easCreditsUsed;
    @SerializedName("eas_status")
    @Expose
    private String easStatus;
    @SerializedName("eas_amt")
    @Expose
    private String easAmt;
    @SerializedName("eas_currency_for")
    @Expose
    private String easCurrencyFor;
    @SerializedName("eas_amt_for")
    @Expose
    private String easAmtFor;
    @SerializedName("eas_currency")
    @Expose
    private String easCurrency;
    @SerializedName("eas_type")
    @Expose
    private String easType;
    @SerializedName("eas_date_applied")
    @Expose
    private String easDateApplied;
    @SerializedName("eas_transaction_id")
    @Expose
    private String easTransactionId;

    public String getUsId() {
        return usId;
    }

    public void setUsId(String usId) {
        this.usId = usId;
    }

    public String getUsUserId() {
        return usUserId;
    }

    public void setUsUserId(String usUserId) {
        this.usUserId = usUserId;
    }

    public String getUsDaysValidity() {
        return usDaysValidity;
    }

    public void setUsDaysValidity(String usDaysValidity) {
        this.usDaysValidity = usDaysValidity;
    }

    public String getUsExpireDate() {
        return usExpireDate;
    }

    public void setUsExpireDate(String usExpireDate) {
        this.usExpireDate = usExpireDate;
    }

    public String getUsType() {
        return usType;
    }

    public void setUsType(String usType) {
        this.usType = usType;
    }

    public String getUsAmount() {
        return usAmount;
    }

    public void setUsAmount(String usAmount) {
        this.usAmount = usAmount;
    }

    public String getUsCurrency() {
        return usCurrency;
    }

    public void setUsCurrency(String usCurrency) {
        this.usCurrency = usCurrency;
    }

    public String getUsAmountForeign() {
        return usAmountForeign;
    }

    public void setUsAmountForeign(String usAmountForeign) {
        this.usAmountForeign = usAmountForeign;
    }

    public String getUsForCurrencyForeign() {
        return usForCurrencyForeign;
    }

    public void setUsForCurrencyForeign(String usForCurrencyForeign) {
        this.usForCurrencyForeign = usForCurrencyForeign;
    }

    public String getUsJobsAllowed() {
        return usJobsAllowed;
    }

    public void setUsJobsAllowed(String usJobsAllowed) {
        this.usJobsAllowed = usJobsAllowed;
    }

    public String getUsDate() {
        return usDate;
    }

    public void setUsDate(String usDate) {
        this.usDate = usDate;
    }

    public String getUsStatus() {
        return usStatus;
    }

    public void setUsStatus(String usStatus) {
        this.usStatus = usStatus;
    }

    public String getUsTransactionId() {
        return usTransactionId;
    }

    public void setUsTransactionId(String usTransactionId) {
        this.usTransactionId = usTransactionId;
    }

    public String getUsConsumedJobs() {
        return usConsumedJobs;
    }

    public void setUsConsumedJobs(String usConsumedJobs) {
        this.usConsumedJobs = usConsumedJobs;
    }

    public String getUsActiveDate() {
        return usActiveDate;
    }

    public void setUsActiveDate(String usActiveDate) {
        this.usActiveDate = usActiveDate;
    }

    public String getEasId() {
        return easId;
    }

    public void setEasId(String easId) {
        this.easId = easId;
    }

    public String getEasPlanUserId() {
        return easPlanUserId;
    }

    public void setEasPlanUserId(String easPlanUserId) {
        this.easPlanUserId = easPlanUserId;
    }

    public String getEasCreditsRec() {
        return easCreditsRec;
    }

    public void setEasCreditsRec(String easCreditsRec) {
        this.easCreditsRec = easCreditsRec;
    }

    public String getEasCreditsUsed() {
        return easCreditsUsed;
    }

    public void setEasCreditsUsed(String easCreditsUsed) {
        this.easCreditsUsed = easCreditsUsed;
    }

    public String getEasStatus() {
        return easStatus;
    }

    public void setEasStatus(String easStatus) {
        this.easStatus = easStatus;
    }

    public String getEasAmt() {
        return easAmt;
    }

    public void setEasAmt(String easAmt) {
        this.easAmt = easAmt;
    }

    public String getEasCurrencyFor() {
        return easCurrencyFor;
    }

    public void setEasCurrencyFor(String easCurrencyFor) {
        this.easCurrencyFor = easCurrencyFor;
    }

    public String getEasAmtFor() {
        return easAmtFor;
    }

    public void setEasAmtFor(String easAmtFor) {
        this.easAmtFor = easAmtFor;
    }

    public String getEasCurrency() {
        return easCurrency;
    }

    public void setEasCurrency(String easCurrency) {
        this.easCurrency = easCurrency;
    }

    public String getEasType() {
        return easType;
    }

    public void setEasType(String easType) {
        this.easType = easType;
    }

    public String getEasDateApplied() {
        return easDateApplied;
    }

    public void setEasDateApplied(String easDateApplied) {
        this.easDateApplied = easDateApplied;
    }

    public String getEasTransactionId() {
        return easTransactionId;
    }

    public void setEasTransactionId(String easTransactionId) {
        this.easTransactionId = easTransactionId;
    }
}
