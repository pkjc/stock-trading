package edu.oakland.stocktrading;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;
import java.util.Timer;

import static edu.oakland.stocktrading.MainActivity.accountBal;

public class GrowthViewActivity extends AppCompatActivity {
    WebView webView = null;
    static WebViewBridge bridge = null;
    private static final String TAG = "GrowthViewActivity";
    Timer timer = new Timer();
    static int time = 10;
    static boolean isWebViewLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_growth_view);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");

        final ArrayList<Double> accountBalVals = (ArrayList<Double>) args.getSerializable("accountBalVals");

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
            Log.d(TAG, "onCreate: accountBalVal >>>>>>>>>> " + accountBal);
            bridge.addDataToWebView(Integer.toString(time), Double.toString(accountBal));
            time = time + 10;
        }
    }
}

//class PollAccountBal extends TimerTask {
//    private static final String TAG = "PollAccountBal";
//    public void run() {
//        Log.d(TAG,  " <<<<< ACCOUNT BALANACE >>>>>> " + accountBal);
//
//        time = time + 10;
//    }
//}
