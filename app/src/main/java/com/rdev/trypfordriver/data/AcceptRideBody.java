package com.rdev.trypfordriver.data;

public class AcceptRideBody {
    String user_id;
    String ride_request_id;

    public AcceptRideBody(String user_id, String ride_request_id) {
        this.user_id = user_id;
        this.ride_request_id = ride_request_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRide_request_id() {
        return ride_request_id;
    }

    public void setRide_request_id(String ride_request_id) {
        this.ride_request_id = ride_request_id;
    }

}
