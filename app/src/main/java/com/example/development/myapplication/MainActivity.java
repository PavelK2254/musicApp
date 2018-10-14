package com.example.development.myapplication;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.example.development.myapplication.Interfaces.ILoaderCallback;
import com.example.development.myapplication.Interfaces.INetworkCallback;
import com.example.development.myapplication.Player.PlayerFragment;
import com.example.development.myapplication.Utils.NetworkManager;
import com.example.development.myapplication.Utils.YoulinkObjectModel;
import com.google.gson.Gson;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements ILoaderCallback, INetworkCallback {

    private WebView mainWebView;
    private NetworkManager networkManager;
    private FragmentTransaction fragmentTransaction;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mainWebView = findViewById(R.id.mainWebView);
        mainWebView.setWebViewClient(new MainWebClient(this,this.getBaseContext()));
        mainWebView.getSettings().setJavaScriptEnabled(true);
        mainWebView.loadUrl(getString(R.string.homepage));
        networkManager = new NetworkManager(this);
    }

    @Override
    public void pageIntercepted(String url) {
        try {
            networkManager.run(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void getResponse(String response) {
        YoulinkObjectModel[] youLinkObjectsArray = gson.fromJson(response,YoulinkObjectModel[].class);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PlayerFragment playerFragment = new PlayerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url",youLinkObjectsArray[0].url);

        playerFragment.setArguments(bundle);
        fragmentTransaction.setCustomAnimations(R.anim.slide_in,R.anim.slide_out);
        fragmentTransaction.add(R.id.mainContainer,playerFragment).addToBackStack("player").commitAllowingStateLoss();

    }
}
