package com.example.assignment.retrofit;

import com.example.assignment.model.DriverLogins;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NewsStoreAPI {
    @GET("/driverLogins/{driverId}")
    Call<DriverLogins> getDriverLogin(@Path("driverId") String driverId);
    @GET("/driverLogins/{driverId}/{date}")
    Call<DriverLogins> getTripDetailsByIdAndDate(@Path("driverId") String id, @Path("date") String date);
}
