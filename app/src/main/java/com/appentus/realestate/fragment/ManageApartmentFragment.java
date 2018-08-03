package com.appentus.realestate.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.appentus.realestate.R;
import com.appentus.realestate.adapter.AppartmentAdapter;
import com.appentus.realestate.fragmentcontroller.FragmentController;
import com.appentus.realestate.pojo.CategoryPOJO;
import com.appentus.realestate.pojo.PropertyPOJO;
import com.appentus.realestate.pojo.ResponseListPOJO;
import com.appentus.realestate.utils.Constants;
import com.appentus.realestate.webservice.ResponseListCallback;
import com.appentus.realestate.webservice.WebServiceBaseResponseList;
import com.appentus.realestate.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@SuppressLint("ValidFragment")
public class ManageApartmentFragment extends FragmentController {

    @BindView(R.id.rv_appartments)
    RecyclerView rv_appartments;
    @BindView(R.id.pb_loader)
    ProgressBar pb_loader;

    public ManageApartmentFragment(String type){
        this.type=type;
    }
    String type = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_appartment, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString("type");

        }
        attachMediaAdapter();
    }

    public void callAPI() {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        String url = WebServicesUrls.MANAGE_PROPERTY_URL;

        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetailPOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("property_status", type));

//        pb_loader.setVisibility(View.VISIBLE);


        new WebServiceBaseResponseList<PropertyPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<PropertyPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<PropertyPOJO> responseListPOJO) {
                pb_loader.setVisibility(View.GONE);
                if (responseListPOJO.isSuccess()) {
                    stringArrayList.clear();
                    stringArrayList.addAll(responseListPOJO.getResultList());
                    appartmentAdapter.notifyDataSetChanged();
                }
            }
        }, PropertyPOJO.class, "GET_MY_PROPERTY", false).execute(url);
    }

    boolean is_initialize = false;

    public void initialize() {
        if (!is_initialize) {
            callAPI();
            is_initialize = true;
        }
    }

    AppartmentAdapter appartmentAdapter;
    List<PropertyPOJO> stringArrayList = new ArrayList<>();

    public void attachMediaAdapter() {

        appartmentAdapter = new AppartmentAdapter(getActivity(), this, stringArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_appartments.setHasFixedSize(true);
        rv_appartments.setAdapter(appartmentAdapter);
        rv_appartments.setLayoutManager(linearLayoutManager);
        rv_appartments.setItemAnimator(new DefaultItemAnimator());

    }

}
