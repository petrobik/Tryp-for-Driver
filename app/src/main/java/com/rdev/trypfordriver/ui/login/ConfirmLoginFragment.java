package com.rdev.trypfordriver.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.rdev.trypfordriver.views.PinEntryEditText;
import com.rdev.trypfordriver.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ConfirmLoginFragment extends Fragment implements View.OnClickListener {
    ImageButton backBtn;
    ImageButton verifySmsBtn;
    PinEntryEditText pinEntryEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.confirm_layout, container, false);
        backBtn = v.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);
        pinEntryEditText = v.findViewById(R.id.pin_edit_text);
        verifySmsBtn = v.findViewById(R.id.send_btn);
        verifySmsBtn.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                ((LoginActivity) getActivity()).goBack();
                break;
            case R.id.send_btn:
                Toast.makeText(getContext(), "Verify", Toast.LENGTH_SHORT).show();
                ((LoginActivity) getActivity()).verifySms(pinEntryEditText.getText().toString());
//                ((LoginActivity) getActivity()).verifySms(pinEntryEditText.getText().toString());
        }
    }
}
