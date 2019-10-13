package com.ayvpn.client.bb.devicetest;

/**
 * Created by shidu on 17/9/28.
 */

public class CustomBuild {

    /**
     * The name of the overall product.
     */
    public static final String PRODUCT = getString("ro.product.name");
    public static final String USER = getString("ro.build.user");
    /**
     * The system bootloader version number.
     */
    public static final String BOOTLOADER = getString("ro.bootloader");

    private String name;

    public CustomBuild(String a) {
        name = a;
    }

    public String getName() {
        return name;
    }

    private static String getString(String property) {
//        return SystemProperties.get(property, UNKNOWN);
        return DeviceChecker.getProperty(property, "UNKNOWN");
    }

    public static String getString() {
        return "CustomBuild getString.";
    }

}
