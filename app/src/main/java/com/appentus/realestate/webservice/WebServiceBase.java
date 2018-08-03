package com.appentus.realestate.webservice;

/**
 * Created by sunil on 29-12-2016.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.appentus.realestate.R;
import com.appentus.realestate.utils.Pref;
import com.appentus.realestate.utils.StringUtils;
import com.appentus.realestate.utils.TagUtils;
import com.appentus.realestate.utils.ToastClass;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by sunil on 28-12-2016.
 */

public class WebServiceBase extends AsyncTask<String, Void, String> {
    ArrayList<NameValuePair> nameValuePairs;
    String jResult;
    Context context;
    Object object;
    static HttpClient httpClient;
    static HttpPost httppost;
    static HttpResponse response;
    static BufferedReader bufferedReader;
    InputStream is;
    ProgressDialog progressDialog;
    boolean isdialog = true;
    String msg;
    private final String TAG = getClass().getName();

    public WebServiceBase(ArrayList<NameValuePair> nameValuePairs,Context context,Object object, String msg, boolean isdialog) {
        this.nameValuePairs = nameValuePairs;
//        this.nameValuePairs.addAll(UtilityFunction.getNameValuePairs(context));
        this.object = object;
        this.context=context;
        this.msg = msg;
        this.isdialog = isdialog;
        String nmv="";
        for(NameValuePair nameValuePair:nameValuePairs){
            nmv=nmv+nameValuePair.getName()+" : "+nameValuePair.getValue()+"\n";
        }
        Log.d(TagUtils.getTag(),"nmv:-"+nmv);
        Log.d(TAG, this.toString());
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (isdialog) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(context.getString(R.string.please_wait));
            progressDialog.setCancelable(true);
            progressDialog.show();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            jResult = httpCall(params[0], nameValuePairs);
            Log.d(TagUtils.getTag(),msg+":-"+jResult);
            if(!Pref.GetStringPref(context, StringUtils.LANGUAGE,StringUtils.ENGLISH).equals("en")){
                jResult= StringEscapeUtils.unescapeJava(jResult);
            }
            Log.d(TagUtils.getTag(),msg+":-"+ StringEscapeUtils.unescapeJava(jResult));


        } catch (Exception e) {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            e.printStackTrace();
        }
        return jResult;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        if(s!=null&&s.length()>0) {
            if (object != null) {
                WebServicesCallBack mcallback = (WebServicesCallBack) object;
                mcallback.onGetMsg(msg, s);
            } else {
                ToastClass.showShortToast(context, "Something went wrong");
            }
        }else{
            ToastClass.showShortToast(context,"No Internet");
        }

    }

    public String covertToString(String myString){
        String str = myString.split(" ")[0];
        str = str.replace("\\","");
        String[] arr = str.split("u");
        String text = "";
        for(int i = 1; i < arr.length; i++){
            int hexVal = Integer.parseInt(arr[i], 16);
            text += (char)hexVal;
        }

        return text;
    }


    public static String httpCall(String url, ArrayList<NameValuePair> postParameters) {
        String result = "";
        try {
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, "UTF-8");
            params.setBooleanParameter("http.protocol.expect-continue", false);
            httpClient = new DefaultHttpClient(params);
            httppost = new HttpPost(url);

            httppost.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));
            Log.d(TagUtils.getTag(),"httppost url:-"+httppost.getURI());
//            Log.d(TagUtils.getTag(),"httppost:-"+httppost.toString());
            // Execute HTTP Post Request
            response = httpClient.execute(httppost);

            //converting response into string
            result = convertToString(response);
            Log.d(TagUtils.getTag(),"response from server :-"+result);
            return result;
        } catch (IOException e) {
            Log.i("Io", e.toString());

            return "";
        }
    }

    private static String convertToString(HttpResponse response) {

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
            StringBuffer stringBuffer = new StringBuffer("");
            String line = "";
            String LineSeparator = System.getProperty("line.separator");
            while ((line = bufferedReader.readLine()) != null) {
                Log.d(TagUtils.getTag(),"line:-"+line);
                stringBuffer.append(line + LineSeparator);
            }
            bufferedReader.close();
            return stringBuffer.toString();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }

    }

    @Override
    public String toString() {
        return "WebServiceBase{" +
                "nameValuePairs=" + nameValuePairs +
                ", jResult='" + jResult + '\'' +
                ", context=" + context +
                ", object=" + object +
                ", is=" + is +
                ", progressDialog=" + progressDialog +
                ", isdialog=" + isdialog +
                ", msg='" + msg + '\'' +
                ", TAG='" + TAG + '\'' +
                '}';
    }
}
