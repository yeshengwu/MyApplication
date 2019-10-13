/*
package com.example;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.MessageDigest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletDemo extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String adv_id = request.getParameter("adv_id");//被下载应用ID
		String app_id = request.getParameter("app_id");//万普平台注册应用id
		String key = request.getParameter("key");//调用积分墙时传入的key或者用户id

		String udid = request.getParameter("udid");//设备唯码
		String openudid = request.getParameter("open_udid");	//设备open_udid
		String bill = request.getParameter("bill");//价格
		if(bill == null){bill = "";}
		String points = request.getParameter("points");//积分
		if(points == null){points = "";}
		String ad_name = request.getParameter("ad_name");//下载的应用名称
		String status = request.getParameter("status");//状态
		if(status == null){status = "";}
		String activate_time = request.getParameter("activate_time");//激活时间
		//========以下参数为旧版验证数据的参数，可以废弃此方法加密
		String order_id = request.getParameter("order_id");				//订单号
		String random_code = request.getParameter("random_code");//随机串
		String secret_key = request.getParameter("secret_key");//验证密钥
		//========新版验证
		String wapskey = request.getParameter("wapskey");//数据加密串
		String callBackKey = "test";		//回调密钥
		//当满足价格、积分不为空且不为0和状态码为1时才为有效数据
		if(!bill.equals("0") && !points.equals("0") && status.equals("1")){
			activate_time = URLEncoder.encode(activate_time, "UTF-8");//激活时间在传输时会自动解码，加密是要用url编码过的，如果没有自动解码请忽略转码

			//加密并判断密钥
			String plaintext = adv_id+app_id+key+udid+bill+points+activate_time+order_id+callBackKey;
			System.out.println("加密前的密钥：" + plaintext);
			String keys = MD5(plaintext);
			System.out.println("解密后的密钥：" + keys);
			//判断和wapskey是否相同
			if(keys.equalsIgnoreCase(wapskey)){
				//成功接收数据
				System.out.println("验证成功");
			}else{
				System.out.println("验证失败");
			}

		}else{
			System.out.println("无效数据");
		}
	}

	public static String MD5(String s) {
		char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

*/
