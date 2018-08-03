package com.appentus.realestate.firebase;

import android.content.Context;
import android.util.Log;

import com.appentus.realestate.utils.Pref;
import com.appentus.realestate.utils.TagUtils;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by sunil on 18-08-2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    Context ctx;

    @Override
    public void onTokenRefresh() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TagUtils.getTag(), "Refreshed token: " + refreshedToken);
        Pref.SaveDeviceToken(getApplicationContext(),refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server
        //Not required for current project
    }
}