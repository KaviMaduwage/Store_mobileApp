package com.example.assignment.model;

public class TripDetail {
    private int tripId;
    private String status;
    private String date;
    private SubscriberDetail subscriberDetail;
    private SubscriptionDetail subscriptionDetail;

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public SubscriberDetail getSubscriberDetail() {
        return subscriberDetail;
    }

    public void setSubscriberDetail(SubscriberDetail subscriberDetail) {
//        this.subscriberDetail = subscriberDetail;
    }

    public SubscriptionDetail getSubscriptionDetail() {
        return subscriptionDetail;
    }

    public void setSubscriptionDetail(SubscriptionDetail subscriptionDetail) {
        this.subscriptionDetail = subscriptionDetail;
    }
}
