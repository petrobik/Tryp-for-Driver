package com.rdev.trypfordriver.ui.on_demand_rides;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.rdev.trypfordriver.R;
import com.rdev.trypfordriver.data.model.on_demand_rides.RidesItem;
import com.rdev.trypfordriver.ui.map.MapActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RidesFragment extends Fragment implements View.OnClickListener, RidesAdapter.OnRidePickListener {
    RecyclerView ridesRv;
    ImageButton back_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.rides, container, false);
        ridesRv = v.findViewById(R.id.rides_rv);
        ridesRv.setLayoutManager(new LinearLayoutManager(getContext()));
        back_btn = v.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(this);
        return v;
    }

    public void showRidesList(List<RidesItem> data) {
        ridesRv.setAdapter(new RidesAdapter(data, this));
    }

    @Override
    public void onClick(View view) {
        ((MapActivity) getActivity()).presenter.onBackClick();
    }

    @Override
    public void onRide(RidesItem item) {
        ((MapActivity) getActivity()).presenter.onRideClick(item);
    }
}
