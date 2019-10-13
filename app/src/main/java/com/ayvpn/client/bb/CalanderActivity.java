package com.ayvpn.client.bb;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.TextView;

import com.ayvpn.client.bb.util.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by shidu on 18/6/2.
 * https://blog.csdn.net/shaohx0518/article/details/38899305 获取与修改android自带日历日程
 */

public class CalanderActivity extends Activity {

    // 为了兼容不同版本的日历,2.2以后url发生改变
    private static String calanderEventURL = "";
    static {
        if (Integer.parseInt(Build.VERSION.SDK) >= 8) {
            calanderEventURL = "content://com.android.calendar/events";
        } else {
            calanderEventURL = "content://calendar/events";
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textView = new TextView(this);
        textView.setText("getCalenderEvent(this) file = "+new File(Environment.getExternalStorageDirectory(),"evan.txt").getAbsolutePath());
        setContentView(textView);

        try {
            getCalenderEvent(this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getCalenderEvent(Context context) throws IOException {
        File calenderFile = new File(Environment.getExternalStorageDirectory(),"evan.txt");
        if (calenderFile.exists()) {
            calenderFile.delete();
        }
        FileOutputStream outputStream = new FileOutputStream(calenderFile);
        ContentResolver cr = context.getContentResolver();
        // 日历里面相应的Event的URI
        Uri uri = Uri.parse(calanderEventURL);
        Cursor cursor = cr.query(uri, null, null, null, null);
        while (cursor.moveToNext()) {
//            CalendarEvent cde = new CalendarEvent();
//            cde.setCalendar_id(cursor.getString(cursor
//                    .getColumnIndex(CalendarContract.Events.CALENDAR_ID)));
//            cde.setTitle(cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE)));
//            cde.setDtstart(cursor.getString(cursor
//                    .getColumnIndex(CalendarContract.Events.DTSTART)));
//            cde.setDtend(cursor.getString(cursor.getColumnIndex(CalendarContract.Events.DTEND)));
//            cde.setDuration(cursor.getString(cursor
//                    .getColumnIndex(CalendarContract.Events.DURATION)));
//            cde.setRrule(cursor.getString(cursor.getColumnIndex(CalendarContract.Events.RRULE)));
//            cde.setEventTimezone(cursor.getString(cursor
//                    .getColumnIndex(CalendarContract.Events.EVENT_TIMEZONE)));
//            calendarEvents.add(cde);
            String title = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE));
            String startTime = Util.getDateTime("yyyy-MM-dd HH:MM:SS", cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTSTART)));
            Log.e("evan", title);
            Log.e("evan", startTime);

            outputStream.write(startTime.getBytes());
            outputStream.write("\n".getBytes());
            outputStream.write(title.getBytes());
            outputStream.write("\n".getBytes());
        }
        outputStream.close();
    }

}
