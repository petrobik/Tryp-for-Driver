package com.rdev.trypfordriver.data.source;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rdev.trypfordriver.data.model.firebase_model.AvailableDriver;
import com.rdev.trypfordriver.data.model.firebase_model.FirebaseDriver;
import com.rdev.trypfordriver.data.model.firebase_model.FirebaseVehicle;
import com.rdev.trypfordriver.data.model.firebase_model.LatLngWrapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;

@Singleton
public class DriverRepository implements ValueEventListener {
    FirebaseDatabase database;

    private String driverId; //test value, driver id will be available after login
    private boolean isDriverAvailable;
    DatabaseReference currentDriverReference;
    DatabaseReference availableDriversReference;
    //Driver model
    FirebaseDriver driver;
    AvailableDriver availableDriver;
    LatLngWrapper wrapper;

    @Inject
    public DriverRepository(FirebaseDatabase database) {
        this.database = database;
        availableDriversReference = database.getReference("available_drivers");
    }

    public void updateDriverLocation(LatLng driverLocation) {
        if (isDriverAvailable) {
            wrapper = new LatLngWrapper();
            wrapper.setLocation(driverLocation);
            availableDriver.setLocation(wrapper);
            availableDriversReference.child(driverId).setValue(availableDriver);
        }
    }

    public boolean isDriverAvailable() {
        return isDriverAvailable;
    }

    public void setDriverAvailable(boolean driverAvailable) {
        isDriverAvailable = driverAvailable;
        if (isDriverAvailable) {
            availableDriversReference.child(driverId)
                    .setValue(availableDriver);
        } else {
            availableDriversReference.child(driverId).removeValue();
        }
    }

    public String getDriverId() {
        return driverId;
    }

    public void setId(String id) {
        driverId = id;
        if (availableDriver == null) {
            availableDriver = new AvailableDriver();
        }
        availableDriver.setId(id);

        currentDriverReference = database.getReference("driversDb/" + driverId);
        currentDriverReference.addListenerForSingleValueEvent(this);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        Log.d("tag", "onDataChange in setId");
        driver = dataSnapshot.getValue(FirebaseDriver.class);
        //Check if driver model present in direBase
        if (driver != null) {
            Log.d("tag", "onDataChange" + driver.toString());
        } else {
            //create new Driver
            driver = new FirebaseDriver();
            driver.setId(driverId);
            driver.setCategory("Tryp");
            driver.setType("liftback");
            driver.setFirstName("Test");
            driver.setLastName("test");
            driver.setMaxLuggage(4);
            driver.setMaxPassenger(8);
            driver.setRating(4);
            FirebaseVehicle vehicle = new FirebaseVehicle();
            vehicle.setColor("#FFFFFF");
            vehicle.setImage("https://s.aolcdn.com/dims-global/dims3/GLOB/legacy_thumbnail/640x400/quality/80/https://s.aolcdn.com/commerce/autodata/images/USC70TSC024B021001.jpg");
            vehicle.setModel("Tesla Model S");
            driver.setVehicle(vehicle);
            driver.setImage("https://cdn1.iconfinder.com/data/icons/occupations-3/100/07-512.png");
            currentDriverReference.setValue(driver);
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        Log.d("tag", "onCancelled" + databaseError.getDetails());
    }
}
