package com.rdev.trypfordriver.data.model.accept_ride_response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Errors {

    @SerializedName("code")
    private List<String> code;

    public void setCode(List<String> code) {
        this.code = code;
    }

    public List<String> getCode() {
        return code;
    }

    @Override
    public String toString() {
        return
                "Errors{" +
                        "code = '" + code + '\'' +
                        "}";
    }
}