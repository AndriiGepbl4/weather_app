package com.andrii_gerashchenko.weatherandrii;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.andrii_gerashchenko.weatherandrii.DTO.ChosenLocation;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Weather extends AppCompatActivity {

    @BindView(R.id.tvLocation)
    TextView tvLocation;

    @BindView(R.id.tvLat)
    TextView tvLat;

    @BindView(R.id.tvLong)
    TextView tvLond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
//        if (extras != null){
//            tvLocation.setText(extras.getString("location"));
//        }
        String jsonMyObject;
        if (extras != null) {
            jsonMyObject = extras.getString("location");
            ChosenLocation myLocation = new Gson().fromJson(jsonMyObject, ChosenLocation.class);
            String text = myLocation.getCountryName() + " " + myLocation.getAdminArea() + " " + myLocation.getLocality();
            tvLocation.setText(text);
            tvLat.setText("" + myLocation.getLatitude());
            tvLond.setText("" + myLocation.getLongitude());
        }
    }
}
