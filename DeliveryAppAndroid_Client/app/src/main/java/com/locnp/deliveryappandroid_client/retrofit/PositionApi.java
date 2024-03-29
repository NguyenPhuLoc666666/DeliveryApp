package com.locnp.deliveryappandroid_client.retrofit;


import com.locnp.deliveryappandroid_client.model.PositionModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PositionApi {
    @GET("/position/get-all")
    Call<List<PositionModel>> getAllPositions();

    @POST("/position/save")
    Call<PositionModel> save(@Body PositionModel positionModel);
}
