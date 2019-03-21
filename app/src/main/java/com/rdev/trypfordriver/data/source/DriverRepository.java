package com.rdev.trypfordriver.data.source;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rdev.trypfordriver.data.model.firebase_model.AvailableDriver;
import com.rdev.trypfordriver.data.model.firebase_model.Driver;
import com.rdev.trypfordriver.data.model.firebase_model.FirebaseDriver;
import com.rdev.trypfordriver.data.model.firebase_model.FirebaseVehicle;
import com.rdev.trypfordriver.data.model.firebase_model.LatLngWrapper;

import java.util.ArrayList;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DriverRepository {
    FirebaseDatabase database;

    private String driverId; //test value, driver id will be available after login
    private boolean isDriverAvailable;
    private LatLng driverLocation;
    DatabaseReference currentDriverReference;
    DatabaseReference availableDriversReference;
    FirebaseDriver driver;
    Driver availableDriver;
    LatLngWrapper wrapper;
    ArrayList<AvailableDriver> availableDrivers;

    @Inject
    public DriverRepository(FirebaseDatabase database) {
        this.database = database;
        availableDriversReference = database.getReference("available_drivers");
    }

    public LatLng getDriverLocation() {
        return driverLocation;
    }

    public void updateDriverLocation(LatLng driverLocation) {
        this.driverLocation = driverLocation;
        if (availableDriver == null) {
            availableDriver = new Driver();
        }
        wrapper = new LatLngWrapper();
        wrapper.setLocation(driverLocation);
        availableDriver.setLocation(wrapper);
        availableDriversReference.child(driverId).setValue(availableDriver);
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
            availableDriver = new Driver();
        }
        availableDriver.setId(id);
        ArrayList<Driver> drivers = new ArrayList<>();
        database.getReference("available_drivers/").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                drivers.add(dataSnapshot.getValue(Driver.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        currentDriverReference = database.getReference("driversDb/" + driverId);
        currentDriverReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                driver = dataSnapshot.getValue(FirebaseDriver.class);
                if (driver != null) {
                    Log.d("tag", "onDataChange" + driver.toString());
                } else {
                    //create new Driver
                    driver = new FirebaseDriver();
                    driver.setId(driverId);
                    driver.setCategory("Tryp");
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
        });
    }
}
