package com.rdev.trypfordriver.ui.start_tryp;

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
public class StartTrypFragment extends Fragment {
    Button start_tryp_btn;
    String customerLocation;
    TextView customer_location_tv;
    private ImageButton backBtn;

    public StartTrypFragment(String customerLocation) {
        this.customerLocation = customerLocation;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.start_tryp_fragment, container, false);
        start_tryp_btn = v.findViewById(R.id.apply_otp_btn);
        customer_location_tv = v.findViewById(R.id.adress_tv);
        customer_location_tv.setText(customerLocation);
        start_tryp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MapActivity) getActivity()).presenter.onStartTrypClick();
            }
        });
        backBtn = v.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MapActivity) getActivity()).presenter.onBackClick();
            }
        });
        return v;
    }
}
