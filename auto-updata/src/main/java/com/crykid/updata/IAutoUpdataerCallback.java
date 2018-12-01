package com.crykid.updata;

/**
 * Created by : mr.lu
 * Created at : 2018/12/1 at 14:37
 * Description: 自动升级结果回调
 */
public interface IAutoUpdataerCallback {

    void uploadFailed();

    void uploadSuccess();
}
