package com.template;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WebActivity extends AppCompatActivity {

    private final OkHttpClient client = new OkHttpClient();
    private String siteAccess = "error";

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        WebView webView = findViewById(R.id.webContent);
        String url = getIntent().getStringExtra("link");
        String userAgent = webView.getSettings().getUserAgentString();

        // Get site access
        Log.i("link2", url);
        //getLinkFromServer(url, userAgent, webView);
        Log.i("link3", siteAccess);

        // Check our access
        if ("error".equals(siteAccess)) {
            Intent intentMain = new Intent(this, MainActivity.class);
            intentMain.putExtra("access", siteAccess);
            startActivity(intentMain);
        } else {
            webView.loadUrl(siteAccess);
        }
    }

    public void getLinkFromServer(String url, String userAgent, WebView webView) {

        Request request = new Request.Builder()
                .header("User-Agent", userAgent)
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.i("link1", response.toString());
                if (response.isSuccessful()) {
                    Log.i("link1", "SUCCESS");
                    siteAccess = (Objects.requireNonNull(response.body())).string();
                } else {
                    siteAccess = "error";
                }
            }
        });
    }
}