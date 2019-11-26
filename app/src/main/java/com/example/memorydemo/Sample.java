package com.example.memorydemo;

import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * 很多时候我们在需要用到 Activity 或者 Context 的地方，会直接将 Activity 的实例
 * <p>
 * 作为参数传给对应的类，就像这样：
 */

//public class Sample {
//
//    private Context mContext;
//
//    public Sample(Context context){
//        this.mContext = context;
//    }
//
//    public Context getContext() {
//        return mContext;
//    }
//}

// 外部调用
//Sample sample = new Sample(MainActivity.this);

/**
 * 这种情况如果不注意的话，很容易就会造成内存泄露，比较好的写法是使用弱引用
 *
 * （WeakReference）来进行改进。
 *
 */

public class Sample {
    private WeakReference<Context> weakReference;

    public Sample(Context context) {
        this.weakReference = new WeakReference<>(context);
    }

    public Context getContext() {
        if (weakReference.get() != null) {
            return weakReference.get();
        }
        return null;
    }
}

/**
 * 被弱引用关联的对象只能存活到下一次垃圾回收之前，也就是说即使 Sample 持有 Activity 的引用，
 * 但由于 GC 会帮我们回收相关的引用，被销毁的 Activity 也会被回收内存，这样我们就不用担心会
 * 发生内存泄露了。
 */