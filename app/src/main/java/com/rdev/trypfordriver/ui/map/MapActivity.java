package com.rdev.trypfordriver.ui.map;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.material.navigation.NavigationView;
import com.rdev.trypfordriver.R;
import com.rdev.trypfordriver.data.model.FirebaseClient;
import com.rdev.trypfordriver.ui.CastomerActivity;
import com.rdev.trypfordriver.ui.expense_tracking.ExpenseActivity;
import com.rdev.trypfordriver.ui.login.LoginActivity;
import com.rdev.trypfordriver.ui.ready_to_trip.ReadyToTripFragment;
import com.rdev.trypfordriver.ui.report.ReportActivity;
import com.rdev.trypfordriver.ui.trip_history.TripHistoryActivity;
import com.rdev.trypfordriver.ui.welcome.WelcomeActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import dagger.android.support.DaggerAppCompatActivity;

public class MapActivity extends DaggerAppCompatActivity implements MapContract.View, OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {
    @Inject
    public MapPresenter presenter;

    private static final String TAG = "tag";
    private GoogleMap mMap;
    FragmentManager fm;
    public LatLng pickUpLocation;
    private Marker clientLocationMarker;
    private DrawerLayout mDrawerLayout;
    private TextView notification;
    Bitmap marker_bitmap;
    ImageView menu_icon;
    TextView pop_up_time;
    TextView pop_up_address;
    CardView popUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        presenter.attachView(this);
        Places.initialize(getApplicationContext(), "AIzaSyC41CJUJMGe_9n44zKA0Jk1BAQ_pWp_p1o");

        if (Build.VERSION.SDK_INT >= 23) {
            isStoragePermissionGranted();
        }
        fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(flags);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        mDrawerLayout = findViewById(R.id.drawer_layout);

        popUp = findViewById(R.id.pop_up);
        pop_up_address = findViewById(R.id.pop_up_address);
        pop_up_time = findViewById(R.id.pop_up_time);
        menu_icon = findViewById(R.id.menu_icon);
        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        MenuItem notificationItem = navigationView.getMenu().findItem(R.id.action_notification);
        notification = (TextView) notificationItem.getActionView();

//        notification.setText("2");

//        initCountDrawer();

//        notification = (TextView) MenuItemCompat.getActionProvider(navigationView.getMenu().getItem(R.id.action_notification));

