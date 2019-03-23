package com.rdev.trypfordriver.data.source;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Handler;

import com.google.android.gms.maps.model.LatLng;
import com.rdev.trypfordriver.utill.Utill;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FakeLocationRepository {
    ArrayList<LatLng> fakeLocations;
    Location cachedLocation;
    Handler handler;

    public Location getCachedLocation() {
        return cachedLocation;
    }

    public LatLng getCachedLatLng() {
        return Utill.locationToLatLng(cachedLocation);
    }

    @Inject
    public FakeLocationRepository() {
        fakeLocations = new ArrayList<>();
        fakeLocations.add(new LatLng(49.874568, 24.008773));
        fakeLocations.add(new LatLng(49.874609, 24.008516));
        fakeLocations.add(new LatLng(49.874671, 24.008119));
        fakeLocations.add(new LatLng(49.874754, 24.007711));
        fakeLocations.add(new LatLng(49.874893, 24.007539));
        fakeLocations.add(new LatLng(49.875066, 24.007507));
        fakeLocations.add(new LatLng(49.875218, 24.007593));
        fakeLocations.add(new LatLng(49.875356, 24.007657));
        fakeLocations.add(new LatLng(49.875563, 24.007861));
        fakeLocations.add(new LatLng(49.875833, 24.007883));
        fakeLocations.add(new LatLng(49.876124, 24.007626));
        fakeLocations.add(new LatLng(49.876338, 24.007014));
        handler = new Handler();
    }

    public interface ProvideLocationCallback {
        void onLocationChanged(Location location);
    }

    @SuppressLint("MissingPermission")
    public void registerLocationListener(final LocationRepository.ProvideLocationCallback callback) {
        handler.post(new Runnable() {
            int i = 0;

            @Override
            public void run() {
                if (i < fakeLocations.size()) {
                    LatLng latLng = fakeLocations.get(i);
                    Location location = new Location("");
                    location.setLatitude(latLng.latitude);
                    location.setLongitude(latLng.longitude);
                    cachedLocation = location;
                    callback.onLocationChanged(location);
                    i++;
                    handler.postDelayed(this, 2500);
                }
            }
        });
    }
}
