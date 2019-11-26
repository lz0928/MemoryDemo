package com.example.memorydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ThreadLeakActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_leak);

        /**
         * new一个匿名非静态内部类做耗时操作，如果Activity退出，但Thread中的
         * 耗时操作没有结束的话，便会内存泄露。
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

/**
 * 解决办法：创建一个静态内部类MyThread，继承Thread。
 */
