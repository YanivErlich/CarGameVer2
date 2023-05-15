package com.example.cargamever2.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class RecordList {
    private ArrayList<Record> records = new ArrayList<>();


    public RecordList() {

    }




    public ArrayList<Record> getRecords() {
        return records;
    }

    public RecordList setRecords(ArrayList<Record> records) {
        this.records = records;
        return this;
    }

    public void addRecord(Record record){
        this.records.add(record);
    }


    public ArrayList<Record> makeBestRecords(){
        ArrayList<Record> bestRecords = new ArrayList<>();
        if(records != null && records.size() > 0) {
            records.removeIf(Objects::isNull); // Filter out null records
            Collections.sort(records);
            bestRecords.addAll(records.subList(0, Math.min(records.size(), 10)));
        }
        return bestRecords;
    }






}
