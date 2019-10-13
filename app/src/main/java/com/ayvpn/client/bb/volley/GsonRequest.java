package com.ayvpn.client.bb.volley;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


import java.io.UnsupportedEncodingException;

public class GsonRequest<T> extends JsonRequest<T> {

    private Class<T> mClass;

    public GsonRequest(String url, Class<T> cls, Listener<T> listener, ErrorListener errorListener) {
        this(Method.GET, url, null, cls, listener, errorListener);
    }

    public GsonRequest(int method, String url, Class<T> cls, Listener<T> listener, ErrorListener errorListener) {
        this(method, url, null, cls, listener, errorListener);
    }

    public GsonRequest(String url, String requestBody, Class<T> cls, Listener<T> listener, ErrorListener errorListener) {
        this(Method.GET, url, requestBody, cls, listener, errorListener);
    }

    public GsonRequest(int method, String url, String requestBody, Class<T> cls, Listener<T> listener, ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);

        mClass = cls;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
//            String json = "{\"code\":0,\"msg\":\"成功\",\"data\":{\"groups\":[{\"tag\":\"编辑推荐\",\"games\":[{\"main_activity\":\"com.supercell.clashroyale.GameAppKunlun\",\"pack_name\":\"com.supercell.clashroyale.kunlun\",\"app_name\":\"皇室战争\",\"icon_url\":\"http://img.52xiu.me/icon/com.supercell.clashroyale.baidu-85.jpg\",\"download_url\":\"http://hs.xd.com/\"},{\"main_activity\":\"com.supercell.boombeach.GameAppKunlun\",\"pack_name\":\"com.supercell.boombeach.landing\",\"app_name\":\"海岛奇兵\",\"icon_url\":\"http://img.52xiu.me/banner/201605271458058660b7.png\",\"download_url\":\"http://hs.xd.com/\"}]}]}}";
            Log.e("evan","response.statusCode = "+response.statusCode+" |json = "+json);

            return Response.success(new Gson().fromJson(json, mClass), HttpHeaderParser.parseCacheHeaders(response));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }
}
