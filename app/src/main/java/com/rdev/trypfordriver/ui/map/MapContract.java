package com.rdev.trypfordriver.ui.map;


import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.rdev.trypfordriver.data.model.on_demand_rides.RidesItem;

import androidx.fragment.app.Fragment;

public interface MapContract {
    interface View {
        void showFragment(Fragment fragment);

        void showCurrentLocation(Location location);

        void popBackStack();

        void drawRoute(LatLng from, LatLng to);

        void showClientDetailActivity();

        void showToast(String s);

        void cleanBackStack();

        void clearMap();
    }

    interface Presenter {
        void onBackClick();

        void onCurrentLocationBtnClick();

        void onMapReady();

        void attachView(MapContract.View mView);

        void detachView();

        void onDriverAvailableClick(boolean isChecked);

        void onRideClick(RidesItem item);

        void onRideRequestClick();

        void onClientClick();

        //Driver arrived to pickup location
        void onStartTripLocate();

        //Driver arrived to destination location
        void onRideFinishLocation();

        void onStartTrypClick();

        void onStopTrypClick();

        void onRidePriceClick();

        void onOtpEntered();

        void onFareCalculated(double totalDistance);

        void onSubmitFeedBack();
    }
}
