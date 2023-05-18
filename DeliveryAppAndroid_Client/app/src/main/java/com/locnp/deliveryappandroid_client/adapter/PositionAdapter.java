package com.locnp.deliveryappandroid_client.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.locnp.deliveryappandroid_client.R;
import com.locnp.deliveryappandroid_client.model.PositionModel;

import java.util.List;

public class PositionAdapter extends RecyclerView.Adapter<PositionHolder> {
    private List<PositionModel> listOfPositions;

    public PositionAdapter(List<PositionModel> listOfPositions) {
        this.listOfPositions = listOfPositions;
    }

    @NonNull
    @Override
    public PositionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_position_item, parent, false);
        return new PositionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PositionHolder holder, int position) {
        PositionModel positionModel = listOfPositions.get(position);
        holder.tv_priority.setText(positionModel.getPriority());
        holder.tv_latitude.setText((int) positionModel.getLatitude());
        holder.tv_longitude.setText((int) positionModel.getLongitude());
        holder.tv_testData.setText(positionModel.getTestData());
    }

    @Override
    public int getItemCount() {
        return listOfPositions.size();
    }
}
