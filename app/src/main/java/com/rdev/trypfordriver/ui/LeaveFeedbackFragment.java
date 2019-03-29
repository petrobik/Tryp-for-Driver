package com.rdev.trypfordriver.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.rdev.trypfordriver.R;
import com.rdev.trypfordriver.ui.map.MapActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LeaveFeedbackFragment extends Fragment {
    FloatingActionButton backBtn;
    MaterialButton submit_button;
    TextInputEditText review_text;
    RatingBar customer_ratingBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.leave_feedback, container, false);
        backBtn = v.findViewById(R.id.back_btn);
        review_text = v.findViewById(R.id.review_text);
        customer_ratingBar = v.findViewById(R.id.customer_ratingBar);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MapActivity) getActivity()).presenter.onBackClick();
            }
        });
        submit_button = v.findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MapActivity) getActivity()).presenter.onSubmitFeedBack(review_text.getText().toString(),customer_ratingBar.getRating());
            }
        });
        return v;
    }
}
