package com.crykid.updata;

import android.os.Environment;

import java.io.File;

/**
 * Created by : mr.lu
 * Created at : 2018/12/2 at 02:06
 * Description: 全局常量
 */
public class Constants {

    /**
     * 默认文件夹
     */
    public final static String DEFAULT_DIR = Environment.getExternalStorageDirectory() + "/" + "auto-updata";

    /**
     * 默认的fiprovider的authorities
     */
    public final static String DEFAULT_AUTHORITIES = ".provider";


    /**
     * 默认apk文件名
     */
    public final static String DEFAULT_APK_NAME = "auto_updata.apk";

    /**
     * apk文件名后缀
     */
    public final static String APKSUFFIX = ".apk";
}
