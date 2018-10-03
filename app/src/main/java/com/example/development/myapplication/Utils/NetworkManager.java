package com.example.development.myapplication.Utils;

import android.util.Log;

import com.example.development.myapplication.Interfaces.INetworkCallback;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkManager {


    INetworkCallback nc;
    OkHttpClient client = new OkHttpClient();
    String youLinkUrl = "https://you-link.herokuapp.com/?url=";



    public NetworkManager(INetworkCallback nc) {
        this.nc = nc;
    }

   public void run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(youLinkUrl + url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("NetworkError", e.getLocalizedMessage() );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                nc.getResponse(response.body().string());
            }
        });


    }
}
