package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subscription {

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
