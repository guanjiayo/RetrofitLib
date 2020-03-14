package zs.xmx.retrofit.net.retrofit_rx;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import zs.xmx.retrofit.net.https.HttpsUtils;
import zs.xmx.retrofit.util.global.ConfigKeys;
import zs.xmx.retrofit.util.global.ProjectInit;


/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2018/6/14 11:03
 * @本类描述	  Retrofit 封装(完成后能够链式调度使用)
 * @内容说明   使用RxJava方式
 *
 */
public final class RxRestCreator {
    /**
     * 产生一个全局的Retrofit客户端
     */
    private static final class RetrofitHolder {
        private static final String   BASE_URL        = ProjectInit.getConfiguration(ConfigKeys.NATIVE_API_HOST);
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//框架使用RxJava
                .build();

    }

    private static final class OkHttpHolder {
        private static final long                   TIME_OUT     = 60;//连接超时时间
        private static final ArrayList<Interceptor> INTERCEPTORS = ProjectInit.getConfiguration(ConfigKeys.INTERCEPTOR);
        private static final OkHttpClient.Builder   BUILDER      = new OkHttpClient.Builder();

        private static OkHttpClient.Builder addInterceptor() {
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()) {
                for (Interceptor interceptor : INTERCEPTORS) {
                    //仅在response调用一次
                    BUILDER.addInterceptor(interceptor);
                    //request 和 response 都调用一次
                    //BUILDER.addNetworkInterceptor(interceptor);
                }
            }
            return BUILDER;
        }


        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                //rust all the https point
                .sslSocketFactory(HttpsUtils.initSSLSocketFactory(),
                        HttpsUtils.initTrustManager())
                .build();
    }


    private static final class RxRestServiceHolder {
        private static final RxRestService REST_SERVICE = RetrofitHolder.RETROFIT_CLIENT.create(RxRestService.class);
    }

    /**
     * 提供接口让调用者得到Retrofit对象
     */
    public static RxRestService getRxRestService() {
        return RxRestServiceHolder.REST_SERVICE;
    }
}
