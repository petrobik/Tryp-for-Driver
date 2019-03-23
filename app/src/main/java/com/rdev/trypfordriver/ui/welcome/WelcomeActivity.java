package com.rdev.trypfordriver.ui.welcome;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.FirebaseApp;
import com.rdev.trypfordriver.R;
import com.rdev.trypfordriver.data.source.DriverRepository;
import com.rdev.trypfordriver.ui.createAccount.CreateActivity;
import com.rdev.trypfordriver.ui.login.LoginActivity;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import dagger.android.support.DaggerAppCompatActivity;
import io.fabric.sdk.android.Fabric;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatButton login_btn;
    AppCompatButton sign_up_btn;
    private String TAG = "tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        Fabric.with(this, new Crashlytics());

        if (Build.VERSION.SDK_INT >= 23) {
            isStoragePermissionGranted();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(this);
        sign_up_btn = findViewById(R.id.sign_up_btn);
        sign_up_btn.setOnClickListener(this);
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
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
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.login_btn:
                intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                break;
            case R.id.sign_up_btn:
                intent = new Intent(WelcomeActivity.this, CreateActivity.class);
        }
        startActivity(intent);
    }
}
