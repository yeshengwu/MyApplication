package com.ayvpn.client.bb.data;

/*
*TODO ios统一成 Response
 *
 *   see also {@link #Response.java}.
  * */
public class Response2<T> {
    public int code;
    public String msg;
    public T data;
}
