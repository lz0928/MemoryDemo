package com.example.memorydemo;

import android.content.Context;

import java.lang.ref.PhantomReference;
import java.lang.ref.SoftReference;
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
 *
 * Java中的引用
 * 在Java中，将引用方式分为：强引用、软引用、弱引用、虚引用，这四种引用强度依次逐渐减弱。
 *
 * 强引用：类似“Object obj = new Object()”这类的引用，只要强引用还存在，垃圾收集器永远不会回收掉被引用的对象。
 *
 * 软引用：用来描述一些还有用但并非必须的对象。在系统将要发生内存溢出之前，将会把这些对象列进回收范围之中进行第二次回收。
 *
 * 弱引用：用户描述非必须对象的。被弱引用关联的对象只能生存到下一次垃圾收集发生之前。当垃圾收集器工作时，无论当前内存是否足够，都会回收掉只被弱引用关联的对象。
 *
 * 虚引用：一个对象是否有虚引用存在，完全不会对其生存时间构成影响，也无法通过虚引用来取得一个对象实例。为一个对象设置虚引用的唯一目的就是能在这个对象被收集器回收时刻得到一个系统通知。
 *
 * 作者：编码前线
 * 链接：https://www.jianshu.com/p/0775fed46f9d
 * 来源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 *
 *   @author zhoguang@unipus.cn
 *   @date  2019/11/27 14:32
 */
public class ReferenceSample {

    /**
     * 用来描述一些还有用但并非必须的对象。在系统将要发生内存溢出之前，
     * 将会把这些对象列进回收范围之中进行第二次回收。
     */
    private SoftReference<Context> softReference;

    /**
     * 用户描述非必须对象的。被弱引用关联的对象只能生存到下一次垃圾收集发生之前。
     * 当垃圾收集器工作时，无论当前内存是否足够，都会回收掉只被弱引用关联的对象。
     */
    private WeakReference<Context> weakReference;



    /**
     * 使用虚引用的目的就是为了得知对象被GC的时机，所以可以利用虚引用来进行销毁前
     * 的一些操作，比如说资源释放等。这个虚引用对于对象而言完全是无感知的，有没有
     * 完全一样，但是对于虚引用的使用者而言，就像是待观察的对象的把脉线，可以通过
     * 它来观察对象是否已经被回收，从而进行相应的处理
     */
    private PhantomReference<Context> phantomReference;

    public ReferenceSample(){

    }

    //ReferceSample只拿到了Context的一个弱引用，不会导致Activity不能释放。
    public ReferenceSample(Context context) {
        this.weakReference = new WeakReference<>(context);
    }

    public Context getContext() {
        if (weakReference.get() != null) {
            return weakReference.get();
        }
        return null;
    }

    public void sayHello(){
        System.out.println("hello World!");
    }
}

/**
 * 被弱引用关联的对象只能存活到下一次垃圾回收之前，也就是说即使 Sample 持有 Activity 的引用，
 * 但由于 GC 会帮我们回收相关的引用，被销毁的 Activity 也会被回收内存，这样我们就不用担心会
 * 发生内存泄露了。
 */