package com.example.numad22sp_huixinhuang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class WeatherActivity extends AppCompatActivity {
    private Button button;
    private Button button2;
    private Button button3;
    private EditText getCity;
    private ListView lv_weatherReport;


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

        //clickListeners for each button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // this did not return anything.
                weatherDataService.getCityID(getCity.getText().toString(), new WeatherDataService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(WeatherActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(String cityID) {
                        Toast.makeText(WeatherActivity.this, "Returned an ID of "+ cityID, Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weatherDataService.getCityForecastByID(getCity.getText().toString(), new WeatherDataService.ForeCastByIDResponse() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(WeatherActivity.this, "something wrong", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModel) {
                        ArrayAdapter arrayAdapter = new ArrayAdapter(WeatherActivity.this, android.R.layout.simple_list_item_1, weatherReportModel);
                        lv_weatherReport.setAdapter(arrayAdapter);



                        //Toast.makeText(WeatherActivity.this, weatherReportModel.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

      button3.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View view) {
              weatherDataService.getCityForecastByName(getCity.getText().toString(), new WeatherDataService.GetCityForecastByNameCallback() {
                  @Override
                  public void onError(String message) {
                      Toast.makeText(WeatherActivity.this, "something wrong", Toast.LENGTH_SHORT).show();

                  }

                  @Override
                  public void onResponse(List<WeatherReportModel> weatherReportModel) {
                      ArrayAdapter arrayAdapter = new ArrayAdapter(WeatherActivity.this, android.R.layout.simple_list_item_1, weatherReportModel);
                      lv_weatherReport.setAdapter(arrayAdapter);



                      //Toast.makeText(WeatherActivity.this, weatherReportModel.toString(), Toast.LENGTH_SHORT).show();
                  }
              });

          }
      });

    }
}