package com.rdev.trypfordriver.ui.map;

import com.rdev.trypfordriver.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class MapModule {
    @ActivityScoped
    @Binds
    abstract MapContract.Presenter mapPresenter(MapPresenter presenter);
}
