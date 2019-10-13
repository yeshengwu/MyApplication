package com.ayvpn.client.bb.retrofit;

public class UserAgentInterceptor implements RequestInterceptor {
    @Override
    public void intercept(RequestFacade request) {
        request.addHeader("User-Agent","AyVPN Android/%s (%d); SDK %d");
    }
}
