package com.ayvpn.client.bb;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ayvpn.client.bb.data.AppInfo;
import com.ayvpn.client.bb.data.AppInfoResponse;
import com.ayvpn.client.bb.retrofit.RequestBuilder;
import com.ayvpn.client.bb.retrofit.RequestInterceptor;
import com.ayvpn.client.bb.retrofit.RequestInterceptorTape;
import com.ayvpn.client.bb.retrofit.UserAgentInterceptor;
import com.ayvpn.client.bb.volley.GsonRequest;
import com.ayvpn.client.bb.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListMap;

//import org.apache.http.conn.util.InetAddressUtils;


public class MainActivity extends Activity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    private final ConcurrentSkipListMap<Object, String> cachedMap = new ConcurrentSkipListMap<>(new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            return ((Long) o2).compareTo(((Long) o1));
        }
    });

    private AppInfo mAppInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

//        TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
//        String imei = tm.getDeviceId();

        //MediaCodecInfo

//        Build.MODEL;
//        LinkedHashMap
//ThreadLocal
//        VideoView

        TextView textView = new TextView(this);
        setContentView(textView);
//        textView.setText(imei);
        textView.setText("imei hardcode");

        cachedMap.put(4l, "string4");
        cachedMap.put(1L, "string1");
        cachedMap.put(2l, "string2");
        cachedMap.put(3l, "string3");

        Set<Map.Entry<Object, String>> weekSet = cachedMap.entrySet();
        Iterator<Map.Entry<Object, String>> week = weekSet.iterator();
        while (week.hasNext()) {
            Map.Entry<Object, String> weekEntry = week.next();
            String lastRetry = weekEntry.getValue();
            long retryPts = (long) weekEntry.getKey();
            Log.e("evan", "retryPts=" + retryPts + " value=" + lastRetry);
        }

        //volley learning.
        parseItemInfo("124");

        Log.e("evan", "getLocalIpAddress = " + getLocalIpAddress());
        Log.e("evan", "getDeviceIPAddress = " + getDeviceIPAddress(true));
        textView.setText(getDeviceIPAddress(true));

        //        Log.e("evan","getSubscriberId = "+ getSubscriberId(this));
//        String imsi = SystemProperties.get(android.telephony.TelephonyProperties.PROPERTY_IMSI);
//        Log.e("evan","get"+get(this,android.telephony.TelephonyProperties.PROPERTY_IMSI));

        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
//        Log.e("evan","androidId = "+androidId);

//        test = new Test();

//        UUID

        int i = 5;
        Log.e("evan", "i++=" + i++); //E/evan: i++=5  i++先做别的事，再自己加1

        Log.e("evan", " 3/ 5 = " + (float) 3 / 5);


        String test = "错误码：2008 错误信息：获取用户资料失败----";
        String[] splits = test.split("错误信息");
        for (String item : splits) {
            Log.e("evan", "item = " + item);
        }
        splits = splits[1].split(" ");
        for (String item : splits) {
            Log.e("evan", "item2 = " + item);
        }
        String msg = "登录失败，原因" + splits[0];
        Log.e("evan", "msg = " + msg);
        Map<String, String> map = new HashMap<>();
        map.put("key1", "key1value");
        map.put("key2", "" + 1);
        Log.e("evan", "map = " + map.toString());

        encodeParameters(map, "utf-8");

        /*main = getLayoutInflater().from(this).inflate(R.layout.main, null);
        main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(main);*/

//        String url = "content://com.android.launcher2.settings/favorites?Notify=true";
//        ContentResolver resolver = getContentResolver();
//        Cursor cursor = resolver.query (Uri.parse(url), null, null, null, null);

