如何避免内存泄漏:

不要在匿名内部类中进行异步操作

将非静态内部类转为静态内部类 + WeakReference（弱引用）的方式

在 Activity 回调 onDestroy 时或者 onStop 时

移除消息队列 MessageQueue 中的消息
静态变量置null
停止异步任务
取消注册
使用Context时，尽量使用Application 的 Context

尽量避免使用static 成员变量。另外可以考虑lazy初始化

为webView开启另外一个进程，通过AIDL与主线程进行通信，WebView所在的进程可以根据业务的需要选择合适的时机进行销毁，从而达到内存的完整释放

及时关闭资源。Bitmap 使用后调用recycle()方法

