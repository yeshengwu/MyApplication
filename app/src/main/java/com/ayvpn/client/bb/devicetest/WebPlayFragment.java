package com.ayvpn.client.bb.devicetest;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ayvpn.client.bb.R;

import java.lang.ref.WeakReference;

public class WebPlayFragment extends Activity  {

    private static final int LOADING_PROGRESS = 70;
    private static final int MSG_TIMEOUT = 1;
//    private static final String APP_URL = "http://h5.changxianapp.com/mse/";
//    private static final String APP_URL = "http://192.168.0.142:8000";
//    private static final String APP_URL = "http://www.scottandrew.com/mp3/syfy/01%20-%20Scott%20Andrew%20-%20More%20Good%20Days.mp3";

    private Handler mHandler;

    private WebView mWebView;
    private ProgressBar mLoadBar;

//    private String mEpsodeUrl = "http://192.168.0.246/";
    private String mEpsodeUrl = "http://www.baidu.com/";

    private boolean mProgressReady;
    private boolean mIsBeingPlay;

    //evan add for Tester;
    private TextView tv_info;

//    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View rootView;//缓存Fragment view so that webview keep its goback history

    private static class LocalHandler extends Handler {
        private WeakReference<WebPlayFragment> mContext;

        public LocalHandler(WebPlayFragment context) {
            this.mContext = new WeakReference<WebPlayFragment>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            WebPlayFragment context = mContext.get();
            if (context != null) {

            }
        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_webpaly);

        setupViews();

        mHandler = new LocalHandler(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        mWebView.resumeTimers();
        mWebView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        mWebView.pauseTimers();
        mWebView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mWebView != null) {
            mWebView.stopLoading();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // see http://stackoverflow.com/questions/18755318/webview-destroy-causing-fatal-signal-11-crash
        WebStorage.getInstance().deleteAllData();
        if (mWebView != null && mWebView.getParent() != null){
            ((ViewGroup)mWebView.getParent()).removeView(mWebView); //http://stackoverflow.com/questions/5267639/how-to-safely-turn-webview-zooming-on-and-off-as-needed
            mWebView.destroy();
            mWebView = null;
        }

        if (mHandler != null) {
            synchronized (mHandler) {
                mHandler.removeCallbacksAndMessages(null);
                mHandler = null;
            }
        }
    }

    //http://stackoverflow.com/questions/5267639/how-to-safely-turn-webview-zooming-on-and-off-as-needed
    @Override
    public void finish() {
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.removeAllViews();
        super.finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ActionBar actionBar = getActionBar();
        if (actionBar == null) {
            return;
        }
        if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
            actionBar.hide();
        } else {
            actionBar.show();
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupViews() {
        mWebView = (WebView) findViewById(R.id.webview);
        mLoadBar = (ProgressBar) findViewById(R.id.web_progress_bar);

//        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
//        mSwipeRefreshLayout.setOnRefreshListener(mRefreshListener);
//        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setPluginState(PluginState.ON);

        // 启用localStorage 和 sessionStorage see Logcat Web Console error
        mWebView.getSettings().setDomStorageEnabled(true);

        //自适应
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);

        mWebView.getSettings().setBuiltInZoomControls(true);


        //load from the network
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //handle download things
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                if (!TextUtils.isEmpty(url)){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }
            }
        });

//        mWebView.getSettings()
//                .setUserAgentString(
//                        "Mozilla/5.0 (Linux; U; Android 4.1.1; en-us; MI 2S Build/JRO03L) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");

        mWebView.setWebViewClient(mWebViewClient);
        mWebView.setWebChromeClient(mWebChromeClient);

        WebViewJsInterface m_webViewInterface = new WebViewJsInterface();
        mWebView.addJavascriptInterface(m_webViewInterface, "Android");

        loadData();

        tv_info = (TextView) findViewById(R.id.tv_info);
        tv_info.setText(mWebView.getSettings().getUserAgentString());
        tv_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("evan","reload");
//                mWebView.reload(); //走 onPageStarted onPageFinished
                mWebView.onResume(); // 没日志打出。
            }
        });

//        tv_info.setVisibility(View.GONE);
    }

    class WebViewJsInterface {
        @JavascriptInterface
        public void tryGame(boolean isPlaying) {
            mIsBeingPlay = isPlaying;
//            if (isPlaying){
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mSwipeRefreshLayout.setEnabled(false);
//                    }
//                });
//            }else {
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mSwipeRefreshLayout.setEnabled(true);
//                    }
//                });
//            }
        }
    }

    private WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.e("evan","onPageStarted url = "+url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mEpsodeUrl = url;
//            view.loadUrl("javascript:try{autoplay();}catch(e){}");
            Log.e("evan","onPageFinished url = "+url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);

            mLoadBar.setVisibility(View.GONE);
//            mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress >= LOADING_PROGRESS && !mProgressReady) {
            }

            if (newProgress == 100) {
                mLoadBar.setVisibility(View.GONE);
//                mSwipeRefreshLayout.setRefreshing(false);
                mProgressReady = true;

            } else {
                if (mLoadBar.getVisibility() == View.GONE)
                    mLoadBar.setVisibility(View.VISIBLE);
                mLoadBar.setProgress(newProgress);
            }


        }

    };


//    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
//
//        @Override
//        public void onRefresh() {
//
//            mWebView.clearCache(true);
//            loadData();
//        }
//    };

    private void loadData() {
        if (!TextUtils.isEmpty(mEpsodeUrl)) {
            mWebView.loadUrl(mEpsodeUrl);
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            mIsBeingPlay = false;
//            mSwipeRefreshLayout.setEnabled(true);
        }else {
            super.onBackPressed();
        }
    }
}
