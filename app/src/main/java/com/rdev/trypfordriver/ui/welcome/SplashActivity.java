package com.rdev.trypfordriver.ui.welcome;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.rdev.trypfordriver.R;
import com.rdev.trypfordriver.data.localDb.CachedDriver;
import com.rdev.trypfordriver.data.source.DriverRepository;
import com.rdev.trypfordriver.ui.map.MapActivity;

import javax.inject.Inject;

import androidx.lifecycle.Observer;
import dagger.android.support.DaggerAppCompatActivity;

public class SplashActivity extends DaggerAppCompatActivity {
    @Inject
    DriverRepository driverRepository;
    CachedDriver cachedDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        driverRepository.getCachedDriverLiveData().observe(this, new Observer<CachedDriver>() {
            @Override
            public void onChanged(CachedDriver cachedDriver) {
                SplashActivity.this.cachedDriver = cachedDriver;
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (cachedDriver != null) {
                    driverRepository.setDriver(cachedDriver);
                    intent = new Intent(SplashActivity.this, MapActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 2000);


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
