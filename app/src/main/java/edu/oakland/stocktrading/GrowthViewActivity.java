package edu.oakland.stocktrading;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;

import static edu.oakland.stocktrading.MainActivity.accountBal;
import static edu.oakland.stocktrading.MainActivity.isGameStopped;

public class GrowthViewActivity extends AppCompatActivity {
    WebView webView = null;
    static WebViewBridge bridge = null;
    private static final String TAG = "GrowthViewActivity";
    Timer timer = new Timer();
    static int time = 10;
    static boolean isWebViewLoaded = false;
    Button graphStopBtn, goBack = null;
    Handler growthActivityHandler = null;
    PollAccountBal pollAccountBal = null;
    ArrayList<Double> accountBalVals = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_growth_view);
        graphStopBtn = findViewById(R.id.graphStopBtn);
        goBack = findViewById(R.id.goBack);

        growthActivityHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                if(bundle.get("GameOver") == null) {
                    Double accountBal = bundle.getDouble("Gain");
                    bridge.addDataToWebView(Integer.toString(time), Double.toString(accountBal));
                }
            }
        };

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GrowthViewActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        graphStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                isGameStopped = true;
                Toast.makeText(GrowthViewActivity.this, "Game Over!", Toast.LENGTH_SHORT).show();
            }
        });
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        final ArrayList<Double> accountBalVals = (ArrayList<Double>) args.getSerializable("accountBalVals");
        if(!isGameStopped) {
            timer.schedule(new PollAccountBal(growthActivityHandler), 0, 10000);
        }

        webView = findViewById(R.id. webView);
        webView.getSettings().setJavaScriptEnabled(true);

        bridge = new WebViewBridge(webView);
        webView.addJavascriptInterface(bridge, "Android");

        /* WebViewClient must be set BEFORE calling loadUrl! */
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                addValuesToGraph(accountBalVals);
            }
        });
        webView.loadUrl("file:///android_asset/index.html");
    }

    private void addValuesToGraph(ArrayList<Double> accountBalVals) {
        int time = 10;
        for(Double val : accountBalVals) {
            Log.d(TAG, "onCreate: accountBalVal >>>>>>>>>> " + val);
            bridge.addDataToWebView(Integer.toString(time), Double.toString(val));
            time = time + 10;
        }
    }
}