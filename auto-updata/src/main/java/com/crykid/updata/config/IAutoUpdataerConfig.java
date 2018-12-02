package com.crykid.updata.config;

/**
 * Created by : mr.lu
 * Created at : 2018/12/1 at 15:49
 * Description: 升级器配置
 */
public interface IAutoUpdataerConfig {

    /**
     * apk下载的url
     * 一般都应该是get方式的，所以我没有做请求方式的配置
     *
     * @param url 下载apk的url
     * @return
     */
    IAutoUpdataerConfig url(String url);

    /**
     * 设置apk存储文件夹的名字及provider的Authorities，
     * 可以不主动调用，因为我添加了默认配置在Constants{@link com.crykid.updata.Constants}
     *
     * @param desDir      将要存储apk的文件夹名字
     * @param authorities 文件夹的fileProvider的authorities，就是manifest里面的authorities（不包含applicationId）
     *                    eg：name.provider,example.provider,test.provider
     * @return
     */
    IAutoUpdataerConfig desDir(String desDir, String authorities);

    /**
     * 设置apk文件名字
     *
     * @param apkName apk的文件名字，带或不带".apk"后缀都可以
     * @return
     */
    IAutoUpdataerConfig apkName(String apkName);

    /**
     * 是否强制升级
     *
     * @param force type boolean：true-强制升级（不可取消）
     * @return
     */
    IAutoUpdataerConfig forceUpdata(boolean force);


}
