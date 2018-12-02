package com.example.mrlu.autoupdata;

import android.app.Application;

import com.crykid.updata.config.AutoUpdataerConfig;

public class AtApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AutoUpdataerConfig
                .getInstance()
                .url("https://test.95590.cn/nloan_test/aa.apk")
                .desDir("auto-updata-apk", "example.provider")
                .apkName("auto-updata")
                .forceUpdata(true)
                .autoDelApk(true)
                .configure();
    }
}
