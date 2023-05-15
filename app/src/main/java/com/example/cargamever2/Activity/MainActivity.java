package com.example.cargamever2.Activity;

import static com.example.cargamever2.Activity.MenuActivity.BT_MODE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.cargamever2.Interfaces.StepCallBack;
import com.example.cargamever2.Model.Record;
import com.example.cargamever2.R;
import com.example.cargamever2.Utilites.StepDetector;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final String LOCATION = "LOCATION";
    public static final String CHANGE_SPEED = "CHANGE_SPEED";
    public final int DELAY = 1000;
    private MaterialButton[] main_BTN_options;
    private ShapeableImageView[] main_IMG_hearts;

    private ShapeableImageView[][] main_IMG_rocks;


    private ShapeableImageView[] cars;

    long startTime = 0;

    private StepDetector stepDetector;
    private Random rand = new Random();

    private CountDownTimer timer;


    private MaterialTextView materialScore;
    private int counter = 3;

    private int score;
    private Record finishScore;
    private int speed = 0;
    private boolean sensors = false;

    private MediaPlayer mediaPlayer;
    private MediaPlayer mediaPlayer2;
    private  Handler handler;
    private LocationManager locationManager;
   private Location location;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        location = intent.getParcelableExtra(LOCATION);

        location = intent.getParcelableExtra(LeaderboardActivity.LOCATION);
        int intentNum = intent.getIntExtra(BT_MODE, 0);
        findViews();
        setVisibility();
        if (intentNum == 0) {
            sensors = true;
            moveCarBySensors();
            main_BTN_options[0].setVisibility(View.INVISIBLE);
            main_BTN_options[1].setVisibility(View.INVISIBLE);

        } else if (intentNum == 1) {
            setButtonsClickListeners();
        }
        startGame();


    }

    private void startGame() {
        handler = new Handler();
        Intent intent = getIntent();
        int intentNum = intent.getIntExtra(CHANGE_SPEED, 0);
        if (intentNum == 0) {
            speed = -380;
        } else {
            speed = 200;
        }
        runOnUiThread(new Runnable() {
            public void run() {
                handler.postDelayed(this, DELAY - 300 + (speed));
                changeRockPlaceView();
                crashCheck();

            }
        });
        runOnUiThread(new Runnable() {
            public void run() {
                handler.postDelayed(this, DELAY + 500 + (speed));
                startFallingRock();
            }
        });
    }

    private void gameOver() {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        finishScore = new Record(this.score);
        finishScore.setScore(this.score);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        finishScore.setLongitude(location.getLongitude());
        finishScore.setLatitude(location.getLatitude());
        intent.putExtra(LeaderboardActivity.SCORE, this.finishScore);
        startActivity(intent);
        handler.removeCallbacksAndMessages(null);
        onPause();
        finish();
    }


    private void crashCheck() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        if (main_IMG_rocks[6][0].getVisibility() == View.VISIBLE && cars[0].getVisibility() == View.VISIBLE) {
            if (main_IMG_rocks[6][0].getDrawable().getConstantState() == getResources().getDrawable(R.drawable.rock).getConstantState()) {
                lifeManager(counter);
                toastVibrator(getApplicationContext(), v, counter, 0);
                score -= 50;
                counter--;
                cars[0].setImageResource(R.drawable.explosion);
                main_IMG_rocks[6][0].setVisibility(View.INVISIBLE);
                mediaPlayerStart(0);
            } else if (main_IMG_rocks[6][0].getDrawable().getConstantState() == getResources().getDrawable(R.drawable.coin).getConstantState() && cars[0].getVisibility() == View.VISIBLE) {
                toastVibrator(getApplicationContext(), v, counter, 1);
                score += 50;
                mediaPlayerStart(1);
            }
        } else {
            cars[0].setImageResource(R.drawable.sport_car);
        }
        if (main_IMG_rocks[6][1].getVisibility() == View.VISIBLE && cars[1].getVisibility() == View.VISIBLE) {

            if (main_IMG_rocks[6][1].getDrawable().getConstantState() == getResources().getDrawable(R.drawable.rock).getConstantState()) {
                lifeManager(counter);
                toastVibrator(getApplicationContext(), v, counter, 0);
                score -= 50;
                counter--;
                cars[1].setImageResource(R.drawable.explosion);
                main_IMG_rocks[6][1].setVisibility(View.INVISIBLE);
                mediaPlayerStart(0);
            } else if (main_IMG_rocks[6][1].getDrawable().getConstantState() == getResources().getDrawable(R.drawable.coin).getConstantState() && cars[1].getVisibility() == View.VISIBLE) {
                toastVibrator(getApplicationContext(), v, counter, 1);
                score += 50;
                mediaPlayerStart(1);
            }

        } else {
            cars[1].setImageResource(R.drawable.sport_car);
        }
        if (main_IMG_rocks[6][2].getVisibility() == View.VISIBLE && cars[2].getVisibility() == View.VISIBLE) {

            if (main_IMG_rocks[6][2].getDrawable().getConstantState() == getResources().getDrawable(R.drawable.rock).getConstantState()) {
                lifeManager(counter);
                toastVibrator(getApplicationContext(), v, counter, 0);
                score -= 50;
                counter--;
                cars[2].setImageResource(R.drawable.explosion);
                main_IMG_rocks[6][2].setVisibility(View.INVISIBLE);
                mediaPlayerStart(0);
            } else if (main_IMG_rocks[6][2].getDrawable().getConstantState() == getResources().getDrawable(R.drawable.coin).getConstantState() && cars[2].getVisibility() == View.VISIBLE) {
                toastVibrator(getApplicationContext(), v, counter, 1);
                score += 50;
                mediaPlayerStart(1);
            }

        } else {
            cars[2].setImageResource(R.drawable.sport_car);
        }

        if (main_IMG_rocks[6][3].getVisibility() == View.VISIBLE && cars[3].getVisibility() == View.VISIBLE) {

            if (main_IMG_rocks[6][3].getDrawable().getConstantState() == getResources().getDrawable(R.drawable.rock).getConstantState()) {
                lifeManager(counter);
                toastVibrator(getApplicationContext(), v, counter, 0);
                score -= 50;
                counter--;
                cars[3].setImageResource(R.drawable.explosion);
                main_IMG_rocks[6][3].setVisibility(View.INVISIBLE);
                mediaPlayerStart(0);
            } else if (main_IMG_rocks[6][3].getDrawable().getConstantState() == getResources().getDrawable(R.drawable.coin).getConstantState() && cars[3].getVisibility() == View.VISIBLE) {
                toastVibrator(getApplicationContext(), v, counter, 1);
                score += 50;
                mediaPlayerStart(1);
            }

        } else {
            cars[3].setImageResource(R.drawable.sport_car);
        }

        if (main_IMG_rocks[6][4].getVisibility() == View.VISIBLE && cars[4].getVisibility() == View.VISIBLE) {

            if (main_IMG_rocks[6][4].getDrawable().getConstantState() == getResources().getDrawable(R.drawable.rock).getConstantState()) {
                lifeManager(counter);
                toastVibrator(getApplicationContext(), v, counter, 0);
                score -= 50;
                counter--;
                cars[4].setImageResource(R.drawable.explosion);
                main_IMG_rocks[6][4].setVisibility(View.INVISIBLE);
                mediaPlayerStart(0);
            } else if (main_IMG_rocks[6][4].getDrawable().getConstantState() == getResources().getDrawable(R.drawable.coin).getConstantState() && cars[4].getVisibility() == View.VISIBLE) {
                toastVibrator(getApplicationContext(), v, counter, 1);
                score += 50;
                mediaPlayerStart(1);
            }

        } else {
            cars[4].setImageResource(R.drawable.sport_car);
        }

        if (score < 0)
            score = 0;
        materialScore.setText("Score: " + score);
        score += 10;

        if (counter == 0) {
            if (mediaPlayer != null && mediaPlayer2 != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer2.stop();
                mediaPlayer2.release();
                mediaPlayer2 = null;
                mediaPlayer = null;
            }
        }
    }


    private void lifeManager(int life) {
        if (life > 0 && life <= main_IMG_hearts.length) {
            main_IMG_hearts[life - 1].setVisibility(View.INVISIBLE);
        }
        if (life == 1) {
            gameOver();

        }
    }


    private void setButtonsClickListeners() {

        main_BTN_options[0].setOnClickListener(v -> moveLeft());
        main_BTN_options[1].setOnClickListener(v -> moveRight());

    }


    private void setVisibility() {
        setRocksInvis();
        setCarInvis();
        ;
    }

    private void findViews() {

        main_BTN_options = new MaterialButton[]{
                findViewById(R.id.Main_button_left),
                findViewById(R.id.Main_button_right)};
        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)};
        cars = new ShapeableImageView[]{findViewById(R.id.main_car1),// initiate cars
                findViewById(R.id.main_car2),// initiate cars
                findViewById(R.id.main_car3),// initiate cars
                findViewById(R.id.main_car4),// initiate cars
                findViewById(R.id.main_car5)};// initiate cars


        materialScore = findViewById(R.id.game_score);
        setRocksView();

    }

    public void setRocksInvis() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                this.main_IMG_rocks[i][j].setVisibility(View.INVISIBLE);
            }
        }
    }


    public void startFallingRock() {
        int randRock = rand.nextInt(5);//0-4
        int randCoin = rand.nextInt(20);
        main_IMG_rocks[0][randRock].setVisibility(View.VISIBLE);
        if (randCoin == 5) {
            main_IMG_rocks[0][randRock].setImageResource(R.drawable.coin);


        }

    }


    public void changeRockPlaceView() {
        boolean check[][] = new boolean[7][5];
        for (int i = 6; i >= 0; i--) {
            for (int j = 4; j >= 0; j--) {
                if (i == 6) {
                    main_IMG_rocks[i][j].setVisibility(View.INVISIBLE);
                    check[i][j] = true; // Mark the rock as moved
                    if (main_IMG_rocks[i][j].getDrawable().getConstantState() != getResources().getDrawable(R.drawable.coin).getConstantState())
                        main_IMG_rocks[i][j].setImageResource(R.drawable.rock);
                } else if (main_IMG_rocks[i][j].getVisibility() == View.VISIBLE && !check[i][j]) {
                    main_IMG_rocks[i][j].setVisibility(View.INVISIBLE);
                    main_IMG_rocks[i + 1][j].setVisibility(View.VISIBLE);
                    check[i + 1][j] = true; // Mark the rock as moved

                    if (main_IMG_rocks[i][j].getDrawable().getConstantState() == getResources().getDrawable(R.drawable.coin).getConstantState()) {
                        main_IMG_rocks[i + 1][j].setImageResource(R.drawable.coin);

                    }
                } else {
                    main_IMG_rocks[i][j].setVisibility(View.INVISIBLE);
                }
            }
        }
    }


    public void setCarInvis() {
        cars[0].setVisibility(View.INVISIBLE);// make left and right car invisible
        cars[1].setVisibility(View.INVISIBLE);// make left and right car invisible
        cars[3].setVisibility(View.INVISIBLE);// make left and right car invisible
        cars[4].setVisibility(View.INVISIBLE);// make left and right car invisible
    }


    public void moveCarBySensors() {
        stepDetector = new StepDetector(this, getResources().getDisplayMetrics().widthPixels, 350, new StepCallBack() {
            @Override
            public void stepXLeft() {
                moveLeft();
            }

            @Override
            public void stepXRight() {
                moveRight();
            }


        });


    }

    public void moveLeft() {
        if (cars[1].getVisibility() == View.VISIBLE) {
            cars[0].setVisibility((View.VISIBLE));
            cars[1].setVisibility((View.INVISIBLE));
        } else if (cars[2].getVisibility() == View.VISIBLE) {
            cars[1].setVisibility(View.VISIBLE);
            cars[2].setVisibility(View.INVISIBLE);
        } else if (cars[3].getVisibility() == View.VISIBLE) {
            cars[2].setVisibility(View.VISIBLE);
            cars[3].setVisibility(View.INVISIBLE);
        } else if (cars[4].getVisibility() == View.VISIBLE) {
            cars[3].setVisibility((View.VISIBLE));
            cars[4].setVisibility((View.INVISIBLE));
        }


    }

    public void moveRight() {
        if (cars[1].getVisibility() == View.VISIBLE) {
            cars[2].setVisibility((View.VISIBLE));
            cars[1].setVisibility((View.INVISIBLE));
        } else if (cars[0].getVisibility() == View.VISIBLE) {
            cars[1].setVisibility((View.VISIBLE));
            cars[0].setVisibility((View.INVISIBLE));
        } else if (cars[2].getVisibility() == View.VISIBLE) {
            cars[3].setVisibility((View.VISIBLE));
            cars[2].setVisibility((View.INVISIBLE));
        } else if (cars[3].getVisibility() == View.VISIBLE) {
            cars[4].setVisibility((View.VISIBLE));
            cars[3].setVisibility((View.INVISIBLE));
        }
    }

    private void setRocksView() {

        main_IMG_rocks = new ShapeableImageView[7][5];
        int num = 1;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                String numRock = "rock" + num;
                num++;
                int resID = getResources().getIdentifier(numRock, "id", getPackageName());
                main_IMG_rocks[i][j] = ((ShapeableImageView) findViewById(resID));
            }
        }

    }

    private void toastVibrator(Context context, Vibrator v, int life, int number) {

        switch (number) {
            case 0:
                Toast.makeText(context, "Crash!!!  -50 points!", Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    v.vibrate(500);
                }
                break;

            case 1:
                Toast.makeText(context, "Nice!! + 50 points!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void mediaPlayerStart(int choice) {

        if (choice == 0) {

            mediaPlayer = MediaPlayer.create(this, R.raw.explosion);
            mediaPlayer.start();
        }
        if (choice == 1) {
            mediaPlayer2 = MediaPlayer.create(this, R.raw.coin);
            mediaPlayer2.start();
        }


    }


    protected void onResume() {
        super.onResume();
        if (sensors) {
            stepDetector.start();
        }

    }

    protected void onPause() {
        super.onPause();

        if (stepDetector != null) {
            stepDetector.stop();
        }
    }


}