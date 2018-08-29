package com.andrii_gerashchenko.weatherandrii;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Places extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btnAddPlace)
    public void onAddPlaceClick(){
        Intent intent = new Intent(this, Map.class);
        startActivity(intent);
    }
}
