package com.rdev.trypfordriver.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.rdev.trypfordriver.R;
import com.rdev.trypfordriver.ui.map.MapActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class MapNextTrip extends Fragment {
    CardView were_to;
    ImageButton location_btn;
    ImageButton back_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.next_fragment, container, false);
        were_to = v.findViewById(R.id.were_to);
        were_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ((MapActivity) getActivity()).pickAdress();
            }
        });
        location_btn = v.findViewById(R.id.location_btn);
        location_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MapActivity) getActivity()).zoomToCurrentLocation();
            }
        });
        back_btn = v.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MapActivity) getActivity()).popBackStack();
            }
        });
        return v;
    }
}
