package com.rdev.trypfordriver.data.model.accept_ride_response;

import com.google.gson.annotations.SerializedName;

public class AcceptRideResponse {

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
                "AcceptRideResponse{" +
                        "data = '" + data + '\'' +
                        "}";
    }

    @SerializedName("errors")
    private Errors errors;

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}