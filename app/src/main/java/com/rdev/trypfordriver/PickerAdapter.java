package com.rdev.trypfordriver;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.rdev.trypfordriver.utill.Utill.getCountryName;
import static com.rdev.trypfordriver.utill.Utill.getDialingCode;

class PickerAdapter extends RecyclerView.Adapter<PickerAdapter.ItemHolder> {
    String[] data;
    SelectCountryListener listener;

    public PickerAdapter(String[] mTestArray, SelectCountryListener listener) {
        data = mTestArray;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.phone_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.countryTv.setText(getCountryName(data[position])
                + " (+" + getDialingCode(data[position]) + ")");
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        TextView countryTv;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            countryTv = itemView.findViewById(R.id.country_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onSelect(data[getAdapterPosition()]);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.length;
    }
}
