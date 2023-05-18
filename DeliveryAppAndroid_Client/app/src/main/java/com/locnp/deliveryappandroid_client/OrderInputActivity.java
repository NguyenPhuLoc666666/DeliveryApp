package com.locnp.deliveryappandroid_client;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.locnp.deliveryappandroid_client.model.PositionModel;
import com.locnp.deliveryappandroid_client.retrofit.PositionApi;
import com.locnp.deliveryappandroid_client.retrofit.RetrofitService;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderInputActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText priority;
    TextInputEditText latitude;
    TextInputEditText longitude;
    TextInputEditText testData;
    MaterialButton btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_input);

        initializeComponents();
    }

    private void initializeComponents() {
        priority = findViewById(R.id.form_textFieldPriority);
        latitude = findViewById(R.id.form_textFieldLatitude);
        longitude = findViewById(R.id.form_textFieldLongitude);
        testData = findViewById(R.id.form_textFieldTestData);
        btnSave = findViewById(R.id.form_buttonSave);
        btnSave.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.form_buttonSave:
                runButtonSaveEvent();
                break;
            case 2:
                //more button
                break;
        }
    }

    private void runButtonSaveEvent() {
        RetrofitService retrofitService = new RetrofitService();
        PositionApi positionApi = retrofitService.getRetrofit().create(PositionApi.class);

        int priority = Integer.parseInt(String.valueOf(this.priority.getText()));
        double latitude = Double.parseDouble(String.valueOf(this.latitude.getText()));
        double longitude = Double.parseDouble(String.valueOf(this.longitude.getText()));
        int testData = Integer.parseInt(String.valueOf(this.testData.getText()));

        PositionModel positionModel = new PositionModel();
        positionModel.setPriority(priority);
        positionModel.setLatitude(latitude);
        positionModel.setLongitude(longitude);
        positionModel.setTestData(testData);

        positionApi.save(positionModel)
                .enqueue(new Callback<PositionModel>() {
                    @Override
                    public void onResponse(@NonNull Call<PositionModel> call, @NonNull Response<PositionModel> response) {
                        Toast.makeText(OrderInputActivity.this, "save successful!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(@NonNull Call<PositionModel> call, @NonNull Throwable t) {
                        Toast.makeText(OrderInputActivity.this, "save failed!", Toast.LENGTH_SHORT).show();
                        Logger.getLogger(OrderInputActivity.class.getName()).log(Level.SEVERE, "Saving error occurred!", t);
                    }
                });
    }
}