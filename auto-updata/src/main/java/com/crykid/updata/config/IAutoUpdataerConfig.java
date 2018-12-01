package com.crykid.updata.config;

/**
 * Created by : mr.lu
 * Created at : 2018/12/1 at 15:49
 * Description: 升级器配置
 */
public interface IAutoUpdataerConfig {

    /**
     * 下载url
     *
     * @param url
     * @return
     */
    IAutoUpdataerConfig url(String url);

    /**
     * 存储的目标文件夹
     *
     * @param desDir
     * @return
     */
    IAutoUpdataerConfig desDir(String desDir);

    /**
     * apk文件名字
     *
     * @param apkName
     * @return
     */
    IAutoUpdataerConfig apkName(String apkName);

    /**
     * 是否强制升级
     *
     * @param force
     * @return
     */
    IAutoUpdataerConfig forceUpdata(boolean force);

    /**
     * 是否在安装完成自动删除apk文件
     *
     * @param autoDel
     * @return
     */
    IAutoUpdataerConfig autoDelApk(boolean autoDel);


}
