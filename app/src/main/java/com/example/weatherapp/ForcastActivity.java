package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ForcastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forcast);

        // Read possible intend data.
        // if there is a city name read it and put it on the screen.
        String cityName = getIntent().getStringExtra("CITY_NAME");

        // let's put it on the screen.
        TextView weatherForcastCityName = findViewById(R.id.textViewForcastCityName);

        if(cityName != null) {
            weatherForcastCityName.setText(cityName);
        } else {
            weatherForcastCityName.setText("No city name given!");
        }

    }
}