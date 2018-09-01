package com.andrii_gerashchenko.weatherandrii;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.andrii_gerashchenko.weatherandrii.DTO.ChosenLocation;
import com.andrii_gerashchenko.weatherandrii.DTO.WeatherLocation;
import com.andrii_gerashchenko.weatherandrii.utils.Api;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Weather extends AppCompatActivity {

    @BindView(R.id.tvLocation)
    TextView tvLocation;

    @BindView(R.id.rvWeatherList)
    RecyclerView rvWeatherList;

    private ChosenLocation myLocation;
//    private LinearLayoutManager mLayoutManager;
//    private WeatherAdapter mWeatherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

//        mLayoutManager = new LinearLayoutManager(this);
//        rvWeatherList.setLayoutManager(mLayoutManager);
////---------------------
//        mWeatherAdapter = WeatherAdapter();
//        rvWeatherList.setAdapter(mWeatherAdapter);

        Bundle extras = getIntent().getExtras();
        String jsonMyObject;

        if (extras != null) {
            jsonMyObject = extras.getString("location");
            myLocation = new Gson().fromJson(jsonMyObject, ChosenLocation.class);
            String text = myLocation.getCountryName() + " " + myLocation.getAdminArea() + " " + myLocation.getLocality();
            tvLocation.setText(text);
        }
    }

    @OnClick (R.id.btnCheckWeather)
    public void onCheckWeatherClick(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call <WeatherLocation> call = api.getWeather(myLocation.getLatitude(), myLocation.getLongitude(),Api.units, Api.KEY);

        call.enqueue(new Callback<WeatherLocation>() {
            @Override
            public void onResponse(Call<WeatherLocation> call, Response<WeatherLocation> response) {
                Log.d("MYTAG", response.toString());
                WeatherLocation data = response.body();

                if (response.isSuccessful()) {
                    tvLocation.setText(data.getCity() + " " + data.getTempWithDegree());
//                    tvLocation.setText(data.get);
                }
            }

            @Override
            public void onFailure(Call<WeatherLocation> call, Throwable t) {

            }
        });
    }
}
