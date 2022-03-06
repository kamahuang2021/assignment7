package com.example.numad22sp_huixinhuang;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class WeatherActivity extends AppCompatActivity {
    private Button button;
    private Button button2;
    private Button button3;
    private EditText getCity;
    private ListView lv_weatherReport;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        getCity = (EditText) findViewById(R.id.et_getCity);
        lv_weatherReport = (ListView) findViewById(R.id.lv_weatherReport);

        final WeatherDataService weatherDataService = new WeatherDataService(WeatherActivity.this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Interacting with weather API"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.setCancelable(false);

        //clickListeners for each button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // this did not return anything.
                        weatherDataService.getCityID(getCity.getText().toString(), new WeatherDataService.VolleyResponseListener() {
                            @Override
                            public void onError(String message) {
                                progressDialog.dismiss();
                                Toast.makeText(WeatherActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String cityID) {
                                progressDialog.dismiss();
                                Toast.makeText(WeatherActivity.this, "Returned an ID of " + cityID, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        weatherDataService.getCityForecastByID(getCity.getText().toString(), new WeatherDataService.ForeCastByIDResponse() {
                            @Override
                            public void onError(String message) {
                                progressDialog.dismiss();
                                Toast.makeText(WeatherActivity.this, "something wrong", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(List<WeatherReportModel> weatherReportModel) {
                                progressDialog.dismiss();
                                ArrayAdapter arrayAdapter = new ArrayAdapter(WeatherActivity.this, android.R.layout.simple_list_item_1, weatherReportModel);
                                lv_weatherReport.setAdapter(arrayAdapter);
                                //Toast.makeText(WeatherActivity.this, weatherReportModel.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        weatherDataService.getCityForecastByName(getCity.getText().toString(), new WeatherDataService.GetCityForecastByNameCallback() {
                            @Override
                            public void onError(String message) {
                                progressDialog.dismiss();
                                Toast.makeText(WeatherActivity.this, "something wrong", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(List<WeatherReportModel> weatherReportModel) {
                                progressDialog.dismiss();
                                ArrayAdapter arrayAdapter = new ArrayAdapter(WeatherActivity.this, android.R.layout.simple_list_item_1, weatherReportModel);
                                lv_weatherReport.setAdapter(arrayAdapter);
                                //Toast.makeText(WeatherActivity.this, weatherReportModel.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
        });

    }
}