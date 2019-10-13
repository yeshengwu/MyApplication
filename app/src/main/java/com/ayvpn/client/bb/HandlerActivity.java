package com.ayvpn.client.bb;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class HandlerActivity extends Activity {
    /**
     *
     * 程序说明：
     * 	UI Thread 通过handler向其他线程发送数据并进行打印
     *
     */

    private Handler superHandler;
    private Handler normalHandler;

    private TextView tv_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        tv_result = (TextView) findViewById(R.id.tv_result);

        NormalThread normalThread = new NormalThread();
        normalThread.start();

        HandlerThread handlerThread = new HandlerThread("leochin.com");
        handlerThread.start();

		/*
		 * 将handlerThread中创建的looper传递给Handler。
		 *
		 * 也就意味着该Handler收到Message后，程序在HandlerThread创建的线程中运行
		 *
		 */
        superHandler = new Handler(handlerThread.getLooper()){

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);

                int what = msg.what;

                if(what == 2){
                    Log.d("wenhao",Thread.currentThread().getName()+" HandlerThread is OK");
//                    tv_result.setText("HandlerThread");
                }
            }
        };

        final Button normal = (Button) findViewById(R.id.btn_normal);
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                normalThreadUse(normal);
            }
        });

        final Button btn_test = (Button) findViewById(R.id.btn_test);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerThreadUse(btn_test);
            }
        });

        final Handler normalHandler12 = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                int what = msg.what;

                if(what == 1){
                    Log.d("wenhao",Thread.currentThread().getName()+ " NormalThread is OK");
                    tv_result.setText((String)msg.obj);
                }
            }

        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();

                //获得www.taobao.com对应的IP地址，并通过Toast的方式打印出来
                try {
                    InetAddress inetAddress = InetAddress.getByName("www.taobao.com");
                    Toast.makeText(HandlerActivity.this, "Address is " + inetAddress.getHostAddress(), Toast.LENGTH_LONG).show();
                    Log.e("evan","inetAddress.getHostAddress() = "+inetAddress.getHostAddress());

                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = inetAddress.getHostAddress();
                    normalHandler12.sendMessage(message);

                } catch (UnknownHostException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                Looper.loop();
            }

        }).start();
    }

    /**
     *
     * 普通线程
     *
     */
    class NormalThread extends Thread{

        @Override
        public void run() {
            // TODO Auto-generated method stub

            Looper.prepare();

            normalHandler = new Handler(){

                @Override
                public void handleMessage(Message msg) {
                    // TODO Auto-generated method stub
                    super.handleMessage(msg);

                    int what = msg.what;

                    if(what == 1){
                        Log.d("wenhao",Thread.currentThread().getName()+ " NormalThread is OK");
//                        Intent i = new Intent(HandlerActivity.this, MainActivity.class);
//                        Bundle bundle = new Bundle();
//                        i.putExtras(bundle);
//                        HandlerActivity.this.startActivity(i);
                    }
                }


            };

            Looper.loop();
        }

    }


    /**
     *
     * 向普通线程发送数据
     *
     * @param view
     */
    public void normalThreadUse(View view){

        if(normalHandler == null){
            return;
        }

        normalHandler.sendEmptyMessage(1);
    }

    /**
     *
     * 向HandlerThread发送数据
     *
     * @param view
     */
    public void handlerThreadUse(View view){

        if(superHandler == null){
            return;
        }

        superHandler.sendEmptyMessage(2);
    }


}
