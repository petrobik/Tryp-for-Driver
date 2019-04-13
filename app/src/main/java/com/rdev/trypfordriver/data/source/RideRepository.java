package com.rdev.trypfordriver.data.source;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rdev.trypfordriver.data.ApiService;
import com.rdev.trypfordriver.data.DirectionsApiService;
import com.rdev.trypfordriver.data.model.FirebaseClient;
import com.rdev.trypfordriver.data.model.FirebaseFeedback;
import com.rdev.trypfordriver.data.model.directions.DirectionsResponse;
import com.rdev.trypfordriver.data.model.firebase_model.AvailableDriver;
import com.rdev.trypfordriver.data.model.firebase_model.FirebaseDriver;
import com.rdev.trypfordriver.data.model.firebase_model.FirebaseRide;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class RideRepository implements ValueEventListener {
    private final DriverRepository driverRepository;
    private final DirectionsApiService directionsApiService;
    ApiService service;
    FirebaseRide acceptedRide;
    int rideStatus = 0;
    public static final int STATUS_RIDE_TO_PICK_UP = 1;

    public static final int STATUS_RIDE_TO_DESTINATION = 2;
    private static final int STATUS_RIDE_COMPLETED = 3;
    FirebaseDatabase database;
    private ProvideRideCallback callback;

    public FirebaseRide getFirebaseRide() {
        return firebaseRide;
    }

    public void setFirebaseRide(FirebaseRide firebaseRide) {
        this.firebaseRide = firebaseRide;
    }

    FirebaseRide firebaseRide;


    @Inject
    public RideRepository(DirectionsApiService directionsApiService, ApiService service, FirebaseDatabase database, DriverRepository driverRepository) {
        this.service = service;
        this.directionsApiService = directionsApiService;
        this.database = database;
        this.driverRepository = driverRepository;
    }

    FirebaseDriver driver;
    AvailableDriver availableDriver;
    FirebaseClient firebaseClient;

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
                getClient(ride.getClientId(), callback);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getClient(String clienId, ProvideRideCallback callback) {
//        firebaseClient = new FirebaseClient();
        database.getReference("clientsDb/" + clienId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (firebaseClient == null) {
//                    firebaseClient = new FirebaseClient();
//                }
                FirebaseClient client = dataSnapshot.getValue(FirebaseClient.class);
                firebaseClient = client;
                callback.onGetClientInfo(client);
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
    ArrayList<FirebaseFeedback> firebaseFeedbacks;

    public void acceptRide(AvailableDriver availableDriver) {
        this.availableDriver = availableDriver;
        firebaseRide.setDriver(availableDriver);
        this.acceptedRide = firebaseRide;
        firebaseFeedbacks = new ArrayList<>();
//        database.getReference("clientsDb/" + firebaseRide.getClientId()).child("feedbacks").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                GenericTypeIndicator<ArrayList<FirebaseFeedback>> t = new GenericTypeIndicator<ArrayList<FirebaseFeedback>>() {
//                };
//
//                firebaseFeedbacks = dataSnapshot.getValue(t);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        database.getReference("rides/" + firebaseRide.getId()).child("driver").setValue(availableDriver);
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
//
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

    //Update driver model
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

    public void pushDriverLocation(LatLng locationToLatLng) {
        availableDriver.setLatLng(locationToLatLng);
        firebaseRide.setDriver(availableDriver);
        database.getReference("rides/" + firebaseRide.getId()).child("driver").setValue(availableDriver);
    }

    public int getStatus() {
        return rideStatus;
    }

    public FirebaseClient getFirebaseClient() {

        return firebaseClient;
    }

    public void updateRideStatus(int i) {
        database.getReference("rides/" + firebaseRide.getId()).child("status").setValue(i);
    }

    public void leaveFeedback(String s, float rating) {
        if (firebaseFeedbacks == null) {
            firebaseFeedbacks = new ArrayList<>();
        }
        firebaseFeedbacks.add(0, new FirebaseFeedback(s, rating, driver.getDriverId()));

        database.getReference("clientsDb/" + firebaseRide.getClientId()).child("feedbacks").setValue(firebaseFeedbacks);
    }

    public void pushTimePrediction(String time) {
        if (firebaseRide != null) {
            database.getReference("rides/" + firebaseRide.getId()).child("predictedTime").setValue(time);
        }
    }

    public interface ProvideRideCallback {
//        void onGetRideRequest(FirebaseRide ridesItem);
        void onGetRideRequest(FirebaseRide ridesItem);

        void onGetClientInfo(FirebaseClient client);

        void onError(String error);
    }

    public void compareLocation(LatLng driverLocation, CompareLocationCallBack callBack) {
        Location from = new Location("from");
        from.setLatitude(driverLocation.latitude);
        from.setLongitude(driverLocation.longitude);
        Location to = new Location("locationB");
        if (rideStatus == STATUS_RIDE_TO_PICK_UP) {
            Log.d("tag", "rideStatus == STATUS_RIDE_TO_PICK_UP");
            to.setLatitude(acceptedRide.getPickUpLocation().getLat());
            to.setLongitude(acceptedRide.getPickUpLocation().getLng());
            Log.d("tag", "from.distanceTo(to)" + from.distanceTo(to));
            if (from.distanceTo(to) < 100) {
                callBack.driverNearPickUp();
            }
        } else if (rideStatus == STATUS_RIDE_TO_DESTINATION) {
            to.setLatitude(acceptedRide.getDestinationLocation().getLat());
            to.setLongitude(acceptedRide.getDestinationLocation().getLng());
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

    public void notifyDeclainedClient(String rideId) {
        database.getReference("rides/" + rideId).child("status").setValue(50);
    }


    public FirebaseRide getAcceptedRide() {
        return acceptedRide;
    }

    public void setAcceptedRide(FirebaseRide acceptedRide) {
        this.acceptedRide = acceptedRide;
    }

    public void updateTimePredictions(LatLng driverLocation, TimePredictionsCallBack callBack) {
        directionsApiService.getPredictedTime(driverLocation.latitude + "," + driverLocation.longitude,
                firebaseRide.getPickUpLocation().toString(), "AIzaSyC41CJUJMGe_9n44zKA0Jk1BAQ_pWp_p1o").enqueue(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                callBack.onTimePredicted(response.body().getRoutes().get(0).getLegs().get(0).getDuration().getText());
            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable t) {

            }
        });
    }
}
