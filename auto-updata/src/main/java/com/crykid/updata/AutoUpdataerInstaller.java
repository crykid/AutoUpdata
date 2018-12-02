package com.crykid.updata;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;

/**
 * Created by : mr.lu
 * Created at : 2018/12/2 at 02:45
 * Description: apk安装
 */
public class AutoUpdataerInstaller {
    private static final String TAG = "AutoUpdataerInstaller";

    /**
     * @param context
     * @param file        apk文件
     * @param authorities fileProvider的ahthorities，不用加applicationId
     */
    public static void install(Context context, File file, String authorities) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + authorities, file);

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");

        } else {
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

}
