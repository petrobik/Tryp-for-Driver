package com.rdev.trypfordriver.data;

import com.rdev.trypfordriver.data.model.DriverLoginBody;
import com.rdev.trypfordriver.data.model.LoginModel;
import com.rdev.trypfordriver.data.model.LoginResponse;
import com.rdev.trypfordriver.data.model.NearbyDriver;
import com.rdev.trypfordriver.data.model.UserPhoneNumber;
import com.rdev.trypfordriver.data.model.VerifySmsResponse;
import com.rdev.trypfordriver.data.model.accept_ride_response.AcceptRideResponse;
import com.rdev.trypfordriver.data.model.driver_login_response.DriverLoginResponse;
import com.rdev.trypfordriver.data.model.favourite_driver.FavouriteDriver;
import com.rdev.trypfordriver.data.model.on_demand_rides.OnDemanRidesResponse;
import com.rdev.trypfordriver.data.model.ride_responce.RequestRideBody;
import com.rdev.trypfordriver.data.model.ride_responce.RideResponse;
import com.rdev.trypfordriver.data.model.status_response.StatusResponse;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    //driver api
    @POST("api/v1/driver_login")
    Call<DriverLoginResponse> login_driver(@Body DriverLoginBody body);

    @GET("api/v1/driver_get_favorite_rides")
    Call<Response> driver_get_favorite_rides(@Query("user_id") String userId);

    @GET("api/v1/driver_get_ondemand_rides")
    Call<OnDemanRidesResponse> driver_get_ondemand_rides(@Query("user_id") String userId);

    @POST("api/v1/driver_accept_ride")
    Call<AcceptRideResponse> driver_accept_ride(@Body AcceptRideBody body);


    //CustomerApi

    @POST("api/v1/send_sms")
    Call<LoginResponse> sendSms(@Body UserPhoneNumber model);

    @POST("api/v1/verify_sms")
    Call<VerifySmsResponse> verifySms(@Body LoginModel number);

    @GET("api/v1/get_nearby_drivers")
    Call<NearbyDriver> get_nearby_drivers(@Query("lat") double lat, @Query("lng") double lng);

    @GET("api/v1/get_favourite_drivers")
    Call<FavouriteDriver> get_favourite_drivers(@Query("user_id") int user_id);

    @POST("api/v1/ride_request")
    Call<RideResponse> ride_request(@Body RequestRideBody body);

    @GET("api/v1/ride_request_status")
    Call<StatusResponse> ride_request_status(@Query("ride_request_id") String id);

}
