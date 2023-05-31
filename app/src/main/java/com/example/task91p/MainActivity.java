package com.example.task91p;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button createAdvertBtn, showAdvertsBtn, showOnMapBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createAdvertBtn = findViewById(R.id.new_advert_btn);
        showAdvertsBtn = findViewById(R.id.show_adverts_btn);
        showOnMapBtn = findViewById(R.id.show_map_btn);

        createAdvertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CreateAdvertActivity.class);
                startActivity(i);
            }
        });

        showAdvertsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ShowAdvertsActivity.class);
                startActivity(i);
            }
        });

        showOnMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });

    }

    private void handleButtonClicks(Class activityClass){
        Intent i = new Intent(MainActivity.this, activityClass);
        startActivity(i);
    }
}