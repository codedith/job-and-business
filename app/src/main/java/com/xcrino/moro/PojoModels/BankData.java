package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankData {
    @SerializedName("ubd_id")
    @Expose
    private String ubdId;
    @SerializedName("ubd_user_id")
    @Expose
    private String ubdUserId;
    @SerializedName("ubd_name")
    @Expose
    private String ubdName;
    @SerializedName("ubd_bank_name")
    @Expose
    private String ubdBankName;
    @SerializedName("ubd_branch")
    @Expose
    private String ubdBranch;
    @SerializedName("ubd_ifsc")
    @Expose
    private String ubdIfsc;
    @SerializedName("ubd_account_no")
    @Expose
    private String ubdAccountNo;
    @SerializedName("ubd_created")
    @Expose
    private String ubdCreated;
    @SerializedName("ubd_last_update")
    @Expose
    private String ubdLastUpdate;
    @SerializedName("available_amount")
    @Expose
    private Double availableAmount;
    @SerializedName("currency")
    @Expose
    private String currency;

    public String getUbdId() {
        return ubdId;
    }

    public void setUbdId(String ubdId) {
        this.ubdId = ubdId;
    }

    public String getUbdUserId() {
        return ubdUserId;
    }

    public void setUbdUserId(String ubdUserId) {
        this.ubdUserId = ubdUserId;
    }

    public String getUbdName() {
        return ubdName;
    }

    public void setUbdName(String ubdName) {
        this.ubdName = ubdName;
    }

    public String getUbdBankName() {
        return ubdBankName;
    }

    public void setUbdBankName(String ubdBankName) {
        this.ubdBankName = ubdBankName;
    }

    public String getUbdBranch() {
        return ubdBranch;
    }

    public void setUbdBranch(String ubdBranch) {
        this.ubdBranch = ubdBranch;
    }

    public String getUbdIfsc() {
        return ubdIfsc;
    }

    public void setUbdIfsc(String ubdIfsc) {
        this.ubdIfsc = ubdIfsc;
    }

    public String getUbdAccountNo() {
        return ubdAccountNo;
    }

    public void setUbdAccountNo(String ubdAccountNo) {
        this.ubdAccountNo = ubdAccountNo;
    }

    public String getUbdCreated() {
        return ubdCreated;
    }

    public void setUbdCreated(String ubdCreated) {
        this.ubdCreated = ubdCreated;
    }

    public String getUbdLastUpdate() {
        return ubdLastUpdate;
    }

    public void setUbdLastUpdate(String ubdLastUpdate) {
        this.ubdLastUpdate = ubdLastUpdate;
    }

    public Double getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(Double availableAmount) {
        this.availableAmount = availableAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
