package com.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088121761382548";
	// 商户的私钥
	public static String private_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALKsArOQ/ESY8wZa7rZFl2zNqyWz7klQhp7s20gEhsFyY0Uf/JAtRi5wxKG+OjhAf0pu0HZ8bJ1E2YlZ8KVcInSAvnjA51mTaSuPKHcaJ+zIOOR7D+rc1EHHDcHYzob1k41ZRgA4LmtD7ek9hT2yIpagqpojbsmUDFw2v7JvHYRFAgMBAAECgYASbq5kqftsdD72E+F2il1hyyHx5MsbYyRQNu00U8s3H77f/bnHgfQwjE+sw/wrm3PGUQJOhu2Jl/W9NrsdxBCFuiZE6DvL6YGL0S8LHDBhCoIzyRd/w0sXyTVpICyVlx+AC/z/omS6SZVQ2wEdfz2a8j2Hh5Q4P2x+v7u1eSTJQQJBAOFghhpNGCMGpgT0lvKVWURFwg4GFDzEuorahpwQtoItxwnKIvuGfI9p9KWqWP43ITCkJHDE2uQfB4DTY5Ki2vUCQQDK8udPc0mulBLb/qCcD++a//WVZBMGyrAnq5bPBZ0eMxsbm683kYdmV0JQ+W5JtO1cA9FmpFXEYixgaIhh8NIRAkEArGynTBntPGbKvwHNNhwSpkvkMcxu0SsFOFQFJgMXTjLtkszFxL7YLvQ2CkRwP1U4HDmH84p3mI0wtVuEU1wqvQJAcDsXs5YbvxFfxIzxIJyncl3t32lyJCVeePGo+l8uXg8BBKl5og82g1/p6oGlfdkyEdqCqHJkg6+OvtnP4emdIQJBAIh9JgaFinRCNovpnOJaPly1HRqNMsKXp5nKj7d4Gd+An8eLl9wHxzate30uS0LvQWF1crF9nfWwT+Haq/dWo5s=";
	
	// 支付宝的公钥，无需修改该值
	public static String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "RSA";

}
