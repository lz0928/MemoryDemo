package com.example.memorydemo;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         *  如果当前进程是用来给LeakCanary进行堆分析的则return，否则会执行LeakCanary的install方法。这样我们就可以
         *
         *  使用LeakCanary了，如果检测到某个Activity有内存泄露,LeakCanary就会给出提示。
         */
        if(LeakCanary.isInAnalyzerProcess(this)){
            return;
        }

        LeakCanary.install(this);
    }
}
