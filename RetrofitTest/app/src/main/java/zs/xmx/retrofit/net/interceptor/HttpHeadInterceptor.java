package zs.xmx.retrofit.net.interceptor;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2018/9/5 12:58
 * @本类描述	  动态修改请求头拦截器
 * @内容说明
 *
 */
public class HttpHeadInterceptor  {

    // 参考下 start
//    init {
//        //通用拦截(把token放到Head,不需要每次请求都传token)
//        interceptor = Interceptor {
//            chain -> val request = chain.request()
//                    .newBuilder()
//                    .addHeader("Content_Type","application/json")
//                    .addHeader("charset","UTF-8")
//                    .addHeader("token",AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN))
//                    .build()
//
//            chain.proceed(request)
//        }
    // 参考下 end


//    private String newHost = "127.0.0.1";
//    private String path1 = "/test/upload/img";
//    private String path2 = "/test/upload/voice";
//
//    public static String requestBodyToString(RequestBody requestBody) throws IOException {
//        Buffer buffer = new Buffer();
//        requestBody.writeTo(buffer);
//        return buffer.readUtf8();
//    }
//
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        Request request = chain.request();
//        HttpUrl url = request.url();
//
//        //http://127.0.0.1/test/upload/img?userName=xiaoming&userPassword=12345
//        String scheme = url.scheme();//  http https
//        String host = url.host();//   127.0.0.1
//        String path = url.encodedPath();//  /test/upload/img
//        String query = url.encodedQuery();//  userName=xiaoming&userPassword=12345
//
//        StringBuffer sb = new StringBuffer();
//        sb.append(scheme).append(newHost).append(path).append("?");
//        Set<String> queryList = url.queryParameterNames();
//        Iterator<String> iterator = queryList.iterator();
//
//        for (int i = 0; i < queryList.size(); i++) {
//
//            String queryName = iterator.next();
//            sb.append(queryName).append("=");
//            String queryKey = url.queryParameter(queryName);
//            //对query的key进行加密
//            sb.append(CommonUtils.getMD5(queryKey));
//            if (iterator.hasNext()) {
//                sb.append("&");
//            }
//        }
//
//
//        String newUrl = sb.toString();
//
//
//        RequestBody body = request.body();
//        String bodyToString = requestBodyToString(body);
//        TestBean testBean = GsonTools.changeGsonToBean(bodyToString, TestBean.class);
//        String userPassword = testBean.getUserPassword();
//        //加密body体中的用户密码
//        testBean.setUserPassword(CommonUtils.getMD5(userPassword));
//
//        String testGsonString = GsonTools.createGsonString(testBean);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), testGsonString);
//
//        Request.Builder builder = request.newBuilder()
//                .post(requestBody)
//                .url(newUrl);
//
//        switch (path) {
//            case path1:
//                builder.addHeader("token","token");
//                break;
//            case path2:
//                builder.addHeader("token","token");
//                builder.addHeader("uid","uid");
//                break;
//        }
//
//
//
//
//
//        return chain.proceed(builder.build());
//    }

}
