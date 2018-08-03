package com.appentus.realestate.testing;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.appentus.realestate.R;
import com.appentus.realestate.utils.TagUtils;
import com.appentus.realestate.webservice.WebServiceBase;
import com.appentus.realestate.webservice.WebServicesCallBack;
import com.appentus.realestate.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.net.URLDecoder;
import java.util.ArrayList;

public class TestingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        propertyList();
    }

    public void propertyList() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("selected_language", "ar"));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                Log.d(TagUtils.getTag(), "response:-" + response);
                try {
                    String str = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                    str = URLDecoder.decode(str, "utf-8");
                    Log.d(TagUtils.getTag(), "str:-" + str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "GET_ALL_CATEGORY", true).execute(WebServicesUrls.CATEGORY_URL);


//        RequestQueue queue = Volley.newRequestQueue(this);
//        StringRequest getRequest = new StringRequest(Request.Method.GET, WebServicesUrls.CATEGORY_URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d(TagUtils.getTag(),"response:-"+response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> map=new HashMap<>();
//                map.put("selected_language","ar");
//                return super.getParams();
//            }
//        };
//        queue.add(getRequest);


    }

}

