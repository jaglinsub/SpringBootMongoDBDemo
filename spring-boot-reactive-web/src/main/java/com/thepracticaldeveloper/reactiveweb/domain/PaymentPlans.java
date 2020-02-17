package com.thepracticaldeveloper.reactiveweb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Component;

import java.awt.geom.Arc2D;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentPlans {

    private boolean active;
    private String amount;
    private String amountDecimal;
    private String billingScheme;
    private String id;
    private String interval;
    private String intervalCount;
    private boolean livemode;
    private String nickname;
    private String product;
    private String trialPeriodDays;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmountDecimal() {
        return Double.toString(Double.parseDouble(amountDecimal)/100);
    }

    public void setAmountDecimal(String amountDecimal) {
        this.amountDecimal = amountDecimal;
    }

    public String getBillingScheme() {
        return billingScheme;
    }

    public void setBillingScheme(String billingScheme) {
        this.billingScheme = billingScheme;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getIntervalCount() {
        return intervalCount;
    }

    public void setIntervalCount(String intervalCount) {
        this.intervalCount = intervalCount;
    }

    public boolean isLivemode() {
        return livemode;
    }

    public void setLivemode(boolean livemode) {
        this.livemode = livemode;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getTrialPeriodDays() {
        return trialPeriodDays;
    }

    public void setTrialPeriodDays(String trialPeriodDays) {
        this.trialPeriodDays = trialPeriodDays;
    }

    @Override
    public String toString() {
        return "PaymentPlans{" +
                "active=" + active +
                ", amount='" + amount + '\'' +
                ", amountDecimal='" + amountDecimal + '\'' +
                ", billingScheme='" + billingScheme + '\'' +
                ", id='" + id + '\'' +
                ", interval='" + interval + '\'' +
                ", intervalCount='" + intervalCount + '\'' +
                ", livemode=" + livemode +
                ", nickname='" + nickname + '\'' +
                ", product='" + product + '\'' +
                ", trialPeriodDays='" + trialPeriodDays + '\'' +
                '}';
    }
}
