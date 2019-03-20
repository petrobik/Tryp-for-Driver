package com.rdev.trypfordriver.data.model;

public class DriverLoginBody {
    String username;
    String password;
    String device_token;
    String device_type;

    public DriverLoginBody(String username, String password, String device_token, String device_type) {
        this.username = username;
        this.password = password;
        this.device_token = device_token;
        this.device_type = device_type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

}
