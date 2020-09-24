package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommonResponseModel {
    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("data")
    @Expose
    private String data;
//    @SerializedName("data")
//    @Expose
    private Data dataExtra;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("subscriptionPlan")
    @Expose
    private List<SubscriptionPlan> subscriptionPlanList = null;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getDataExtra() {
        return dataExtra;
    }

    public void setDataExtra(Data dataExtra) {
        this.dataExtra = dataExtra;
    }

    public List<SubscriptionPlan> getSubscriptionPlanList() {
        return subscriptionPlanList;
    }

    public void setSubscriptionPlanList(List<SubscriptionPlan> subscriptionPlanList) {
        this.subscriptionPlanList = subscriptionPlanList;
    }
}
