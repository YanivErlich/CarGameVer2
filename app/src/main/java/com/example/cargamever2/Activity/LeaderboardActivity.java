package com.example.cargamever2.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.cargamever2.Fragments.ListFragment;
import com.example.cargamever2.Fragments.MapFragment;
import com.example.cargamever2.Interfaces.ListCallBack;
import com.example.cargamever2.Interfaces.MapCallBack;
import com.example.cargamever2.Model.Record;
import com.example.cargamever2.Model.RecordList;
import com.example.cargamever2.R;

import com.example.cargamever2.Utilites.MySP3;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;


import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {

    private ListFragment listFragment;
    private MapFragment mapFragment;

    private MaterialButton replayBT;

    private Record score;
    private Location location;
    private RecordList recordList;
    public static final String SCORE = "SCORE";

    public static String LOCATION = "LOCATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        initFragments(saveRecords());
        beginTransactions();
        initButton();
    }

    private void initFragments(ArrayList<Record> bestRecords) {

        listFragment = new ListFragment(bestRecords, mapCallBack);
        mapFragment = new MapFragment();

    }

    private void beginTransactions() {
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_list, listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_map, mapFragment).commit();


    }

    private void initButton() {
        replayBT = findViewById(R.id.leaderboard_BTN_replay);
        replayBT.setOnClickListener(v -> restartGame());

    }

    private void restartGame() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    private ArrayList<Record> saveRecords() {
        MySP3.init(getApplicationContext());
        Intent previousIntent = getIntent();
        //int score = previousIntent.getIntExtra(LeaderboardActivity.SCORE, 0); // get SCORE as int
        score = (Record) previousIntent.getSerializableExtra(LeaderboardActivity.SCORE);
        Log.d("ddddddddddddddd", score.getLatitude()+"");

        // Load the saved RecordList from SharedPreferences or create a new one
        String recordListJson = MySP3.getInstance().getString("recordList", "");
        if (!recordListJson.isEmpty()) {
            recordList = new Gson().fromJson(recordListJson, RecordList.class);
        } else {
            recordList = new RecordList();
        }


        // Add the new Record and save the updated RecordList to SharedPreferences
        Record record = new Record(score.getScore());
        recordList.addRecord(record);
        String updatedRecordListJson = new Gson().toJson(recordList);
        MySP3.getInstance().putString("recordList", updatedRecordListJson);
        ArrayList<Record> bestRecords = recordList.makeBestRecords();
        return bestRecords;
    }


    MapCallBack mapCallBack = new MapCallBack() {
        @Override
        public void sendTheLocation(double longitude, double latitude) {
            mapFragment.markLocation(score.getLongitude(), score.getLatitude());
        }
    };
}
