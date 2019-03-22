package com.rdev.trypfordriver.data.model.firebase_model;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.Exclude;

public class AvailableDriver {
    String id;

    LatLngWrapper location;

    public LatLngWrapper getLocation() {
        return location;
    }

    public AvailableDriver() {
    }

    public void setLocation(LatLngWrapper location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AvailableDriver(String id) {
        this.id = id;
    }

    @Exclude
    public void setLatLng(LatLng latLng) {
        location = new LatLngWrapper();
        location.setLocation(latLng);
    }
}
