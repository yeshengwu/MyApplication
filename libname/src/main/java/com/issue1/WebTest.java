package com.issue1;

/**
 * Created by shidu on 18/1/5.
 * issue: printDriver driver = null; openDriver后应该有值，为何打印的没有？
 * 求解：
 */

public class WebTest {
    private Driver driver = CommonApi.driver;
    private CommonApi api = new CommonApi();

    public void doTest (){
        api.openDriver();
    }

    public void printDriver(){
        System.out.println("printDriver driver = "+driver);
        System.out.println("printDriver CommonApi.driver = "+CommonApi.driver);
    }
}