/*
        Intent shortcutsIntent = new Intent(Intent.ACTION_CREATE_SHORTCUT);
        ArrayList<Intent> intentList = new ArrayList<Intent>();
        Intent intent=null;
        String launchers="";
        final PackageManager packageManager=getPackageManager();
        for(final ResolveInfo resolveInfo:packageManager.queryIntentActivities(shortcutsIntent,   0)) {
            launchers=launchers+"\n"+resolveInfo.activityInfo.packageName;
            Log.e("evan","launchers="+launchers);
            intent=packageManager
                    .getLaunchIntentForPackage(resolveInfo.activityInfo.packageName);
            intentList.add(intent);
        }


        PackageInfo info = packageManager.getPackageArchiveInfo("sdcard/DCIM/Wangcai_3.2_baidu.apk",
                PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;


            Log.e("evan","packageName="+appInfo.packageName);
        }

        for (Intent intent1 : intentList){
            Log.e("evan","unt="+intent1.toString());
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        Log.e("evan", "getCurday=" + GetCurrentDateString());
        String mOrderId="1001";
        String returnUrl = "http://1.getwangcai.com/my/recharge";
        if (!TextUtils.isEmpty(mOrderId)) {
            returnUrl += mOrderId;
        }
        returnUrl += "?pay=1";
        Log.e("evan", "returnUrl=" + returnUrl);

        String successValue ="\"true\"";
        if (TextUtils.equals(successValue,"\"true\"")){
            Log.e("evan", "equal 双引号");
        }else {
            Log.e("evan", "not equal 双引号");
        }

        if (TextUtils.equals(successValue,"true")){
            Log.e("evan", "equal");
        }

//        String rawText = "{\"text\":\"\",\"type\":\"NewAward\",\"title\":\"\"|}}";
        String rawText = "{\"text\":\"\",\"type\":\"NewAward\",\"title\":\"\"}";
        try {
            JSONObject rootObject = new JSONObject(rawText);

            String result =  ReadJsonString(rootObject,"type");
            Log.e("evan","result="+result+" | tostring="+rootObject.toString());

            return;
        } catch (JSONException e) {
            Log.e("evan", "JSONException="+e.getMessage());
        }

        Log.e("evan", "raw="+rawText);
        */
    }

    public static String ReadJsonString(JSONObject jsonObj, String strKey) {
        try {
            return jsonObj.getString(strKey);
        } catch (JSONException e) {
            return "";
        }
    }

    private String GetCurrentDateString() {
        Time time = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.CUPCAKE) {
            time = new Time();
        }
        time.setToNow();
        return String.format("%d%d", time.year, time.yearDay);
    }

    private void parseItemInfo(String itemId) {
        if (!TextUtils.isEmpty(itemId)) {
//            String url = String.format("%s/api/apps/item/%s", "http://www.changxianapp.com", itemId);
            String url = "http://c1.idianyun.cn/api/devices/apps?type=1&hangUpAppId=1";
            Log.d("evan", "parseItemInfo. url = " + url);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, url, AppInfoResponse.class, new Response.Listener<AppInfoResponse>() {
                @Override
                public void onResponse(AppInfoResponse response) {

//                    Log.d("evan","parseItemInfo. response.data = " + response.data);
                    Log.d("evan", "parseItemInfo. response.data");
                    if (response.code == 0) {
                        mAppInfo = response.data;

                    } else {

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("evan", "parseItemInfo. response.data = " + error);
                }
            });

            VolleySingleton.getInstance(MainActivity.this).addToRequestQueue(gsonRequest);
        }
    }

    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en =
                 NetworkInterface.getNetworkInterfaces(); en
                         .hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr =
                     intf.getInetAddresses(); enumIpAddr
                             .hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement
                            ();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString
                                ();
                    }
                }
            }
        } catch (SocketException ex) {
//            Log.e(S.TAG, ex.toString());
        }
        return null;
    }

    public static String getDeviceIPAddress(boolean useIPv4) {
        /*try {
            List<NetworkInterface> networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface networkInterface : networkInterfaces) {
                List<InetAddress> inetAddresses = Collections.list(networkInterface.getInetAddresses());
                for (InetAddress inetAddress : inetAddresses) {
                    if (!inetAddress.isLoopbackAddress()) {
                        String sAddr = inetAddress.getHostAddress().toUpperCase();
                        boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                // drop ip6 port suffix
                                int delim = sAddr.indexOf('%');
                                return delim < 0 ? sAddr : sAddr.substring(0, delim);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/
        return "";
    }

    /**
     * Converts <code>params</code> into an application/x-www-form-urlencoded encoded string.
     */
    private byte[] encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            Log.e("evan", "params toString = " + encodedParams.toString());
            return encodedParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }

    private static String getUniquePsuedoID() {
        String str1 = "35" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10 + Build.TYPE.length() % 10 + Build.USER.length() % 10;
        Log.e("evan", "str1" + str1);

        try {
            String str2 = Build.class.getField("SERIAL").get(null).toString();
            Log.e("evan", "str2" + str2);
            str2 = new UUID(str1.hashCode(), str2.hashCode()).toString();
            return str2;
        } catch (Exception localException) {
        }
        return new UUID(str1.hashCode(), "serial".hashCode()).toString();
    }

    /**
     * Get the value for the given key.
     *
     * @return an empty string if the key isn't found
     */
    public static String get(Context context, String key) {
        String ret = "";

        try {
            ClassLoader cl = context.getClassLoader();
            @SuppressWarnings("rawtypes")
            Class SystemProperties = cl.loadClass("android.os.SystemProperties");

            //Parameters Types
            @SuppressWarnings("rawtypes")
            Class[] paramTypes = new Class[1];
            paramTypes[0] = String.class;

            Method get = SystemProperties.getMethod("get", paramTypes);

            //Parameters
            Object[] params = new Object[1];
            params[0] = new String(key);

            ret = (String) get.invoke(SystemProperties, params);
        } catch (Exception e) {
            ret = "";
            //TODO : Error handling
        }

        return ret;
    }

    public static String getSubscriberId(Context context) {
        try {
//            return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
//            return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSimSerialNumber(); //89014103211118510720
            return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId(); //000000000000000
        } catch (Exception E) {
            return "00000000";
        }
    }

    private void printCpuinfo2() {
        File file = new File("/system/lib/arm/cpuinfo");
        if (file.exists()) {
            try {
                FileInputStream in = new FileInputStream(file);
                /*StringWriter writer = new StringWriter();
                IOUtils.copy(in, writer);
                String info = writer.toString();
                Log.e("evan","info = "+info);*/
                int size = 0;
                while (in.read() != -1) {
                    size += in.read();
                    Log.e("evan", "outputStream.read() size =" + size);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("evan", "/system/lib/arm/cpuinfo not exist");
        }
    }

    public static final String copyNativeBinaries(File apkFile, File sharedLibraryDir) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return "Build.VERSION_CODES.LOLLIPOP";
        } else {
            return "copyNativeBinaries Before L";
        }
    }

    StringBuilder sb = new StringBuilder();

    private String cpuinfo() {
        //        DexClassLoader
//        File
//        IInterface
        File dexOutputDir = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dexOutputDir = MainActivity.this.getCodeCacheDir();
            try {
                Log.e("evan", "dexOutputDir AbsolutePath = " + dexOutputDir.getAbsolutePath() + " dexOutputDir CanonicalPath=" + dexOutputDir.getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File cacheDir = MainActivity.this.getCacheDir();
        File externalCacheDir = MainActivity.this.getExternalCacheDir();
        Log.e("evan", "cacheDir = " + cacheDir + " externalCacheDir = " + externalCacheDir);

        String tankPath = MainActivity.this.getDir("plugin", Context.MODE_PRIVATE).getAbsolutePath()
                + File.separator + "tank";

        Log.e("evan", "tankPath=" + tankPath + " getFilesDir=" + MainActivity.this.getFilesDir());


        Log.e("evan", "Build.CPU_ABI=" + Build.CPU_ABI);
        Log.e("evan", "Build.CPU_ABI2=" + Build.CPU_ABI2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String[] abis = Build.SUPPORTED_ABIS;
            for (String item : abis) {
                Log.e("evan", "item=" + item);
                sb.append("SUPPORTED_ABIS = " + item);
            }
        } else {
            sb.append("Build.CPU_ABI = " + Build.CPU_ABI);
        }

        return sb.toString();
    }

    public static boolean isSupportedDevice() {
        boolean supported = false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            if (Build.CPU_ABI.equals("armeabi-v7a")) {
                File cpuInfo = new File("/proc/cpuinfo");
                if (cpuInfo.exists()) {
                    StringWriter writer = new StringWriter();

                    FileInputStream in = null;
                    try {
                        in = new FileInputStream(cpuInfo);
//                        IOUtils.copy(in, writer);
                        String info = writer.toString();
                        Log.e("evan", "info = " + info);
                        supported = info.contains("neon");
                    } catch (IOException e) {
                    } finally {
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e) {
                            }
                        }
                    }
                }
            } else if (Build.CPU_ABI.equals("x86")) {
//                supported = isX86Supported();
            }
        }

        return supported;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        private RequestInterceptor requestInterceptor = new UserAgentInterceptor();
//        private RequestInterceptor requestInterceptor = RequestInterceptor.NONE;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                RequestInterceptorTape interceptorTape = new RequestInterceptorTape();
                requestInterceptor.intercept(interceptorTape);
                invokeRequest(interceptorTape);
            }

            return rootView;
        }

        private Object invokeRequest(RequestInterceptor requestInterceptor) {
            RequestBuilder requestBuilder = new RequestBuilder();
            requestInterceptor.intercept(requestBuilder);
            return null;
        }
    }

}
