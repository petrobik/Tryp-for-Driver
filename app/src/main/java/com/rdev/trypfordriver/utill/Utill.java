package com.rdev.trypfordriver.utill;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class Utill {
    public static String getCountryCode(String data) {
        return data.substring(data.indexOf(",") + 1, data.lastIndexOf(","));
    }

    public static String getDialingCode(String data) {
        return data.substring(0, data.indexOf(","));

    }

    public static String getCountryName(String data) {
        return data.substring(data.lastIndexOf(",") + 1, data.length());


    }

    public static LatLng locationToLatLng(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }
}
