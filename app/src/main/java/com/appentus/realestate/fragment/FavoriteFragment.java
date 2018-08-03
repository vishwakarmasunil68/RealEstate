package com.appentus.realestate.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appentus.realestate.R;
import com.appentus.realestate.adapter.AppartmentAdapter;
import com.appentus.realestate.fragmentcontroller.FragmentController;
import com.appentus.realestate.pojo.PropertyPOJO;
import com.appentus.realestate.utils.Constants;
import com.appentus.realestate.utils.ToastClass;
import com.appentus.realestate.webservice.WebServiceBase;
import com.appentus.realestate.webservice.WebServicesCallBack;
import com.appentus.realestate.webservice.WebServicesUrls;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FavoriteFragment extends FragmentController {

    @BindView(R.id.rv_appartments)
    RecyclerView rv_appartments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_favorite, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachMediaAdapter();
    }

    List<PropertyPOJO> propertyPOJOS = new ArrayList<>();

    public void callAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetailPOJO.getUserId()));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {
                        JSONArray jsonArray = jsonObject.optJSONArray("property");
                        List<PropertyPOJO> propertyPOJOS = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            propertyPOJOS.add(new Gson().fromJson(jsonArray.optJSONObject(i).toString(), PropertyPOJO.class));
                        }
                        FavoriteFragment.this.propertyPOJOS.clear();
                        FavoriteFragment.this.propertyPOJOS.addAll(propertyPOJOS);
                        appartmentAdapter.notifyDataSetChanged();
                    } else {
                        ToastClass.showShortToast(getActivity().getApplicationContext(), "No Property Found");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "GET_FAV_PROPERTY", true).execute(WebServicesUrls.GET_FAV_LIST);
    }


    AppartmentAdapter appartmentAdapter;
    List<PropertyPOJO> stringArrayList= new ArrayList<>();

    public void attachMediaAdapter() {

        appartmentAdapter = new AppartmentAdapter(getActivity(), this, stringArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_appartments.setHasFixedSize(true);
        rv_appartments.setAdapter(appartmentAdapter);
        rv_appartments.setLayoutManager(linearLayoutManager);
        rv_appartments.setItemAnimator(new DefaultItemAnimator());
        rv_appartments.setNestedScrollingEnabled(false);
    }
}
