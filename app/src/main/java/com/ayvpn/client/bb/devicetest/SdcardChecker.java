package com.ayvpn.client.bb.devicetest;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by shidu on 17/10/12.
 */

public class SdcardChecker {

    public static String writeReadFile() {
        // /storage/emulated/0/com.ayvpn.client.bb
        StringBuilder sb = new StringBuilder();
        File file = Environment.getExternalStorageDirectory();

        if (file.exists()) {
            sb.append("Environment.getExternalStorageDirectory() = " + file.getAbsolutePath() + " | exist").append("\n");
            printDirectory(file.getParentFile());
            sb.append("write file a.txt value = abcdefg to " + new File(Environment.getExternalStorageDirectory(), "a.txt").getAbsolutePath()).append("\n");

            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(new File(Environment.getExternalStorageDirectory(), "a.txt"));
                byte[] abc = "abcdefg".getBytes("utf-8");
                fileOutputStream.write(abc);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(new File(Environment.getExternalStorageDirectory(), "a.txt"));
                byte[] buffer = new byte[3];
                int len = -1;
                StringBuilder stringBuilder = new StringBuilder();
                while ((len = fileInputStream.read(buffer)) != -1) {
                    // http://blog.csdn.net/qq_32451699/article/details/52314824 Java基础学习之InputStream的read()方法陷阱
                    stringBuilder.append(new String(buffer, 0, len, "utf-8"));
                }
                sb.append("read file a.txt in " + new File(Environment.getExternalStorageDirectory(), "a.txt").getAbsolutePath()).append("\n");
                sb.append(stringBuilder.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            sb.append(Environment.getExternalStorageDirectory().getAbsolutePath() + " + file.getAbsolutePath() +  | not exist");
        }

        return sb.toString();
    }

    public static void printDirectory(File file) {
        File[] childFiles = file.listFiles();
        for (File childFile : childFiles) {
//            if (childFile.isDirectory()) {
//                printDirectory(childFile);
//            }
            System.out.println(childFile.getName());
        }
    }
}
