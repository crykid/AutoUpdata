package com.crykid.updata.upload;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.crykid.updata.AutoUpdataerInstaller;
import com.crykid.updata.IAutoUpdataerCallback;
import com.crykid.updata.downladerfactory.DownloaderFactory;
import com.crykid.updata.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import me.jessyan.progressmanager.ProgressListener;
import me.jessyan.progressmanager.ProgressManager;
import me.jessyan.progressmanager.body.ProgressInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by : mr.lu
 * Created at : 2018/12/1 at 14:00
 * Description:dialog方式升级
 */
public class DialogUpload implements IStartUpload, Callback {
    private static final String TAG = "DialogUpload";
    private Context mContext;
    private final String DOWNLOAD_URL;
    private ProgressDialog pd;
    private final String ABSTRACT_FILENAME;
    private final String FILEPROVIDER_AUTHORITIES;
    private final IAutoUpdataerCallback UPLOAD_CALLBACK;

    /**
     * @param url         下载地址
     * @param absFileName apk存储目标文件夹+名字
     * @param authorities apk所在目录的fileProvider的authorities
     * @param pd          progressDialog
     * @param callback    IAutoUpdataerCallback，用于通知升级结果
     */
    public DialogUpload(Context context, String url, String absFileName, String authorities, ProgressDialog pd, IAutoUpdataerCallback callback) {
        this.mContext = context;
        this.DOWNLOAD_URL = url;
        this.ABSTRACT_FILENAME = absFileName;
        this.FILEPROVIDER_AUTHORITIES = authorities;
        this.pd = pd;
        this.UPLOAD_CALLBACK = callback;
    }

    @Override
    public void start() {
        pd.show();
        DownloaderFactory.getInstance().okhttp3Downloader(this).download(DOWNLOAD_URL);
        addDownloadListener(DOWNLOAD_URL, pd);

    }

    /**
     * 设置下载监听-更新下载进度弹窗的进度
     * <p>通过给OkHttp设置inteceptor的方式监听下载进度，进而更新ProgressDialog的进度</p>
     *
     * @param intercepteTarget 监听对象地址，我们以url为tag来监听
     * @param pd               进度框
     */
    private void addDownloadListener(String intercepteTarget, final ProgressDialog pd) {
        //下载进度监听
        ProgressManager.getInstance().addResponseListener(DOWNLOAD_URL, new UpdataProgressListener(pd));
    }


    @Override
    public void onFailure(Call call, IOException e) {
        Log.e(TAG, "onFailure: ", e);
        if (UPLOAD_CALLBACK != null)
            UPLOAD_CALLBACK.uploadFailed();
        if (pd != null && pd.isShowing()) {
            pd.cancel();
        }
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response != null && response.body() != null) {
            if (response.body().byteStream() != null) {
                InputStream is = response.body().byteStream();
                try {
                    //存储文件
                    File file = FileUtils.saveFile(is, ABSTRACT_FILENAME);
                    if (file != null) {
                        if (UPLOAD_CALLBACK != null) {
                            UPLOAD_CALLBACK.uploadSuccess();
                        }
                        if (pd != null) {
                            pd.cancel();
                        }
                        //安装apk
                        AutoUpdataerInstaller.install(mContext, file, FILEPROVIDER_AUTHORITIES);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (UPLOAD_CALLBACK != null) {
                        UPLOAD_CALLBACK.uploadFailed();
                    }
                }
            }
        }
        if (pd != null && pd.isShowing()) {
            pd.cancel();
        }
        if (UPLOAD_CALLBACK != null) {
            UPLOAD_CALLBACK.uploadFailed();
        }
    }

    static class UpdataProgressListener implements ProgressListener {
        ProgressDialog pd;

        public UpdataProgressListener(ProgressDialog pd) {
            this.pd = pd;
        }

        @Override
        public void onProgress(ProgressInfo progressInfo) {
            double curent = progressInfo.getCurrentbytes();
            double total = progressInfo.getContentLength();
            //计算进度
            double percent = curent / total;
            //进度只能是0-100的整数
            int progress = (int) (percent * 100);

            if (pd != null) {
                pd.setProgress(progress);
                Log.d(TAG, "onProgress: " + progress);
                //当进度达到100%则隐藏进度
                if (progress == pd.getMax()) {
                    pd.cancel();
                }
            }
        }

        @Override
        public void onError(long id, Exception e) {
            Log.e(TAG, "onError: ", e);
        }
    }


}
