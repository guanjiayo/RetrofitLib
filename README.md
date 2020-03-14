# RetrofitLib
基于Retrofit封装的网络(包含普通的回调方式和Rxjava回调方式)



普通使用:

```
 HashMap params=new HashMap();
 
            RestClient.create()
            .url("域名后面的")
            .params(params)         
            .success(new ISuccess() {
                           @Override
                            public void onSuccess(String responce) {
                                Toast.makeText(MainActivity.this, responce.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }).build()
            .get();
            
     @注意: 我们这个封装库只能使用
     implementation 'com.squareup.retrofit2:converter-scalars:2.4.0'
```



结合RxJava2使用:

```
HashMap params=new HashMap();
                RxRestClient.create()
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
```



由于这个库用起来感觉失去自定义接口参数的优势,然后必须只能返回String,后续又要重新处理一遍数据,感觉和直接使用OKhttp差不多,因此个人暂时弃用,这里留作备份