package com.ayvpn.client.bb.data;

public class AVPacket {
    public int type;
    public long pts;
    public byte[] data;
    public int size;

    public AVPacket(long pts, byte[] data, int size) {
        this.pts = pts;
        this.size = size;
//        this.data = new byte[size];
//        System.arraycopy(data, 0, this.data, 0,  size);
        this.data = data;
    }
}
