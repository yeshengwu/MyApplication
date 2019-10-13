package com.example;

/**
 * Created by shidu on 17/11/3.
 * https://www.v2ex.com/t/403042#reply10  一个 Java 多线程问题
 */
public class Test1 implements Runnable {

    volatile String name;

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        name = "test1";
        System.out.println("set name over");
    }

    public String getName() {
        return name;
    }
}
