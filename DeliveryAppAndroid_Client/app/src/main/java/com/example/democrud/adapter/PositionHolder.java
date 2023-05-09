package com.example.democrud.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.democrud.R;

public class PositionHolder extends RecyclerView.ViewHolder {

    TextView tv_priority, tv_latitude, tv_longitude, tv_testData;

    public PositionHolder(@NonNull View itemView) {
        super(itemView);
        tv_priority = itemView.findViewById(R.id.positionListItem_priority);
        tv_latitude = itemView.findViewById(R.id.positionListItem_latitude);
        tv_longitude = itemView.findViewById(R.id.positionListItem_longitude);
        tv_testData = itemView.findViewById(R.id.positionListItem_testData);
    }
}
