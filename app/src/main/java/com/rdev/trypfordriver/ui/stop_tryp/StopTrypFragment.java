package com.rdev.trypfordriver.ui.stop_tryp;

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

public class StopTrypFragment extends Fragment {
    Button stopTrypBtn;
    String customerLocation;
    TextView customer_location_tv;
    ImageButton backBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.stop_tryp_fragment, container, false);
        stopTrypBtn = v.findViewById(R.id.stop_tryp_btn);
        customer_location_tv = v.findViewById(R.id.adress_tv);
        stopTrypBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MapActivity) getActivity()).presenter.onStopTrypClick();
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
