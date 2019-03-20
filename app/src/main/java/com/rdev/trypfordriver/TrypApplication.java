package com.rdev.trypfordriver;

import android.app.Application;
import android.content.Context;

import com.rdev.trypfordriver.data.source.DriverRepository;
import com.rdev.trypfordriver.data.source.LocationRepository;
import com.rdev.trypfordriver.data.source.RideRepository;
import com.rdev.trypfordriver.di.DaggerAppComponent;

import javax.inject.Inject;

import androidx.multidex.MultiDex;
import dagger.Lazy;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * We create a custom {@link Application} class that extends  {@link DaggerApplication}.
 * We then override applicationInjector() which tells Dagger how to make our @Singleton Component
 * We never have to call `component.inject(this)` as {@link DaggerApplication} will do that for us.
 */
public class TrypApplication extends DaggerApplication {
    @Inject
    Lazy<LocationRepository> locationRepository;
    @Inject
    Lazy<RideRepository> rideRepository;
    @Inject
    Lazy<DriverRepository> driverRepositoryLazy;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

    /**
     * Our Espresso tests need to be able to get an instance of the {@link LocationRepository}
     * so that we can delete all tasks before running each test
     */

}
