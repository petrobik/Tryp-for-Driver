package com.rdev.trypfordriver.data.model.on_demand_rides;

import com.google.gson.annotations.SerializedName;

public class OnDemanRidesResponse {

    @SerializedName("data")
    private Data data;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    @Override
    public String toString() {
        return
                "OnDemanRidesResponse{" +
                        "data = '" + data + '\'' +
                        "}";
    }
}