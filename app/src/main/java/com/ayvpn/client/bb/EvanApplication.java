package com.ayvpn.client.bb;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by shidu on 16/11/1.
 */
public class EvanApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        Log.e("evan","EvanApplication attachBaseContext");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e("evan","EvanApplication onCreate");
    }
}
