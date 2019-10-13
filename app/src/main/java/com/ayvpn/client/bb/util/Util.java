package com.ayvpn.client.bb.util;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util {
    /**
     * Hidden constructor.
     */
    private Util() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static final String ObjToJson(Object obj) {
        Gson gson = new Gson();

        return gson.toJson(obj);
    }

    // Cmd cmd = Util.loadObject(msg, Cmd.class);
    public static <T> T loadObject(String json, Class<T> klass) {
        T resp = null;
        Gson mGson = new Gson();
        try {
            resp = mGson.fromJson(json, klass);
        } catch (JsonSyntaxException e) {
        }

        return resp;
    }

    public static String getMobileOSLevel()
    {
        return "Android" + Build.VERSION.SDK_INT;
    }

    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * @param times 1526431920000
     * @return 平板返回 True，手机返回 False
     */
    public static String getDateTime(String pattern, long times) {
        DateFormat format = new SimpleDateFormat(pattern, Locale.US);
        String dateTime = format.format(new Date(times));

        return dateTime;
    }
}
