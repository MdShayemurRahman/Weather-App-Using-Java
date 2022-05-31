package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;



public class MainActivity extends AppCompatActivity {
    // how to make a simple request: read more, https://google.github.io/volley/simple.html

    EditText eCity;
    TextView cityName;

    String url = "https://api.openweathermap.org/data/2.5/weather";
    String appid = "641e6a95d36a4f8c2fbb81ab27ae53cb";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void openForcast(View view) {
        // Create an intent to open ForecastActivity (navigate into other page)
        Intent intent = new Intent(this, ForcastActivity.class);

        // send some data with the other Activity with the intent.
        intent.putExtra("CITY_NAME", "Tampere");

        // start the activity through intent.
        startActivity(intent);
    }



    public void updateWeather(View view) {

        eCity = findViewById(R.id.editTextCityName);
        cityName = findViewById(R.id.textViewCityNames);

        String tempUrl = "";
        String city = eCity.getText().toString().trim();


        if(city.equals("")) {
            Toast.makeText(this, "City field cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            tempUrl = url + "?q=" + city + "&units=metric&appid=" + appid;
        }

        // make a post request..
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, response -> {
            Log.d("res", response);
            parseJsonAndUpdateUI(response);
        }, error -> Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void parseJsonAndUpdateUI(String response) {

        String weatherDesc = "";
        double temperature = 0.0;
        double windSpeed = 0.0;

        // parse the data from response
        // 1. Convert the response into JSONObject.
        try {
            JSONObject weather = new JSONObject(response);
            cityName.setText(eCity.getText());
            temperature = weather.getJSONObject("main").getDouble("temp");
            weatherDesc = weather.getJSONArray("weather").getJSONObject(0).getString("main");
            windSpeed = weather.getJSONObject("wind").getDouble("speed");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // 2. Update data in the Screen.
        TextView weatherDescTextView = findViewById(R.id.textViewWeatherDescription);
        weatherDescTextView.setText(weatherDesc);

        TextView temperatureTextView = findViewById(R.id.textViewTemperature);
        temperatureTextView.setText("" + temperature + " °C");

        TextView windSpeedTextView = findViewById(R.id.textViewWindSpeed);
        windSpeedTextView.setText("" + windSpeed + " m/s");
    }

}