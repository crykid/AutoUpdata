package com.example.mrlu.autoupdata;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.crykid.updata.AutoUpdataer;
import com.crykid.updata.IAutoUpdataerCallback;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);
        final String url = "";

        //使用方式一，首先在Application中完成全部配置，然后在需要调用的地方调用下面代码
        AutoUpdataer.getInstance().dialog(this);

        AutoUpdataer.getInstance().dialog(this, new IAutoUpdataerCallback() {
            @Override
            public void uploadFailed() {

            }

            @Override
            public void uploadSuccess() {

            }
        });


        //使用方式二，不需要application中配置，全部在使用的时候再配置

        AutoUpdataer
                .getInstance()
                //下载地址,需要完整的地址
                .url("https://www.test.com")
                //目标存储目录/文件夹 和authorities,需要您自己在manifest和xml中做相关配置
                .desDir("demo-dir", "example.provider")
                //apk文件名，可以不包含后缀
                .apkName("test.apk")
                //是否强制升级
                .forceUpdata(true)
                //通过dialog提示进度的方式升级
                .dialog(this, new IAutoUpdataerCallback() {
                    @Override
                    public void uploadFailed() {
                        Log.d(TAG, "uploadFailed: ");
                    }

                    @Override
                    public void uploadSuccess() {
                        Log.d(TAG, "uploadSuccess: ");
                    }
                });
    }

    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
