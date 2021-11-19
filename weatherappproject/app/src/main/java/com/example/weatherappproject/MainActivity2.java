package com.example.weatherappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity2 extends AppCompatActivity {

    private TextView city_name;
    private Button button_ok;
    private RadioGroup Radio_Group;
    String temp_set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        city_name =findViewById(R.id.city_text);
        button_ok = findViewById(R.id.button_ok1);
        Radio_Group = findViewById(R.id.temperature_options);

        Radio_Group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.Celcius){
                    //Toast.makeText(MainActivity2.this, "Celcius", Toast.LENGTH_SHORT).show();
                    temp_set = "celcius";
                }
                else if(checkedId == R.id.Fahrenheit){
                    //Toast.makeText(MainActivity2.this, "Fahrenheit", Toast.LENGTH_SHORT).show();
                    temp_set = "fahrenheit";
                }
            }
        });

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String C_name = city_name.getText().toString();
                Intent intent = new Intent(MainActivity2.this,MainActivity.class);
                intent.putExtra("city_name",C_name);
                intent.putExtra("temperature_kind", temp_set);
                startActivity(intent);
            }
        });

    }

}