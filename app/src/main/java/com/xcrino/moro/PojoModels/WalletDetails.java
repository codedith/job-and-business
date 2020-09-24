package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletDetails {

    @SerializedName("r_wallet_id")
    @Expose
    private String rWalletId;
    @SerializedName("r_wallet_user_id")
    @Expose
    private String rWalletUserId;
    @SerializedName("r_wallet_amt")
    @Expose
    private String rWalletAmt;
    @SerializedName("r_wallet_currency")
    @Expose
    private String rWalletCurrency;
    @SerializedName("r_wallet_created_on")
    @Expose
    private String rWalletCreatedOn;
    @SerializedName("r_wallet_last_update")
    @Expose
    private String rWalletLastUpdate;

    public String getrWalletId() {
        return rWalletId;
    }

    public void setrWalletId(String rWalletId) {
        this.rWalletId = rWalletId;
    }

    public String getrWalletUserId() {
        return rWalletUserId;
    }

    public void setrWalletUserId(String rWalletUserId) {
        this.rWalletUserId = rWalletUserId;
    }

    public String getrWalletAmt() {
        return rWalletAmt;
    }

    public void setrWalletAmt(String rWalletAmt) {
        this.rWalletAmt = rWalletAmt;
    }

    public String getrWalletCurrency() {
        return rWalletCurrency;
    }

    public void setrWalletCurrency(String rWalletCurrency) {
        this.rWalletCurrency = rWalletCurrency;
    }

    public String getrWalletCreatedOn() {
        return rWalletCreatedOn;
    }

    public void setrWalletCreatedOn(String rWalletCreatedOn) {
        this.rWalletCreatedOn = rWalletCreatedOn;
    }

    public String getrWalletLastUpdate() {
        return rWalletLastUpdate;
    }

    public void setrWalletLastUpdate(String rWalletLastUpdate) {
        this.rWalletLastUpdate = rWalletLastUpdate;
    }
}
