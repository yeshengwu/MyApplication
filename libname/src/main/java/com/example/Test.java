package com.example;

import org.apache.http.util.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shidu on 16/11/16.
 * https://www.v2ex.com/t/403042#reply10  一个 Java 多线程问题
 */
public class Test implements ICompose ,ICompose.IData{
    private static final String LOG_TAG = "Test";

    private static List<Notify> notifyCacheList = new ArrayList<>();

    public static void main(String[] args) {
        /*Integer a =100,b=100;
        System.out.println(a==b);

        Integer c =150,d=150;
        System.out.println(c==d); //false see Integer.valueof()
        System.out.println(c.equals(d)); //true

        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("ja").append("va").toString(); //false
//        String str2 = new StringBuilder("evan").append("ye").toString(); //true
        System.out.println(str2.intern() == str2);*/

        Test1 test1 = new Test1();

        new Thread(test1).start();

        int i = 0;
        while (test1.getName() == null){
            // 关键点
//            System.out.println("xuanxue");
            i++;
        }

        System.out.println(test1.getName() + "  " + i);

        float price = (float) 1.01;
        System.out.println(String.valueOf(price));
        System.out.println(String.format("¥%.2f", price));

//        DetailData detailData = null;
//        System.out.println("detailData = null get field "+ detailData.data); // NullPointerException

        DetailData detailData2 = new DetailData();
        System.out.println("detailData2 != null get field = "+ detailData2.data); // NullPointerException

//        String[] splits = null;
//        String[] splits = {"a b"};
        String[] splits = {"a b","c"};
        if (splits.length > 1 && !TextUtils.isEmpty(splits[1])) {
            splits = splits[1].split(" ");
            System.out.println("evan:"+splits);
        } else {
            System.out.println("evan"+"splits null");
        }

        System.out.println("go ....");

//        ThreadLocal
        int capacity = 2 >> 1;
        System.out.println(">> 1 capacity = "+capacity);
        System.out.println("2 << 1 capacity = "+(2 << 1));

        String WECHAT_LUCKMONEY_RECEIVE_ACTIVITY = "com.tencent.mm.plugin.luckymoney.ui.En_fba4b94f";
        if ("com.tencent.mm.plugin.luckymoney.ui.En_fba4b94f".contains(WECHAT_LUCKMONEY_RECEIVE_ACTIVITY)){
            System.out.println("contains");
        } else {
            System.out.println("contains no");
        }


        String str = "jjava怎么把字符串中的的汉字取出来";
        String reg = "[^\u4e00-\u9fa5]";
        str = str.replaceAll(reg, " ");
        System.out.println(str);

//        String des = "当前所在页面,与红包测试(6)的聊天"; // extract 红包测试(6)
//        String des = "当前所在页面,与红包的测试(6)的聊天"; // extract 红包测试(6)
        String des = "当前所在页面,与红包测试(6)聊天"; // extract 红包测试(6)
        //正则去匹配
        String reg1 = "^当前所在页面,与(.*)的聊天$";
        Pattern p = Pattern.compile(reg1);
        Matcher m = p.matcher(des);
        if (m.matches()) {
            System.out.println("m.groupCount() = " + m.groupCount());
            System.out.println("m.group(0) = " + m.group(0)); //Group zero denotes the entire pattern
            System.out.println("m.group(1) = " + m.group(1));
        } else {
            System.out.println("matches no.");
        }

       /* int indexOf1 = des.indexOf("与");
        System.out.println(indexOf1);
        int indexOf2 = des.lastIndexOf("的");
        System.out.println(indexOf2);
        if (indexOf2 == -1) indexOf2 = des.length();
        System.out.println(des.substring(indexOf1+1,indexOf2));*/
    }

    /**
     * 缓存一个 Notify
     */
    public static void cacheNotify(Notify n) {
        synchronized (notifyCacheList) {
            notifyCacheList.add(n);
        }

        System.out.println("cache notify");
    }

    /**
     * 上传缓存的通知
     */
    private static void uploadCachedNotifies() {
        Notify[] notifies;

        System.out.println("1cached notify count=" + notifyCacheList.size());

        synchronized (notifyCacheList) {
            notifies = notifyCacheList.toArray(new Notify[0]);
            notifyCacheList.clear();
        }

        if(notifies != null && notifies.length > 0)
//            Notify.send(db, notifies);

        System.out.println("2cached notify count=" + notifyCacheList.size());
    }

    @Override
    public void onCompose(String url) {

    }

    @Override
    public void onData(String url) {

    }


    private static class Notify {
    }
}

