package com.example.memorydemo;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

public class RefrenceTest {
    boolean isRun = true;

    public static void main(String[] args) {
        new RefrenceTest().testReference();
    }

    public void testReference() {
        /**
         *  引用变量sample是ReferenceSample对象的强引用。sample变量是存在栈内存里，函数执行
         *  完sample变量就自动销毁，但如果函数一直没执行完，sample就一直不能销毁，进而sample
         *  指向的ReferenceSample对象所占用的内存也不能被回收。
         */
        ReferenceSample sample = new ReferenceSample();
        sample.sayHello();

        /**
         * 引用变量mySoftReference是ReferenceSample的软引用。即使sample没销毁，在系统将要发生
         * 内存溢出之前，也会把ReferenceSample删除，内存回收。
         */
        SoftReference<ReferenceSample> softReference = new SoftReference<>(new ReferenceSample());
        ReferenceSample mySoftReferenceSample = softReference.get();
        mySoftReferenceSample.sayHello();

        /**
         * 引用变量myWeakReferenceSample是ReferenceSample的弱引用。被弱引用关联的对象只能生存
         * 到下一次GC垃圾回收之前。当垃圾回收器工作时，无论当前内存是否充足，都会回收只被弱引
         * 用关联的对象。
         */
        WeakReference<ReferenceSample> weakReference = new WeakReference(new ReferenceSample());
        ReferenceSample myWeakReferenceSample = weakReference.get();
        myWeakReferenceSample.sayHello();



        ReferenceSample sampe4 = new ReferenceSample();
        final ReferenceQueue<ReferenceSample> referenceQueue = new ReferenceQueue<>();
        PhantomReference<ReferenceSample> phantomReference = new PhantomReference(sampe4, referenceQueue);
        ReferenceSample mPhantomReferenceSample = phantomReference.get();
        mPhantomReferenceSample.sayHello();


        System.out.println(sampe4.getClass() + "@" + sampe4.hashCode());
        new Thread() {
            public void run() {
                while (isRun) {
                    Object obj = referenceQueue.poll();
                    if (obj != null) {
                        try {
                            Field rereferent = Reference.class
                                    .getDeclaredField("referent");
                            rereferent.setAccessible(true);
                            Object result = rereferent.get(obj);
                            System.out.println("gc will collect："
                                    + result.getClass() + "@"
                                    + result.hashCode() + "\t"
                                    + (String) result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();
        sampe4 = null;  //去掉强引用
        try {
            Thread.currentThread().sleep(3000);
            System.gc();
            Thread.currentThread().sleep(3000);
            isRun = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
