package com.crykid.updata.downloader;

import me.jessyan.progressmanager.ProgressManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Okhttp3IDownloader implements IDownloader {

    private final Callback CALLBACK;

    public Okhttp3IDownloader(Callback callback) {
        this.CALLBACK = callback;
    }

    @Override
    public void download(String url) {
        OkHttpClient client = ProgressManager.getInstance().with(new OkHttpClient.Builder()).build();
        Request request = new Request
                .Builder()
                .url(url)
                .method("GET", null)
                .build();
        Call call = client.newCall(request);
        call.enqueue(CALLBACK);
    }

}
