package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmployeeSubscriptionWalletData {

    @SerializedName("subscription_domestic")
    @Expose
    private SubscriptionDomestic subscriptionDomestic;
    @SerializedName("subscription_international")
    @Expose
    private SubscriptionInternational subscriptionInternational;
    @SerializedName("upcoming_subscriptions")
    @Expose
    private List<UpcomingSubscription> upcomingSubscriptions;

    public SubscriptionDomestic getSubscriptionDomestic() {
        return subscriptionDomestic;
    }

    public void setSubscriptionDomestic(SubscriptionDomestic subscriptionDomestic) {
        this.subscriptionDomestic = subscriptionDomestic;
    }

    public SubscriptionInternational getSubscriptionInternational() {
        return subscriptionInternational;
    }

    public void setSubscriptionInternational(SubscriptionInternational subscriptionInternational) {
        this.subscriptionInternational = subscriptionInternational;
    }

    public List<UpcomingSubscription> getUpcomingSubscriptions() {
        return upcomingSubscriptions;
    }

    public void setUpcomingSubscriptions(List<UpcomingSubscription> upcomingSubscriptions) {
        this.upcomingSubscriptions = upcomingSubscriptions;
    }
}
