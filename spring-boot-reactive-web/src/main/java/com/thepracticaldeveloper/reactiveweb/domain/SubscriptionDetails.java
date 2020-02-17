package com.thepracticaldeveloper.reactiveweb.domain;

import java.util.Date;

public final class SubscriptionDetails {
    private String id;
    private String customerId;
    private Date billingCycleAnchor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getBillingCycleAnchor() {
        return billingCycleAnchor;
    }

    public void setBillingCycleAnchor(Date billingCycleAnchor) {
        this.billingCycleAnchor = billingCycleAnchor;
    }
}
