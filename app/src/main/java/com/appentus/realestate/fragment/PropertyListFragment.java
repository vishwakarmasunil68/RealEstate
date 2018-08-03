package com.appentus.realestate.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.appentus.realestate.R;
import com.appentus.realestate.activity.HomeActivity;
import com.appentus.realestate.activity.LoginActivity;
import com.appentus.realestate.adapter.ViewPagerWithTitleAdapter;
import com.appentus.realestate.fragmentcontroller.FragmentController;
import com.appentus.realestate.pojo.CategoryPOJO;
import com.appentus.realestate.pojo.PropertyPOJO;
import com.appentus.realestate.pojo.ResponseListPOJO;
import com.appentus.realestate.utils.Pref;
import com.appentus.realestate.utils.StringUtils;
import com.appentus.realestate.utils.ToastClass;
import com.appentus.realestate.webservice.ResponseListCallback;
import com.appentus.realestate.webservice.WebServiceBase;
import com.appentus.realestate.webservice.WebServiceBaseResponseList;
import com.appentus.realestate.webservice.WebServicesCallBack;
import com.appentus.realestate.webservice.WebServicesUrls;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PropertyListFragment extends FragmentController implements OnMapReadyCallback{

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.ll_map_view)
    LinearLayout ll_map_view;
    GoogleMap googleMap;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_property_list, container, false);
        setUpView(getActivity(),this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        callAPI();

    }
    List<CategoryPOJO> categoryPOJOS=new ArrayList<>();
    public void callAPI(){

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("selected_language", Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.LANGUAGE, StringUtils.ENGLISH)));
        if(Pref.GetStringPref(getActivity().getApplicationContext(),StringUtils.LANGUAGE,StringUtils.ENGLISH).equals("en")){
            new WebServiceBaseResponseList<CategoryPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<CategoryPOJO>() {
                @Override
                public void onGetMsg(ResponseListPOJO<CategoryPOJO> responseListPOJO) {
                    if(responseListPOJO.isSuccess()){
                        categoryPOJOS.clear();
//                    setUpViewPager(responseListPOJO.getResultList());
                        categoryPOJOS.addAll(responseListPOJO.getResultList());
                        setUpViewPager(categoryPOJOS);
                    }
                }
            },CategoryPOJO.class,"GET_ALL_CATEGORY",true).execute(WebServicesUrls.CATEGORY_URL);
        }else{
            new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        if(jsonObject.optString("status").equals("success")){
                            JSONArray jsonArray=jsonObject.optJSONArray("result");
                            categoryPOJOS.clear();
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.optJSONObject(i);
                                CategoryPOJO categoryPOJO=new CategoryPOJO();
                                categoryPOJO.setCategory_id(jsonObject1.optString("category_id"));
                                categoryPOJO.setCategory_name(jsonObject1.optString("category_arabic"));
                                categoryPOJO.setCategory_status(jsonObject1.optString("category_status"));
                                categoryPOJOS.add(categoryPOJO);
                            }
                            setUpViewPager(categoryPOJOS);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "GET_ALL_CATEGORY", true).execute(WebServicesUrls.CATEGORY_URL);
        }

    }

    public void setUpViewPager(List<CategoryPOJO> categoryPOJOS) {

        ViewPagerWithTitleAdapter adapter = new ViewPagerWithTitleAdapter(getChildFragmentManager());
//        adapter.addFrag(new ApartmentFragment(), "ALL");
//        adapter.addFrag(new ApartmentFragment(), "APARTMENT");
//        adapter.addFrag(new ApartmentFragment(), "LAND");
//        adapter.addFrag(new ApartmentFragment(), "VILA");
//        adapter.addFrag(new ApartmentFragment(), "HOUSE");
//        adapter.addFrag(new ApartmentFragment(), "BUILDING");
//        adapter.addFrag(new ApartmentFragment(), "HALL");
//        adapter.addFrag(new ApartmentFragment(), "FLOOR");
//        adapter.addFrag(new ApartmentFragment(), "OFFICE");
//        adapter.addFrag(new ApartmentFragment(), "FARM");

        final List<ApartmentFragment> apartmentFragments=new ArrayList<>();
        for(CategoryPOJO categoryPOJO:categoryPOJOS){
            Bundle bundle=new Bundle();
            bundle.putSerializable("categoryPOJO",categoryPOJO);
            ApartmentFragment apartmentFragment=new ApartmentFragment();
            apartmentFragment.setArguments(bundle);
            adapter.addFrag(apartmentFragment,categoryPOJO.getCategory_name());

            apartmentFragments.add(apartmentFragment);
        }

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());

        tabs.setupWithViewPager(viewPager);
        apartmentFragments.get(0).initialize();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                apartmentFragments.get(position).initialize();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void listMapMarkers(List<PropertyPOJO> propertyPOJOS){
        if(googleMap!=null&&propertyPOJOS!=null&&propertyPOJOS.size()>0) {
            googleMap.clear();
            for(PropertyPOJO propertyPOJO:propertyPOJOS) {
                try {
                    LatLng latLng = new LatLng(Double.parseDouble(propertyPOJO.getPropertyLat()), Double.parseDouble(propertyPOJO.getPropertyLong()));
                    googleMap.addMarker(new MarkerOptions().position(latLng).title(propertyPOJO.getPropertyLocation()));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void hideViewPager() {
        viewPager.setVisibility(View.GONE);
        ll_map_view.setVisibility(View.VISIBLE);
    }

    public void showViewPager() {
        viewPager.setVisibility(View.VISIBLE);
        ll_map_view.setVisibility(View.GONE);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap=googleMap;
    }
}
