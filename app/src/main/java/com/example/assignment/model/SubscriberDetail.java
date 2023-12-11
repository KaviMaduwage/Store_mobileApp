package com.example.assignment.model;

public class SubscriberDetail {
    private int subscriberId;
    private String subscriberName;
    private String subscriberAddress;
    private String subscriberTel;
//

//    @OneToMany(mappedBy = "subscriberDetail",fetch = FetchType.LAZY)
//    private List<SubscriptionDetail> subscriptionDetailList;
//

    public int getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(int subscriberId) {
        this.subscriberId = subscriberId;
    }

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

    public String getSubscriberTel() {
        return subscriberTel;
    }

    public void setSubscriberTel(String subscriberTel) {
        this.subscriberTel = subscriberTel;
    }

//    public List<TripDetail> getTripDetails() {
//        return tripDetails;
//    }
//
//    public void setTripDetails(List<TripDetail> tripDetails) {
//        this.tripDetails = tripDetails;
//    }
}

