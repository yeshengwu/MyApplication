package com.testforpro;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * 实际做项目中需要验证的小测试代码片段。
 * Created by shidu on 18/1/25.
 */

public class Test {
    public static void main(String[] args) throws URISyntaxException {
        //119
//        String testLen = "{\"result\":0,\"periodId\":10773,\"status\":2,\"bid\":101930,\"bidLeftTime\":7,\"bidUserId\":\"1858146465\",\"bidNickname\":\"角落hahaha\"}";
        //115
//        String testLen = "{\"result\":0,\"periodId\":10773,\"status\":2,\"bid\":103180,\"bidLeftTime\":9,\"bidUserId\":1134076960,\"bidNickname\":\"小可爱周芷馨\"}";
//        String testLen = "{\"result\":0,\"bidNickname\":\"小可爱周芷馨\"}"; //35

        System.out.println("System.currentTimeMillis() = "+ System.currentTimeMillis());

        String testLen = "小"; //1
        System.out.println("testLen = "+testLen.length());
        if ("http://192.168.0.21:81/detail.html?id=11626".equals("http://192.168.0.21:81/")){
            System.out.println("equals");
        } else {
            System.out.println("equals no");
        }

        int offsetx = 1080;
        double index = Math.floor((offsetx - 360 / 2.0) / 360) + 1;
        System.out.println("index ："+ index);

        System.out.println("1.3/1.0 ："+ 1.3/1.0);
        System.out.println("1.3/1.0 ："+ 1.3/1.0);
        System.out.println("0.7/1.0 ："+ 0.7/1.0);
        System.out.println("1.7/0.3 ："+ 1.7/0.3);

        System.out.println("四舍五入："+ (int) (0 * 2 + 0.5f));

        System.out.println("format = "+ String.format("%X%X", 10000, 3));

//        serviceId = 10002, commandId = 2, seq = 3, str  = {"result":0,"periodId":10002,"usedRealCoin":4,"usedGiftCoin":5}
        /*String data= "data";
        String errorMsg = "null";
        String format = "%s('%s', %s, %d, %d, '%s')";  //window.UMClientApp.onEvent('response', null, 10002, 3, 'data')
//        if (!"null".equals(errorMsg)) {
//            format = "%s('%s', '%s', %d, %d, %s)";
//        }
        String js = String.format(format, "window.UMClientApp.onEvent", "response", errorMsg, 10002, 3, data);
        System.out.println("onResponse. js = "+js);*/
        String data= "null";
        String errorMsg = "Timeout";
        String format = "%s('%s', %s, %d, %d, '%s')";  //window.UMClientApp.onEvent('response', null, 10002, 3, 'data')
        if (!"null".equals(errorMsg)) {
            format = "%s('%s', '%s', %d, %d, %s)"; //window.UMClientApp.onEvent('response', 'Timeout', 10002, 3, null)
        }
        String js = String.format(format, "window.UMClientApp.onEvent", "response", errorMsg, 10002, 3, data);
        System.out.println("onResponse. js = "+js);

//        String url = "http://192.168.0.133:8080/login.html?redirect=%2Fmycenter.html";
//        String url = "http://192.168.0.133:8080/mycenter.html";
//        String url = "http://192.168.0.133:8080/";
//        String url = "http://192.168.0.21:81/";
        String url = "https://paipai.idianyun.cn/";
//        String url = "https://paipai.idianyun.cn/mycenter.html";

        URI uri = new URI(url);
        System.out.println("getHost() = "+uri.getHost());
        System.out.println("getPort() = "+uri.getPort());
        //paipai.idianyun.cn
        System.out.println("uri.getHost() + : + uri.getPort() = "+uri.getHost() + ":" + uri.getPort()); //paipai.idianyun.cn:-1
        if ((uri.getHost()+":"+uri.getPort()).equals("192.168.0.21:81")){
            System.out.println("port equals");
        } else {
            System.out.println("port equals 2");
        }

        if (uri.getPath().equals("mycenter.html")){
            System.out.println("getPath1");
        } else {
            System.out.println("getPath 2");
        }

        if (uri.getQuery() != null) {
            System.out.println("uri.getQuery() = " + uri.getQuery());
        } else {
            System.out.println("uri.getQuery() =  no.");
        }

        if (url.endsWith("mycenter.html") /*&& url.*/) {
            System.out.println("endsWith");
        } else {
            System.out.println("endsWith no.");
        }

    }
}
