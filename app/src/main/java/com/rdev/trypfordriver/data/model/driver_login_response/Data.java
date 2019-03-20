package com.rdev.trypfordriver.data.model.driver_login_response;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("driver")
    private Driver driver;

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Driver getDriver() {
        return driver;
    }

    @Override
    public String toString() {
        return
                "Data{" +
                        "driver = '" + driver + '\'' +
                        "}";
    }
}