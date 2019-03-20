package com.rdev.trypfordriver.data.model.on_demand_rides;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("rides")
    private List<RidesItem> rides;

    public void setRides(List<RidesItem> rides) {
        this.rides = rides;
    }

    public List<RidesItem> getRides() {
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