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
        fakeLocations.add(new LatLng(49.874513, 24.009247));
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
        fakeLocations.add(new LatLng(49.875758, 24.009120));
        fakeLocations.add(new LatLng(49.874557, 24.011803));
        fakeLocations.add(new LatLng(49.873109, 24.016303));
        fakeLocations.add(new LatLng(49.870773, 24.018719));
        fakeLocations.add(new LatLng(49.869046, 24.023079));
        fakeLocations.add(new LatLng(49.866744, 24.024549));
        fakeLocations.add(new LatLng(49.864795, 24.025538));
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
