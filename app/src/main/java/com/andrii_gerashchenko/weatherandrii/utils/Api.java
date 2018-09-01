package com.andrii_gerashchenko.weatherandrii.utils;

import com.andrii_gerashchenko.weatherandrii.DTO.WeatherLocation;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    String KEY = "7f86084167996c634b1d3e0df01a33e7";
    String BASE_URL = "http://api.openweathermap.org";
    String units = "metric";

    @GET("/data/2.5/weather")
    Call<WeatherLocation> getWeather(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("units") String units,
            @Query("appid") String appid
    );
}
