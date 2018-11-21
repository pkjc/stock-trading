package edu.oakland.stocktrading;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class GrowthViewActivity extends AppCompatActivity {
    WebView webView = null;
    WebViewBridge bridge = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_growth_view);

        webView = findViewById(R.id.webView);

        webView.loadUrl("file:///android_asset/index.html");
        webView.getSettings().setJavaScriptEnabled(true);

        bridge = new WebViewBridge(webView);
        webView.addJavascriptInterface(bridge, "Android");
    }
}
