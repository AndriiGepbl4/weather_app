package com.andrii_gerashchenko.weatherandrii;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.andrii_gerashchenko.weatherandrii.DTO.ChosenLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Places extends AppCompatActivity {

    @BindView(R.id.lvLocations)
    ListView lvLocations;

    private static final int ERROR_DIALOG_REQUEST = 9001;
    private ArrayList<ChosenLocation> locations = new ArrayList<>();
    private ArrayList<String> locationsString = new ArrayList<>();
    private ChosenLocation myLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        if (isServiceOk()) {
            ButterKnife.bind(this);
        }
    }

    private boolean isServiceOk() {
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Places.this);

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can resolve it
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(Places.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (locations != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_places, locationsString);
            lvLocations.setAdapter(adapter);

            lvLocations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent = new Intent(Places.this, Weather.class);
                    intent.putExtra("location", new Gson().toJson(locations.get(position)));
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String jsonMyObject;
        Bundle extras = data.getExtras();
        if (extras != null) {
            jsonMyObject = extras.getString("places");
            myLocation = new Gson().fromJson(jsonMyObject, ChosenLocation.class);
            setLists();
        }
    }

    private void setLists() {
        locations.add(myLocation);
        locationsString.add(myLocation.getCountryName() + " " + myLocation.getAdminArea() + " " + myLocation.getLocality());
    }

    @OnClick(R.id.btnAddPlace)
    public void onAddPlaceClick() {
        Intent intent = new Intent(this, Map.class);
        startActivityForResult(intent, 1);
    }
}
