package zs.xmx.retrofit.net.retrofit_rx;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @本类描述	  RestClientBuilder和RestClient写成建造者模式让外部类链式调用
 * @内容说明   结合RxJava使用
 *
 */
public class RxRestClientBuilder {
    private WeakHashMap<String, Object> mParams = new WeakHashMap<>();
    private String                      mUrl;
    private RequestBody                 mBody;
    //上传与下载
    private File                        mFile;

    RxRestClientBuilder() {

    }

    public final RxRestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RxRestClientBuilder params(WeakHashMap<String, Object> params) {
        this.mParams.putAll(params);
        return this;
    }

    public final RxRestClientBuilder params(String key, Object value) {
        this.mParams.put(key, value);
        return this;
    }

    public final RxRestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    public final RxRestClientBuilder uploadFile(File file) {
        this.mFile = file;
        return this;
    }

    /**
     * 上传文件路径
     *
     * @param filePath
     * @return
     */
    public final RxRestClientBuilder uploadFile(String filePath) {
        this.mFile = new File(filePath);
        return this;
    }


    public final RxRestClient build() {
        return new RxRestClient(mParams, mUrl, mBody, mFile);
    }

}
