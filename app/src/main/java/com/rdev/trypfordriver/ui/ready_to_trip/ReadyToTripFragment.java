package com.rdev.trypfordriver.ui.ready_to_trip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;
import com.rdev.trypfordriver.R;
import com.rdev.trypfordriver.ui.map.MapActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ReadyToTripFragment extends Fragment implements View.OnClickListener {
    ImageButton backBtn;
    ImageButton currentLocationBtn;
    LabeledSwitch aSwitch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ready_to_tryp, container, false);
        backBtn = v.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);
        currentLocationBtn = v.findViewById(R.id.current_location_btn);
        currentLocationBtn.setOnClickListener(this);
        aSwitch = v.findViewById(R.id.ready_switch);
        aSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                ((MapActivity) getActivity()).presenter.onDriverAvailableClick(isOn);
            }
        });
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                ((MapActivity) getActivity()).presenter.onBackClick();
                break;
            case R.id.current_location_btn:
                ((MapActivity) getActivity()).presenter.onCurrentLocationBtnClick();
                break;
        }
    }

}
