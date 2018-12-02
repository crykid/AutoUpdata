package com.crykid.updata.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.crykid.updata.Constants;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
    private static final String TAG = "FileUtils";

    public static String defaultDir() {
        return Constants.DEFAULT_DIR;
    }

    public static String defaultApkName() {
        return Constants.DEFAULT_APK_NAME;
    }

    public static String getPath(String packageName) {
        return Environment.getExternalStorageDirectory() + "/" + packageName;
    }


    /**
     * @param stream
     * @param filePath 保存app的文件夹
     * @param fileName 文件名字
     * @throws Exception
     */
    public static void saveFile(InputStream stream, String filePath, String fileName) throws Exception {
        String absFileName = "";
        if (TextUtils.isEmpty(fileName) || TextUtils.isEmpty(fileName)) {
            throw new Exception("fileNath and fileName can not be empty");
        } else if (!TextUtils.isEmpty(filePath) && !TextUtils.isEmpty(fileName)) {
            checkOrCreateDir(filePath);
            absFileName = filePath + "/" + fileName;

        } else {
            absFileName = getDefaultAbsName();
        }


    }

    public static File saveFile(InputStream stream, String absFileName) throws Exception {

        InputStream is = stream;
        File file = null;
        try {
            file = new File(absFileName);
            // 目录不存在创建目录
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);

            }
            fos.close();
            bis.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }


    /**
     * @param dir
     */
    public static void checkOrCreateDir(String dir) {
        File f = new File(dir);
        if (!f.exists()) {
            f.mkdirs();
            Log.d(TAG, "checkOrCreateDir: " + dir);
        }

    }


    private static String getDefaultAbsName() {
        return Constants.DEFAULT_DIR + "/" + Constants.DEFAULT_APK_NAME;
    }


}
