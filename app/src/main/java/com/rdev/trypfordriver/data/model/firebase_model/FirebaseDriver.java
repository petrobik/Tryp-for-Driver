package com.rdev.trypfordriver.data.model.firebase_model;

public class FirebaseDriver {
    public String getRideId() {
        return rideId;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }

    public String rideId;
    private int maxLuggage;

    private double fare;

    private int maxPassenger;

    private String category;

    private String type;

    private FirebaseVehicle vehicle;

    public String getDriverId() {
        return driverId;
    }

    private String driverId;

    public FirebaseDriver() {
    }

    public int getMaxLuggage() {
        return maxLuggage;
    }

    public void setMaxLuggage(int maxLuggage) {
        this.maxLuggage = maxLuggage;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public int getMaxPassenger() {
        return maxPassenger;
    }

    public void setMaxPassenger(int maxPassenger) {
        this.maxPassenger = maxPassenger;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FirebaseVehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(FirebaseVehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getDialingCode() {
        return dialingCode;
    }

    public void setDialingCode(String dialingCode) {
        this.dialingCode = dialingCode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private String dialingCode;

    private String image;

    private int rating;

    private String lastName;

    private String phoneNumber;

    private String firstName;

    public void setId(String driverId) {
        this.driverId = driverId;
    }
}
