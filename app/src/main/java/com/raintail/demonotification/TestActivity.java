
package com.raintail.demonotification;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class TestActivity extends Activity {

    private Button back;
    private Button show_dialog;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test2);
        back = (Button) findViewById(R.id.back);

        back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        show_dialog = (Button) findViewById(R.id.show_dialog);
        mWebView = (WebView) findViewById(R.id.web_view);


        show_dialog.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(TestActivity.this)
                        .setTitle("标题")
                        .setMessage("简单消息框")
                        .setPositiveButton("确定", null)
                        .show();
            }
        });

//        initWebView();

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//        View decorView = getWindow().getDecorView();
//        decorView.setSystemUiVisibility(uiOptions);
//    }


    private static final String URL = "file:///android_asset/helloworld.html";

    private void initWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new JSHook(), "hello");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);// 当打开新链接时，使用当前的 WebView，不会使用系统其他浏览器
                return true;
            }
        });
        mWebView.loadUrl(URL);
    }

    public class JSHook{

        @JavascriptInterface
        public String toast(String content) {
            Toast.makeText(TestActivity.this, content, Toast.LENGTH_SHORT).show();
            return "获取手机内的信息！！";
        }

    }


}
