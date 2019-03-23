package com.rdev.trypfordriver.data.model.firebase_model;

public class FirebaseRide {
    String id;
    String fromAddress;
    String toAddress;
    LatLngWrapper destinationLocation;
    float fare;

    public float getFare() {
        return fare;
    }

    public void setFare(float fare) {
        this.fare = fare;
    }

    AvailableDriver driver;

    public AvailableDriver getDriver() {
        return driver;
    }

    public void setDriver(AvailableDriver driver) {
        this.driver = driver;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public LatLngWrapper getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(LatLngWrapper destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public LatLngWrapper getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(LatLngWrapper pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public FirebaseRide() {
    }

    LatLngWrapper pickUpLocation;
}

