package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecruiterSubscriptionWallet {

    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("data")
    @Expose
    private RecruiterSubscriptionWalletData data;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public RecruiterSubscriptionWalletData getData() {
        return data;
    }

    public void setData(RecruiterSubscriptionWalletData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
