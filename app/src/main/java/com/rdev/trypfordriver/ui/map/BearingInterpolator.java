package com.rdev.trypfordriver.ui.map;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public interface BearingInterpolator {
    float interpolate(float fraction, float lastbearing, LatLng a, LatLng b);

    class Degree implements BearingInterpolator {
        @Override
        public float interpolate(float fraction, float lastbearing, LatLng from, LatLng to) {
            // http://en.wikipedia.org/wiki/Slerp
            Log.d("tag", "fraction" + fraction);
            float deduction = (float) (angleFromCoordinate(from, to) - lastbearing);
            return lastbearing + deduction * fraction;
        }

        private static double angleFromCoordinate(LatLng latLng1, LatLng latLng2) {
            double PI = 3.14159;
            double lat1 = latLng1.latitude * PI / 180;
            double long1 = latLng1.longitude * PI / 180;
            double lat2 = latLng2.latitude * PI / 180;
            double long2 = latLng2.longitude * PI / 180;

            double dLon = (long2 - long1);

            double y = Math.sin(dLon) * Math.cos(lat2);
            double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
                    * Math.cos(lat2) * Math.cos(dLon);

            double brng = Math.atan2(y, x);

            brng = Math.toDegrees(brng);
            brng = (brng + 90) % 360;

            return brng;
        }
    }
}
