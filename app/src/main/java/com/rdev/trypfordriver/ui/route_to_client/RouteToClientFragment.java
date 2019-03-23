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
    Button decline_btn;
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
        acceptRide = v.findViewById(R.id.accept_btn);
        decline_btn = v.findViewById(R.id.decline_btn);
        decline_btn.setOnClickListener(this);
        acceptRide.setOnClickListener(this);
        backBtn = v.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        MapActivity mapActivity = ((MapActivity) getActivity());
        if (mapActivity!=null) {
            switch (view.getId()) {
                case R.id.back_btn:
                    mapActivity.presenter.onBackClick();
                    break;
                case R.id.decline_btn:
                    mapActivity.presenter.onDeclineRequest();
                    break;
                case R.id.accept_btn:
                    mapActivity.presenter.onRideRequestClick();
                    break;
            }
        }
    }
}
