package com.rdev.trypfordriver.ui.detailFragment;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.rdev.trypfordriver.ui.map.MapActivity;
import com.rdev.trypfordriver.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class DetailHostFragment extends Fragment {
    ViewPager viewPager;
    ImageButton back_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.detail_host_fragment, container, false);
        ViewPager viewpager = v.findViewById(R.id.detail_viewpager);
        HostPagerAdapter adapter = new HostPagerAdapter(getChildFragmentManager());
        viewpager.setAdapter(adapter);
        viewpager.setPageMargin(dpToPx(8));
        back_btn = v.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MapActivity) getActivity()).popBackStack();
            }
        });

        return v;
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
