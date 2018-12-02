package com.crykid.updata.config;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.crykid.updata.AutoUpdataer;
import com.crykid.updata.Constants;
import com.crykid.updata.utils.FileUtils;

import java.util.HashMap;

/**
 * Created by : mr.lu
 * Created at : 2018/12/1 at 17:02
 * Description: 自动升级-全局配置类，建议在Application中调用.</br>
 * <p>可以看到，这里和AutoUploader{@link AutoUpdataer}中存在相同的方法，这是因为他们实现了相同的配置接口类，
 * 这就意味着【自动升级】有两种配置的方式：</p>
 * <p>方式一：就是当前方式，在程序开始的地方，例如Application中调用该配置方式</p>
 * <p>方式二：通过AutoUploader的方式，在需要升级的时候在调用相关的方法</p>
 * <p>虽然是两种方式，但是殊途同归，最终都是调用了此种方式，所以实际应用中可以根据情况来配置</p>
 */
public class AutoUpdataerConfig implements IAutoUpdataerConfig {

    private String url;
    private String desDir;
    private String authorities;
    private String apkName;
    private boolean forceUpdata;


    private final static HashMap<ConfigEnum, Object> CONFIGS = new HashMap<>();

    private AutoUpdataerConfig() {
        CONFIGS.put(ConfigEnum.CONFIGURE_COMPLETE, false);
        setDefault();
    }

    private final static class HOLDER {
        private final static AutoUpdataerConfig INSTANCE = new AutoUpdataerConfig();
    }

    public static AutoUpdataerConfig getInstance() {
        return HOLDER.INSTANCE;
    }

    /**
     * 无论以何种方式配置，url都是必须的
     *
     * @param url 下载apk的地址
     * @return
     */
    @Override
    public final AutoUpdataerConfig url(String url) {
        if (!url.startsWith("http")) {
            throw new IllegalArgumentException("this url/link may not be correct !");
        }
        this.url = url;
        return this;
    }


    /**
     * @param desDir      将要存储apk的文件夹名字
     * @param authorities 文件夹的fileProvider的authorities，就是manifest里面的authorities（不包含applicationId）
     *                    eg：name.provider,example.provider,test.provider
     * @return
     */
    @Override
    public AutoUpdataerConfig desDir(String desDir, String authorities) {
        if (TextUtils.isEmpty(desDir) || TextUtils.isEmpty(authorities)) {
            throw new NullPointerException("the name or authorities of destination  directory of apk  can not be empty !");
        }
        if (!desDir.endsWith("/")) {
            this.desDir = desDir + "/";
        } else {
            this.desDir = desDir;
        }

        if (authorities.startsWith(".")) {
            this.authorities = authorities;
        } else {
            this.authorities = "." + authorities;
        }
        return this;
    }

    /**
     * 下载的apk名字
     * the name of apk
     *
     * @param name
     * @return
     */
    @Override
    public final AutoUpdataerConfig apkName(String name) {
        if (TextUtils.isEmpty(name)) {
            throw new NullPointerException("the name of apk can not be empty !");
        }
        if (!name.endsWith(Constants.APKSUFFIX)) {
            this.apkName = name + Constants.APKSUFFIX;
        } else {
            this.apkName = name;
        }
        return this;
    }

    /**
     * 是否强制升级
     *
     * @param force type boolean：true-强制升级（不可取消）
     * @return
     */
    @Override
    public final AutoUpdataerConfig forceUpdata(boolean force) {
        this.forceUpdata = force;
        return this;
    }


    /**
     * 配置默认项
     */
    private void setDefault() {
        this.desDir = FileUtils.defaultDir() + "/";
        this.authorities = Constants.DEFAULT_AUTHORITIES;
        this.apkName = FileUtils.defaultApkName();
        this.forceUpdata = false;
    }

    /**
     * 完成配置
     */
    public void configure() {
        CONFIGS.put(ConfigEnum.DOWNLOAD_URL, url);
        CONFIGS.put(ConfigEnum.DESTINATION_DIR, FileUtils.getPath(desDir));
        CONFIGS.put(ConfigEnum.FILEPROVIDER_AUTHORITIES, authorities);
        CONFIGS.put(ConfigEnum.APK_NAME, apkName);
        CONFIGS.put(ConfigEnum.FORCE_UPDATA, forceUpdata);
        CONFIGS.put(ConfigEnum.CONFIGURE_COMPLETE, true);
        //检查或创建目标文件夹
        FileUtils.checkOrCreateDir(desDir);

    }

    /**
     * 检查是否配置完成
     */
    private void configureComplete() {
        final boolean complete = (boolean) CONFIGS.get(ConfigEnum.CONFIGURE_COMPLETE);
        if (!complete) {
            throw new RuntimeException("Configuration is not complete,please call configure()");
        }
    }

    /**
     * 获取单个配置,如果不存在则抛出异常
     *
     * @param key
     * @param <T>
     * @return
     */
    public final <T> T getConfiguration(@NonNull ConfigEnum key) {
        configureComplete();
        final Object o = CONFIGS.get(key);
        if (o == null) {
            throw new NullPointerException(key.name() + " is null !");
        }
        return (T) CONFIGS.get(key);
    }

    /**
     * 获取全部配置
     *
     * @return
     */
    public final HashMap<ConfigEnum, Object> getConfigurations() {
        configureComplete();
        return CONFIGS;
    }


}
