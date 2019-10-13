package com.ayvpn.client.bb.locate;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;

/**
 * Created by shidu on 17/9/26.
 */

public class LocationSvc extends Service implements LocationListener {

    private LocationManager locationManager;

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("wxy", "--onBind()--");
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.e("wxy", "judgeProvider = " + judgeProvider(locationManager));
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
        } else {
            Log.i("wxy", "onStart 无法定位"); //ev
            Toast.makeText(this, "无法定位", Toast.LENGTH_SHORT).show();
        }

    }

    public class MyBinder extends Binder {
        LocationSvc getService(){
            return LocationSvc.this;
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
            return NETWORK_PROVIDER;
        } else if (prodiverlist.contains(LocationManager.GPS_PROVIDER)) {
            return LocationManager.GPS_PROVIDER;
        } else {
            Toast.makeText(LocationSvc.this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("wxy", "onLocationChanged:" + location); //ev
        //通知Activity
        Intent intent = new Intent();
        intent.setAction(Common.LOCATION_ACTION);
        intent.putExtra(Common.LOCATION, location.toString());
        sendBroadcast(intent);

        // 如果只是需要定位一次，这里就移除监听，停掉服务。如果要进行实时定位，可以在退出应用或者其他时刻停掉定位服务。
        locationManager.removeUpdates(this);
        stopSelf();
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

}
