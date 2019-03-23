package com.rdev.trypfordriver.ui.contact_to_user;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.rdev.trypfordriver.R;
import com.rdev.trypfordriver.data.model.firebase_model.FirebaseRide;
import com.rdev.trypfordriver.ui.map.MapActivity;
import com.rdev.trypfordriver.ui.map.MapPresenter;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

@SuppressLint("ValidFragment")
public class ContactToUserFragment extends Fragment implements View.OnClickListener {
    @Inject
    MapPresenter mapPresenter;
    FirebaseRide rides;
    TextView adress_tv;
    TextView client_name_tv;
    ImageView client_avatar_iv;
    ImageButton backBtn;
    Button otp_btn;

    @SuppressLint("ValidFragment")
    public ContactToUserFragment(FirebaseRide rides) {
        this.rides = rides;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contact_user, container, false);
        adress_tv = v.findViewById(R.id.adress_tv);
        if (rides.getToAddress() != null) {
            adress_tv.setText(rides.getFromAddress());
        }
        client_name_tv = v.findViewById(R.id.client_name_tv);
        client_name_tv.setOnClickListener(this);
        client_avatar_iv = v.findViewById(R.id.client_avatar_iv);
        client_avatar_iv.setOnClickListener(this);

        backBtn = v.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);

        otp_btn = v.findViewById(R.id.decline_btn);
        otp_btn.setOnClickListener(this);

        return v;
    }

    AlertDialog dialog;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                ((MapActivity) getActivity()).presenter.onBackClick();
                break;
            case R.id.client_avatar_iv:
            case R.id.client_name_tv:
                ((MapActivity) getActivity()).presenter.onClientClick();
                break;
            case R.id.decline_btn:
                Button apply_otp_button;
                View dialog_view = LayoutInflater.from(getContext()).inflate(R.layout.otp_dialog, null);
                apply_otp_button = dialog_view.findViewById(R.id.apply_otp_btn);
                apply_otp_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        ((MapActivity) getActivity()).presenter.onOtpEntered();
                    }
                });
                dialog = new AlertDialog.Builder(getContext())
                        .setView(dialog_view).create();
                dialog.show();
                break;
        }
    }
}
