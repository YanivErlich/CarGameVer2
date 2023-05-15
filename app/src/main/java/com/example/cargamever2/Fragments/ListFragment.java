package com.example.cargamever2.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cargamever2.Adapter.RecordAdapter;
import com.example.cargamever2.Interfaces.ListCallBack;
import com.example.cargamever2.Interfaces.MapCallBack;
import com.example.cargamever2.Model.Record;
import com.example.cargamever2.R;


import java.util.ArrayList;


public class ListFragment extends Fragment {


    private ArrayList<Record> recordList;
    private RecyclerView main_LST_records;
    private MapCallBack mapCallBack;

    public ListFragment(ArrayList<Record> recordList, MapCallBack mapCallBack){
        this.recordList = recordList;
        this.mapCallBack = mapCallBack;
    }



    MapCallBack clickListCallBack = new MapCallBack() {
        @Override
        public void sendTheLocation(double longitude, double latitude) {
            mapCallBack.sendTheLocation(longitude,latitude);
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecordAdapter recordAdapter = new RecordAdapter(getActivity(),recordList, clickListCallBack);
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        main_LST_records = view.findViewById(R.id.main_LST_records);
        main_LST_records.setLayoutManager(linearLayoutManager);
        main_LST_records.setAdapter(recordAdapter);
        return view;
    }







}

