package com.ayvpn.client.bb;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Constructor;

/**
 * any java little test
 * Created by shidu on 17/10/13.
 */

public class EvanTester {
    private static final String TAG = "evan-tester";

    public static void testReflect(Context context) {

//        InetAddress

        try {
            Class<?> c2 = Class.forName("com.ayvpn.client.bb.devicetest.CustomBuild");
            Object obj = null;
//            obj = c2.newInstance();
            Constructor c = c2.getConstructor(String.class);
            obj = c.newInstance("abc");    //通过有参构造创建对象
//            System.out.printf("%s",null);
            Log.e(TAG, "obj = " + obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testAppend() {
        String ab = null;
        StringBuilder sb = new StringBuilder();
        Log.e("evan", "evan append null and " + sb.append("ab:").append(ab)); //ab:null
    }
}
