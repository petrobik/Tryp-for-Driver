package com.rdev.trypfordriver.ui.map;

import android.location.Location;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.rdev.trypfordriver.data.model.firebase_model.AvailableDriver;
import com.rdev.trypfordriver.data.model.firebase_model.FirebaseRide;
import com.rdev.trypfordriver.data.model.on_demand_rides.RidesItem;
import com.rdev.trypfordriver.data.source.DriverRepository;
import com.rdev.trypfordriver.data.source.FakeLocationRepository;
import com.rdev.trypfordriver.data.source.LocationRepository;
import com.rdev.trypfordriver.data.source.RideRepository;
import com.rdev.trypfordriver.di.ActivityScoped;
import com.rdev.trypfordriver.ui.LeaveFeedbackFragment;
import com.rdev.trypfordriver.ui.contact_to_user.ContactToUserFragment;
import com.rdev.trypfordriver.ui.ready_to_trip.ReadyToTripFragment;
import com.rdev.trypfordriver.ui.ride.RideFragment;
import com.rdev.trypfordriver.ui.route_to_client.RouteToClientFragment;
import com.rdev.trypfordriver.ui.start_tryp.StartTrypFragment;
import com.rdev.trypfordriver.ui.stop_tryp.StopTrypFragment;
import com.rdev.trypfordriver.utill.Utill;

import java.util.Calendar;

import javax.inject.Inject;

@ActivityScoped
public class MapPresenter implements MapContract.Presenter, RideRepository.CompareLocationCallBack, LocationRepository.ProvideLocationCallback {
    MapContract.View mView;
    FakeLocationRepository locationRepository;
    RouteToClientFragment routeToClientFragment;
    ReadyToTripFragment readyToTripFragment;
    RidesItem item;
    RideRepository rideRepository;
    Handler handler;
    Calendar calendar;
    DriverRepository driverRepository;


    public static int STATUS_RIDE_COMPLETED = 200;
    public static int STATUS_RIDE_STARTED = 100;
    public static int STATUS_DRIVER_ARRIVED_TO_CLIENT = 150;


    @Inject
    public MapPresenter(FakeLocationRepository locationRepository, RideRepository rideRepository, DriverRepository driverRepository) {
        this.locationRepository = locationRepository;
        this.rideRepository = rideRepository;
        this.driverRepository = driverRepository;
        handler = new Handler();
        calendar = Calendar.getInstance();
    }


    @Override
    public void onBackClick() {
        mView.popBackStack();
    }

    @Override
    public void onDeclineRequest() {
        mView.popBackStack();
        mView.clearMap();
        rideRepository.updateRideStatus(50);
        driverRepository.declineRideRequest();
    }

    @Override
    public void onCurrentLocationBtnClick() {
        Log.d("tag", "onCurrentLocationBtnClick");
        if (locationRepository.getCachedLocation() != null) {
            mView.moveCameraToCurrentPosition(locationRepository.getCachedLocation());
        }
    }

    @Override
    public void onMapReady() {
        Log.d("tag", "OnMapReady presenter");
        if (locationRepository.getCachedLocation() != null) {
            mView.showCurrentLocation(locationRepository.getCachedLocation());
        }
        locationRepository.registerLocationListener(this);

    }

    @Override
    public void attachView(MapContract.View mView) {
        this.mView = mView;
        readyToTripFragment = new ReadyToTripFragment();
        readyToTripFragment.setAvailable(driverRepository.isDriverAvailable());
        mView.showFragment(readyToTripFragment);
    }

    @Override
    public void detachView() {
        this.mView = null;
        driverRepository.setDriverAvailable(false);
    }

    @Override
    public void onDriverAvailableClick(boolean isChecked) {
        Log.d("tag", "onDriverAvailableClick");
        driverRepository.setDriverAvailable(isChecked);
        if (driverRepository.isDriverAvailable()) {
            rideRepository.setRideListener(new RideRepository.ProvideRideCallback() {
                //We are getting ride request and show it to driver
                @Override
                public void onGetRideRequest(FirebaseRide ridesItem) {
                    if (mView != null) {
                        routeToClientFragment = new RouteToClientFragment(ridesItem.getFromAddress());
                        mView.showFragment(routeToClientFragment);
                        mView.drawRoute(locationRepository.getCachedLatLng(), ridesItem.getPickUpLocation().getLocation());
                    }
                }

                @Override
                public void onError(String error) {

                }
            });
        }
    }

