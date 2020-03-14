package zs.xmx.retrofit.util.global;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @本类描述   全局配置信息的key枚举类
 * @内容说明   枚举虽然会损耗一点性能,但它的线程安全
 */
public enum ConfigKeys {
    NATIVE_API_HOST,//服务器域名
    WEB_API_HOST,//WebView可能用的其他的域名
    APPLICATION_CONTEXT,//全局上下文
    ACTIVITY,//用于某些API不能使用全局上下文的情况
    INTERCEPTOR,//拦截器
    CONFIG_READY,//配置完成状态FLAG
    ICON,//字体图标库配置
    JAVASCRIPT_INTERFACE,//WebView调用JS代码唯一flag
    USER_AGENTS//判断是否是我们的app来打开这个网页flag

}
