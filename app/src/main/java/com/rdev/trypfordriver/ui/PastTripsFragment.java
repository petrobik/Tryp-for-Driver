package com.rdev.trypfordriver.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.rdev.trypfordriver.R;
import com.rdev.trypfordriver.data.model.TripModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class PastTripsFragment extends Fragment {

    private RecyclerView mTripsRecyclerView;
    private TripsAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_past_trips, container, false);

        mTripsRecyclerView = view.findViewById(R.id.trips_recycler_view);
        mTripsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        List<TripModel> trips = new ArrayList<>();
//        trips.add(new TripModel("Clint", "333 Rolling Park, New York",
//                "7025 Washing Apt, Feltonport", "25 Jun at 5:30 PM",
//                true, R.drawable.portrait1));
//        trips.add(new TripModel("John", "333 Rolling Park, New York",
//                "7025 Washing Apt, Feltonport", "25 Jun at 5:30 PM",
//                true, R.drawable.portrait2));
//        trips.add(new TripModel("Vivek", "333 Rolling Park, New York",
//                "7025 Washing Apt, Feltonport", "25 Jun at 5:30 PM",
//                false, R.drawable.portrait3));
//        trips.add(new TripModel("John", "333 Rolling Park, New York",
//                "7025 Washing Apt, Feltonport", "25 Jun at 5:30 PM",
//                true, R.drawable.portrait2));
//
//        mAdapter = new TripsAdapter(trips);
//        mTripsRecyclerView.setAdapter(mAdapter);
        updateUI();

        return view;
    }

    private void updateUI() {
        List<TripModel> trips = new ArrayList<>();
        trips.add(new TripModel("Clint", "333 Rolling Park, New York",
                "7025 Washing Apt, Feltonport", "25 Jun at 5:30 PM",
                true, R.drawable.portrait1));
        trips.add(new TripModel("John", "333 Rolling Park, New York",
                "7025 Washing Apt, Feltonport", "25 Jun at 5:30 PM",
                true, R.drawable.portrait2));
        trips.add(new TripModel("Vivek", "333 Rolling Park, New York",
                "7025 Washing Apt, Feltonport", "25 Jun at 5:30 PM",
                false, R.drawable.portrait3));
        trips.add(new TripModel("John", "333 Rolling Park, New York",
                "7025 Washing Apt, Feltonport", "25 Jun at 5:30 PM",
                true, R.drawable.portrait2));

        mAdapter = new TripsAdapter(trips);
        mTripsRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private class TripsHolder extends RecyclerView.ViewHolder {

        private CircleImageView mAvatarImageView;
        private TextView mTripFromTextView;
        private TextView mTripToTextView;
        private MaterialButton mDateButton;
        private TextView mStatusTextView;
        private TextView mNameTextView;

        private TripModel mTripModel;

        public TripsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item, parent, false));

            mAvatarImageView = itemView.findViewById(R.id.avatar_imageview);
            mTripFromTextView = itemView.findViewById(R.id.from_textView);
            mTripToTextView = itemView.findViewById(R.id.to_textView);
            mStatusTextView = itemView.findViewById(R.id.status_textView);
            mDateButton = itemView.findViewById(R.id.date_button);
            mNameTextView = itemView.findViewById(R.id.client_name_textView);
        }

        public void bind(TripModel model) {
            mTripModel = model;
            mAvatarImageView.setImageResource(model.getAvatar());
            mTripFromTextView.setText(model.getTripFrom());
            mTripToTextView.setText(model.getTripTo());
            mDateButton.setText(model.getDate());
            mNameTextView.setText(model.getClientName());

            if (model.isStatus()) {
                mStatusTextView.setTextColor(getResources().getColor(R.color.confirmed));
                mStatusTextView.setText("Confirmed");
            } else {
                mStatusTextView.setTextColor(getResources().getColor(R.color.cancelled));
                mStatusTextView.setText("Cancelled");
            }
        }
    }

    private class TripsAdapter extends RecyclerView.Adapter<TripsHolder> {

        private TripModel mTripModel;

        private List<TripModel> mPastTrips;

        public TripsAdapter(List<TripModel> pastTrips) {
            mPastTrips = pastTrips;
        }

        @NonNull
        @Override
        public TripsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());

            return new TripsHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TripsHolder holder, int position) {
            TripModel model = mPastTrips.get(position);
            holder.bind(model);

        }

        @Override
        public int getItemCount() {
            return mPastTrips.size();
        }
    }



}
