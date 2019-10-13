package com.ayvpn.client.bb.devicetest;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayvpn.client.bb.EvanTester;
import com.ayvpn.client.bb.R;
import com.ayvpn.client.bb.locate.Common;
import com.ayvpn.client.bb.util.DeviceInfo;
import com.ayvpn.client.bb.util.DeviceUtils;
import com.ayvpn.client.bb.util.ShellUtils;
import com.ayvpn.client.bb.util.UIHelper;
import com.ayvpn.client.bb.util.Util;
import com.jaredrummler.android.processes.AndroidProcesses;
import com.jaredrummler.android.processes.models.AndroidAppProcess;
import com.jaredrummler.android.processes.models.Stat;
import com.jaredrummler.android.processes.models.Statm;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.Executor;

import static com.ayvpn.client.bb.util.Util.isPad;

public class EvanTestActivity extends Activity {
    private TextView mTextView;
    private BroadcastReceiver mLocationReceiver;
    private boolean mReceiverTag = false; //广播接受者标识位

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_evan);
        mTextView = (TextView) findViewById(R.id.text);
        mTextView.setTextIsSelectable(true);

        findViewById(R.id.btn_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start LocationTestActivity
                Intent intent = new Intent();
                intent.setClass(EvanTestActivity.this, LocationTestActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_webview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(EvanTestActivity.this, WebPlayFragment.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_webview2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(EvanTestActivity.this, WebPlayFragment2.class);
                startActivity(intent);
            }
        });

        //Handler Executor https://blog.csdn.net/vvzhouruifeng/article/details/46366491 Android中的volley_9_ResponseDelivery和ExecutorDelivery
        final Handler uiHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                Log.e("evan","uiHandler handleMessage msg = "+msg);
            }

            @Override
            public void dispatchMessage(Message msg) {
                super.dispatchMessage(msg);

                Log.e("evan","uiHandler dispatchMessage msg = "+msg);
            }
        };

        Executor executor = new Executor() {
            @Override
            public void execute(Runnable command) {
                uiHandler.post(command);
            }
        };

        executor.execute(new ResponseDeliveryRunnable(new Runnable() {
            @Override
            public void run() {
                Log.e("evan","ResponseDeliveryRunnable new Runnable");
            }
        }));

        // 注册广播
       /* IntentFilter filter = new IntentFilter();
        filter.addAction(Common.LOCATION_ACTION);
        mLocationReceiver = new LocationBroadcastReceiver();
        registerReceiver(mLocationReceiver, filter);
        mReceiverTag = true;
//
//        // 启动服务
        Intent intent = new Intent();
        intent.setClass(this, LocationSvc.class);
        startService(intent);*/

//        GLSurfaceView glView = new DemoGLSurfaceView(this);
//        this.setContentView(glView);

