package com.rdev.trypfordriver.ui.detailFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

class HostPagerAdapter extends FragmentStatePagerAdapter {

    public HostPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new DetailFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
