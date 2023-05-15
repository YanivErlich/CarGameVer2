package com.example.cargamever2.Model;

import java.io.Serializable;

public class Record implements Comparable<Record>, Serializable {
    private int score = 0;

    private double latitude = 0;

    private double longitude = 0;


    public Record(int score) {
        this.score = score;
    }


    public Record setScore(int score) {
        this.score = score;
        return this;
    }
    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Record " + getScore();
    }

    public int compareTo(Record o) {
        return Integer.compare(o.score, this.score);
    }

    public double getLatitude(){
        return latitude;
    }

    public Record setLatitude(double latitude){
        this.latitude = latitude;
        return this;
    }

    public double getLongitude(){
        return longitude;
    }

    public Record setLongitude(double longitude){
        this.longitude = longitude;
        return this;
    }




}
