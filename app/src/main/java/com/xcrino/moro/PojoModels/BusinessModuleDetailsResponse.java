package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusinessModuleDetailsResponse {
    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("data")
    @Expose
    private BusinessModuleData businessModuleData;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public BusinessModuleData getBusinessModuleData() {
        return businessModuleData;
    }

    public void setBusinessModuleData(BusinessModuleData businessModuleData) {
        this.businessModuleData = businessModuleData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
