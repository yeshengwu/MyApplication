package com.ayvpn.client.bb.data;

import java.io.Serializable;

public class AppInfo implements Serializable {
    public String apple_id;
    public String pack_name;
    public String activity;
    public String app_name;
    public String company;
    public String icon_url;
    public String download_url;
    public String apk_md5;
    /**
     * 0 landscape
     * 1 portrait
     */
    public int vertical;
    public int channel_duration; //360s
    public int channel_tips_begin; //240s

    @Override
    public String toString() {
        return "AppInfo part {" +
                "apple_id='" + apple_id + '\'' +
                ", pack_name='" + pack_name + '\'' +
                ", activity='" + activity + '\'' +
                ", app_name='" + app_name + '\'' +
                ", company='" + company + '\'' +
                ", icon_url='" + icon_url + '\'' +
                ", vertical=" + vertical +
                ", channel_duration=" + channel_duration +
                ", channel_tips_begin=" + channel_tips_begin +
                '}';
    }
}

