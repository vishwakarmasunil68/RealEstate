package com.appentus.realestate.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appentus.realestate.R;
import com.appentus.realestate.fragmentcontroller.ActivityManager;
import com.appentus.realestate.pojo.UserDetailPOJO;
import com.appentus.realestate.utils.Constants;
import com.appentus.realestate.utils.Pref;
import com.appentus.realestate.utils.StringUtils;
import com.appentus.realestate.utils.TagUtils;
import com.appentus.realestate.utils.ToastClass;
import com.appentus.realestate.webservice.WebServiceBase;
import com.appentus.realestate.webservice.WebServicesCallBack;
import com.appentus.realestate.webservice.WebServicesUrls;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends ActivityManager {

    @BindView(R.id.ccp)
    CountryCodePicker ccp;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.et_number)
    EditText et_number;
    @BindView(R.id.tv_arabic)
    TextView tv_arabic;
    @BindView(R.id.btn_loginTwitter)
    TwitterLoginButton loginTwetter;
    CallbackManager callbackManager;
    public TwitterAuthClient client;
    private static final String TAG = "TwitterLogin";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))//enable logging when app is in debug mode
                .twitterAuthConfig(new TwitterAuthConfig(getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_KEY), getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_KEY_secret)))//pass the created app Consumer KEY and Secret also called API Key and Secret
                .debug(true)//enable debug mode
                .build();

        //finally initialize twitter with created configs
        Twitter.initialize(config);
        FacebookSdk.setApplicationId(getResources().getString(R.string.facebook_app_id));
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");
                        RequestData();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAPI();
//                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            }
        });

//

        loginTwetter.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.d(TAG, "twitterLogin" + result);
                String accessToken = result.data.getAuthToken().token;
                String secretToken = result.data.getAuthToken().secret;

                Log.i(TAG, "Access Token:- " + accessToken);
                Log.i(TAG, "Secreat Token:- " + secretToken);
                // saving access tokens in sharedPReference

            }

            @Override
            public void failure(TwitterException exception) {
                Log.e(TAG,"Twitter Login Exception:- "+exception.toString());
            }
        });
        tv_arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLanguage();
            }
        });
    }

    public void changeLanguage(){
        if(Pref.GetStringPref(getApplicationContext(),StringUtils.LANGUAGE,StringUtils.ENGLISH).equals(StringUtils.ENGLISH)){
            Pref.SetStringPref(getApplicationContext(),StringUtils.LANGUAGE,StringUtils.ARABIC);
            setLanguage("ar");
        }else{
            Pref.SetStringPref(getApplicationContext(),StringUtils.LANGUAGE,StringUtils.ENGLISH);
            setLanguage("en");
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginTwetter.onActivityResult(requestCode, resultCode, data);
    }


    public void FbIntegration() {
        LoginManager.getInstance().logOut();
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "user_friends", "email"));
    }


    public void callAPI(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_mobile",et_number.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("user_country_code","+"+ccp.getSelectedCountryCode()));
        nameValuePairs.add(new BasicNameValuePair("user_country",ccp.getSelectedCountryName()));
        nameValuePairs.add(new BasicNameValuePair("user_social",""));
        nameValuePairs.add(new BasicNameValuePair("user_device_token",Pref.GetDeviceToken(getApplicationContext(),"")));
        nameValuePairs.add(new BasicNameValuePair("device_os","android"));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
//                Log.d(TagUtils.getTag(),"apicall")
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.optString("status").equalsIgnoreCase("success")){
                        Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN,true);
                        Pref.SetStringPref(getApplicationContext(), StringUtils.USER_DETAILS,jsonObject.optJSONArray("user_details").optJSONObject(0).toString());

                        Gson gson=new Gson();
                        UserDetailPOJO userDetailPOJO=gson.fromJson(jsonObject.optJSONArray("user_details").optJSONObject(0).toString(),UserDetailPOJO.class);
                        Constants.userDetailPOJO=userDetailPOJO;
                        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                    }
                    ToastClass.showShortToast(getApplicationContext(),jsonObject.optString("message"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"LOGIN",true).execute(WebServicesUrls.LOGIN_URL);
    }

    public void printHashKey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }


    public void RequestData() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d(TagUtils.getTag(), "facebook response:-" + response.toString());
                JSONObject json = response.getJSONObject();
                try {
                    if (json != null) {
                        String id = "";
                        String name = "";
                        String email = "";
                        String photo = "";
                        String phone = "";

                        if (json.has("id")) {
                            id = json.optString("id");
                        }
                        if (json.has("name")) {
                            name = json.optString("name");
                        }
                        if (json.has("email")) {
                            email = json.optString("email");
                        }
                        if (json.has("phone")) {
                            phone = json.optString("phone");
                        }

                        String profile_url = "https://graph.facebook.com/" + json.getString("id") + "/picture?type=large";
                        Log.d(TagUtils.getTag(), "facebook profile url:-" + profile_url);


//                        saveImageFromUrl("facebook", id, name, email, "", phone);
                        facebookLogin(json.optString("id"));
                    }

                } catch (JSONException e) {
                    Log.d("profile", e.toString());

                    try {
                        Log.d(TagUtils.getTag(), e.toString());
                        Log.d(TagUtils.getTag(), "id:-" + json.getString("id"));
                        Log.d(TagUtils.getTag(), "name:-" + json.getString("name"));
//                        String email="temp_"+json.getString("id")+"@bjain.com";
//                        FacebookLoginAPI(email);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//    }

    public void facebookLogin(String facebook_id){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_mobile",""));
        nameValuePairs.add(new BasicNameValuePair("user_country_code",""));
        nameValuePairs.add(new BasicNameValuePair("user_country",""));
        nameValuePairs.add(new BasicNameValuePair("user_social",facebook_id));
        nameValuePairs.add(new BasicNameValuePair("user_device_token",""));
        nameValuePairs.add(new BasicNameValuePair("device_os","android"));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
//                Log.d(TagUtils.getTag(),"apicall")
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.optString("status").equalsIgnoreCase("success")){
                        Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN,true);
                        Pref.SetStringPref(getApplicationContext(), StringUtils.USER_DETAILS,jsonObject.optJSONArray("user_details").optJSONObject(0).toString());

                        Gson gson=new Gson();
                        UserDetailPOJO userDetailPOJO=gson.fromJson(jsonObject.optJSONArray("user_details").optJSONObject(0).toString(),UserDetailPOJO.class);
                        Constants.userDetailPOJO=userDetailPOJO;
                        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                    }
                    ToastClass.showShortToast(getApplicationContext(),jsonObject.optString("message"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"LOGIN",true).execute(WebServicesUrls.LOGIN_URL);
    }

}
