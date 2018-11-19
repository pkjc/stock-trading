package edu.oakland.stocktrading;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

public class WebViewBridge {
    WebView webView = null;

    public WebViewBridge(WebView webView) {
        this.webView = webView;
    }

    public void addDataToWebView(String time, String gain){
        webView.loadUrl("javascript:addTimeGain("+time+","+gain+")");
    }

    @JavascriptInterface
    public void ShowToast(){
        Toast.makeText(webView.getContext(), "WebView button is clicked", Toast.LENGTH_LONG).show();
    }
}
