package com.rdev.trypfordriver.data.model.driver_login_response;

import com.google.gson.annotations.SerializedName;

public class DriverLoginResponse {

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
                "DriverLoginResponse{" +
                        "data = '" + data + '\'' +
                        "}";
    }
}