package com.rdev.trypfordriver.ui.map;

import android.location.Location;
import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.LatLng;
import com.rdev.trypfordriver.utill.Utill;

public class CarAnimation {
    static GroundOverlay carMarker;
    static Handler handler;

    public static void animateMarkerToGB(final GroundOverlay marker, final Location finalPosition, final LatLngInterpolator latLngInterpolator, BearingInterpolator bearingInterpolator) {
        carMarker = marker;
        float lastBearing = marker.getBearing();
        final LatLng startPosition = marker.getPosition();
        handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        final float durationInMs = 2000;
        handler.post(new Runnable() {
            long elapsed;
            float t;
            float v;

            @Override
            public void run() {
                // Calculate progress using interpolator
                elapsed = SystemClock.uptimeMillis() - start;
                t = elapsed / durationInMs;
                v = interpolator.getInterpolation(t);
                // Repeat till progress is complete.
                if (t < 1) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
                marker.setPosition(latLngInterpolator.interpolate(v, startPosition, Utill.locationToLatLng(finalPosition)));
                marker.setBearing(bearingInterpolator.interpolate(v, lastBearing, startPosition, Utill.locationToLatLng(finalPosition)));
            }
        });
    }


}
