package com.rdev.trypfordriver.ui.map;

import android.location.Location;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.rdev.trypfordriver.data.model.accept_ride_response.Rides;
import com.rdev.trypfordriver.data.model.on_demand_rides.RidesItem;
import com.rdev.trypfordriver.data.source.DriverRepository;
import com.rdev.trypfordriver.data.source.LocationRepository;
import com.rdev.trypfordriver.data.source.RideRepository;
import com.rdev.trypfordriver.di.ActivityScoped;
import com.rdev.trypfordriver.ui.LeaveFeedbackFragment;
import com.rdev.trypfordriver.ui.contact_to_user.ContactToUserFragment;
import com.rdev.trypfordriver.ui.on_demand_rides.RidesFragment;
import com.rdev.trypfordriver.ui.ready_to_trip.ReadyToTripFragment;
import com.rdev.trypfordriver.ui.ride.RideFragment;
import com.rdev.trypfordriver.ui.route_to_client.RouteToClientFragment;
import com.rdev.trypfordriver.ui.start_tryp.StartTrypFragment;
import com.rdev.trypfordriver.ui.stop_tryp.StopTrypFragment;
import com.rdev.trypfordriver.utill.Utill;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.inject.Inject;

@ActivityScoped
public class MapPresenter implements MapContract.Presenter, RideRepository.CompareLocationCallBack, RideRepository.ProvideOnDemandsRidesCallback, LocationRepository.ProvideLocationCallback {
    MapContract.View mView;
    LocationRepository locationRepository;
    RidesFragment ridesFragment;
    RouteToClientFragment routeToClientFragment;
    RidesItem item;
    RideRepository rideRepository;
    Handler handler;
    Calendar calendar;
    DriverRepository driverRepository;


    @Inject
    public MapPresenter(LocationRepository locationRepository, RideRepository rideRepository, DriverRepository driverRepository) {
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
    public void onCurrentLocationBtnClick() {
        Log.d("tag", "onCurrentLocationBtnClick");
        if (locationRepository.getCachedLocation() != null) {
            mView.showCurrentLocation(locationRepository.getCachedLocation());
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
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    @Override
    public void onDriverAvailableClick(boolean isChecked) {
        Log.d("tag", "onDriverAvailableClick");
        driverRepository.setDriverAvailable(isChecked);
        if (driverRepository.isDriverAvailable()) {
            rideRepository.driver_get_ondemand_rides("18405", this);
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

        rideRepository.acceptRide("18406", item.getRideRequestId(), new RideRepository.onAcceptRideCallBack() {
            @Override
            public void onRideAccepted(Rides rides) {
                mView.showFragment(new ContactToUserFragment(rides));
                rideRepository.rideToPickUp();
            }

            @Override
            public void onError(String error) {
                mView.showToast(error);
            }
        });
    }

    @Override
    public void onClientClick() {
        Log.d("tag", "onClientClick");
        mView.showClientDetailActivity();
    }

    @Override
    public void onStartTripLocate() {
        Log.d("tag", "onStartTripLocate");
        mView.showFragment(new StartTrypFragment(item.getPickupAddress()));
    }

    @Override
    public void onRideFinishLocation() {
        Log.d("tag", "onRideFinishLocation");
        mView.showFragment(new StopTrypFragment());
    }

    @Override
    public void onStartTrypClick() {
        Log.d("tag", "onStartTrypClick");
        mView.drawRoute(new LatLng(item.getPickupLat(), item.getPickupLng()),
                new LatLng(item.getDestinationLat(), item.getDestinationLng()));
        mView.showFragment(new RideFragment(item.getPickupAddress(), item.getDestinationAddress()));
    }

    @Override
    public void onStopTrypClick() {
        Log.d("tag", "onStopTrypClick");
        rideRepository.rideCompleted();
        mView.showFragment(new RideCompletedFragment(rideRepository.getAcceptedRide().getFare() + "$"));
    }


    @Override
    public void onRidePriceClick() {
        Log.d("tag", "onRidePriceClick");
        mView.showFragment(new LeaveFeedbackFragment());
    }

    @Override
    public void onOtpEntered() {
        Log.d("tag", "onOtpEntered");
        mView.showFragment(new StartTrypFragment(item.getPickupAddress()));
    }

    @Override
    public void onFareCalculated(double totalDistance) {
        if (rideRepository.getAcceptedRide() != null) {
            rideRepository.getAcceptedRide().setFare(totalDistance);
        }
    }

    @Override
    public void onSubmitFeedBack() {
        mView.cleanBackStack();
        mView.showFragment(new ReadyToTripFragment());
    }

    @Override
    public void setDriverId(String driverId) {
        driverRepository.setId(driverId);
    }

    @Override
    public void driverNearPickUp() {
        Log.d("tag", "driverNearPickUp");
        rideRepository.rideToDestination();
        mView.showFragment(new StartTrypFragment(item.getPickupAddress()));
    }

    @Override
    public void driverNearDestination() {
        Log.d("tag", "driverNearDestination");
        mView.showFragment(new StopTrypFragment());
    }

    @Override
    public void onFindOnDemansRides(List<RidesItem> rides) {
        Log.d("tag", "onFindOnDemansRides");
        item = rides.get(rides.size() - 1);
        String updatedAt = item.getUpdatedAt();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(updatedAt);
            System.out.println(date);
        } catch (ParseException e) {
            Log.d("tag", e.toString());
            e.printStackTrace();
        }
        TimeZone fromTimezone = TimeZone.getDefault();
        TimeZone toTimezone = TimeZone.getTimeZone("UTC");

        long fromOffset = fromTimezone.getOffset(calendar.getTimeInMillis());
        long toOffset = toTimezone.getOffset(calendar.getTimeInMillis());

        long convertedTime = System.currentTimeMillis() - (fromOffset - toOffset);
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        if (convertedTime - date.getTime() < 5 * 60 * 1000) {
            if (mView != null) {
                mView.drawRoute(Utill.locationToLatLng(locationRepository.getCachedLocation()),
                        new LatLng(item.getPickupLat(), item.getPickupLng()));
                routeToClientFragment = new RouteToClientFragment(item.getPickupAddress());
                mView.showFragment(routeToClientFragment);
            }
        } else {
            handler.postDelayed(() -> {
                if (driverRepository.isDriverAvailable()) {
                    rideRepository.driver_get_ondemand_rides("18405", MapPresenter.this);
                }
            }, 10000);
        }
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("tag", "OnLocationChange");
        if (mView != null) {
            if (rideRepository.getAcceptedRide() != null) {
                rideRepository.pushLocationToFirebase(location);
            }
            if (driverRepository.isDriverAvailable()) {
                driverRepository.updateDriverLocation(Utill.locationToLatLng(location));
            } else {
                rideRepository.compareLocation(Utill.locationToLatLng(location), MapPresenter.this);
            }
            mView.showCurrentLocation(location);
        }
    }
}
