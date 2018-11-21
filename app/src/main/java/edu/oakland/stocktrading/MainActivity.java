package edu.oakland.stocktrading;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import static edu.oakland.stocktrading.GrowthViewActivity.time;
import static edu.oakland.stocktrading.MainActivity.accountBal;
import static edu.oakland.stocktrading.MainActivity.accountBalVals;

// start button click starts 2 threads and go to next screen
// good strategy thread and bad strategy thread
// each thread sleeps for 1 sec
// poll account balance every 10 sec and store it for plotting
// if balance reaches 0 or less stop game
// if stop game button is clicked stop the game
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    TextView accountBalTextView;
    TextView gameMsg;
    Button startBtn, stopBtn, showGraph;
    static volatile double accountBal = 100.0;
    GoodStrategy goodStrategy = new GoodStrategy();
    BadStrategy badStrategy = new BadStrategy();
    static List<Double> accountBalVals = new ArrayList<>();
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accountBalTextView = findViewById(R.id.balance);
        gameMsg = findViewById(R.id.gameMsg);

        startBtn = findViewById(R.id.startGame);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goodStrategy.start();
                badStrategy.start();
                timer.schedule(new PollAccountBal(), 0, 10000);
                Toast.makeText(MainActivity.this, "Game Started!", Toast.LENGTH_LONG).show();
                gameMsg.setText("Click on 'Show Growth' to see the graph");
            }
        });

        stopBtn = findViewById(R.id.stopGame);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goodStrategy.stopThread();
                badStrategy.stopThread();
                timer.purge();
            }
        });

        showGraph = findViewById(R.id.showGraph);
        showGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GrowthViewActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("accountBalVals", (Serializable) accountBalVals);
                intent.putExtra("BUNDLE",args);
                view.getContext().startActivity(intent);
            }
        });

    }
}

class PollAccountBal extends TimerTask {
    private static final String TAG = "PollAccountBal";
    public void run() {
        Log.d(TAG,  " <<<<< ACCOUNT BALANACE >>>>>> " + accountBal);
        accountBalVals.add(accountBal);
        time = time + 10;
    }
}

class GoodStrategy extends Thread{
    private static final String TAG = "GoodStrategy";
    private final AtomicBoolean running = new AtomicBoolean(false);

    public void stopThread() {
        running.set(false);
    }

    @Override
    public void run() {
        running.set(true);
        Log.d(TAG, "GOOD STRATEGY THREAD RUNNING ----------------------------------------------------------------\n");
        try {
            while (accountBal > 0 && running.get()){
                Log.d(TAG, "IN good strategy BEFORE : " + accountBal + "\n\n");
                Thread.sleep(1000);
                accountBal = accountBal + Math.random();
                Log.d(TAG, "IN good strategy AFTER : " + accountBal + "\n\n");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    double roundOff(double value){
        return Math.round(value * 100)/100;
    }
}

class BadStrategy extends Thread{
    private static final String TAG = "BadStrategy";
    private final AtomicBoolean running = new AtomicBoolean(false);

    public void stopThread() {
        running.set(false);
    }

    @Override
    public void run() {
        running.set(true);
        Log.d(TAG, "BAD STRATEGY THREAD RUNNING ----------------------------------------------------------------\n");
        try {
            while (accountBal > 0 && running.get()){
                Log.d(TAG, "IN BAD strategy BEFORE : " + accountBal + "\n\n");
                Thread.sleep(1000);
                accountBal = accountBal - Math.random();
                Log.d(TAG, "IN BAD strategy AFTER : " + accountBal + "\n\n");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    double roundOff(double value){
        return Math.round(value * 100)/100;
    }
}
