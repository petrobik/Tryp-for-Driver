package com.rdev.trypfordriver.ui.map;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rdev.trypfordriver.R;

import java.text.DecimalFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

@SuppressLint("ValidFragment")
public class RideCompletedFragment extends Fragment implements View.OnClickListener {
    ConstraintLayout parent_layout;
    CardView circle_cardView;
    FloatingActionButton backBtn;
    String price;
    TextView fareTv;

    @SuppressLint("ValidFragment")
    public RideCompletedFragment(double price) {
        DecimalFormat df = new DecimalFormat("0.00$");
        this.price = df.format(price);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ride_complete, container, false);
        fareTv = v.findViewById(R.id.fare_tv);
        fareTv.setText(price);
        parent_layout = v.findViewById(R.id.parent_layout);
        parent_layout.setOnClickListener(this);
        circle_cardView = v.findViewById(R.id.circle_cardView);
        circle_cardView.setOnClickListener(this);
        backBtn = v.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(view -> ((MapActivity) getActivity()).presenter.onBackClick());
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.parent_layout:
            case R.id.circle_cardView:
                ((MapActivity) getActivity()).presenter.onShowFeedbackFragment();
                break;
        }
    }
}
