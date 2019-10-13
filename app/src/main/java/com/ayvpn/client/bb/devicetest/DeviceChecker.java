package com.ayvpn.client.bb.devicetest;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ayvpn.client.bb.util.UIHelper;
import com.ayvpn.client.bb.util.Util;
import com.ayvpn.client.bb.volley.GsonRequest;
import com.ayvpn.client.bb.volley.VolleySingleton;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.os.Build.BOARD;
import static android.os.Build.DEVICE;
import static android.os.Build.HOST;
import static android.os.Build.MANUFACTURER;

/**
 * Created by shidu on 17/9/22.
 */

public class DeviceChecker {

    public static String getProperty(String key, String defaultValue) {
        String value = defaultValue;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");

            Method get = c.getMethod("get", String.class, String.class);
            value = (String) (get.invoke(c, key, defaultValue));
//            Method get = c.getMethod("get", String.class);
//            value = (String) (get.invoke(c, key));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static void setProperty(String key, String value) {
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method set = c.getMethod("set", String.class, String.class);
            set.invoke(c, key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void changeBuild() {
        try {
            Field idField = Build.class.getDeclaredField("PRODUCT");
            idField.setAccessible(true);
            idField.set(Build.class, "PRODUCT-changed");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //    public static String[] fieldNames = {"PRODUCT", "DEVICE", "BOARD", "MANUFACTURER", "BRAND",
//            "MODEL", "HARDWARE", "TAGS", "HOST", "SERIAL"};
    public static HashMap<String, String> filedKeyValues = new HashMap<>();
    // <getDeviceId ,<12345679,0000000>>  12345679为真实值，0000000为模拟器特征值
    public static HashMap<String, Map<String, String>> sTelephonyMethodValues = new HashMap<>();
    private static HashMap<String, String> successResultMaps = new HashMap<>();

    public static String[] propertyArray = {"init.svc.qemud", "init.svc.qemu-props", "qemu.hw.mainkeys",
            "qemu.sf.fake_camera", "qemu.sf.lcd_density", "ro.bootloader", "ro.bootmode",
            "ro.hardware", "ro.kernel.android.qemud", "ro.kernel.qemu.gles", "ro.kernel.qemu",
            "ro.product.device", "ro.product.model", "ro.product.name", "ro.serialno"};

    private static String[] excludedFileArray = {"/dev/socket/qemud", "/dev/qemu_pipe", "/sys/qemu_trace",
            "/system/lib/libc_malloc_debug_qemu.so", "/system/bin/qemu-props",
            "/dev/socket/genyd", "/dev/socket/baseband_genyd"};

    public static String checkIsEmulator(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n模拟器检测，注意：多个条件判断，有一个条件命中即命中\n");
        sb.append("Build check:").append("\n");

        filedKeyValues.put("Build.PRODUCT", Build.PRODUCT);
        filedKeyValues.put("Build.DEVICE", Build.DEVICE);
        filedKeyValues.put("Build.BOARD", Build.BOARD);
        filedKeyValues.put("Build.MANUFACTURER", Build.MANUFACTURER);
        filedKeyValues.put("Build.BRAND", Build.BRAND);
        filedKeyValues.put("Build.MODEL", Build.MODEL);
        filedKeyValues.put("Build.HARDWARE", Build.HARDWARE);
        filedKeyValues.put("Build.TAGS", Build.TAGS);
        filedKeyValues.put("Build.HOST", Build.HOST);
        filedKeyValues.put("Build.SERIAL", Build.SERIAL);
        filedKeyValues.put("Build.FINGERPRINT", Build.FINGERPRINT);


        Iterator<Map.Entry<String, String>> iter = filedKeyValues.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
            if (entry.getValue().contains("sdk") || entry.getValue().contains("test-keys") || entry.getValue().contains("generic") || entry.getValue().contains("ranchu")) {
                sb.append(entry.getKey() + " value = " + entry.getValue() + " | contains sdk || test-keys || generic || ranchu. YES YES YES!!!").append("\n");
                successResultMaps.put(entry.getKey(), "value = " + entry.getValue());
            } else {
                sb.append(entry.getKey() + " value = " + entry.getValue() + " | 未包含 sdk || test-keys || generic || ranchu. no").append("\n");
            }
        }

        sb.append("\nfile check:").append("\n");
        for (String file : excludedFileArray) {
            if (new File(file).exists()) {
                sb.append(file + " exists. YES YES YES!!!").append("\n");
                successResultMaps.put(file, file);
            } else {
                sb.append(file + " not exists. no").append("\n");
            }
        }

        sb.append("\nandroid.os.SystemProperties check:").append("\n");
        for (String key : propertyArray) {
            if (getProperty(key, Build.UNKNOWN).contains("qemu") || getProperty(key, Build.UNKNOWN).contains("ranchu") || getProperty(key, Build.UNKNOWN).contains("generic")) {
                sb.append("checked key = " + key + " value = " + getProperty(key, Build.UNKNOWN) + " | contains qemu || ranchu || generic. YES YES YES!!!").append("\n");
                successResultMaps.put("SystemProperties", key + " value = " + getProperty(key, Build.UNKNOWN));
            } else {
                sb.append("checked key = " + key + " value = " + getProperty(key, Build.UNKNOWN) + " |未包含 qemu || ranchu || generic. no").append("\n");
            }
        }

        sb.append("\nTelephonyManager check:").append("\n");
        final TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        sTelephonyMethodValues.put("getLine1Number", new HashMap<String, String>() {
            {
                put(manager.getLine1Number(), "15555215554");
            }
        });
        sTelephonyMethodValues.put("getVoiceMailNumber", new HashMap<String, String>() {
            {
                put(manager.getVoiceMailNumber(), "+15552175049");
            }
        });
//        sTelephonyMethodValues.put("getDeviceId", new HashMap<String, String>() {
//            {
//                put(manager.getDeviceId(), "0000000"); //真机拒绝获取时也为0，所以不好区分。
//            }
//        });

        sTelephonyMethodValues.put("getSubscriberId", new HashMap<String, String>() {
            {
                put(manager.getSubscriberId(), "310260000000000");
            }
        });
        sTelephonyMethodValues.put("getSimSerialNumber", new HashMap<String, String>() {
            {
                put(manager.getSimSerialNumber(), "89014103211118510720");
            }
        });
        sTelephonyMethodValues.put("getNetworkOperatorName", new HashMap<String, String>() {
            {
                put(manager.getNetworkOperatorName(), "unknown");
            }
        });
        Iterator<Map.Entry<String, Map<String, String>>> telephonyItr = sTelephonyMethodValues.entrySet().iterator();
        while (telephonyItr.hasNext()) {
            Map.Entry<String, Map<String, String>> entry = telephonyItr.next();
            Map<String, String> valueMap = entry.getValue();
            Iterator<Map.Entry<String, String>> iterValue = valueMap.entrySet().iterator();
            while (iterValue.hasNext()) {
                Map.Entry<String, String> entry2 = (Map.Entry<String, String>) iterValue.next();
                if (entry2.getKey() != null && entry2.getKey().contains(entry2.getValue())) {
                    sb.append("checked key = " + entry.getKey() + " value = " + entry2.getKey() + " | contains " + entry2.getValue() + ". YES YES YES!!!").append("\n");
                    successResultMaps.put(entry.getKey(), "value = " + entry2.getKey());
                } else {
                    sb.append("checked key = " + entry.getKey() + " value = " + entry2.getKey() + " | 未包含 " + entry2.getValue() + ". no").append("\n");
                }
            }
        }

        Iterator<Map.Entry<String, String>> iterResult = successResultMaps.entrySet().iterator();
        if (successResultMaps.size() > 0) {
            while (iterResult.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry<String, String>) iterResult.next();
                sb.append("\n检测最终结果：是模拟器，原因：" + entry.getKey() + " value = " + entry.getValue()).append("\n");
                UIHelper.showToast(context, "是模拟器", Toast.LENGTH_LONG);
            }
        } else {
            sb.append("\n检测最终结果：不是模拟器").append("\n");
            UIHelper.showToast(context, "不是模拟器", Toast.LENGTH_LONG);
        }

        Log.e("evan", sb.toString());
        return sb.toString();
    }

    public static String getAllDeviceInfo(Context context) {
//        changeBuild();
//        Log.e("evan", "changeBuild Build.PRODUCT newValue = " + Build.PRODUCT);

        Log.e("build", "Build.VERSION.SDK_INT = " + Build.VERSION.SDK_INT + " |Build.VERSION.SDK = " + Build.VERSION.SDK + " |Build.VERSION.RELEASE = " + Build.VERSION.RELEASE + " | Build.INCREMENTAL = " + Build.VERSION.INCREMENTAL + " | Build.DISPLAY = " + Build.DISPLAY + " |Build.TYPE = " + Build.TYPE + " |Build.ID = " + Build.ID);
        Log.e("evan", getBuildFiled());
        return getBuildFiled() + "\n\n" + getTelephonyManagerApi(context) + "\n" + getMacAddress2(context) + "\n" + getBluetoothInfo(context) + "\n" + getNetWorkByConnectivityManager(context).info + "\n" + testNetworkInterface(context);
    }

    public static String getAllDevice2Json(Context context) {
        DeviceModel model = new DeviceModel();
        //Build
        model.name = Build.MODEL;
        model.id = Build.ID;
        model.release = Build.VERSION.RELEASE;
        model.display = Build.DISPLAY;
        model.type = Build.TYPE;
        model.incremental = Build.VERSION.INCREMENTAL;
        model.product = Build.PRODUCT;
        model.device = Build.DEVICE;
        model.board = Build.BOARD;
        model.manufacturer = Build.MANUFACTURER;
        model.brand = Build.BRAND;
        model.model = Build.MODEL;
        model.hardware = Build.HARDWARE;
        String tags = Build.TAGS;
        model.tags = tags.split(",");
        model.serial = Build.SERIAL.length();

        //TelephonyManager
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = manager.getDeviceId();
        model.imei = imei.substring(0, 8);
        model.carriers = new int[]{getSimOperatorByMnc(context)};

        //bluetooth
        model.bluetooth = new DeviceModel.BluetoothData();
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        String bluetoothAddress = "02:00:00:00:00:00";
        if (adapter != null) {
            model.bluetooth.name = adapter.getName();
            bluetoothAddress = adapter.getAddress();
        }

        if (bluetoothAddress.equals("02:00:00:00:00:00")) {
            bluetoothAddress = android.provider.Settings.Secure.getString(context.getContentResolver(), "bluetooth_address");
            if (bluetoothAddress == null) {
                bluetoothAddress = "02:00:00:00:00:00";
            }
        }
        //B8:BC:1B:98:68:8E
        model.bluetooth.address = bluetoothAddress.substring(0, 8).toLowerCase();

        //macAddress
        model.macAddress = getMacAddress(context).substring(0, 8);
        Log.e("json", Util.ObjToJson(model));
        postJsonToEvan(context,Util.ObjToJson(model));
        return Util.ObjToJson(model);
    }

    private static void postJsonToEvan(final Context context, String json){
        String url = "http://192.168.0.214:8080";
//        String url = "http://14.215.129.100:8090/";
        GsonRequest<Void> request = new GsonRequest<Void>(Request.Method.POST, url, json, Void.class, new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
//                UIHelper.showToast(context,"upload to evan pc success",Toast.LENGTH_LONG);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                UIHelper.showToast(context,"upload to evan pc failure",Toast.LENGTH_LONG);
            }
        });
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public static String getBuildFiled() {
        return "PRODUCT:" + Build.PRODUCT + "| DEVICE:" + DEVICE + "| BOARD:" + BOARD + "| MANUFACTURER:" + MANUFACTURER +
                "| BRAND:" + Build.BRAND + "| MODEL:" + Build.MODEL + "| HARDWARE:" + Build.HARDWARE + "| TAGS:" + Build.TAGS + "| HOST:" + HOST + "| SERIAL:" + Build.SERIAL + "| FINGERPRINT:" + Build.FINGERPRINT;
    }

    public static String getTelephonyManagerApi(Context context) {
        StringBuilder sb = new StringBuilder();
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        sb.append(manager.getClass().getCanonicalName() + " |getDeviceId = " + manager.getDeviceId()).append('\n');
        sb.append(manager.getClass().getCanonicalName() + " |getSubscriberId = " + manager.getSubscriberId()).append('\n');
        sb.append(manager.getClass().getCanonicalName() + " |getLine1Number = " + manager.getLine1Number()).append('\n');
        sb.append(manager.getClass().getCanonicalName() + " |getSimSerialNumber = " + manager.getSimSerialNumber()).append('\n');
        sb.append(manager.getClass().getCanonicalName() + " |getSimCountryIso = " + manager.getSimCountryIso()).append('\n');
        sb.append(manager.getClass().getCanonicalName() + " |getSimOperatorName = " + manager.getSimOperatorName()).append('\n');
        sb.append(manager.getClass().getCanonicalName() + " |getNetworkCountryIso = " + manager.getNetworkCountryIso()).append('\n');
        sb.append(manager.getClass().getCanonicalName() + " |getNetworkOperator = " + manager.getNetworkOperator()).append('\n');
        sb.append(manager.getClass().getCanonicalName() + " |getNetworkOperatorName = " + manager.getNetworkOperatorName()).append('\n');

        sb.append(manager.getClass().getCanonicalName() + " |getPhoneType = " + manager.getPhoneType()).append('\n');
        sb.append(manager.getClass().getCanonicalName() + " |getNetworkType = " + manager.getNetworkType()).append('\n');
        sb.append(manager.getClass().getCanonicalName() + " |getSimState = " + manager.getSimState()).append('\n');
        sb.append(manager.getClass().getCanonicalName() + " |hasIccCard = " + manager.hasIccCard()).append('\n');
        Log.e("evan", sb.toString());
        return sb.toString();
    }

    /**
     * 获取Sim卡运营商名称
     * <p>中国移动、如中国联通、中国电信</p>
     *
     * @return 移动网络运营商名称
     */
    public static int getSimOperatorByMnc(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String operator = tm != null ? tm.getSimOperator() : null;
        if (operator == null) return 0;
        switch (operator) {
            case "46000":
            case "46002":
            case "46007":
                return 0; //中国移动
            case "46001":
                return 1; //中国联通
            case "46003":
                return 2; //中国电信
            default:
                return 0;
        }
    }

    /**
     * 获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     *
     * @return MAC地址
     */
    public static String getMacAddress(Context context) {
        String macAddress = getMacAddressByWifiInfo(context);
        if (macAddress != null && !"02:00:00:00:00:00".equals(macAddress)) {
            return macAddress;
        }
        macAddress = getMacAddressByNetworkInterface();
        if (macAddress != null && !"02:00:00:00:00:00".equals(macAddress)) {
            return macAddress;
        }

        return "02:00:00:00:00:00";
    }


    public static String getMacAddress2(Context context) {
        Log.e("evan", "getMacAddress2 see https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/DeviceUtils.java");
        StringBuilder sb = new StringBuilder();
        String macAddress = getMacAddressByWifiInfo2(context);
//        if (!"02:00:00:00:00:00".equals(macAddress)) {
//            return macAddress;
//        }
        sb.append(macAddress).append('\n');
        macAddress = getMacAddressByNetworkInterface();
        sb.append("getMacAddressByNetworkInterface = " + macAddress).append('\n');
        Log.e("evan", sb.toString());
//        if (!"02:00:00:00:00:00".equals(macAddress)) {
//            return macAddress;
//        }

        return sb.toString();
//        return "please open wifi";
    }

    /**
     * 获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
     *
     * @return MAC地址
     */
    @SuppressLint("HardwareIds")
    public static String getMacAddressByWifiInfo(Context context) {
//        StringBuilder sb = new StringBuilder();
        try {
            @SuppressLint("WifiManagerLeak")
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (wifi != null) {
                WifiInfo info = wifi.getConnectionInfo();
                if (info != null) {
//                    sb.append(info.getClass().getCanonicalName() + " |getMacAddress2 = " + info.getMacAddress()).append('\n');
//                    sb.append(info.getClass().getCanonicalName() + " |getBSSID = " + info.getBSSID()).append('\n');
//                    sb.append(info.getClass().getCanonicalName() + " |getSSID = " + info.getSSID()).append('\n');
//                    return sb.toString();
                    return info.getMacAddress();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }

    @SuppressLint("HardwareIds")
    public static String getMacAddressByWifiInfo2(Context context) {
        StringBuilder sb = new StringBuilder();
        try {
            @SuppressLint("WifiManagerLeak")
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (wifi != null) {
                WifiInfo info = wifi.getConnectionInfo();
                if (info != null) {
                    sb.append(info.getClass().getCanonicalName() + " |getMacAddress = " + info.getMacAddress()).append('\n');
                    sb.append(info.getClass().getCanonicalName() + " |getBSSID = " + info.getBSSID()).append('\n');
                    sb.append(info.getClass().getCanonicalName() + " |getSSID = " + info.getSSID()).append('\n');
                    return sb.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }

    /**
     * 获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     *
     * @return MAC地址
     */
    public static String getMacAddressByNetworkInterface() {
        try {
            List<NetworkInterface> nis = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface ni : nis) {
                if (!ni.getName().equalsIgnoreCase("wlan0")) continue;
                byte[] macBytes = ni.getHardwareAddress();
                if (macBytes != null && macBytes.length > 0) {
                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        res1.append(String.format("%02x:", b));
                    }
                    return res1.deleteCharAt(res1.length() - 1).toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }

    public static String testNetworkInterface(Context context) {
        StringBuilder sb = new StringBuilder();
        NetworkInterface interface2 = null;
        try {
            interface2 = NetworkInterface.getByName("wlan0");
            if (interface2 == null) {
                return sb.append("NetworkInterface.getByName(\"wlan0\"\")" + "return null").append('\n').toString();
            }
            byte[] macBytes = interface2.getHardwareAddress();
            if (macBytes != null && macBytes.length > 0) {
                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02x:", b));
                }
                sb.append("NetworkInterface.getByName(\"wlan0\"\")" + " getHardwareAddress = " + res1.deleteCharAt(res1.length() - 1).toString()).append('\n');
            }
            sb.append("interface2.getDisplayName() = " + interface2.getDisplayName()).append('\n');
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static String getBluetoothInfo(Context context) {
        StringBuilder sb = new StringBuilder();
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter != null) {
            sb.append(adapter.getClass().getCanonicalName() + " |getName = " + adapter.getName()).append('\n');
            sb.append(adapter.getClass().getCanonicalName() + " |getAddress = " + adapter.getAddress()).append('\n');
        } else {
            sb.append("本地蓝牙不可用");
        }

        String bluetoothAddress6 = android.provider.Settings.Secure.getString(context.getContentResolver(), "bluetooth_address");
        sb.append("android.provider.Settings.Secure.getString(context.getContentResolver(), bluetooth_address) = " + bluetoothAddress6).append('\n');

        Log.e("evan", sb.toString());
        return sb.toString();
    }

    public static DebugWrapper<Integer> getNetWorkByConnectivityManager(Context context) {
        StringBuilder sb = new StringBuilder();
        DebugWrapper<Integer> wrapper = new DebugWrapper<>();
        int netWorkType = -1;

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            netWorkType = networkInfo.getType();
            if (netWorkType == ConnectivityManager.TYPE_WIFI) {
                sb.append(networkInfo.getClass().getCanonicalName() + " |getType = " + networkInfo.getType() + " | TYPE_WIFI").append('\n');
            } else if (netWorkType == ConnectivityManager.TYPE_MOBILE) {
                sb.append(networkInfo.getClass().getCanonicalName() + " |getType = " + networkInfo.getType() + " | TYPE_MOBILE").append('\n');
            }
        } else {
            sb.append("getNetWorkByConnectivityManager unknown type").append('\n');
        }
        Log.e("evan", sb.toString());
        wrapper.info = sb.toString();
        wrapper.result = netWorkType;
        return wrapper;
    }

    private static class DebugWrapper<T> {
        public String info;
        public T result;
    }

}
