package com.example.cargamever2.Utilites;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.cargamever2.Interfaces.StepCallBack;

public class StepDetector  {
    private Sensor sensor;

    private SensorManager sensorManager;

    private StepCallBack stepCallback;

    private int delayMillis; // delay between steps in milliseconds
    private long lastStepTime;
    float x ;
    private int stepCounterX = 0;
    private int stepCounterY = 0;
    private long timestamp = 0;
    float width;

    private SensorEventListener sensorEventListener;

    public StepDetector(Context context,float width,int delayMillis, StepCallBack stepCallback) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.stepCallback = stepCallback;
        this.delayMillis = delayMillis;
        this.width = width;
        initEventListener();

    }

    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];


                calculateStep(x);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    public void calculateStep(float x) {
        this.x = x ;
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastStepTime < delayMillis) {
            return;
        }
        if (x > 0) {
            if (stepCallback != null)
                stepCallback.stepXLeft();

        }else {
            if (stepCallback != null) {
                stepCallback.stepXRight();
            }
        }
        lastStepTime = currentTime;

    }


    public float getStepsX() {
        return this.x;
    }



    public void start() {
        sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void stop() {
        sensorManager.unregisterListener(
                sensorEventListener,
                sensor
        );
    }
}
