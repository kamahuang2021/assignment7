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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //wire up the button to do stuff
        //1. to get the button
        Button btn = findViewById(R.id.btnAboutMe);
        //2. set what happens when the user click
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("MyApp", "Huixin Huang. huang.huix@northeaster.edu");
                Toast.makeText(getApplicationContext(), "Huixin Huang. huang.huix@northeastern.edu", Toast.LENGTH_LONG).show();
            }
        });

        clickClicky = (Button) findViewById(R.id.clickClicky);
        clickClicky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openClickClickyActivity();
            }
        });
    }

    public void openClickClickyActivity() {
        Intent intent = new Intent(this, ClickClickyActivity.class);
        startActivity(intent);
    }
}