package com.rdev.trypfordriver.data.model.firebase_model;

import com.google.android.gms.maps.model.LatLng;

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
}
