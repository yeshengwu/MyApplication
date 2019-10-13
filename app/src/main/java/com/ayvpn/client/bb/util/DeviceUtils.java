package com.ayvpn.client.bb.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by shidu on 17/10/19.
 */

public class DeviceUtils {
    public static String getTotalRam(Context context){//MB,GB
        String path = "/proc/meminfo";
        String firstLine = null;
        String totalRam = "0";
        try{
            FileReader fileReader = new FileReader(path);
            BufferedReader br = new BufferedReader(fileReader,8192);
            firstLine = br.readLine().split("\\s+")[1];
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(firstLine != null){
//            totalRam = (int)Math.ceil((new Float(Float.valueOf(firstLine) / (1024 * 1024)).doubleValue()));
            totalRam = android.text.format.Formatter.formatFileSize(context,Long.parseLong(firstLine) *1024);
        }

        return totalRam;
    }

    public static String getCpuInfo()
    {
        String[] arrayOfString1 = new String[2];
        arrayOfString1[0] = "";
        arrayOfString1[1] = "";
        try
        {
            BufferedReader localBufferedReader = new BufferedReader(new FileReader("/proc/cpuinfo"), 8192);
            String[] arrayOfString2 = localBufferedReader.readLine().split("\\s+");
            int i = 2;
            while (true)
            {
                if (i >= arrayOfString2.length)
                {
                    arrayOfString2 = localBufferedReader.readLine().split("\\s+");
                    arrayOfString1[1] = (arrayOfString1[1] + arrayOfString2[2]);
                    localBufferedReader.close();
                    return arrayOfString1[0];
                }
                arrayOfString1[0] = (arrayOfString1[0] + arrayOfString2[i] + "_");
                i += 1;
            }
        }
        catch (IOException localIOException)
        {

        }

        return arrayOfString1[0];
    }

    /*public static String getCpuFileFirstLine() {
        // Processor	: AArch64 Processor rev 2 (aarch64)
        // AArch64_Processor_rev_1_(aarch64)_
        String[] arrayOfString1 = new String[2];
        arrayOfString1[0] = "";
        arrayOfString1[1] = "";
        try {
            BufferedReader localBufferedReader = new BufferedReader(new FileReader("/proc/cpuinfo"), 8192);
            String[] arrayOfString2 = localBufferedReader.readLine().split("\\s+");

            for (int i = 2;i<arrayOfString2.length;i++){
                arrayOfString1[0] += arrayOfString2[i]+ "_";
            }

        } catch (IOException localIOException) {

        }
        return arrayOfString1[0];
    }*/
}
