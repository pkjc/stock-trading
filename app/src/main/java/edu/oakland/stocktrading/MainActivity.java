package edu.oakland.stocktrading;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

            }
        });
    }
}

class goodStrategy extends Thread{
    double accountBal;
    double gain;

    public goodStrategy(double accountBal) {
        this.accountBal = accountBal;
    }

    @Override
    public void run() {
        accountBal = accountBal + calculateGain();
    }

    private double calculateGain() {
        Random random = new Random();
        random.nextDouble();
        double randomNum = Math.random();

        return 0.0;
    }
}

class badStrategy extends Thread{
    double accountBal;
    double gain;

    public badStrategy(double accountBal) {
        this.accountBal = accountBal;
    }

    @Override
    public void run() {
        accountBal = accountBal + gain;
    }
}
