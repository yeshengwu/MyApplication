package com.ayvpn.client.bb;

import android.os.Looper;

/**
 * HandlerThread. 抽出 HandlerThread 代码来 单步debug。
 * see {@link android.os.HandlerThread }
 * <p>
 * Created by shidu on 16/12/8.
 */
public class SimulateHandleThread extends Thread {
    Looper mLooper;

    public SimulateHandleThread(String name) {
        super(name);
//        IntentService

    }

    @Override
    public void run() {
        Looper.prepare();
        synchronized (this) {
            mLooper = Looper.myLooper();
            notifyAll();
        }
//        onLooperPrepared();
        Looper.loop();
    }


    public Looper getLooper() {
        if (!isAlive()) {
            return null;
        }

        synchronized (this) {
            while (isAlive() && mLooper == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return mLooper;
    }
}
