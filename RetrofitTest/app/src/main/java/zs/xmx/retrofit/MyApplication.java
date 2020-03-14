package zs.xmx.retrofit;

import android.app.Application;

import zs.xmx.retrofit.util.global.ProjectInit;


public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ProjectInit.init(this)
                .withNativeApiHost("http://www.baidu.com/")
                .configure();
    }
}
