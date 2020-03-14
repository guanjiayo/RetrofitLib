package zs.xmx.retrofit.net.download;

import android.os.AsyncTask;

import java.util.WeakHashMap;

import androidx.annotation.NonNull;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zs.xmx.retrofit.net.callback.IError;
import zs.xmx.retrofit.net.callback.IFailure;
import zs.xmx.retrofit.net.callback.IRequest;
import zs.xmx.retrofit.net.callback.ISuccess;
import zs.xmx.retrofit.net.retrofit_normal.RestCreator;


/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2018/6/14 16:13
 * @本类描述	  下载处理类
 * @内容说明   //todo 可以在这里把单点续存什么的处理完
 *
 */
public class DownloadHandler {
    private final WeakHashMap<String, Object> PARAMS;
    private final String                      URL;
    private final IRequest                    REQUEST;
    private final ISuccess                    SUCCESS;
    private final IFailure                    FAILURE;
    private final IError                      ERROR;
    private final String                      DOWNLOAD_DIR;
    private final String                      EXTENSION;
    private final String                      FILENAME;

    public DownloadHandler(WeakHashMap<String, Object> params, String url, IRequest request, ISuccess success, IFailure failure, IError error, String downloadDir, String extension, String filename) {
        this.PARAMS = params;
        this.URL = url;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.FILENAME = filename;
    }

    public final void handleDownload() {
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }
        RestCreator
                .getRestService()
                .download(URL, PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            final ResponseBody responseBody = response.body();
                            //开始保存文件(异步任务来做)
                            final SaveFileTask task = new SaveFileTask(REQUEST, SUCCESS);
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                                    DOWNLOAD_DIR, EXTENSION, responseBody, FILENAME);

                            //如果下载完成
                            //一定要注意判断，否则文件下载不全
                            if (task.isCancelled()) {
                                if (REQUEST != null) {
                                    REQUEST.onRequestEnd();
                                }
                            }
                        } else {
                            if (ERROR != null) {
                                ERROR.onError(response.code(), response.message());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (FAILURE != null) {
                            FAILURE.onFailure();
                        }
                    }
                });
    }
}
