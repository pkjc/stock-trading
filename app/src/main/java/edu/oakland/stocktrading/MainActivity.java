package edu.oakland.stocktrading;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

// start button click starts 2 threads and go to next screen
// good strategy thread and bad strategy thread
// each thread sleeps for 1 sec
// poll account balance every 10 sec and store it for plotting
// if balance reaches 0 or less stop game
// if stop game button is clicked stop the game
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    TextView accountBal;
    Button startBtn, stopBtn, showGraph;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accountBal = findViewById(R.id.balance);

        startBtn = findViewById(R.id.startGame);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BadStrategy badStrategy = new BadStrategy(0.0);
                badStrategy.start();
                GoodStrategy goodStrategy = new GoodStrategy(0.0);
                goodStrategy.start();
            }
        });

        stopBtn = findViewById(R.id.stopGame);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        showGraph = findViewById(R.id.showGraph);
        showGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GrowthViewActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }
}

class GoodStrategy extends Thread{
    private static final String TAG = "GoodStrategy";
    double accountBal;
    double gain;

    public GoodStrategy(double accountBal) {
        this.accountBal = accountBal;
    }

    @Override
    public void run() {
        accountBal = accountBal + calculateGain();
        Log.d(TAG, "GOOD STRATEGY THREAD RUNNING ----------------------------------------------------------------");
        try {
            for (int i=0;i<=10;i++){
                Log.d(TAG, "run: " + i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private double calculateGain() {
        Random random = new Random();
        random.nextDouble();
        double randomNum = Math.random();

        return 0.0;
    }
}

class BadStrategy extends Thread{
    private static final String TAG = "BadStrategy";
    double accountBal;
    double gain;

    public BadStrategy(double accountBal) {
        this.accountBal = accountBal;
    }

    private double calculateGain() {
        Random random = new Random();
        random.nextDouble();
        double randomNum = Math.random();

        return 0.0;
    }
    @Override
    public void run() {
        accountBal = accountBal + calculateGain();
        Log.d(TAG, "BAD STRATEGY THREAD RUNNING ----------------------------------------------------------------");
        try {

            for (int i=0;i<=10;i++){
                Log.d(TAG, "run: " + i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
