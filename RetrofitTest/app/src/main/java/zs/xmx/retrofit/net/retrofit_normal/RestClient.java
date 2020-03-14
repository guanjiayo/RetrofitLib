package zs.xmx.retrofit.net.retrofit_normal;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import zs.xmx.retrofit.net.HttpMethod;
import zs.xmx.retrofit.net.callback.IError;
import zs.xmx.retrofit.net.callback.IFailure;
import zs.xmx.retrofit.net.callback.IRequest;
import zs.xmx.retrofit.net.callback.ISuccess;
import zs.xmx.retrofit.net.callback.RequestCallBacks;
import zs.xmx.retrofit.net.download.DownloadHandler;


/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2018/6/14 12:05
 * @本类描述	  RestClientBuilder和RestClient写成建造者模式让外部类链式调用
 * @内容说明   真正外部使用的是这个类:
 *            HashMap params=new HashMap();
 *
 *            RestClient.create()
 *            .url("域名后面的")
 *            .params(params)
 *            .build()
 *            .get();
 *
 * @注意: 我们这个封装库只能使用
     implementation 'com.squareup.retrofit2:converter-scalars:2.4.0'
 *
 */
public class RestClient {
    private final WeakHashMap<String, Object> mParams;
    private final String                      mUrl;
    private final IRequest                    mRequest;
    private final ISuccess                    mSuccess;
    private final IFailure                    mFailure;
    private final IError                      mError;
    private final RequestBody                 mBody;
    //上传与下载
    private final File                        FILE;
    private final String                      DOWNLOAD_DIR;
    private final String                      EXTENSION;
    private final String                      FILENAME;

    RestClient(WeakHashMap<String, Object> params,
               String url,
               IRequest request,
               ISuccess success,
               IFailure failure,
               IError error,
               RequestBody body,
               File file,
               String download_dir,
               String extension,
               String filename) {
        this.mParams = params;
        this.mUrl = url;
        this.mRequest = request;
        this.mSuccess = success;
        this.mFailure = failure;
        this.mError = error;
        this.mBody = body;
        this.FILE = file;
        this.DOWNLOAD_DIR = download_dir;
        this.EXTENSION = extension;
        this.FILENAME = filename;
    }

    public static RestClientBuilder create() {
        return new RestClientBuilder();
    }

    //Retrofit实现请求数据
    private void request(HttpMethod method) {
        final RestService service = RestCreator.getRestService();
        Call<String> call = null;
        if (mRequest != null) {
            //开始请求服务器数据
            mRequest.onRequestStart();
        }
        switch (method) {
            case GET:
                if (mParams == null) {
                    call = service.getNull(mUrl);
                } else {
                    call = service.get(mUrl, mParams);
                }
                break;
            case POST:
                if (mParams == null) {
                    call = service.postNull(mUrl);
                } else {
                    call = service.post(mUrl, mParams);
                }
                break;
            case PUT:
                call = service.put(mUrl, mParams);
                break;
            case DELETE:
                call = service.delete(mUrl, mParams);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData(
                                "file", FILE.getName(), requestBody);
                call = service.upload(mUrl, body);
                break;
            case DOWNLOAD:
                new DownloadHandler(mParams, mUrl, mRequest, mSuccess,
                        mFailure, mError, DOWNLOAD_DIR,
                        EXTENSION, FILENAME).handleDownload();
                break;
            case PUT_RAW:
                call = service.putRaw(mUrl, mBody);
                break;
            case POST_RAW:
                call = service.postRaw(mUrl, mBody);
                break;
            default:
                break;
        }
        if (call != null) {
            call.enqueue(getRequestCallback());
        }

    }

    private Callback<String> getRequestCallback() {
        return new RequestCallBacks(mRequest, mSuccess, mFailure, mError);
    }

    //下面是各种请求
    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
        if (mBody == null) {
            request(HttpMethod.POST);
        } else {
            if (mParams != null && !mParams.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.POST_RAW);
        }

    }

    public final void put() {
        if (mBody == null) {
            request(HttpMethod.PUT);
        } else {
            if (mParams != null && !mParams.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

    public final void download() {
        request(HttpMethod.DOWNLOAD);
    }

    public final void upload() {
        request(HttpMethod.UPLOAD);
    }
}
