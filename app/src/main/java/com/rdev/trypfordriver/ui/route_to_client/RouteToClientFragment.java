package com.rdev.trypfordriver.ui.route_to_client;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rdev.trypfordriver.R;
import com.rdev.trypfordriver.ui.map.MapActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

@SuppressLint("ValidFragment")
public class RouteToClientFragment extends Fragment implements View.OnClickListener {
    String pickUpAdress;
    TextView adressTv;
    Button acceptRide;
    private ImageButton backBtn;

    @SuppressLint("ValidFragment")
    public RouteToClientFragment(String pickupAdress) {
        this.pickUpAdress = pickupAdress;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_ride_activity, container, false);
        adressTv = v.findViewById(R.id.adress_tv);
        if (pickUpAdress != null) {
            adressTv.setText(pickUpAdress);
        }
        acceptRide = v.findViewById(R.id.apply_otp_btn);
        acceptRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MapActivity) getActivity()).presenter.onRideRequestClick();
            }
        });
        backBtn = v.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        ((MapActivity) getActivity()).presenter.onBackClick();
    }
}
