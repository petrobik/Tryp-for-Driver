package com.rdev.trypfordriver.ui.welcome;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rdev.trypfordriver.R;
import com.rdev.trypfordriver.data.localDb.CachedDriver;
import com.rdev.trypfordriver.data.model.Driver;
import com.rdev.trypfordriver.data.source.DriverRepository;
import com.rdev.trypfordriver.ui.map.MapActivity;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import dagger.android.support.DaggerAppCompatActivity;

public class SplashActivity extends DaggerAppCompatActivity {
    @Inject
    DriverRepository driverRepository;
    CachedDriver cachedDriver;

    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference drivers;
    private FirebaseAuth.AuthStateListener authStateListener;
    FirebaseUser user;

    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        auth = FirebaseAuth.getInstance();

//        auth.signOut();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = auth.getCurrentUser();
            }
        };


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
                if (user != null) {
                    String userId = user.getUid();
                    DatabaseReference driverDb = FirebaseDatabase.getInstance().getReference().child("driversDb").child(userId);

                    driverDb.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Driver driver = dataSnapshot.getValue(Driver.class);
//                            CachedDriver cachedDriver = new CachedDriver();
//                            cachedDriver.setFirstName(driver.getFirstName());
//                            cachedDriver.setLastName(driver.getLastName());
//                            driverRepository.setDriver(cachedDriver);
//                            driverRepository.setDriver(driver);
                            driverRepository.setDriver(userId);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    intent = new Intent(SplashActivity.this, MapActivity.class);

                } else {
                    Log.d(TAG, "user == null");
                    intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                }

//                if (cachedDriver != null) {
//                    driverRepository.setDriver(cachedDriver);
//                    intent = new Intent(SplashActivity.this, MapActivity.class);
//                } else {
//                    intent = new Intent(SplashActivity.this, WelcomeActivity.class);
//                }

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

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(authStateListener);
    }
}
