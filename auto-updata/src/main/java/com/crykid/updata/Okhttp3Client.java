package com.crykid.updata;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class Okhttp3Client {
    private static final long TIMEOUT = 60;

    public static OkHttpClient.Builder BUILDER = new OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS);

    public static OkHttpClient CLIENT = BUILDER.build();

}
