package com.crykid.updata.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by : mr.lu
 * Created at : 2018/12/1 at 16:07
 * Description: progressDialog
 */
public class DownloadProgresser {

    public static ProgressDialog getProcesser(Context context, boolean cancleAble) {
        ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(cancleAble);
        pd.setCanceledOnTouchOutside(cancleAble);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载安装包，请稍后");
        pd.setTitle("版本升级");
        return pd;

    }

}
