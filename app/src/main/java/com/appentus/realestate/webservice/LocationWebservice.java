package com.appentus.realestate.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.appentus.realestate.pojo.location.LocationResponseListPOJO;
import com.appentus.realestate.pojo.location.NewLocationPOJO;
import com.appentus.realestate.utils.TagUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunil on 05-04-2017.
 */

public class LocationWebservice extends AsyncTask<String,Void,List<NewLocationPOJO>> {

    private final String TAG=getClass().getSimpleName();
    Context context;
    ProgressDialog progressDialog;
    String msg;
    boolean is_dialog=true;
    String response;
    Object className;
    public LocationWebservice(Context context, Object className, String msg, boolean is_dialog){
        this.context=context;
        this.msg = msg;
        this.className=className;
        this.is_dialog=is_dialog;
        Log.d(TAG, this.toString());
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(is_dialog) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    protected List<NewLocationPOJO> doInBackground(String... strings) {
        HttpURLConnection urlConnection = null;
        URL url = null;
        InputStream inStream = null;
        try {
            Log.d(TagUtils.getTag(),"url called:-"+strings[0]);
            url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.connect();
            inStream = urlConnection.getInputStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
            String temp, response = "";
            while ((temp = bReader.readLine()) != null) {
                response += temp;
            }
            Log.d(TagUtils.getTag(),"get response:-"+response);
            this.response=response;

            if(response!=null&&response.length()>0){
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.optJSONArray("predictions");
                    if(jsonArray.length()>0){
                        List<NewLocationPOJO> list=new ArrayList<>();
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject prediction=jsonArray.optJSONObject(i);
                            NewLocationPOJO newLocationPOJO=new NewLocationPOJO();
                            newLocationPOJO.setDescription(prediction.optString("description"));
                            newLocationPOJO.setPlace_id(prediction.optString("place_id"));
                            newLocationPOJO.setMain_text(prediction.optJSONObject("structured_formatting").optString("main_text"));
                            newLocationPOJO.setSecondary_text(prediction.optJSONObject("structured_formatting").optString("secondary_text"));

                            list.add(newLocationPOJO);
                        }
                        return list;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return null;

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<NewLocationPOJO> responsePOJO) {
        super.onPostExecute(responsePOJO);
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
        LocationResponseListCallback mcallback = (LocationResponseListCallback) className;

        if (responsePOJO != null) {
            LocationResponseListPOJO locationResponseListPOJO=new LocationResponseListPOJO();
            locationResponseListPOJO.setSuccess(true);
            locationResponseListPOJO.setResultList(responsePOJO);
            mcallback.onGetMsg(locationResponseListPOJO);
        }else{
            LocationResponseListPOJO locationResponseListPOJO=new LocationResponseListPOJO();
            locationResponseListPOJO.setSuccess(false);
            mcallback.onGetMsg(locationResponseListPOJO);
        }
    }
}
