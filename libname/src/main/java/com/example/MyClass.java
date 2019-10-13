package com.example;

import com.alipay.AlipayNotify;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MyClass {

    public static void main(String[] args) {
        System.out.println("MyClass main test print");

        boolean ex = checkAppExpired(1459353600);
        System.out.println("ex=" + ex);

        try {
            System.out.println("valueOf = " + Integer.valueOf("/images/apps/20170116/81876145a81cca0c7ba8e6e84db67e29_180_180.jpg"));
        }catch(NumberFormatException e){
            e.printStackTrace();
        }
//        Experience experience = Experience.getExperience(1900, 17);
//        System.out.println("cur=" + experience.currentExperience + " | nex=" + experience.nextLevelExperience);

        Experience experience = new Experience();
        String order = experience.getOrderInfo("subject", "body", "price", "tradeNo");
        System.out.println("order=" + order);

        try {
            order = URLEncoder.encode(order, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("encode data=" + order);// encode data=partner%3D%222088121761382548%22

        try {
            order = URLDecoder.decode(order,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("decode data=" + order);// decode data=partner="2088121761382548"

        String response = "discount=0.00&payment_type=1&subject=充值&trade_no=2016012721001004390005989820&buyer_email=54zhoujian@gmail.com&gmt_create=2016-01-27 18:17:06&notify_type=trade_status_sync&quantity=1&out_trade_no=20160127181656158239&seller_id=2088121761382548&notify_time=2016-01-27 18:17:07&body=充值&trade_status=TRADE_SUCCESS&is_total_fee_adjust=N&total_fee=0.01&gmt_payment=2016-01-27 18:17:07&seller_email=wangcaiapp@qq.com&price=0.01&buyer_id=2088102087978392&notify_id=8b929edbc00f72494226c21734fa563j0c&use_coupon=N&sign_type=RSA&sign=Kv9rrmmII6f12u7yALOtSEdqWQB/3Wto/9w2VVajxM6it8v5dSRB51d2NZDXmTTzduDkzIOsKAVCFiTDkaQqesUuog3QqKD6bcd280NghziNSffTpK4KeT+jn4XdTkdLCZHlU0WcmD8VAdr0bl56vekjhm/eQzNtl667D3W83sk=";

        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("discount", GetUrlKeyValue(response,"discount"));
        sParaTemp.put("payment_type", GetUrlKeyValue(response,"payment_type"));
        sParaTemp.put("subject", GetUrlKeyValue(response,"subject"));
        sParaTemp.put("trade_no", GetUrlKeyValue(response,"trade_no"));
        sParaTemp.put("buyer_email", GetUrlKeyValue(response,"buyer_email"));
        sParaTemp.put("gmt_create", GetUrlKeyValue(response,"gmt_create"));
        sParaTemp.put("notify_type", GetUrlKeyValue(response,"notify_type"));
        sParaTemp.put("quantity", GetUrlKeyValue(response,"quantity"));
        sParaTemp.put("out_trade_no", GetUrlKeyValue(response,"out_trade_no"));
        sParaTemp.put("seller_id", GetUrlKeyValue(response,"seller_id"));
        sParaTemp.put("notify_time", GetUrlKeyValue(response,"notify_time"));
        sParaTemp.put("body", GetUrlKeyValue(response,"body"));
        sParaTemp.put("trade_status", GetUrlKeyValue(response,"trade_status"));
        sParaTemp.put("is_total_fee_adjust", GetUrlKeyValue(response,"is_total_fee_adjust"));
        sParaTemp.put("total_fee", GetUrlKeyValue(response,"total_fee"));
        sParaTemp.put("gmt_payment", GetUrlKeyValue(response,"gmt_payment"));
        sParaTemp.put("seller_email", GetUrlKeyValue(response,"seller_email"));
        sParaTemp.put("price", GetUrlKeyValue(response,"price"));
        sParaTemp.put("buyer_id", GetUrlKeyValue(response,"buyer_id"));
        sParaTemp.put("notify_id", GetUrlKeyValue(response,"notify_id"));
        sParaTemp.put("use_coupon", GetUrlKeyValue(response, "use_coupon"));
        sParaTemp.put("sign_type", GetUrlKeyValue(response, "sign_type"));

        String sign= GetUrlKeyValue(response, "sign");
        System.out.println("sign=" + sign);

        /*try {
            sign = URLEncoder.encode(sign, "utf-8");
            System.out.println("encode decode=" + sign);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/

        sParaTemp.put("sign", sign);

       boolean veryfy= AlipayNotify.verify(sParaTemp);
        System.out.println("veryfy=" + veryfy);


//        String[] header= null;
//        Map<String, String> convert = convertHeaders(header);
//        System.out.print("convert="+convert);
    }

    /**
     * Converts Headers[] to Map<String, String>.
     */
    protected static Map<String, String> convertHeaders(String[] headers) {
        Map<String, String> result = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < headers.length; i++) {
//            result.put(headers[i].getName(), headers[i].getValue());
            result.put(""+i,headers[i]);
        }
        return result;
    }

    protected static  String GetUrlKeyValue(String strUrl, String strKeyName) {
        strKeyName += "=";
        //final String strKeyName= "context=";
        int nPos = strUrl.indexOf(strKeyName);
        if (nPos < 0) {
            return "";
        }
        nPos += strKeyName.length();
        int nEndPos = strUrl.indexOf("&", nPos);
        if (nEndPos < 0) {
            nEndPos = strUrl.length();
        }
        String strContent = strUrl.substring(nPos, nEndPos);

//        return URLDecoder.decode(strContent);
        return  strContent;
    }

    // 商户PID
    public static final String PARTNER = "2088121761382548";

    public static class Experience {
        private static final int[] USER_LEVEL_DEFINES = new int[]{0, 20, 50, 70, 100, 130, 160, 200, 240, 280, 350, 500, 800, 1200, 2000, 3000, 5000};

        public int currentExperience;
        public int nextLevelExperience;

        public static Experience getExperience(int m_nIncome, int curLevel) {
            Experience experience = new Experience();

            experience.currentExperience = m_nIncome;
            if (curLevel < USER_LEVEL_DEFINES.length) {
                experience.nextLevelExperience = USER_LEVEL_DEFINES[curLevel] * 100;
            } else {
                //等级到final boss
                experience.nextLevelExperience = USER_LEVEL_DEFINES[curLevel - 1] * 100;
            }

            return experience;
        }

        private String getOrderInfo(String subject, String body, String price, String tradeNo) {
            String orderInfo = "partner=" + "\"" + PARTNER + "\"";
            return orderInfo;
        }

       /* public static Experience getExperience(int m_nIncome,int curLevel) {
            Experience experience = new Experience();

            experience.currentExperience = m_nIncome;
//            int nextIncomeYuan = USER_LEVEL_DEFINES[curLevel];
//            experience.nextLevelExperience = nextIncomeYuan*1000-m_nIncome;

            for (int i = 1; i < USER_LEVEL_DEFINES.length; i++) {
                int next = USER_LEVEL_DEFINES[i];
                if (m_nIncome/100 < next) {
                    experience.nextLevelExperience = next *100;
                    break;
                } else if (i == USER_LEVEL_DEFINES.length - 1) {//等级到final boss
                    experience.nextLevelExperience = 0;
                    break;
                }

            }

            return experience;
        }*/
    }

   /* public static class Experience {
        public int currentExperience;
        public int nextLevelExperience;

        public static Experience getExperience(int m_nIncome) {
            //        USER_LEVEL_DEFINES = [0, 20, 50, 70, 100, 130, 160, 200, 240, 280, 350, 500, 800, 1200, 2000, 3000, 5000]
            final int[] USER_LEVEL_DEFINES = new int[]{0, 20, 50, 70, 100, 130, 160, 200, 240, 280, 350, 500, 800, 1200, 2000, 3000, 5000};//17

            Experience experience = new Experience();
            for (int i = 1; i < USER_LEVEL_DEFINES.length; i++) {
                int next = USER_LEVEL_DEFINES[i];
                if (m_nIncome < next) {
                    experience.currentExperience = i;
                    experience.nextLevelExperience = next - m_nIncome;
                    break;
                } else if (i == USER_LEVEL_DEFINES.length - 1) {
                    experience.currentExperience = USER_LEVEL_DEFINES.length;
                    experience.nextLevelExperience = 0;
                    break;
                }

            }

            return experience;
        }
    }*/

    public static boolean checkAppExpired(long expiredTimes) {
        long curUnixTimes = System.currentTimeMillis() / 1000;
        System.out.println("curUnixTimes=" + curUnixTimes);
        if (curUnixTimes > expiredTimes) {
            return true;
        }
        return false;
    }
}
