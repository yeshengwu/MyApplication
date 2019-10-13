package com.ayvpn.client.bb.data;


import java.io.Serializable;
import java.util.Locale;

public class DownloadInfo implements Serializable {
    public boolean forceInstall;
    public String apkMd5;
    public String apkName;
    public String downloadUrl;

    @Override
    public String toString() {
        return String.format(Locale.CHINA, "[forceInstall = %s, apkMd5 = %s, apkName = %s, downloadUrl = %s]",
                forceInstall ? "true" : "false", apkMd5, apkName, downloadUrl);
    }
}
