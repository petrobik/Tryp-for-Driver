package com.rdev.trypfordriver.ui.map;


import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.rdev.trypfordriver.data.model.FirebaseClient;
import com.rdev.trypfordriver.data.model.on_demand_rides.RidesItem;

import androidx.fragment.app.Fragment;

public interface MapContract {
    interface View {
        void showFragment(Fragment fragment);

        void moveCameraToCurrentPosition(Location location);

        void showCurrentLocation(Location location);

        void popBackStack();

        void drawRoute(LatLng from, LatLng to);

        void showClientDetailActivity(FirebaseClient client);

        void showToast(String s);

        void cleanBackStack();

        void clearMap();

        void openLoginActivity();

        void updatePopUp(String adress, String time);

        void hidePopUp();
    }

    interface Presenter {
        void onBackClick();

        void onDeclineRequest();

        void onCurrentLocationBtnClick();

        void onMapReady();

        void attachView(MapContract.View mView);

        void detachView();

        void onDriverAvailableClick(boolean isChecked);

        void onRideClick(RidesItem item);

        void onRideRequestClick();

        void onClientClick();

        void onStartTrypClick();

        void onStopTrypClick();

        void onShowFeedbackFragment();

        void onOtpEntered();

        void onFareCalculated(double totalDistance);

        void onSubmitFeedBack(String s, float rating);

        void onLogoutClick();

        void onCreateReadyToTripFragment();
    }
}
