package com.appentus.realestate.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.appentus.realestate.R;
import com.appentus.realestate.fragmentcontroller.ActivityManager;
import com.appentus.realestate.utils.TagUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentWebViewActivity extends ActivityManager {
    @BindView(R.id.webview)
    WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_web_view);
        ButterKnife.bind(this);

//        String url = "http://appentus.me/aqar/api/api/payment_view?plan_id=1&user_id=32&plan_name=rnk&amount=10";
        String url = getIntent().getStringExtra("url");
        Log.d(TagUtils.getTag(), "url:-" + url);


        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return false;
                }
                Log.d(TagUtils.getTag(), "Processing webview url click..." + url);
                //  view.loadUrl(url);

                return true;
            }


            public void onPageFinished(WebView view, String url) {
                Log.e(TagUtils.getTag(), "Finished loading URL: " + url);
                if (url.contains("http://appentus.me/aqar/api/api/payment_success")) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result", "");
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }


            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            }
        });

        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if (webView != null) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                super.onBackPressed();
            }
        }
    }

}
