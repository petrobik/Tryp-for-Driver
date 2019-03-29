package com.rdev.trypfordriver.data.source;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rdev.trypfordriver.data.localDb.CachedDriver;
import com.rdev.trypfordriver.data.localDb.DriverDao;
import com.rdev.trypfordriver.data.localDb.DriverRoomDatabase;
import com.rdev.trypfordriver.data.model.firebase_model.AvailableDriver;
import com.rdev.trypfordriver.data.model.firebase_model.FirebaseDriver;
import com.rdev.trypfordriver.data.model.firebase_model.FirebaseVehicle;
import com.rdev.trypfordriver.data.model.firebase_model.LatLngWrapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

@Singleton
public class DriverRepository implements ValueEventListener {
    FirebaseDatabase database;

    private String driverId; //test value, driver id will be available after login
    private boolean isDriverAvailable = false;
    DatabaseReference currentDriverReference;
    DatabaseReference availableDriversReference;
    //Driver model
    FirebaseDriver driver;
    AvailableDriver availableDriver;
    LatLngWrapper wrapper;
    DriverRoomDatabase driverRoomDatabase;
    DriverDao driverDao;
    LiveData<CachedDriver> cachedDriverLiveData;
    CachedDriver cachedDriver;


    @Inject
    public DriverRepository(FirebaseDatabase database, DriverRoomDatabase db) {
        this.database = database;
        this.driverRoomDatabase = db;
        driverDao = driverRoomDatabase.driverDao();
        cachedDriverLiveData = driverDao.getCurrentDriver();
        availableDriversReference = database.getReference("available_drivers");
    }

    public void updateDriverLocation(LatLng driverLocation) {
        wrapper = new LatLngWrapper();
        wrapper.setLocation(driverLocation);
        availableDriver.setLocation(wrapper);
        if (isDriverAvailable) {
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

    public void initModels(String id) {
        if (availableDriver == null) {
            availableDriver = new AvailableDriver();
        }
        availableDriver.setId(id);

        currentDriverReference = database.getReference("driversDb/" + driverId);
        currentDriverReference.addListenerForSingleValueEvent(this);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        Log.d("tag", "onDataChange in initModels");
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

    public LiveData<CachedDriver> getCachedDriverLiveData() {
        return cachedDriverLiveData;
    }

    public void insertCachedDriver(CachedDriver cachedDriver) {
        this.cachedDriver = cachedDriver;
        driverId = Integer.toString(cachedDriver.getDriverId());
        initModels(driverId);
        new insertAsyncTask(driverDao).execute(cachedDriver);
    }

    public void setDriver(CachedDriver cachedDriver) {
        this.cachedDriver = cachedDriver;
        driverId = Integer.toString(cachedDriver.getDriverId());
        initModels(driverId);
    }

    public void deleteCachedDriver() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                driverDao.deleteAll();
            }
        }).start();
    }

    public void declineRideRequest() {

        currentDriverReference.child("rideId").removeValue();
    }

    public void deleteAttachedRide() {
        currentDriverReference.child("rideId").removeValue();
    }

    private static class insertAsyncTask extends AsyncTask<CachedDriver, Void, Void> {

        private DriverDao mAsyncTaskDao;

        insertAsyncTask(DriverDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CachedDriver... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
