package com.crykid.updata;

import android.content.Context;

import com.crykid.updata.config.AutoUpdataerConfig;
import com.crykid.updata.config.ConfigEnum;
import com.crykid.updata.config.IAutoUpdataerConfig;
import com.crykid.updata.upload.DialogUpload;
import com.crykid.updata.utils.DownloadProgresser;

/**
 * Created by : mr.lu
 * Created at : 2018/12/1 at 13:09
 * Description: 自动升级主类</br>
 * <p>可以看到这里有和AutoUploaderConfig{@link AutoUpdataerConfig}中一模一样的方法，这是因为
 * 它和AutoUploaderConfig实现了相同的接口IAutoUploaderConfig{@link IAutoUpdataerConfig},
 * 虽然功能一样，都可以用于初始化一样的参数，但是我建议在程序一开始的地方使用AutoUploaderConfig的初始化方式，
 * 建议在Application中初始化而不是在这里</p>
 * <p>这里若重新配置，则会覆盖配置类中的配置</p>
 */
public class AutoUpdataer implements IAutoUpdataerConfig {

    private String url;
    private String desDir;
    private String authorities;
    private String apkName;
    private String absFileName;
    private boolean forceUpdata;


    private AutoUpdataer() {
    }

    private final static class HOLDER {
        private final static AutoUpdataer INSTANCE = new AutoUpdataer();
    }

    public static AutoUpdataer getInstance() {
        return HOLDER.INSTANCE;
    }

    /**
     * 无论以何种方式配置，url都是必须的
     *
     * @param url
     * @return
     */
    @Override
    public AutoUpdataer url(String url) {
        AutoUpdataerConfig.getInstance().url(url);
        return this;
    }

    @Override
    public AutoUpdataer desDir(String desDir, String authorities) {
        AutoUpdataerConfig.getInstance().desDir(desDir, authorities);
        return this;
    }

    @Override
    public AutoUpdataer apkName(String apkName) {
        AutoUpdataerConfig.getInstance().url(apkName);
        return this;
    }

    @Override
    public AutoUpdataer forceUpdata(boolean force) {
        AutoUpdataerConfig.getInstance().forceUpdata(force);
        return this;
    }




    /**
     * 启用配置
     */
    private void enableConfigs() {
        //添加所有配置
        AutoUpdataerConfig.getInstance().configure();
        //获得所有配置
        url = AutoUpdataerConfig.getInstance().getConfiguration(ConfigEnum.DOWNLOAD_URL);
        desDir = AutoUpdataerConfig.getInstance().getConfiguration(ConfigEnum.DESTINATION_DIR);
        authorities = AutoUpdataerConfig.getInstance().getConfiguration(ConfigEnum.FILEPROVIDER_AUTHORITIES);
        apkName = AutoUpdataerConfig.getInstance().getConfiguration(ConfigEnum.APK_NAME);
        forceUpdata = AutoUpdataerConfig.getInstance().getConfiguration(ConfigEnum.FORCE_UPDATA);
        absFileName = desDir + apkName;

    }


    public void dialog(Context context) {
        dialog(context, null);
    }

    /**
     * 通过dialog进度提示
     */
    public void dialog(Context context, IAutoUpdataerCallback callback) {
        //启用配置
        enableConfigs();
        //开始下载
        new DialogUpload(
                context,
                url,
                absFileName,
                authorities,
                DownloadProgresser.getProcesser(context, forceUpdata),
                callback)
                .start();
    }


    /**
     * 通过系统通知栏方式
     */
    public void notification() {
        enableConfigs();
    }
}
