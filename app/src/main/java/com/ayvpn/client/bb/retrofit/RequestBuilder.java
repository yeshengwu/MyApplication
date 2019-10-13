package com.ayvpn.client.bb.retrofit;

/**
 * Created by shidu on 15/11/11.
 */
public class RequestBuilder implements RequestInterceptor.RequestFacade{

    public RequestBuilder(){

    }

    @Override
    public void addHeader(String name, String value) {
        System.out.println("RequestBuilder.addHeader");
    }

    @Override
    public void addPathParam(String name, String value) {
        System.out.println("RequestBuilder.addPathParam");
    }

    @Override
    public void addEncodedPathParam(String name, String value) {
        System.out.println("RequestBuilder.addEncodedPathParam");
    }

    @Override
    public void addQueryParam(String name, String value) {
        System.out.println("RequestBuilder.addQueryParam");
    }

    @Override
    public void addEncodedQueryParam(String name, String value) {
        System.out.println("RequestBuilder.addEncodedQueryParam");
    }
}
