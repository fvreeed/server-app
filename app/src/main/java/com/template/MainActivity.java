package com.template;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().hasExtra("access")) {

        } else {
            if (isConnect()) {
                Intent intent = new Intent(this, LoadingActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isConnect()) {
            Intent intent = new Intent(this, LoadingActivity.class);
            startActivity(intent);
        }
    }

    public boolean isConnect() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean mobConnect;
        boolean wifiConnect;

        mobConnect = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()
                == NetworkInfo.State.CONNECTED;
        wifiConnect = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()
                == NetworkInfo.State.CONNECTED;
        return mobConnect || wifiConnect;
    }
}