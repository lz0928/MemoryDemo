除了例子中提到的

    单例，非静态内部类(Thread,Handler等),集合等造成的内存泄露。还有下面的情况需要注意：

    1.

    1.手动注册广播时，退出时忘记unregisterReceivere()

    2.EventBus等观察者的框架，忘记手动解除注册。需要在onDestory里面调用
    EventBus.getDefault().unregister(this);

    3.关闭输入输出流。
        在使用IO、File流等资源时要及时关闭。这些资源在进行读写操作时通常用到了缓冲，如果
    不及时关闭，这些缓冲对象就会一直被占用得不释放，以致发生内存泄露。
         inputStream.close();
         outputStream.close();

    4.回收Bitmap
        Bitmap对象比较占内存，当它不再被使用的时候，最好调用Bitmap.recycle()方法主动进行回收。
        bitmap.recycle();
        bitmap = null;
    5.停止动画
        属性动画有一类无限动画，如果Activity退出时不停止动画的话，动画会一直执行下去。因为动
    画持有View的引用，View又持有Activity，最终Activity不能回收。解决的办法，只要我们在销毁
    Activity的时候停止动画就行。
        animation.cancel();

    6.ListView的item泄露。初始时ListView会从BaseAdapter中根据当前的屏幕布局实例化一定数量的view对象，
    同时ListView会将这些view对象缓存起来。我们应用使用convertView利用这View对象，往里填充不用的
    数据。另外listview要注意有图片的时候如果没有提供小图，使用Option.inSampleSize缩放后再加载。另
    外在Adapter中避免使用static中定义静态变量。

    7.销毁WebView
        WebView在加载网页后会长期占用内存而不能被释放，因此在Activity销毁后要调用它的destory
    方法来销毁webview以释放内存。
        @Override
        proteced void onDestory(){
            if(webView!=null){
                ViewParent parent = mWebView.getParent();
                if(parent!=null){
                    ((ViewGroup)parent).removeView(mWebView);
                }
                mWebView.stopLoading();
                //退出时调用此方法，移除绑定的服务，否则某此特定系统会报错。
                mWebView.getSettings().setJavaScriptEnabled(false);
                mWebView.clearHistory();
                mWebView.clearView();
                mWebView.removeAllViews();
                mWebView.destory();
            }
            super.onDestory();
        }


