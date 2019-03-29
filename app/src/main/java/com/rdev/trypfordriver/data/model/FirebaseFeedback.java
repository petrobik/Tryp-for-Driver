package com.rdev.trypfordriver.data.model;

public class FirebaseFeedback {
    String s;
    String driverId;

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    float rating;

    public FirebaseFeedback() {
    }

    public FirebaseFeedback(String s, float rating, String driverId) {
        this.s = s;
        this.rating = rating;
        this.driverId = driverId;
    }
}
