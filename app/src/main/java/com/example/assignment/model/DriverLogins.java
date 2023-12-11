package com.example.assignment.model;

import java.util.List;

public class DriverLogins {

    private String driverId; // nic number is taken as the primary key
    private String driverUserName;
    private String password;
    private List<TripDetail> tripDetails;

    public DriverLogins() {
    }

    public DriverLogins(String driverId, String driverUserName, String password, List<TripDetail> tripDetails) {
        this.driverId = driverId;
        this.driverUserName = driverUserName;
        this.password = password;
        this.tripDetails = tripDetails;
    }

    public List<TripDetail> getTripDetails() {
        return tripDetails;
    }

    public void setTripDetails(List<TripDetail> tripDetails) {
        this.tripDetails = tripDetails;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverUserName() {
        return driverUserName;
    }

    public void setDriverUserName(String driverUserName) {
        this.driverUserName = driverUserName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

