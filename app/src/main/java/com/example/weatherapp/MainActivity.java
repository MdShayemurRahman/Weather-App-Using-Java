package com.example.weatherapp;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
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
    String weatherDesc = "";
    double temperature = 0.0;
    double windSpeed = 0.0;

    String url = "https://api.openweathermap.org/data/2.5/weather";
    String appid = "641e6a95d36a4f8c2fbb81ab27ae53cb";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eCity = findViewById(R.id.editTextCityName);
        cityName = findViewById(R.id.textViewCityNames);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putDouble("temperature",temperature);
        savedInstanceState.putString("weatherDesc", weatherDesc);
        savedInstanceState.putDouble("windSpeed",windSpeed);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            savedInstanceState.getDouble("temperature",temperature);
            savedInstanceState.getString("weatherDesc", weatherDesc);
            savedInstanceState.getDouble("windSpeed",windSpeed);
        }
    }



    public void updateWeather(View view) {
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