        int height = 50;
        int width = 90;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_car);
        Bitmap b = bitmapdraw.getBitmap();
        marker_bitmap = Bitmap.createScaledBitmap(b, width, height, false);

    }

    private void initCountDrawer() {
//        notification.setGravity(Gravity.CENTER_VERTICAL);
        notification.setText("2");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return true;
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

    GroundOverlay currentPosMarker;


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        presenter.onMapReady();


        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));

        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            public void onCameraMove() {
                float zoom = mMap.getCameraPosition().zoom;
                if (zoom > 10) {
                    currentPosMarker.setDimensions((float) (Math.pow(2.5, 20 - zoom) + 40));
                }
            }
        });

    }

    @Override
    public void popBackStack() {
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            finish();
        }
    }

    @Override
    public void drawRoute(LatLng from, final LatLng to) {
        if (route != null && route.isVisible()) {
            route.remove();
        }
        if (clientLocationMarker != null && clientLocationMarker.isVisible()) {
            clientLocationMarker.remove();
        }
        clientLocationMarker = mMap.addMarker(new MarkerOptions().position(to)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.client_location_marker)));
        GoogleDirection.withServerKey("AIzaSyC41CJUJMGe_9n44zKA0Jk1BAQ_pWp_p1o")
                .from(from)
                .to(to)
                .transportMode(TransportMode.DRIVING)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if (direction.isOK()) {
                            double totalDistance;
                            for (int i = 0; i < direction.getRouteList().get(0).getLegList().size(); i++) {
                                Leg leg = direction.getRouteList().get(0).getLegList().get(i);
                                totalDistance = Integer.parseInt(leg.getDistance().getValue()) / 1000.0;
                                presenter.onFareCalculated(totalDistance);
                                Log.d("tag", "total distance = " + totalDistance);
                                ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
                                PolylineOptions polylineOptions = DirectionConverter.createPolyline(MapActivity.this, directionPositionList, 5, Color.BLUE);
                                route = mMap.addPolyline(polylineOptions);
                                route.setZIndex(12);
                            }
                            Display display = getWindowManager().getDefaultDisplay();
                            Point size = new Point();
                            display.getSize(size);
                            int padding = 200;
                            int width = size.x;
                            int height = size.y;

                            /**create for loop/manual to add LatLng's to the LatLngBounds.Builder*/
                            LatLngBounds.Builder builder = new LatLngBounds.Builder();
                            builder.include(to);
                            builder.include(pickUpLocation);

                            /**initialize the padding for map boundary*/
                            /**create the bounds from latlngBuilder to set into map camera*/
                            LatLngBounds bounds = builder.build();
                            /**create the camera with bounds and padding to set into map*/
                            final CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height / 2, padding);
                            mMap.animateCamera(cu);
                        } else {
                            // Do something
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        // Do something
                    }
                });
    }

    @Override
    public void showClientDetailActivity(FirebaseClient client) {
        Intent intent = new Intent(this, CastomerActivity.class);
        String clientName = client.getFirst_name() + " " + client.getLast_name();
        intent.putExtra("name", clientName);
        intent.putExtra("rating", client.getStars());
        intent.putExtra("photo", client.getPhoto());
        startActivity(intent);
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void cleanBackStack() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void clearMap() {
        if (clientLocationMarker != null) {
            clientLocationMarker.remove();
        }
        if (route != null) {
            route.remove();
        }
    }

    @Override
    public void openLoginActivity() {
//        startActivity(new Intent(this, WelcomeActivity.class));
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void updatePopUp(String address, String time) {
        if (popUp.getVisibility() == View.INVISIBLE) {
            popUp.setVisibility(View.VISIBLE);
        }
        pop_up_address.setText(address);
        pop_up_time.setText(time);
    }

    @Override
    public void hidePopUp() {
        popUp.setVisibility(View.INVISIBLE);
    }


    Polyline route;

    public void zoomToCurrentLocation() {
        LatLng currentPos = new LatLng(pickUpLocation.latitude, pickUpLocation.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPos, 17));
    }


    @Override
    public void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack("detail")
                .commit();
    }

    @Override
    public void moveCameraToCurrentPosition(Location location) {
        currentPos = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPos, 17));
        float zoom = mMap.getCameraPosition().zoom;
        if (zoom > 10) {
            currentPosMarker.setDimensions((float) (Math.pow(2.5, 20 - zoom) + 40));
        }
    }

    LatLng currentPos;

    @Override
    public void showCurrentLocation(Location location) {
        if (currentPos == null) {
            //Animate with zoom for first Location update
            currentPos = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPos, 17));
        } else {
            //Animate without Zoom
            currentPos = new LatLng(location.getLatitude(), location.getLongitude());
        }
        if (currentPosMarker == null) {
            currentPosMarker = mMap.addGroundOverlay(new GroundOverlayOptions().position(currentPos, 40f, 20.4f)
                    .image(BitmapDescriptorFactory.fromResource(R.drawable.marker_car)));
            currentPosMarker.setZIndex(100);
        }

        //Todo migrate to warn memory leak
        CarAnimation.animateMarkerToGB(currentPosMarker, location, new LatLngInterpolator.Spherical(), new BearingInterpolator.Degree());
        pickUpLocation = currentPos;
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.action_expense_tracking:
                startActivity(new Intent(MapActivity.this, ExpenseActivity.class));
                break;
            case R.id.action_reports:
                startActivity(new Intent(MapActivity.this, ReportActivity.class));
                break;
            case R.id.action_trip_history:
                startActivity(new Intent(MapActivity.this, TripHistoryActivity.class));
                break;
            case R.id.action_home:
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new ReadyToTripFragment())
                        .commit();
                break;
            case R.id.action_logout:
                presenter.onLogoutClick();
                break;
        }
        mDrawerLayout.closeDrawer(Gravity.LEFT);
        return true;
    }
}