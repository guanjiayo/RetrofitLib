package zs.xmx.retrofit.net.retrofit_rx;

import java.io.File;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import zs.xmx.retrofit.net.HttpMethod;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2018/6/14 12:05
 * @本类描述	  RestClientBuilder和RestClient写成建造者模式让外部类链式调用
 *            (这边结合RxJava实现)
 * @内容说明   真正外部使用的是这个类:
 *            HashMap params=new HashMap();
 *            RxRestClient.create()
                .url(BaseConstant.URL_LOGIN)
                .params(params)
                .build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
            //    .compose(lifecycleProvider.bindToLifecycle()) 在这里绑定RxLifecycle
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


     @注意: 我们这个封装库只能使用
     implementation 'com.squareup.retrofit2:converter-scalars:2.4.0'
 *
 */
public class RxRestClient {
    private final WeakHashMap<String, Object> mParams;
    private final String                      mUrl;
    private final RequestBody                 mBody;
    //上传与下载
    private final File                        FILE;

    RxRestClient(WeakHashMap<String, Object> params,
                 String url,
                 RequestBody body,
                 File file) {
        this.mParams = params;
        this.mUrl = url;
        this.mBody = body;
        this.FILE = file;
    }

    public static RxRestClientBuilder create() {
        return new RxRestClientBuilder();
    }

    //Retrofit实现请求数据
    private Observable<String> request(HttpMethod method) {
        final RxRestService service = RxRestCreator.getRxRestService();
        Observable<String> observable = null;
        switch (method) {
            case GET:
                if (mParams == null) {
                    observable = service.getNull(mUrl);
                } else {
                    observable = service.get(mUrl, mParams);
                }
                break;
            case POST:
                if (mParams == null) {
                    observable = service.postNull(mUrl);
                } else {
                    observable = service.post(mUrl, mParams);
                }
                break;
            case PUT:
                observable = service.put(mUrl, mParams);
                break;
            case DELETE:
                observable = service.delete(mUrl, mParams);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body = MultipartBody.Part.createFormData(
                        "file", FILE.getName(), requestBody);
                observable = service.upload(mUrl, body);
                break;
            case PUT_RAW:
                observable = service.putRaw(mUrl, mBody);
                break;
            case POST_RAW:
                observable = service.postRaw(mUrl, mBody);
                break;
            default:
                break;
        }
        return observable;

    }

    //下面是各种请求
    public final Observable<String> get() {
        return request(HttpMethod.GET);
    }

    public final Observable<String> post() {
        if (mBody == null) {
            return request(HttpMethod.POST);
        } else {
            if (mParams != null && !mParams.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            return request(HttpMethod.POST_RAW);
        }
    }

    public final Observable<String> put() {
        if (mBody == null) {
            return request(HttpMethod.PUT);
        } else {
            if (mParams != null && !mParams.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            return request(HttpMethod.PUT_RAW);
        }
    }

    public final Observable<String> delete() {
        return request(HttpMethod.DELETE);
    }

    public final Observable<ResponseBody> download() {
        return RxRestCreator.getRxRestService().download(mUrl, mParams);
    }

    public final Observable<String> upload() {
        return request(HttpMethod.UPLOAD);
    }
}
