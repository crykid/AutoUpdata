package com.crykid.updata.config;

/**
 * Created by : mr.lu
 * Created at : 2018/12/1 at 14:26
 * Description: 全局配置枚举
 */
public enum ConfigEnum {

    /**
     *
     */
    DOWNLOAD_URL,

    /**
     * apk下载目录
     */
    DESTINATION_DIR,

    /**
     * apk下载文件目录的FileProvider的authorities
     */
    FILEPROVIDER_AUTHORITIES,
    /**
     * apk文件名
     */
    APK_NAME,
    /**
     * 是否强制升级
     */
    FORCE_UPDATA,


    /**
     * 配置完成
     */
    CONFIGURE_COMPLETE,
}
