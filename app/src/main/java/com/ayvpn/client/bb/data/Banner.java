package com.ayvpn.client.bb.data;

import android.content.Context;

import com.ayvpn.client.bb.util.UIHelper;

public class Banner {
    int contacts;
    int x;
    int y;
    int pressure;

    double PercentX;
    double PercentY;

    static Banner sInstance;

    public static Banner getInstance() {
        if (sInstance == null) {
            sInstance = new Banner();
        }
        return sInstance;
    }

    public double getPercentX(Context context) {
        if (x == 0) {
            x = 32767;
        }
        int width = UIHelper.getScreenWidth(context);
        return (double) width / x;
    }

    public double getPercentY(Context context) {
        if (y == 0) {
            y = 32767;
        }
        int height = UIHelper.getScreenHeight(context);
        return (double) height / y;
    }

    @Override
    public String toString() {
        return "Banner [contacts=" + contacts
                + ", x=" + x + ", y=" + y + ", pressure="
                + pressure + ", PercentX=" + PercentX + ", PercentY="
                + PercentY + "]";
    }
}
