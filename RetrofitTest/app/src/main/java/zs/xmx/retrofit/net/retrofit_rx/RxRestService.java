package zs.xmx.retrofit.net.retrofit_rx;

import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2018/6/11 1:26
 * @本类描述	  Retrofit的所有功能(RxJava方式)
 * @内容说明
 *
 */
public interface RxRestService {

    //@QueryMap 就是讲参数拼接到url后面
    @GET
    Observable<String> get(@Url String url, @QueryMap WeakHashMap<String, Object> params);

    //post空参的情况
    @GET
    Observable<String> getNull(@Url String url);

    @FormUrlEncoded //数据以表达式添加 FormUrlEncoded
    @POST
    Observable<String> post(@Url String url, @FieldMap WeakHashMap<String, Object> params);

    //post空参的情况
    @POST
    Observable<String> postNull(@Url String url);

    @FormUrlEncoded
    @PUT
    Observable<String> put(@Url String url, @FieldMap WeakHashMap<String, Object> params);

    @DELETE
    Observable<String> delete(@Url String url, @QueryMap WeakHashMap<String, Object> params);

    //下载是直接到内存,所以需要 @Streaming
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url, @QueryMap WeakHashMap<String, Object> params);

    //上传
    @Multipart
    @POST
    Observable<String> upload(@Url String url, @Part MultipartBody.Part file);

    //原始数据
    //Content-Type为application/json使用这种方案
    @POST
    Observable<String> postRaw(@Url String url, @Body RequestBody body);

    //@Body 讲参数放到请求体,一般适合post方式,上面如果报错,就改成这个
    @PUT
    Observable<String> putRaw(@Url String url, @Body RequestBody body);
}
