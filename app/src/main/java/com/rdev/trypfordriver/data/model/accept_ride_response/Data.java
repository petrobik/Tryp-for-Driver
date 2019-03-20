package com.rdev.trypfordriver.data.model.accept_ride_response;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("rides")
    private Rides rides;

    public void setRides(Rides rides) {
        this.rides = rides;
    }

    public Rides getRides() {
        return rides;
    }

    @Override
    public String toString() {
        return
                "Data{" +
                        "rides = '" + rides + '\'' +
                        "}";
    }
}