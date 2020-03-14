package zs.xmx.retrofit.net.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * 保存Cookies到SP中
 */
class SaveCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())

        val request = chain.request()
        if (request.url.toString().contains("login") || request.url.toString().contains("register")) {
            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                val cookies = originalResponse.headers("Set-Cookie")

                if (cookies.isNotEmpty()) {
                    val cookieStr = StringBuilder()
                    for (cookie in cookies) {
                        cookieStr.append(cookie)
                        cookieStr.append("#")
                    }
                    //todo 看项目
                    //UserConstant.cookies=cookieStr.toString()
                }
            }
        }

        return originalResponse
    }
}