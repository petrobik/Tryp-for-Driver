package com.rdev.trypfordriver.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rdev.trypfordriver.PickerPhoneDialog;
import com.rdev.trypfordriver.R;
import com.rdev.trypfordriver.SelectCountryListener;
import com.rdev.trypfordriver.data.model.UserPhoneNumber;
import com.rdev.trypfordriver.ui.createAccount.CreateActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import static com.rdev.trypfordriver.utill.Utill.getCountryCode;
import static com.rdev.trypfordriver.utill.Utill.getCountryName;
import static com.rdev.trypfordriver.utill.Utill.getDialingCode;

public class LoginFragment extends Fragment implements View.OnClickListener {
    ImageButton backBtn;
    TextView sign_up_tv;
    CardView send_code_card;
    EditText email_et;
    EditText password_et;
    CardView phone_card;
    TextView countrTv;
    EditText phone_number_et;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fragment, container, false);
        backBtn = v.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);
        countrTv = v.findViewById(R.id.country_tv);
        sign_up_tv = v.findViewById(R.id.sign_up_tv);
        sign_up_tv.setOnClickListener(this);
        send_code_card = v.findViewById(R.id.send_code_btn);
        send_code_card.setOnClickListener(this);
//        email_et = v.findViewById(R.id.email_et);
//        password_et = v.findViewById(R.id.login_password_et);
        phone_number_et = v.findViewById(R.id.phone_number_et);
        phone_card = v.findViewById(R.id.cardView2);
        phone_card.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                ((LoginActivity) getActivity()).onFinish();
                break;
            case R.id.sign_up_tv:
                startActivity(new Intent(getContext(), CreateActivity.class));
                ((LoginActivity) getActivity()).onFinish();
                break;
            case R.id.send_code_btn:
                ((LoginActivity) getActivity()).number.setPhone_number(phone_number_et.getText().toString());
                ((LoginActivity) getActivity()).onSendCode();

//                ((LoginActivity) getActivity()).onSendCode(email_et.getText().toString(), password_et.getText().toString());
                break;
            case R.id.cardView2:
                final PickerPhoneDialog dialog = new PickerPhoneDialog();
                dialog.showDialog(getContext(), new SelectCountryListener() {
                    @Override
                    public void onSelect(String data) {
                        dialog.hideDialog();
                        UserPhoneNumber number = ((LoginActivity) getActivity()).number;
                        number.setDialing_code(getDialingCode(data));
                        number.setCountry_code(getCountryCode(data));
                        countrTv.setText(getCountryName(data) + " (+" + number.getDialing_code() + ")");

                    }
                });
                break;
        }
    }

}
