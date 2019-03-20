package com.rdev.trypfordriver.data.model;

public class TripModel {

    private String clientName;
    private String tripFrom;
    private String tripTo;
    private String date;
    private boolean status;
    int avatar;

    public TripModel(String clientName, String tripFrom, String tripTo, String date, boolean status, int avatar) {
        this.clientName = clientName;
        this.tripFrom = tripFrom;
        this.tripTo = tripTo;
        this.date = date;
        this.status = status;
        this.avatar = avatar;
    }

    public String getClientName() {
        return clientName;
    }

    public String getTripFrom() {
        return tripFrom;
    }

    public String getTripTo() {
        return tripTo;
    }

    public String getDate() {
        return date;
    }

    public boolean isStatus() {
        return status;
    }

    public int getAvatar() {
        return avatar;
    }
}
