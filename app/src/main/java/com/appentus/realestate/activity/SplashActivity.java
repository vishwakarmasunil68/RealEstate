package com.appentus.realestate.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.appentus.realestate.R;
import com.appentus.realestate.fragmentcontroller.ActivityManager;
import com.appentus.realestate.pojo.UserDetailPOJO;
import com.appentus.realestate.testing.TestingActivity;
import com.appentus.realestate.utils.Constants;
import com.appentus.realestate.utils.Pref;
import com.appentus.realestate.utils.StringUtils;
import com.google.gson.Gson;

public class SplashActivity extends ActivityManager {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        finishSplash();
    }

    public void finishSplash() {
        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {
                if (Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, false)) {
                    String user_details = Pref.GetStringPref(getApplicationContext(), StringUtils.USER_DETAILS, "");
                    Gson gson = new Gson();
                    UserDetailPOJO userDetailPOJO = gson.fromJson(user_details, UserDetailPOJO.class);
                    Constants.userDetailPOJO = userDetailPOJO;
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
//                    startActivity(new Intent(SplashActivity.this, TestingActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, IntroActivity.class));
                }
                finishAffinity();
            }
        }, 2000);
    }
}
