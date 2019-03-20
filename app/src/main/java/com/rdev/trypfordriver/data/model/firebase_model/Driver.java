package com.rdev.trypfordriver.data.model.firebase_model;

import com.google.android.gms.maps.model.LatLng;

public class Driver {
    private String id;
    LatLngWrapper location;

    public Driver() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", location=" + location +
                '}';
    }

    public LatLngWrapper getLocation() {
        return location;
    }

    public void setLocation(LatLngWrapper location) {
        this.location = location;
    }


}
