package com.template;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.Objects;
import java.util.TimeZone;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Link {

    private String domainFromFirebase;
    private String packageId;
    //private String resultOfResponse;

    // For getting link from server
//    private final OkHttpClient client = new OkHttpClient();
//
//    public void getLinkFromServer(String url, String userAgent) {
//
//        Request request = new Request.Builder()
//                .header("User-Agent", userAgent)
//                .url(url)
//                .build();
//
//        try (Response response = client.newCall(request).execute()){
//            if (response.isSuccessful()) {
//                setResultOfResponse((Objects.requireNonNull(response.body())).string());
//            } else {
//                setResultOfResponse("error");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        client.newCall(request).enqueue(new Callback() {
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//
//                        Log.i("link", response.toString());
//                        if (response.isSuccessful()) {
//                            setResultOfResponse((Objects.requireNonNull(response.body())).string());
//                        } else {
//                            setResultOfResponse("error");
//                        }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                e.printStackTrace();
//            }
//        });
//    }

    public void setDomainFromFirebase(String domainFromFirebase) {
        this.domainFromFirebase = domainFromFirebase;
    }

    public void setPackageId(String packageid) {
        this.packageId = packageid;
    }

    public String createLink() {

        String mainLink;
        String addFirst = "/?";
        String addSecond = "getr=utm_source=google-play&utm_medium=organic";
        UUID userId = UUID.randomUUID();
        String getZone = TimeZone.getDefault().getID();

        mainLink = domainFromFirebase + addFirst +
                "packageid=" + packageId + "&" +
                "userid=" + userId.toString() + "&" +
                "getz=" + getZone + "&" + addSecond;
        return mainLink;
    }
//
//    public String getResultOfResponse() {
//        return resultOfResponse;
//    }
//
//    public void setResultOfResponse(String resultOfResponse) {
//        this.resultOfResponse = resultOfResponse;
//    }
}
