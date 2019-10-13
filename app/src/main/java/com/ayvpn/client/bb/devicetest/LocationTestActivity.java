package com.ayvpn.client.bb.devicetest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ayvpn.client.bb.R;
import com.ayvpn.client.bb.locate.Common;

import java.io.IOException;
import java.util.List;

import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;
import static android.location.LocationManager.PASSIVE_PROVIDER;

public class LocationTestActivity extends Activity implements LocationListener {

    private TextView text;
    private ProgressDialog dialog;

    private LocationManager locationManager;
    private BroadcastReceiver mLocationReceiver;
    private boolean mReceiverTag = false; //广播接受者标识位

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        text = (TextView) findViewById(R.id.text);

        // 注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Common.LOCATION_ACTION);
        mLocationReceiver = new LocationTestActivity.LocationBroadcastReceiver();
        registerReceiver(mLocationReceiver, filter);
        mReceiverTag = true;

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        String judgeProvider = judgeProvider(locationManager);
        Log.e("wxy", "judgeProvider = " + judgeProvider);
        Log.e("wxy", "getLastKnownLocation = " + locationManager.getLastKnownLocation(judgeProvider));

        if (locationManager.getProvider(NETWORK_PROVIDER) != null && locationManager.isProviderEnabled(NETWORK_PROVIDER)) {
            Log.i("wxy", "onStart getProvider:" + NETWORK_PROVIDER); //ev
            locationManager
                    .requestLocationUpdates(NETWORK_PROVIDER, 0, 0,
                            this);
        } else if (locationManager.getProvider(GPS_PROVIDER) != null && locationManager.isProviderEnabled(GPS_PROVIDER)) {
            Log.i("wxy", "onStart getProvider:" + GPS_PROVIDER); //ev
            locationManager
                    .requestLocationUpdates(GPS_PROVIDER, 0, 0,
                            this);
        } else if (locationManager.getProvider(PASSIVE_PROVIDER) != null && locationManager.isProviderEnabled(PASSIVE_PROVIDER)) {
            Log.i("wxy", "onStart getProvider:" + PASSIVE_PROVIDER); //ev
            locationManager
                    .requestLocationUpdates(PASSIVE_PROVIDER, 0, 0,
                            this);
        } else {
            Log.i("wxy", "onStart 无法定位"); //ev
            Toast.makeText(this, "无法定位", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 判断是否有可用的内容提供器
     *
     * @return 不存在返回null
     */
    private String judgeProvider(LocationManager locationManager) {
        List<String> prodiverlist = locationManager.getProviders(true);

        List<String> allProviders = locationManager.getAllProviders();
        for (String provider : allProviders) {
            Log.e("wxy", "getAllProviders item = " + provider);
        }
        if (prodiverlist.contains(NETWORK_PROVIDER)) {
            return LocationManager.NETWORK_PROVIDER;
        } else if (prodiverlist.contains(LocationManager.GPS_PROVIDER)) {
            return LocationManager.GPS_PROVIDER;
        } else if (prodiverlist.contains(LocationManager.PASSIVE_PROVIDER)) {
            return LocationManager.PASSIVE_PROVIDER;
        } else {
            Toast.makeText(LocationTestActivity.this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        if (mReceiverTag) {
            unregisterReceiver(mLocationReceiver);
//        }
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("wxy", "onLocationChanged:" + location); //ev
        //通知Activity
        Intent intent = new Intent();
        intent.setAction(Common.LOCATION_ACTION);
        intent.putExtra(Common.LOCATION, location);
        sendBroadcast(intent);

        // 如果只是需要定位一次，这里就移除监听，停掉服务。如果要进行实时定位，可以在退出应用或者其他时刻停掉定位服务。
//        locationManager.removeUpdates(this);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i("wxy", "onProviderDisabled:" + provider); //ev
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i("wxy", "onProviderEnabled:" + provider); //ev
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i("wxy", "onStatusChanged:" + provider + " | status = " + status); //ev
    }

    private class LocationBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("wxy", "onReceive:" + intent); //ev
            if (!intent.getAction().equals(Common.LOCATION_ACTION))
                return;
            Location locationInfo = (Location) intent.getParcelableExtra(Common.LOCATION);
            Log.i("wxy", "locationInfo:" + locationInfo.getLatitude()+" | longtitude = "+locationInfo.getLongitude());
            double latitude = locationInfo.getLatitude();
            double longitude = locationInfo.getLongitude();
//            try {
//                latitude = Double                                  //截取经纬度转换为double型
//                        .parseDouble(locationInfo.substring(17, 26));
//                longitude = Double.parseDouble(locationInfo
//                        .substring(27, 37));
//            } catch (NumberFormatException e) {
//                e.printStackTrace();
//            }
//            text.setText(getaddress(latitude, longitude));
            Log.e("evan", "latitude = " + latitude + "|longitude = " + longitude + " | " + getaddress(latitude, longitude));
            text.setText(text.getText().toString() + "\n\n" + "latitude = " + latitude + " | longitude = " + longitude + " | " + getaddress(latitude, longitude));
//            LocationTestActivity.this.unregisterReceiver(this);// 不需要时注销
            mReceiverTag = false;
        }

        public String getaddress(double latitude, double longitude) {
            String cityName = "";
            List<Address> addList = null;
            Geocoder ge = new Geocoder(LocationTestActivity.this);
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