    @Override
    public void onRideClick(RidesItem item) {
        this.item = item;
        Log.d("tag", "onRideClick");
        routeToClientFragment = new RouteToClientFragment(item.getPickupAddress());
        mView.showFragment(routeToClientFragment);
        mView.drawRoute(Utill.locationToLatLng(locationRepository.getCachedLocation()),
                new LatLng(item.getPickupLat(), item.getPickupLng()));
    }

    @Override
    public void onRideRequestClick() {
        Log.d("tag", "onRideRequestClick");
        AvailableDriver driver = new AvailableDriver(driverRepository.getDriverId());
        driver.setLatLng(Utill.locationToLatLng(locationRepository.getCachedLocation()));
        rideRepository.acceptRide(driver);
        rideRepository.rideToPickUp();
        driverRepository.setDriverAvailable(false);
        mView.showFragment(new ContactToUserFragment(rideRepository.getFirebaseRide()));
//                rideRepository.rideToPickUp();
//        rideRepository.acceptRide("18406", item.getRideRequestId(), new RideRepository.onAcceptRideCallBack() {
//            @Override
//            public void onRideAccepted(Rides rides) {
//                mView.showFragment(new ContactToUserFragment(rides));
//                rideRepository.rideToPickUp();
//            }
//
//            @Override
//            public void onError(String error) {
//                mView.showToast(error);
//            }
//        });
    }

    @Override
    public void onClientClick() {
        Log.d("tag", "onClientClick");
        mView.showClientDetailActivity();
    }

    @Override
    public void onStartTrypClick() {
        Log.d("tag", "onStartTrypClick");
        FirebaseRide acceptedRide = rideRepository.getAcceptedRide();
        mView.drawRoute(acceptedRide.getPickUpLocation().getLocation(),
                acceptedRide.getDestinationLocation().getLocation());
        rideRepository.updateRideStatus(STATUS_RIDE_STARTED);
        mView.showFragment(new RideFragment(acceptedRide.getFromAddress(), acceptedRide.getToAddress()));
    }

    @Override
    public void onStopTrypClick() {
        Log.d("tag", "onStopTrypClick");
        rideRepository.rideCompleted();
        rideRepository.updateRideStatus(STATUS_RIDE_COMPLETED);
        driverRepository.deleteAttachedRide();
        mView.showFragment(new RideCompletedFragment(rideRepository.getAcceptedRide().getFare()));
    }


    @Override
    public void onShowFeedbackFragment() {
        Log.d("tag", "onShowFeedbackFragment");
        mView.showFragment(new LeaveFeedbackFragment());
    }

    @Override
    public void onOtpEntered() {
        Log.d("tag", "onOtpEntered");
        mView.showFragment(new StartTrypFragment(rideRepository.getAcceptedRide().getFromAddress()));
    }

    @Override
    public void onFareCalculated(double totalDistance) {
//        if (rideRepository.getAcceptedRide() != null) {
//            rideRepository.getAcceptedRide().setFare(totalDistance);
//        }
    }

    @Override
    public void onSubmitFeedBack(String s, float rating) {
        rideRepository.leaveFeedback(s, rating);
        rideRepository.setAcceptedRide(null);
        mView.cleanBackStack();
        mView.clearMap();
        mView.showFragment(new ReadyToTripFragment());
    }

    @Override
    public void onLogoutClick() {
        driverRepository.deleteCachedDriver();
        mView.openLoginActivity();
    }

    @Override
    public void onCreateReadyToTripFragment() {
        if (readyToTripFragment != null) {
            readyToTripFragment.setAvailable(driverRepository.isDriverAvailable());
        }
    }


    @Override
    public void driverNearPickUp() {
        Log.d("tag", "driverNearPickUp");
        rideRepository.rideToDestination();
        rideRepository.updateRideStatus(STATUS_DRIVER_ARRIVED_TO_CLIENT);
        mView.showFragment(new StartTrypFragment(rideRepository.getAcceptedRide().getFromAddress()));
    }

    @Override
    public void driverNearDestination() {
        Log.d("tag", "driverNearDestination");
        mView.showFragment(new StopTrypFragment(rideRepository.getAcceptedRide().getToAddress()));
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("tag", "OnLocationChange");
        driverRepository.updateDriverLocation(Utill.locationToLatLng(location));
        if (mView != null) {
            if (rideRepository.getAcceptedRide() != null) {
                rideRepository.pushDriverLocation(Utill.locationToLatLng(location));
                rideRepository.compareLocation(Utill.locationToLatLng(location), this);
            }
        }
        mView.showCurrentLocation(location);
    }
}
