package com.locnp.deliveryappandroid_client;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.locnp.deliveryappandroid_client.adapter.PositionAdapter;
import com.locnp.deliveryappandroid_client.model.PositionModel;
import com.locnp.deliveryappandroid_client.retrofit.PositionApi;
import com.locnp.deliveryappandroid_client.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView =findViewById(R.id.listOfPositions_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadPostions();
    }

    private void loadPostions() {
        RetrofitService retrofitService = new RetrofitService();
        PositionApi positionApi =retrofitService.getRetrofit().create(PositionApi.class);
        positionApi.getAllPositions()
                .enqueue(new Callback<List<PositionModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<PositionModel>> call, @NonNull Response<List<PositionModel>> response) {
                        populateListView(response.body());
                        Toast.makeText(MainActivity.this, "Load list of positions successful!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<PositionModel>> call, @NonNull Throwable t) {
                        Toast.makeText(MainActivity.this, "Failed to load list of positions", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void populateListView(List<PositionModel> listOfPositions) {
        PositionAdapter positionAdapter = new PositionAdapter(listOfPositions);
        recyclerView.setAdapter(positionAdapter);
    }
}