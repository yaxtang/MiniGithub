package com.example.yaxin.webprofile;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.yaxin.webprofile.R.id.username;

/**
 * Created by Yaxin on 10/30/2017.
 */
public class handleActions{
    public String TAG = com.example.yaxin.webprofile.handleActions.class.getSimpleName();

    public void handleFollow(){
        OkHttpClient client = new OkHttpClient();
        try{  Request request = new Request.Builder()
                .url("https://api.github.com/user/following/" + username)
                .put(null)
                .addHeader("authorization", "Bearer 7f00f23585937070a2a1bd91b6ad6f1a12132b10")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "f6540c87-85cf-423b-743e-bad192c56f0b")
                .build();
        Response response = client.newCall(request).execute();
    }
    catch (final IOException e) {
        Log.e(TAG, "handleActions: " + e.getMessage());
            }
        };
}