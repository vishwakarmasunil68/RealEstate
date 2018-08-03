package com.appentus.realestate.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.appentus.realestate.R;
import com.appentus.realestate.activity.ChatActivity;
import com.appentus.realestate.fragmentcontroller.FragmentController;
import com.appentus.realestate.pojo.PropertyPOJO;
import com.appentus.realestate.utils.Constants;
import com.appentus.realestate.utils.Pref;
import com.appentus.realestate.utils.TagUtils;
import com.appentus.realestate.utils.ToastClass;
import com.appentus.realestate.webservice.WebServiceBase;
import com.appentus.realestate.webservice.WebServicesCallBack;
import com.appentus.realestate.webservice.WebServicesUrls;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;

public class PropertyViewFragment extends FragmentController {

    @BindView(R.id.ic_back)
    ImageView ic_back;
    @BindView(R.id.iv_default_pic)
    ImageView iv_default_pic;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_size)
    TextView tv_size;
    @BindView(R.id.tv_age)
    TextView tv_age;
    @BindView(R.id.tv_halls)
    TextView tv_halls;
    @BindView(R.id.tv_toilets)
    TextView tv_toilets;
    @BindView(R.id.tv_rooms)
    TextView tv_rooms;
    @BindView(R.id.tv_additional_notes)
    TextView tv_additional_notes;
    @BindView(R.id.tv_property_address)
    TextView tv_property_address;
    @BindView(R.id.tv_profile_name)
    TextView tv_profile_name;
    @BindView(R.id.tv_contact_number)
    TextView tv_contact_number;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_chat)
    Button btn_chat;
    @BindView(R.id.iv_fav)
    ImageView iv_fav;

    PropertyPOJO propertyPOJO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_property_view, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (getArguments() != null) {
            propertyPOJO = (PropertyPOJO) getArguments().getSerializable("propertyPOJO");
            getPropertyDetail();
        }

        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ChatActivity.class);
                Log.d(TagUtils.getTag(),"property:-"+propertyPOJO.toString());
                intent.putExtra("fri_id",propertyPOJO.getUserId());
                intent.putExtra("user_name",propertyPOJO.getUserName());
                startActivity(intent);
            }
        });

        iv_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(is_fav){
                    removeFromFavorite();
                }else{
                    callFavoriteAPI();
                }
            }
        });
    }

    public void removeFromFavorite(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetailPOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("property_id",PropertyViewFragment.this.propertyPOJO.getPropertyId()));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.optString("status").equalsIgnoreCase("success")){
                        iv_fav.setImageResource(R.drawable.ic_unfav);
                        is_fav=false;
                    }
                    ToastClass.showShortToast(getActivity().getApplicationContext(),jsonObject.optString("message"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"CALL_FAVORITE_API",true).execute(WebServicesUrls.REMOVE_FROM_FAV);
    }

    public void callFavoriteAPI(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetailPOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("property_id",PropertyViewFragment.this.propertyPOJO.getPropertyId()));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.optString("status").equalsIgnoreCase("success")){
                        is_fav=true;
                        iv_fav.setImageResource(R.drawable.ic_fac);
                    }else{
                        is_fav=false;
                    }
                    ToastClass.showShortToast(getActivity().getApplicationContext(),jsonObject.optString("message"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"CALL_FAVORITE_API",true).execute(WebServicesUrls.ADD_TO_FAV);
    }

    public void showProperty(){

        if (propertyPOJO != null) {
            tv_price.setText(getValue(propertyPOJO.getPropertyPrice()) + " " + getValue(propertyPOJO.getPrice()));
            tv_size.setText(getValue(propertyPOJO.getPropertySize()) + " " + getValue(propertyPOJO.getSize()));
            tv_age.setText(getValue(propertyPOJO.getPropertyAge()));
            tv_halls.setText(getValue(propertyPOJO.getHallCount()));
            tv_toilets.setText(getValue(propertyPOJO.getToiletCount()));
            tv_rooms.setText(getValue(propertyPOJO.getRoomCount()));
            tv_additional_notes.setText(getValue(propertyPOJO.getAdditionalNote()));
            tv_property_address.setText(getValue(propertyPOJO.getPropertyLocation()));
            tv_profile_name.setText(getValue(propertyPOJO.getUserName()));
            tv_contact_number.setText(getValue(propertyPOJO.getUserMobile()));
            tv_title.setText(getValue(propertyPOJO.getPropertyLocation()));

        }

    }
    boolean is_fav=false;
    public void getPropertyDetail(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("property_id",propertyPOJO.getPropertyId()));
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetailPOJO.getUserId()));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.optString("status").equalsIgnoreCase("success")){
                        String property_detail=jsonObject.optJSONObject("property_details").toString();
                        Gson gson=new Gson();
                        PropertyPOJO propertyPOJO=gson.fromJson(property_detail,PropertyPOJO.class);
                        PropertyViewFragment.this.propertyPOJO=propertyPOJO;

                        showProperty();

                        JSONArray jsonArray=jsonObject.optJSONArray("property_files");
                        String image_path=jsonArray.optJSONObject(0).optString("attchment_file_path");
                        Glide.with(getActivity().getApplicationContext())
                                .load(image_path)
                                .placeholder(R.drawable.ic_default_pic)
                                .error(R.drawable.ic_default_pic)
                                .dontAnimate()
                                .into(iv_default_pic);

                        if(jsonObject.optString("favorite").equalsIgnoreCase("no")){
                            is_fav=false;
                            iv_fav.setImageResource(R.drawable.ic_unfav);
                        }else{
                            is_fav=true;
                            iv_fav.setImageResource(R.drawable.ic_fac);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"GET_PROPERTY_DETAILS",true).execute(WebServicesUrls.GET_PROPERTY);
    }

    public String getValue(String val) {
        if (val != null) {
            return val;
        }
        return "";
    }

}
