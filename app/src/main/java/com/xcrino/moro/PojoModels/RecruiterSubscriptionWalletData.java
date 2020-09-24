package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecruiterSubscriptionWalletData {

    @SerializedName("wallet_amt")
    @Expose
    private String walletAmt;
    @SerializedName("wallet")
    @Expose
    private WalletDetails wallet;
    @SerializedName("subscription")
    @Expose
    private DomesticSubscription subscription;
    @SerializedName("international_subscription")
    @Expose
    private InternationalSubscription internationalSubscription;
    @SerializedName("upcoming_subscriptions")
    @Expose
    private List<UpcomingSubscription> upcomingSubscriptions = null;

    public String getWalletAmt() {
        return walletAmt;
    }

    public void setWalletAmt(String walletAmt) {
        this.walletAmt = walletAmt;
    }

    public DomesticSubscription getSubscription() {
        return subscription;
    }

    public void setSubscription(DomesticSubscription subscription) {
        this.subscription = subscription;
    }

    public InternationalSubscription getInternationalSubscription() {
        return internationalSubscription;
    }

    public void setInternationalSubscription(InternationalSubscription internationalSubscription) {
        this.internationalSubscription = internationalSubscription;
    }

    public List<UpcomingSubscription> getUpcomingSubscriptions() {
        return upcomingSubscriptions;
    }

    public void setUpcomingSubscriptions(List<UpcomingSubscription> upcomingSubscriptions) {
        this.upcomingSubscriptions = upcomingSubscriptions;
    }

    public WalletDetails getWallet() {
        return wallet;
    }

    public void setWallet(WalletDetails wallet) {
        this.wallet = wallet;
    }
}