//        Log.e("evan", "reflect SystemProperties getProperty ro.product.name = " + DeviceChecker.getProperty("ro.product.name", "unknown"));
//        DeviceChecker.getTelephonyManagerApi(this);
//        DeviceChecker.geWifiManagerInfo(this);
//
//        Log.e("evan", "Build.VERSION class getName = " + Build.VERSION.class.getName()); //android.os.Build&VERSION
//        Log.e("evan", "Build.VERSION class getCanonicalName = " + Build.VERSION.class.getCanonicalName()); //android.os.Build.VERSION
//        Log.e("evan", "Build.VERSION class getSimpleName = " + Build.VERSION.class.getSimpleName()); //VERSION

        long timeSec = SystemClock.elapsedRealtime();
        Log.e("evan","elapsedRealtime = "+timeSec);
        Log.e("evan","timeSec = "+timeSec /1000);
        Log.e("evan","timeSec = "+timeSec /1000.0);

        Log.e("evan", "CustomBuild.getString() = " + CustomBuild.getString()); //
        Log.e("evan", "CustomBuild.BOOTLOADER = " + CustomBuild.BOOTLOADER); //
        Log.e("evan", "Build.BOOTLOADER = " + Build.BOOTLOADER); //
        Log.e("evan", "Environment.getRootDirectory = " + Environment.getRootDirectory().getAbsolutePath()); //
        Log.e("evan", "Environment.getRootDirectory = " + new File(Environment.getRootDirectory(), "build.prop").getAbsolutePath()); //

        //evan add test for.
        EvanTester.testReflect(this);

        File datasetAss=new File(getExternalFilesDir("AssetsBundles")+"/frxxz/frxxz_yinyue.ass");
        Log.e("evan","datasetAss file path = "+datasetAss.getAbsolutePath());

        File dataSets = getExternalFilesDir("DataSets");
        Log.e("evan","dataSets file path = "+dataSets.getAbsolutePath());
        Log.e("evan","dataSets +\"/evan.txt\" file path = "+getExternalFilesDir("DataSets")+"/evan.txt");

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    URL url = new URL("http://video.mopian.tv/frxxz_hanli.ass");
//                    HttpURLConnection urlcon= null;
//                    urlcon = (HttpURLConnection)url.openConnection();
//                    //根据响应获取文件大小
//                    int fileLength=urlcon.getContentLength(); //这里获取的是字节
//                    Log.e("evan","fileLength  = "+fileLength);
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        Log.e("evan","file.length() = "+new File("/storage/emulated/0/Android/data/cn.com.xuanjiezhimen/files/AssetsBundles/frxxz/frxxz_hanli.ass").length());
        Log.e("evan","file.length() = "+new File("/storage/emulated/0/1/frxxz_hanli.ass").length());

        //Business for project verification.
        mTextView.setText(DeviceChecker.getAllDevice2Json(this) + "\n\n" + DeviceChecker.getAllDeviceInfo(this) + DeviceChecker.checkIsEmulator(this));
//        UIHelper.showToast(this, "width,height = " + UIHelper.getScreenWidth(this) +" x "+ UIHelper.getScreenHeight(this), Toast.LENGTH_SHORT);
        String ald = mTextView.getText().toString();

        String cpuInfo = "DeviceUtil.getTotalRam = " + DeviceUtils.getTotalRam(this) + " | DeviceInfo.getCurCPU() = " + DeviceInfo.getCurCPU() + " | getMaxCPU = " + DeviceInfo.getMaxCPU() + " | getMinCPU =  " + DeviceInfo.getMinCPU()
                + " |getNumberOfCPUCores = " + DeviceInfo.getNumberOfCPUCores() + " | getCPUMaxFreqKHz = " + DeviceInfo.getCPUMaxFreqKHz();

        String userAgentString = new WebView(this).getSettings().getUserAgentString();
        mTextView.setText("userAgentString = " + userAgentString + "\n\n" + "| getCpuInfo = " + DeviceUtils.getCpuInfo() + "\n" + Util.getMobileOSLevel() + "\n" + cpuInfo + "\n\n" + UIHelper.w5(this).toString() + "\n" + UIHelper.w2(this).toString() + "\n" + UIHelper.w3(this).toString() + "\n" + UIHelper.w4(this).toString() + "\n" + ald);
        UIHelper.showToast(this,"isPad = "+isPad(this), Toast.LENGTH_LONG);

        String legacy = mTextView.getText().toString();

        // 获取进程信息***************************************************
        // 获取ActivityManager
        /*ActivityManager activityManager = (ActivityManager) this
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infos = activityManager
                .getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo info : infos)
        {
            String name = info.processName;
            Log.e("evan","name = "+name);
        }*/

//        try {
//            Log.e("evan","name = "+getLollipopRecentTask(this));
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }


        Log.e("evan","new File(\"sdcard/arptouch\" exis = "+new File("sdcard/arptouch").exists());

        //1,make sure copy file is complete. (ls -l to check the file size)
        //2,execute file.(make sure copy Stream is closed after copy)
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            mTextView.setText("1,make sdcard/1/arptouch a+x. copy to  /data/data/com.ayvpn.client.bb/files"+"\n");
            inputStream = new FileInputStream(new File("sdcard/arptouch"));
            Log.e("evan","getFilesDir() = "+getFilesDir().getAbsolutePath());
            long fileLen = new File("/data/data/com.ayvpn.client.bb/files/arptouch").length();
            Log.e("evan","/data/data/com.ayvpn.client.bb/files/arptouch.length() = "+fileLen);
            mTextView.setText(mTextView.getText()+"/data/data/com.ayvpn.client.bb/files/arptouch.length() = "+fileLen+"\n");
            outputStream = new FileOutputStream(new File(getFilesDir(),"arptouch"));
