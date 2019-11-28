package com.example.memorydemo;

import java.util.ArrayList;
import java.util.List;

/**
 *   定义一个引用变量，new一个对象，变量指向对象。引用变量设置成null后，它就不再指向对象了
 *   此时对象没有强引用，内存可以回收。但将引用变量指向的对象放进集合一后，即使引用变量置
 *   成null，对象还是不能被回收，因为集合也挂有对象的强引用。此时需要清空集合对象。
 *   
 *   @author zhoguang@unipus.cn
 *   @date  2019/11/27 15:35
 */
public class CollectionSample {
    List objectList = new ArrayList();

    public static void main(String []args){

    }

    private void init(){

        for(int i = 0;i<10;i++){
            Object object = new Object();
            objectList.add(object);
            object = null;//虽然object变量不再指向Object对象，不是Object的强引用了，但是集合还是Object的强引用
        }
    }

    //清空集合对象，解决集合用完了对象还不能回收的问题。
    private void onDestory(){
        objectList.clear();
        objectList = null;
    }
}
