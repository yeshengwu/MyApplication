package com.ayvpn.client.bb.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {

    private static final int DEFAULT_TIMEOUT = 30000;

    private static VolleySingleton instance;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private Context context;

    private VolleySingleton(Context ctx) {
        context = ctx;
        requestQueue = getRequestQueue();

        final int cacheSize = (int) Runtime.getRuntime().maxMemory() / 8;
        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {

            private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getByteCount();
                }
            };

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }

        return requestQueue;
    }

    public void clearCache() {
        Cache cache = requestQueue.getCache();
        cache.clear();
    }

    public <T> void addToRequestQueue(Request<T> req) {
        addToRequestQueue(req, DEFAULT_TIMEOUT);
    }

    public <T> void addToRequestQueue(Request<T> req, int timeout) {
        setRetryPolicy(req, timeout);
        getRequestQueue().add(req);
    }

    private <T> void setRetryPolicy(Request<T> req, int timeout) {
        RetryPolicy policy = new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        req.setRetryPolicy(policy);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
