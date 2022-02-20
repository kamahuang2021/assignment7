package com.example.numad22sp_huixinhuang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button clickClicky;
    private Button linkCollector;
    private Button btn;
    private Button locationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //wire up the button to do stuff
        //1. to get the button
        btn = findViewById(R.id.btnAboutMe);
        //2. set what happens when the user click
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });

        clickClicky = (Button) findViewById(R.id.clickClicky);
        clickClicky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openClickClickyActivity();
            }
        });

        linkCollector = (Button) findViewById(R.id.linkCollector);
        linkCollector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLinkCollectorActivity();
            }
        });

        locationBtn = (Button) findViewById(R.id.locationService);
        locationBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openLocationActivity();
            }
        });
    }

    private void openLocationActivity() {
        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
    }

    private void openLinkCollectorActivity() {
        Intent intent = new Intent(this, LinkCollector.class);
        startActivity(intent);
    }

    private void openClickClickyActivity() {
        Intent intent = new Intent(this, ClickClickyActivity.class);
        startActivity(intent);
    }

    private void openActivity2() {
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }
}