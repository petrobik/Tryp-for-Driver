package com.rdev.trypfordriver.data.model.firebase_model;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.Exclude;

public class LatLngWrapper {
    double lat;
    double lng;

    public LatLngWrapper() {
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setLocation(LatLng latLng) {
        this.lat = latLng.latitude;
        this.lng = latLng.longitude;
    }

    @Exclude
    public LatLng getLocation() {
        return new LatLng(lat, lng);
    }
}
