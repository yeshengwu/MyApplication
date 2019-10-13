package com.ayvpn.client.bb.devicetest;

/**
 * device model in Build class.
 * <p>
 * Created by shidu on 17/10/11.
 */
public class DeviceModel {
    public String name;
    public String id;
    public String display;
    public String type;
    public String incremental;
    public String product;
    public String device;
    public String board;
    public String manufacturer;
    public String brand;
    public String model;
    public String hardware;
    public String[] tags;
    public BluetoothData bluetooth;

    /**
     * 4.4.2
     */
    public String release;

    /**
     * length
     */
    public int serial;

    /**
     * 86706502 substring.
     */
    public String imei;

    /**
     * b8:bc:1b
     */
    public String macAddress;

    /**
     * 0. 移动 1. 联通 2. 电信
     */
    public int[] carriers;

    public static class BluetoothData {
        public String name;
        /**
         * b8:bc:1b
         */
        public String address;
    }
}