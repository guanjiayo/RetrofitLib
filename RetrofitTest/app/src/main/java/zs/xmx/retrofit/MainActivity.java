package zs.xmx.retrofit;

import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import java.util.WeakHashMap;

import androidx.appcompat.app.AppCompatActivity;
import zs.xmx.retrofit.net.callback.IError;
import zs.xmx.retrofit.net.callback.IFailure;
import zs.xmx.retrofit.net.callback.ISuccess;
import zs.xmx.retrofit.net.retrofit_normal.RestClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //        HashMap params=new HashMap();
        //        params.put("username","jett");
        //        params.put("password","123");
        //        RestClient.create()
        //                .url("/login/info")
        //                .params(params)
        //                .success(new ISuccess() {
        //                    @Override
        //                    public void onSuccess(String responce) {
        //                        Toast.makeText(MainActivity.this, responce.toString(), Toast.LENGTH_SHORT).show();
        //                    }
        //                }).build()
        //                .get();
        //上传
        //http://dengpaoedu.com:8080/fileuploadanddownload/uploadServlet.
        WeakHashMap params=new WeakHashMap();
        params.put("file","abcd.txt");
        RestClient.create()
                .params(params)
                .url("/fileuploadanddownload/uploadServlet")
                .downFileName(Environment.getExternalStorageDirectory().getAbsolutePath()+"/test.txt")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String responce) {
                        Toast.makeText(MainActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    }
                }).failure(new IFailure() {
            @Override
            public void onFailure() {
                Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
            }
        }).error(new IError() {
            @Override
            public void onError(int code, String msg) {
                Toast.makeText(MainActivity.this, code+"", Toast.LENGTH_SHORT).show();
            }
        })
                .build().upload();
        //测试下载  http://dengpaoedu.com:8080/examples/test.zip
        //        RestClient.create()
        //                .params(params)
        //                .url("/examples/test.zip")
        //                .dir("/sdcard")
        //                .extension("zip")
        //                .success(new ISuccess() {
        //                    @Override
        //                    public void onSuccess(String responce) {
        //                        Toast.makeText(MainActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
        //                    }
        //                }).build().download();


    }
}
