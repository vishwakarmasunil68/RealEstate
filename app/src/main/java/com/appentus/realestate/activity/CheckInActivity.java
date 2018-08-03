package com.appentus.realestate.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.appentus.realestate.R;
import com.appentus.realestate.adapter.LocationAdapter;
import com.appentus.realestate.fragmentcontroller.ActivityManager;
import com.appentus.realestate.pojo.location.LocationPOJO;
import com.appentus.realestate.pojo.location.LocationResponseListPOJO;
import com.appentus.realestate.pojo.location.NewLocationPOJO;
import com.appentus.realestate.utils.Pref;
import com.appentus.realestate.utils.StringUtils;
import com.appentus.realestate.utils.TagUtils;
import com.appentus.realestate.webservice.GetWebServices;
import com.appentus.realestate.webservice.LocationResponseListCallback;
import com.appentus.realestate.webservice.LocationWebservice;
import com.appentus.realestate.webservice.WebServicesCallBack;
import com.appentus.realestate.webservice.WebServicesUrls;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckInActivity extends ActivityManager {
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.rv_location)
    RecyclerView rv_location;
    @BindView(R.id.ic_back)
    ImageView ic_back;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        ButterKnife.bind(this);
        attachAdapter();
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                callAPI();
            }
        });

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void callAPI() {
        new LocationWebservice(this, new LocationResponseListCallback() {
            @Override
            public void onGetMsg(LocationResponseListPOJO locationResponseListPOJO) {
                Log.d(TagUtils.getTag(), "location callback:-" + locationResponseListPOJO.isSuccess());
                if (locationResponseListPOJO.isSuccess()) {

                    locationPOJOS.clear();
                    locationPOJOS.addAll(locationResponseListPOJO.getResultList());
                    locationAdapter.notifyDataSetChanged();
                }
            }
        }, "GET_LOCATION", false).execute(WebServicesUrls.getLocationAPI(getResources().getString(R.string.google_places_api_key),
                Pref.GetStringPref(getApplicationContext(), StringUtils.CURRENT_LATITUDE, "28.6276928"),
                Pref.GetStringPref(getApplicationContext(), StringUtils.CURRENT_LONGITUDE, "77.3729876"),
                et_search.getText().toString()));
    }


    List<NewLocationPOJO> locationPOJOS = new ArrayList<>();
    LocationAdapter locationAdapter;

    public void attachAdapter() {
        locationAdapter = new LocationAdapter(this, null, locationPOJOS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_location.setHasFixedSize(true);
        rv_location.setAdapter(locationAdapter);
        rv_location.setLayoutManager(linearLayoutManager);
        rv_location.setItemAnimator(new DefaultItemAnimator());
    }

    public void setActivityLocation(NewLocationPOJO newLocationPOJO) {
        if (newLocationPOJO != null) {
            getLocationInfo(newLocationPOJO.getPlace_id());
        }
//        if(newLocationPOJO!=null){
//            Intent returnIntent = new Intent();
//            returnIntent.putExtra("location",newLocationPOJO);
//            setResult(Activity.RESULT_OK,returnIntent);
//            finish();
//        }else{
//            Intent returnIntent = new Intent();
//            setResult(Activity.RESULT_CANCELED, returnIntent);
//            finish();
//        }
    }

    public void getLocationInfo(String place_id) {
        String url = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + place_id + "&key=" + getResources().getString(R.string.google_places_api_key);
        new GetWebServices(this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    Log.d(TagUtils.getTag(), "location received:-" + response);
                    JSONObject jsonObject = new JSONObject(response);
                    String locationString = jsonObject.optJSONObject("result").toString();
                    LocationPOJO locationPOJO = new Gson().fromJson(locationString, LocationPOJO.class);
                    if (locationPOJO != null) {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("location", locationPOJO);
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                    } else {
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_CANCELED, returnIntent);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "GET_LOCATION_DETAIL", true).execute(url);
    }
}
