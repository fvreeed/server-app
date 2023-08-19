package com.template;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class LoadingActivity extends AppCompatActivity {

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    private boolean mAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        mPrefs = getSharedPreferences("server_RC_WV", MODE_PRIVATE);
        mEditor = mPrefs.edit();
        mAccess = mPrefs.getBoolean("site access", true);

        // For open Activities
        Intent intentMain = new Intent(this, MainActivity.class);
        Intent intentWeb = new Intent(this, WebActivity.class);

        if (mAccess) {

            // Check internet connection
            if (isConnect()) {
                startActivity(intentMain);
            } else {

                // Get link from Firebase
                FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
                FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                        .setMinimumFetchIntervalInSeconds(3600)
                        .build();
                mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
                String check_link = mFirebaseRemoteConfig.getString("check_link");

                Log.i("entry", "im here");
                check_link = "";
                // Check is link empty
                if (check_link.isEmpty()) {
                    startActivity(intentMain);
                    mEditor.putBoolean("server_RC_WV", false);
                    mAccess = false;
                } else {
                    mEditor.putBoolean("server_RC_WV", true);
                    mAccess = true;

                    // Create main link
                    Link mainLink = new Link();
                    mainLink.setDomainFromFirebase(check_link);
                    mainLink.setPackageId(getApplicationContext().getPackageName());

                    // Open WebActivity
                    intentWeb.putExtra("link", mainLink.createLink());
                    //startActivity(intentWeb);
                }
            }
        } else {
            startActivity(intentMain);
        }
    }

    // Check internet connection after closing the app
    @Override
    protected void onResume() {
        super.onResume();

        if (isConnect()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    // Check internet connection
    public boolean isConnect() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean mobConnect;
        boolean wifiConnect;

        mobConnect = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()
                == NetworkInfo.State.CONNECTED;
        wifiConnect = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()
                == NetworkInfo.State.CONNECTED;
        return !(mobConnect || wifiConnect);
    }
}