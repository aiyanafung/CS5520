package com.example.first_app;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Activity5 extends AppCompatActivity {
    public final static double AVERAGE_RADIUS_OF_EARTH_METER = 6371000;
    private static final int REQUEST_CODE_LOCATION = 1;

    private TextView textViewCurrentLat;
    private TextView textViewCurrentLong;
    private TextView textViewDistanceTraveled;

    private Button button_reset;

    private int distanceTraveled = 0;

    private double currentLatitude;
    private double currentLongitude;
    private double previousLatitude;
    private double previousLongitude;

    private boolean startCalculation = false;

    private Handler handler = new Handler();

    GetLocationThread getLocationThread;

    private long onBackPressedTime;

    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5);

        textViewCurrentLat = findViewById(R.id.activity5_current_lat);
        textViewCurrentLong = findViewById(R.id.activity5_current_long);
        textViewDistanceTraveled = findViewById(R.id.activity5_distance_traveled);
        textViewDistanceTraveled.setText("0");

        button_reset = findViewById(R.id.button_reset);

        if (!checkPermission()) {
            requestPermission();
        }

        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                distanceTraveled = 0;
                textViewDistanceTraveled.setText("0");
            }
        });

        getLocationThread = new GetLocationThread();
        getLocationThread.start();
    }

    @Override
    public void onBackPressed() {

        if (onBackPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        onBackPressedTime = System.currentTimeMillis();
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(Activity5.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(Activity5.this, new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION
        }, REQUEST_CODE_LOCATION);
    }

    private int calculateDistance(double lat1, double long1, double lat2, double long2) {
        double latDistance = Math.toRadians(lat1 - lat2);
        double longDistance = Math.toRadians(long1 - long2);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(longDistance / 2) * Math.sin(longDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH_METER * c));
    }

    private void getLocation() {
        if(locationManager == null) {
            locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        }

        if (!checkPermission()) {
            requestPermission();
        } else {
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    previousLatitude = currentLatitude;
                    previousLongitude = currentLongitude;
                    currentLatitude = location.getLatitude();
                    currentLongitude = location.getLongitude();

                    if(!startCalculation) {
                        startCalculation = true;
                    }else {
                        int d = calculateDistance(previousLatitude, previousLongitude, currentLatitude, currentLongitude);
                        distanceTraveled += d;
                    }

                    Log.d("location", "onLocationChanged: " + currentLatitude + ", " + currentLongitude);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            textViewCurrentLat.setText(String.valueOf(currentLatitude));
                            textViewCurrentLong.setText(String.valueOf(currentLongitude));
                            textViewDistanceTraveled.setText(String.valueOf(distanceTraveled));
                        }
                    });
                }
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, locationListener);
        }
    }

    class GetLocationThread extends Thread {


        @Override
        public void run() {
            Looper.prepare();
            if (!checkPermission()) {
                finish();
                return;
            }
            else {
                Log.d("location", "start getting location");
                getLocation();
            }
            Looper.loop();
        }
    }
}