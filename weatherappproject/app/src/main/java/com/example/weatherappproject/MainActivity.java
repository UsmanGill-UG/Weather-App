package com.example.weatherappproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView city_name1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() // Starts when the application is started
    {                        // when this activity comes back MainActivity2, this function is called again
        super.onStart();
       // Toast.makeText(MainActivity.this, "ACTIVITY 1 STARTED", Toast.LENGTH_SHORT).show();
        city_name1 = findViewById(R.id.City_textview);
        String cityname_s = getIntent().getStringExtra("city_name");
        String tempname_s = getIntent().getStringExtra("temperature_kind");
        city_name1.setText(cityname_s);
        String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=3097ed2aabbb5786b54513b7c67b2540", cityname_s);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    URL u = new URL(url);
                    BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));
                    String response = "";
                    String line;
                    while((line = in.readLine() )!=null){
                        response += line;
                    }
                    Log.d("***","***RESPONSE = "+response);

                    JSONObject j = new JSONObject(response);
                    int data = j.getJSONObject("main").getInt("temp");
                    int min_data = j.getJSONObject("main").getInt("temp_min");
                    int max_data = j.getJSONObject("main").getInt("temp_max");
                    int pressure_data = j.getJSONObject("main").getInt("pressure");
                    int humidity_data = j.getJSONObject("main").getInt("humidity");
                    int wind_speed = j.getJSONObject("wind").getInt("speed");
                    int wind_deg = j.getJSONObject("wind").getInt("deg");
                    Double lon = j.getJSONObject("coord").getDouble("lon");
                    Double lat = j.getJSONObject("coord").getDouble("lat");
                    int cloud = j.getJSONObject("clouds").getInt("all");
                    //Log.e("Temp:",tempname_s);
                    switch(tempname_s){
                        case "celcius":
                            data = data - 273;
                            min_data = min_data - 273;
                            max_data = max_data - 273;
                            break;
                        case "fahrenheit":
                            break;
                        default:
                            break;
                    }
                    final int newdata = data;
                    final int new_min_data = min_data;
                    final int new_max_data = max_data ;
                    final int new_pressure_data = pressure_data;
                    final int new_humidity_data = humidity_data;
                    final int new_wind_speed = wind_speed;
                    final int new_wind_deg = wind_deg;
                    final Double new_lon = lon;
                    final Double new_lat = lat;
                    final int new_cloud= cloud;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView tv = findViewById(R.id.textView);
                            TextView mint = findViewById(R.id.mintemp);
                            TextView maxt = findViewById(R.id.maxtemp);
                            TextView press = findViewById(R.id.pressure_tv);
                            TextView humid = findViewById(R.id.humidity_tv);
                            TextView wind_speed1 = findViewById(R.id.wind_tv);
                            TextView wind_deg1 = findViewById(R.id.wind_deg_tv);
                            TextView coords = findViewById(R.id.coord_tv);
                            TextView cloud1 = findViewById(R.id.cloud_tv);
                            switch (tempname_s){
                                case "celcius":
                                    tv.setText("temp : "+newdata+"°C");
                                    mint.setText("Min temp : "+new_min_data+"°C");
                                    maxt.setText("Max temp : "+new_max_data+"°C");
                                    break;
                                case "fahrenheit":
                                    tv.setText("temp : "+newdata+"F");
                                    mint.setText("Min temp : "+new_min_data+"F");
                                    maxt.setText("Max temp : "+new_max_data+"F");

                                    break;
                            }
                            press.setText("Pressure : "+ new_pressure_data + "hpa");
                            humid.setText("Humidity : "+ new_humidity_data);
                            wind_speed1.setText("Wind Speed : "+ new_wind_speed + "m/s");
                            wind_deg1.setText("Wind Degree : "+new_wind_deg + "°");
                            wind_deg1.setText("Wind Degree : "+new_wind_deg + "°");
                            coords.setText("Geo coords ["+new_lon+","+new_lat+ "]");
                            cloud1.setText("Clouds : "+new_cloud + "%");
                        }
                    });
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(r);
        t.start();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menu_inf =  getMenuInflater();
        menu_inf.inflate(R.menu.mymenu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //return super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.settings){
            Intent intent= new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }
}