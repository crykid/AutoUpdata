package com.crykid.updata.downladerfactory;

import com.crykid.updata.downloader.IDownloader;

import okhttp3.Callback;


/**
 * Created by : mr.lu
 * Created at : 2018/12/1 at 11:13
 * Description: 下载器抽象工厂
 */
public interface IDownloaderFactory {

    /**
     * OKhttp3的downloader-也是默认的downloader
     *
     * @return
     */
    IDownloader okhttp3Downloader(Callback c);

    /**
     * 使用系统原生的HttpConnection
     *
     * @return
     */
    IDownloader httpDownloader();

    /**
     * 用户自定义的，只需要实现相关接口即可-不过我暂时不打算让用户自己定义
     *
     * @return
     */
    @Deprecated
    IDownloader customerDownloader();

}
