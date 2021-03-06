package com.andrii_gerashchenko.weatherandrii;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.andrii_gerashchenko.weatherandrii.DTO.ChosenLocation;
import com.andrii_gerashchenko.weatherandrii.DTO.WeatherItem;
import com.andrii_gerashchenko.weatherandrii.DTO.WeatherLocation;
import com.andrii_gerashchenko.weatherandrii.adapters.WeatherItemAdapter;
import com.andrii_gerashchenko.weatherandrii.utils.Api;
import com.andrii_gerashchenko.weatherandrii.utils.DBHelper;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

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

    @BindView(R.id.lvWeatherContainer)
    ListView lvWeatherContainer;

    private ChosenLocation myLocation;
    private DBHelper mDBHelper;
    private WeatherItem mWeatherItem;
    private List<WeatherItem> mList;
    private WeatherItemAdapter mWeatherItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        initDB();
        mList = new ArrayList<>();
    }

    private void initDB() {
        mDBHelper = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        String jsonMyObject;

        if (extras != null) {
            jsonMyObject = extras.getString("location");
            myLocation = new Gson().fromJson(jsonMyObject, ChosenLocation.class);
            String text = myLocation.getCountryName() + " " + myLocation.getAdminArea() + " " + myLocation.getLocality();
            tvLocation.setText(text);
        }
    }

    @OnClick(R.id.btnCheckWeather)
    public void onCheckWeatherClick() {
        getWeather();
    }

    private void getWeather() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<WeatherLocation> call = api.getWeather(myLocation.getLatitude(), myLocation.getLongitude(), Api.units, Api.KEY);

        call.enqueue(new Callback<WeatherLocation>() {
            @Override
            public void onResponse(Call<WeatherLocation> call, Response<WeatherLocation> response) {
                Log.d("MYTAG", response.toString());
                WeatherLocation data = response.body();

                if (response.isSuccessful()) {
                    tvLocation.setText(getLocation());
                    setWeatherToDB(response);
                }
            }

            @Override
            public void onFailure(Call<WeatherLocation> call, Throwable t) {
            }
        });
    }

    private void setWeatherToDB(Response<WeatherLocation> response) {
        final SQLiteDatabase database = mDBHelper.getWritableDatabase();
        final ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_CITY, getLocation());
        contentValues.put(DBHelper.KEY_TEMP, response.body().getTemp());
        contentValues.put(DBHelper.KEY_DATE, response.body().getDate().toString());
        contentValues.put(DBHelper.KEY_ICON, response.body().getIconUrl());

        database.insert(mDBHelper.TABLE_WEATHER, null, contentValues);

        Cursor cursor = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_WEATHER
                + " WHERE " + DBHelper.KEY_CITY + " LIKE " + "'" + getLocation() + "'"
                + " ORDER BY " + DBHelper.KEY_TEMP, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int cityIndex = cursor.getColumnIndex(DBHelper.KEY_CITY);
            int tempIndex = cursor.getColumnIndex(DBHelper.KEY_TEMP);
            int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
            int iconIndex = cursor.getColumnIndex(DBHelper.KEY_ICON);

            mList.clear();
            do {
                int id = cursor.getInt(idIndex);
                String city = cursor.getString(cityIndex);
                String temp = cursor.getString(tempIndex);
                String date = cursor.getString(dateIndex);
                String icon = cursor.getString(iconIndex);

                mWeatherItem = new WeatherItem(city, temp, date, icon);
                mList.add(mWeatherItem);

                mWeatherItemAdapter = new WeatherItemAdapter(this, mList);
                lvWeatherContainer.setAdapter(mWeatherItemAdapter);

            } while (cursor.moveToNext());
        } else

            cursor.close();
        mDBHelper.close();
    }

    private String getLocation() {
        String location = "";
        if (!(myLocation.getCountryName().equals("null"))) {
            location += myLocation.getCountryName();
        }
        if (!(myLocation.getAdminArea().equals("null"))) {
            location += " " + myLocation.getAdminArea();
        }
        if (!myLocation.getLocality().equals("null")) {
            location += " " + myLocation.getLocality();
        }
        return location;
    }
}