package com.crykid.updata.downladerfactory;

import com.crykid.updata.downloader.IDownloader;
import com.crykid.updata.downloader.Okhttp3IDownloader;

import okhttp3.Callback;


public class DownloaderFactory implements IDownloaderFactory {


    private DownloaderFactory() {
    }

    private final static class HOLDER {
        private final static IDownloaderFactory INSTANCE = new DownloaderFactory();
    }

    public static IDownloaderFactory getInstance() {
        return HOLDER.INSTANCE;
    }

    @Override
    public IDownloader okhttp3Downloader(Callback callback) {
        return new Okhttp3IDownloader(callback);
    }

    @Override
    public IDownloader httpDownloader() {
        return null;
    }

    @Override
    public IDownloader customerDownloader() {
        return null;
    }
}
