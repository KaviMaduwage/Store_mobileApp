package com.example.assignment.model;

import java.util.List;

public class TripDetailForSubscriber {
    private String subscriberName;
    private String subscriberAddress;
    private String subscriberPhoneNUmber;
    private List<SubscriptionDetail> subscriptionDetailList;

    public String getSubscriberName() {
        return subscriberName;
    }

    public void setSubscriberName(String subscriberName) {
        this.subscriberName = subscriberName;
    }

    public String getSubscriberAddress() {
        return subscriberAddress;
    }

    public void setSubscriberAddress(String subscriberAddress) {
        this.subscriberAddress = subscriberAddress;
    }

    public String getSubscriberPhoneNUmber() {
        return subscriberPhoneNUmber;
    }

    public void setSubscriberPhoneNUmber(String subscriberPhoneNUmber) {
        this.subscriberPhoneNUmber = subscriberPhoneNUmber;
    }

    public List<SubscriptionDetail> getSubscriptionDetailList() {
        return subscriptionDetailList;
    }

    public void setSubscriptionDetailList(List<SubscriptionDetail> subscriptionDetailList) {
        this.subscriptionDetailList = subscriptionDetailList;
    }
}
