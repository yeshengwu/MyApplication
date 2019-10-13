package com.ayvpn.client.bb.pack;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by shidu on 17/11/2.
 * http://blog.csdn.net/ysmintor_/article/details/69788931  Android 性能：避免在Android上使用ENUM
 * @IntDef   support-annotations-22.2.1
 */

public class AnnotationSeason {
    public static final int WINTER = 0;
    public static final int SPRING = 1;
    public static final int SUMMER = 2;
    public static final int FALL = 3;

    public AnnotationSeason(@Season int season) {
        System.out.println("Season :" + season);
    }

    @IntDef({WINTER, SPRING, SUMMER, FALL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Season {
    }

    public static void main(String[] args) {
        AnnotationSeason annotationSeason = new AnnotationSeason(SPRING);
    }
}