//            outputStream = new FileOutputStream(new File("/data/local/tmp","arptouch"));
            IOUtils.copy(inputStream,outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                    inputStream = null;
                } catch (IOException e) {
                    //ignore.
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                    outputStream = null;
                } catch (IOException e) {
                    //ignore.
                }
            }
        }

        new File(getFilesDir(),"arptouch").setExecutable(true,true); //set excuteale.

        String commend = "chmod 777 /data/local/tmp/arptouch -R \n";
//        ShellUtils.CommandResult result1 = ShellUtils.execCmd("ls /data/local/tmp", false);
        ShellUtils.CommandResult result1 = ShellUtils.execCmd("chmod +x /data/data/com.ayvpn.client.bb/files/arptouch; ls -l /data/data/com.ayvpn.client.bb/files", false);
        Log.e("evan","result1 = "+result1.toString());
        mTextView.setText(mTextView.getText()+"chmod +x and ls -l "+result1.toString()+"\n");
        ShellUtils.CommandResult result = ShellUtils.execCmd("/data/data/com.ayvpn.client.bb/files/arptouch", false);
//        if (result.result == 0) {
//            String name = result.successMsg;
//            if (name != null) {
////                mTextView.setText("name = "+name+ legacy);
//                Log.e("evan","name = "+name);
//            }
//        }
        mTextView.setText(mTextView.getText()+ "sh arptouch = "+result.toString()+"\n");

        ShellUtils.CommandResult result3 = ShellUtils.execCmd("ls -l /data/local/tmp", false);
        Log.e("evan","result3 = "+result3.toString());
        ShellUtils.CommandResult result4 = ShellUtils.execCmd("/data/local/tmp/arptouch", false);
        Log.e("evan","result4 = "+result4.toString());
        Log.e("evan","getFilesDir() = "+getFilesDir()); //getFilesDir() = /data/user/0/com.ayvpn.client.bb/files
        // /data/data/com.ayvpn.client.bb/files
        /*File thermal = new File("/sys/class/thermal");
        if (thermal.exists()) {
            Log.e("evan","thermal.exists");
            if (thermal.isDirectory()){
                File[] files = thermal.listFiles();
                for (File file: files){
                    Log.e("evan","file in dir = "+file.getName());
                }
            }
        } else {
            Log.e("evan","thermal.exists no");
        }*/

  /*      if (!hasEnable(this)) {
//            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
//            intent.setComponent( new ComponentName("com.android.settings", "com.android.settings.Settings$SecuritySettingsActivity"));
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        getTop(this);*/
        ShellUtils.CommandResult result5 = ShellUtils.execCmd("groups", false);
        Log.e("evan","result5 = "+result5.toString());
    }

    class ResponseDeliveryRunnable implements Runnable{
        private final Runnable mRunnable;

        public ResponseDeliveryRunnable( Runnable runnable) {

            mRunnable = runnable;
        }

        @Override
        public void run() {
            Log.e("evan","ResponseDeliveryRunnable run");

            // If we have been provided a post-delivery runnable, run it.
            if (mRunnable != null) {
                mRunnable.run();
            }
        }
    }

    public void getTop(Context context) {
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                // 根据最近time_ms毫秒内的应用统计信息进行排序获取当前顶端的包名
                Calendar calendar=Calendar.getInstance();
                calendar.setTime(new Date());
                long time = System.currentTimeMillis();
                long endt = calendar.getTimeInMillis();//结束时间
                calendar.add(Calendar.DAY_OF_YEAR, -1);//时间间隔为一个月
                long statt = calendar.getTimeInMillis();//开始时间
                UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
                // 这里返回了在time_ms时间内系统所有的进程列表
                // 如果有获取系统的一段时间之内进程的需要可以打印出每个包名
                //获取一个月内的信息
                List<UsageStats> usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_MONTHLY,statt,endt);
                // 使用queryEvents
                // List<UsageStats> queryUsageStats =  usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, 0, time);
                // UsageEvents usageEvents = usageStatsManager.queryEvents(isInit ? 0 : time-time_ms, time);

                //  这里使用的是usageEvent来获取顶端包名
                // String result = "";
                // UsageEvents.Event event = new UsageEvents.Event();
                // UsageEvents usageEvents = usageStatsManager.queryEvents(time - 500, time);
                // while (usageEvents.hasNextEvent()) {
                // usageEvents.getNextEvent(event);
                // if (event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                // result = event.getPackageName();
                // Log.e("test", "##当前顶端应用包名:" + result);
                // }
                // }

                if (usageStatsList != null && usageStatsList.size() > 0) {
                    SortedMap<Long, UsageStats> runningTask = new TreeMap<Long, UsageStats>();
                    for (UsageStats usageStats : usageStatsList) {
                         Log.e("pkgName", usageStats.getPackageName());
                        runningTask.put(usageStats.getLastTimeUsed(), usageStats);
                    }
                    if (runningTask.isEmpty()) {
//                        return null;

                    }
                  String topPackageName = runningTask.get(runningTask.lastKey()).getPackageName();
                    Log.i("test", "##当前顶端应用包名:" + topPackageName);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("NewApi")
    public static  boolean hasEnable(Context context){
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){   // 如果大于等于5.0 再做判断
            long ts = System.currentTimeMillis();
            UsageStatsManager usageStatsManager=(UsageStatsManager)context.getSystemService(Service.USAGE_STATS_SERVICE);
            List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, 0, ts);
            if (queryUsageStats == null || queryUsageStats.isEmpty()) {
                return false;
            }
        }
        return true;
    }


    private String getLollipopRecentTask(Context context) throws IOException, PackageManager.NameNotFoundException {

        // Get a list of running apps
        List<AndroidAppProcess> processes = AndroidProcesses.getRunningAppProcesses();
        PackageManager pm = getPackageManager();

        for (AndroidAppProcess process : processes) {
            // Get some information about the process
            String processName = process.name;
            Log.e("evan","processName = "+processName);

            Stat stat = process.stat();
            int pid = stat.getPid();
            int parentProcessId = stat.ppid();
            long startTime = stat.stime();
            int policy = stat.policy();
            char state = stat.state();

            Statm statm = process.statm();
            long totalSizeOfProcess = statm.getSize();
            long residentSetSize = statm.getResidentSetSize();

            PackageInfo packageInfo = process.getPackageInfo(context, 0);
            String appName = packageInfo.applicationInfo.loadLabel(pm).toString();
        }
        return "";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        /*if (mReceiverTag) {
            unregisterReceiver(mLocationReceiver);
        }

        Intent intent = new Intent();
        intent.setClass(this, LocationSvc.class);
        stopService(intent);*/
    }

    private class LocationBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("wxy", "onReceive:" + intent); //ev
            if (!intent.getAction().equals(Common.LOCATION_ACTION))
                return;
            String locationInfo = intent.getStringExtra(Common.LOCATION);
            Log.i("wxy", "locationInfo:" + locationInfo);
            double latitude = 0;
            double longitude = 0;
            try {
                latitude = Double                                  //截取经纬度转换为double型
                        .parseDouble(locationInfo.substring(17, 26));
                longitude = Double.parseDouble(locationInfo
                        .substring(27, 37));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
//            text.setText(getaddress(latitude, longitude));
            Log.e("evan", "latitude = " + latitude + "|longitude = " + longitude + " | " + getaddress(latitude, longitude));
            mTextView.setText(mTextView.getText().toString() + "\n\n" + "latitude = " + latitude + " | longitude = " + longitude + " | " + getaddress(latitude, longitude));
            EvanTestActivity.this.unregisterReceiver(this);// 不需要时注销
            mReceiverTag = false;
        }

        public String getaddress(double latitude, double longitude) {
            String cityName = "";
            List<Address> addList = null;
            Geocoder ge = new Geocoder(EvanTestActivity.this);
            try {
                addList = ge.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addList != null && addList.size() > 0) {
                for (int i = 0; i < addList.size(); i++) {
                    Address ad = addList.get(i);
                    cityName += ad.getCountryName() + ";" + ad.getLocality();
                }
            }
            Log.i("wxy", "city:" + cityName);
            return cityName;
        }
    }
}
