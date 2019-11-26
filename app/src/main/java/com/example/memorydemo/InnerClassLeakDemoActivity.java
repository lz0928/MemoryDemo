package com.example.memorydemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 *  https://www.jianshu.com/p/65f914e6a2f8
 *
 *  非静态内部类自动获取外部类的强引用，如果一个Activity的非静态内部类
 *  的生命周期比Activity更长，那么Activity的内存便无法回收，也就是发生
 *  了内存泄露。
 *
 *   @author zhoguang@unipus.cn
 *   @date  2019/11/26 13:14
 */
public class InnerClassLeakDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inner_class_leak_activity);

        new MyAsyncTask().execute();
    }

    //非静态内部类自动获得外部类的强引用，即使Activity退出，AsyncTask没有结束，也不能释放Activity。
    class MyAsyncTask extends AsyncTask<Void, Integer, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                Thread.sleep(30000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }
    }

}
