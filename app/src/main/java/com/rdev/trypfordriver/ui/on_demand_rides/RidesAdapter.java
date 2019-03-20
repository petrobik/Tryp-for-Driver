package com.rdev.trypfordriver.ui.on_demand_rides;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.rdev.trypfordriver.R;
import com.rdev.trypfordriver.data.model.on_demand_rides.RidesItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class RidesAdapter extends RecyclerView.Adapter<RidesAdapter.RideHolder> {
    List<RidesItem> data;
    OnRidePickListener listener;

    interface OnRidePickListener {
        void onRide(RidesItem item);
    }

    public RidesAdapter(List<RidesItem> data, OnRidePickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RideHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RideHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ride_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RideHolder holder, int position) {
        holder.fromTv.setText(data.get(position).getPickupAddress());
        holder.toTv.setText(data.get(position).getDestinationAddress());
        if (data.get(position).getFare() != null) {
            holder.fareBtn.setText("$" + data.get(position).getFare().toString());
        }
    }

    class RideHolder extends RecyclerView.ViewHolder {
        TextView fromTv;
        TextView toTv;
        Button fareBtn;

        public RideHolder(@NonNull View itemView) {
            super(itemView);
            fromTv = itemView.findViewById(R.id.from_tv);
            fareBtn = itemView.findViewById(R.id.fare_btn);
            toTv = itemView.findViewById(R.id.to_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onRide(data.get(getAdapterPosition()));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
