package zs.xmx.retrofit.net.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * 添加请求头拦截器
 */
class AddHeadInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .addHeader("charset", "UTF-8")
              //  .addHeader("token", UserConstant.token)
                .build()
        return chain.proceed(request)

    }
}