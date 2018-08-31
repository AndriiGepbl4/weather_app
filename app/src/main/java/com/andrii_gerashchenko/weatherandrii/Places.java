package com.andrii_gerashchenko.weatherandrii;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.andrii_gerashchenko.weatherandrii.DTO.ChosenLocation;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Places extends AppCompatActivity {

    @BindView(R.id.lvLocations)
    ListView lvLocations;

    private ArrayList<ChosenLocation> locations = new ArrayList<>();
    private ArrayList<String> locationsString = new ArrayList<>();

    private final static String MYTAG = "MYTAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        Log.d(MYTAG, "onResume");
        super.onResume();
        if (locations != null) {

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, locationsString);
            lvLocations.setAdapter(adapter);

            lvLocations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                    Intent intent = new Intent(Places.this, Weather.class);
//                    intent.putExtra("location", locations.get(position).getAdminArea());
//                    startActivity(intent);

                    Intent intent = new Intent(Places.this, Weather.class);
                    intent.putExtra("location", new Gson().toJson(locations.get(position)));
                    startActivity(intent);
                }
            });
        }
    }

    @OnClick(R.id.btnAddPlace)
    public void onAddPlaceClick() {
        Intent intent = new Intent(this, Map.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(MYTAG, "onActivityResult");

        String jsonMyObject;
        Bundle extras = data.getExtras();

        if (extras != null) {
            jsonMyObject = extras.getString("places");
            ChosenLocation myLocation = new Gson().fromJson(jsonMyObject, ChosenLocation.class);

            locations.add(myLocation);
            locationsString.add(myLocation.getCountryName() + " " + myLocation.getAdminArea() + " " + myLocation.getLocality());
        }
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        Log.d(MYTAG, "onSaveInstanceState");
//
//        outState.putStringArrayList("loc", locations);
//
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        Log.d(MYTAG, "onRestoreInstanceState");
//        super.onRestoreInstanceState(savedInstanceState);
//        locations = savedInstanceState.getStringArrayList("loc");
//    }
}
