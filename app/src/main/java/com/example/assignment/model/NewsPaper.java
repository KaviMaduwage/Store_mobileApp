package com.example.assignment.model;

public class NewsPaper {
    private int newsPaperId;

    private String description;
    private SubscriptionType subscriptionType;
    //
    public int getNewsPaperId() {
        return newsPaperId;
    }

    public void setNewsPaperId(int newsPaperId) {
        this.newsPaperId = newsPaperId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }
}

