package com.example.development.myapplication;

import android.content.Context;
import android.net.http.SslError;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.development.myapplication.Interfaces.ILoaderCallback;

public class MainWebClient extends WebViewClient {

    Context parentContext;
    ILoaderCallback ic;

    public MainWebClient(ILoaderCallback callback, Context cntxt) {
        parentContext = cntxt;
        ic = callback;
    }

    String TAG = "Current Url";

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
        if(url.contains("---")){
            view.stopLoading();
            view.loadUrl(parentContext.getString(R.string.homepage));

        }
        if(url.contains("watch?v=")){
            ic.pageIntercepted(url);

        }

    }
}
