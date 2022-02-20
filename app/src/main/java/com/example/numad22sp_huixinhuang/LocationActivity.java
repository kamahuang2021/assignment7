package com.example.numad22sp_huixinhuang;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class LocationActivity extends AppCompatActivity {
    final int REQUEST_CODE = 10;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private TextView longitudeText;
    private TextView latitudeText;
    private AlertDialog.Builder dialogBuilder;

    // Jumps to settings page to edit app permission.
    public void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        longitudeText = findViewById(R.id.longitude);
        latitudeText = findViewById(R.id.latitude);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                setLocation(location.getLongitude(), location.getLatitude());
            }
        };

        Activity currActivity = this;
        dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("No enough permission")
                .setMessage("Location permissions needed for getting current location.").setPositiveButton("Open settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startInstalledAppDetailsActivity(currActivity);
            }
        });

        getLocationPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                // The alert dialog will open setting to add permissions for app.
                dialogBuilder.show();
            }
        }
    }

    private void getLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
        } else {
            getLocation();
        }
    }

    private void getLocation() {
        String provider = LocationManager.GPS_PROVIDER;
        locationManager.requestLocationUpdates(provider, 10000, 0, locationListener);
        Location location = locationManager.getLastKnownLocation(provider);
        while (location == null) {
            location = locationManager.getLastKnownLocation(provider);
        }
        locationManager.removeUpdates(locationListener);
        setLocation(location.getLongitude(), location.getLatitude());
    }

    private void setLocation(double longitude, double latitude) {
        longitudeText.setText("longitude: " + longitude);
        latitudeText.setText("latitude: " + latitude);
    }
}
