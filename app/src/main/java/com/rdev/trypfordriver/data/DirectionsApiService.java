package com.rdev.trypfordriver.data;

import com.google.android.gms.maps.model.LatLng;
import com.rdev.trypfordriver.data.model.directions.DirectionsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DirectionsApiService {
    @GET("maps/api/directions/json")
    Call<DirectionsResponse> getPredictedTime(@Query("origin") String origin,
                                              @Query("destination") String destination,
                                              @Query("key") String key);
}
