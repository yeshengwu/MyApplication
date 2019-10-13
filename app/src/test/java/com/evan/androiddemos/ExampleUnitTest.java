package com.evan.androiddemos;

import org.junit.Test;

import java.net.InetAddress;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {

//        char[] a = new char[1];
//        String test = new String(a);
//        System.out.println("test = "+test);
//
////        String ipAddress = "127.0.0.1";
//        String ipAddress = "baidu.com";
//        System.out.println(ping(ipAddress));

//        assertEquals(4, 2 + 2);

        int[] nums = {1,4,2,5};
        reOrderArray(nums);
        for (int item: nums){
            System.out.println(item);
        }
    }

    public static void reOrderArray(int[] nums) {
        // 奇数个数
        int oddCnt = 0;
        for (int val : nums)
            if (val % 2 == 1)
                oddCnt++;
        int[] copy = nums.clone();
        int i = 0, j = oddCnt;
        System.out.println("oddCnt = "+oddCnt);
        for (int num : copy) {
            if (num % 2 == 1) {
                System.out.println("i = "+i+" num = "+num);
                nums[i++] = num;
            }
            else {
                System.out.println("j = "+j+" num = "+num);
                nums[j++] = num;
            }

        }
    }


    public static boolean ping(String ipAddress) throws Exception {
        int  timeOut =  3000 ;  //超时应该在3钞以上
        boolean status = InetAddress.getByName(ipAddress).isReachable(timeOut);     // 当返回值是true时，说明host是可用的，false则不可。
        return status;
    }
}