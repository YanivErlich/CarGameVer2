package com.example.cargamever2.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.example.cargamever2.Model.Record;
import com.example.cargamever2.R;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;



import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {


    private MaterialButton[] main_BTN_options;

    public static final String BT_MODE = "BT_MODE";
    public static final String SENSOR_MODE ="SENSOR_MODE";
    private Record record;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 100;
    FusedLocationProviderClient fusedLocationProviderClient;
    private Location myLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        requestPermission();
        getLocation();
        findViews();
        setButtonsClickListeners();



    }

    private void setButtonsClickListeners() {
        main_BTN_options[0].setOnClickListener(v->starButtonFastMode());
        main_BTN_options[1].setOnClickListener(v->starButtonSlowMode());
        main_BTN_options[2].setOnClickListener(v->startSensorMode());
        main_BTN_options[3].setOnClickListener(v-> showLeaderboard());
    }

    private void startSensorMode() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(SENSOR_MODE,0);
        intent.putExtra(MainActivity.LOCATION,myLocation);
        startActivity(intent);
        finish();
    }

    private void starButtonSlowMode() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(BT_MODE,1);
        intent.putExtra(MainActivity.CHANGE_SPEED,1);
        intent.putExtra(MainActivity.LOCATION,myLocation);
        startActivity(intent);
        finish();
    }

    private void starButtonFastMode() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(BT_MODE,1);
        intent.putExtra(MainActivity.CHANGE_SPEED,0);
        intent.putExtra(MainActivity.LOCATION,myLocation);
        startActivity(intent);
        finish();

    }

    private void showLeaderboard() {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        intent.putExtra(MainActivity.LOCATION,myLocation);
        startActivity(intent);
        finish();
    }

    private void findViews() {
        main_BTN_options = new MaterialButton[]{
                findViewById(R.id.Button_fast),
                findViewById(R.id.Button_slow),
                findViewById(R.id.Sensor),
                findViewById(R.id.leaderboard)
        };
    }


    private void requestPermission() {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},10);


 }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            }


        }
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null)
                        myLocation = location;
                }
            });
        }else{
            requestPermission();
    }
        }





}
