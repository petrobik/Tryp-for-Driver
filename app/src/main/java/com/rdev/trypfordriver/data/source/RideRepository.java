package com.rdev.trypfordriver.data.source;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rdev.trypfordriver.data.ApiService;
import com.rdev.trypfordriver.data.model.accept_ride_response.Rides;
import com.rdev.trypfordriver.data.model.firebase_model.AvailableDriver;
import com.rdev.trypfordriver.data.model.firebase_model.FirebaseDriver;
import com.rdev.trypfordriver.data.model.firebase_model.FirebaseRide;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;

@Singleton
public class RideRepository implements ValueEventListener {
    private final DriverRepository driverRepository;
    ApiService service;
    Rides acceptedRide;
    int rideStatus = 0;
    static final int STATUS_RIDE_TO_PICK_UP = 1;

    static final int STATUS_RIDE_TO_DESTINATION = 2;
    private static final int STATUS_RIDE_COMPLETED = 3;
    FirebaseDatabase database;
    private ProvideRideCallback callback;

    public FirebaseRide getFirebaseRide() {
        return firebaseRide;
    }

    FirebaseRide firebaseRide;


    @Inject
    public RideRepository(ApiService service, FirebaseDatabase database, DriverRepository driverRepository) {
        this.service = service;
        this.database = database;
        this.driverRepository = driverRepository;
    }

    FirebaseDriver driver;

    public void setRideListener(final ProvideRideCallback callback) {
        this.callback = callback;
        driverRepository.currentDriverReference.addValueEventListener(this);
    }

    private void getRide(String rideId, ProvideRideCallback callback) {
        database.getReference("rides/" + rideId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FirebaseRide ride = dataSnapshot.getValue(FirebaseRide.class);
                firebaseRide = ride;
                callback.onGetRideRequest(ride);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    public void driver_get_ondemand_rides(String s, final ProvideOnDemandsRidesCallback callback) {
//        Log.d("tag", "driver_get_ondemand_rides");
//        service.driver_get_ondemand_rides(s).enqueue(new Callback<OnDemanRidesResponse>() {
//            @Override
//            public void onResponse(Call<OnDemanRidesResponse> call, Response<OnDemanRidesResponse> response) {
//                callback.onFindOnDemansRides(response.body().getData().getRides());
//            }
//
//            @Override
//            public void onFailure(Call<OnDemanRidesResponse> call, Throwable t) {
//                callback.onError(t.getMessage());
//            }
//        });
//    }

    public void acceptRide(AvailableDriver driver) {
        firebaseRide.setDriver(driver);
        database.getReference("rides/" + firebaseRide.getId()).setValue(firebaseRide);
    }
//    public void acceptRide(String userId, String rideRequestId, final onAcceptRideCallBack callBack) {
//        service.driver_accept_ride(new AcceptRideBody(userId, rideRequestId)).enqueue(new Callback<AcceptRideResponse>() {
//            @Override
//            public void onResponse(Call<AcceptRideResponse> call, Response<AcceptRideResponse> response) {
//                if (response.body().getData() != null) {
//                    acceptedRide = response.body().getData().getRides();
//                    databaseReference = database.getReference("rides/" + acceptedRide.getRideRequestId());
//
//                    callBack.onRideAccepted(acceptedRide);
//                } else if (response.body().getErrors() != null) {
//                    callBack.onError(response.body().getErrors().getCode().get(0));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AcceptRideResponse> call, Throwable t) {
//                callBack.onError(t.getMessage());
//            }
//        });
//        //TODO TEST
//        acceptedRide = new Rides();
//        acceptedRide.setUpdatedAt("2019-03-16 16:49:40");
//        acceptedRide.setRideRequestId("sgdgsgds");
//        acceptedRide.setPickupAddress("Pick Up adress");
//        acceptedRide.setDestinationAddress("Destination adress");
////                    databaseReference = database.getReference("rides/" + acceptedRide.getRideRequestId());
////
//        callBack.onRideAccepted(acceptedRide);

    public void rideToPickUp() {
        rideStatus = STATUS_RIDE_TO_PICK_UP;
    }

    public void rideToDestination() {
        rideStatus = STATUS_RIDE_TO_DESTINATION;
    }

    public void rideCompleted() {
        rideStatus = STATUS_RIDE_COMPLETED;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        Log.d("tag", "OnDataChange in setRideListener");
        driver = dataSnapshot.getValue(FirebaseDriver.class);
        if (driver.getRideId() != null) {
            getRide(driver.getRideId(), callback);
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

    public interface ProvideRideCallback {
        void onGetRideRequest(FirebaseRide ridesItem);

        void onError(String error);
    }

    public void compareLocation(LatLng driverLocation, CompareLocationCallBack callBack) {
        Location from = new Location("from");
        from.setLatitude(driverLocation.latitude);
        from.setLongitude(driverLocation.longitude);
        Location to = new Location("locationB");
        if (rideStatus == STATUS_RIDE_TO_PICK_UP) {
            Log.d("tag", "rideStatus == STATUS_RIDE_TO_PICK_UP");
            to.setLatitude(acceptedRide.getPickupLat());
            to.setLongitude(acceptedRide.getPickupLng());
            Log.d("tag", "from.distanceTo(to)" + from.distanceTo(to));
            if (from.distanceTo(to) < 100) {
                callBack.driverNearPickUp();
            }
        } else if (rideStatus == STATUS_RIDE_TO_DESTINATION) {
            to.setLatitude(acceptedRide.getDestinationLat());
            to.setLongitude(acceptedRide.getDestinationLng());
            Log.d("tag", "rideStatus == STATUS_RIDE_TO_DESTINATION");
            Log.d("tag", "from.distanceTo(to)" + from.distanceTo(to));
            if (from.distanceTo(to) < 100) {
                callBack.driverNearDestination();
            }
        }
    }

    public interface CompareLocationCallBack {
        void driverNearPickUp();

        void driverNearDestination();

    }

    public Rides getAcceptedRide() {
        return acceptedRide;
    }

}
