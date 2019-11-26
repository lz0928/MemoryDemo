package com.example.memorydemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * new出一个匿名的Handler，采用sendMessageDelayed()方法来发送消息，这时如果
 * Activity被销毁(退出),而Handler里的消息还没处理完的话，Activity的内存也不
 * 会被回收。
 *
 * @author zhoguang@unipus.cn
 * @date 2019/11/26 14:08
 */
public class HandlerLeakActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_leak);

        Message message = Message.obtain();
        mHandler.sendMessageDelayed(message, 30000);

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("handleMessage", "30秒后提示");
        }
    };

    /**
     * 解决办法:
     * 1.继承实现静态内部类
     * 2.在onDestory中remove掉所有的消息
     */

//    private static Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            Log.e("handleMessage", "30秒后提示");
//        }
//    };
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        handler.removeCallbacksAndMessages(null);
//    }
}
