package com.rdev.trypfordriver.ui.ride;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rdev.trypfordriver.R;
import com.rdev.trypfordriver.ui.map.MapActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

@SuppressLint("ValidFragment")
public class RideFragment extends Fragment {
    String fromAdress;
    String toAdress;
    TextView from_tv;
    TextView to_tv;
    private ImageButton backBtn;

    public RideFragment(String fromAdress, String toAdress) {
        this.fromAdress = fromAdress;
        this.toAdress = toAdress;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ride_fragment, container, false);
        from_tv = v.findViewById(R.id.from_tv);
        to_tv = v.findViewById(R.id.to_tv);
        from_tv.setText(fromAdress);
        to_tv.setText(toAdress);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (getActivity() != null) {
//                    ((MapActivity) getActivity()).presenter.onRideFinishLocation();
//                }
//            }
//        }, 5000);
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
