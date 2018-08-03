package com.appentus.realestate.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.appentus.realestate.R;
import com.appentus.realestate.adapter.AmenitiesAdapter;
import com.appentus.realestate.adapter.CategoryAdapter;
import com.appentus.realestate.fragmentcontroller.FragmentController;
import com.appentus.realestate.pojo.AddPropertyPOJO;
import com.appentus.realestate.pojo.AmenitiesPOJO;
import com.appentus.realestate.pojo.CategoryPOJO;
import com.appentus.realestate.pojo.ResponseListPOJO;
import com.appentus.realestate.utils.Pref;
import com.appentus.realestate.utils.StringUtils;
import com.appentus.realestate.utils.ToastClass;
import com.appentus.realestate.webservice.ResponseListCallback;
import com.appentus.realestate.webservice.WebServiceBaseResponseList;
import com.appentus.realestate.webservice.WebServicesUrls;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PropertyAttributesFragment extends FragmentController{

    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.tv_previous)
    TextView tv_previous;
    @BindView(R.id.ic_back)
    ImageView ic_back;
    @BindView(R.id.seek_rooms)
    DiscreteSeekBar seek_rooms;
    @BindView(R.id.seek_toilets)
    DiscreteSeekBar seek_toilets;
    @BindView(R.id.seek_hall)
    DiscreteSeekBar seek_hall;
    @BindView(R.id.seek_age)
    DiscreteSeekBar seek_age;

    @BindView(R.id.rv_amenities)
    RecyclerView rv_amenities;
    List<AmenitiesPOJO> amenitiesStringList=new ArrayList<>();

    AddPropertyPOJO addPropertyPOJO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_property_attributes,container,false);
        setUpView(getActivity(),this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            addPropertyPOJO = (AddPropertyPOJO) getArguments().getSerializable("addPropertyPOJO");
        }

        attachAdapter();

        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPropertyPOJO.setAge(String.valueOf(seek_age.getProgress()));
                addPropertyPOJO.setToilet(String.valueOf(seek_toilets.getProgress()));
                addPropertyPOJO.setRoom(String.valueOf(seek_rooms.getProgress()));
                addPropertyPOJO.setHalls(String.valueOf(seek_hall.getProgress()));
                String amneties="";
                if(amenitiesAdapter.checkedPosition.size()>0){
                    for(int i=0;i<amenitiesAdapter.checkedPosition.size();i++){
                        if((i+1)==amenitiesAdapter.checkedPosition.size()){
                            amneties+=amenitiesAdapter.checkedPosition.get(i);
                        }else{
                            amneties+=amenitiesAdapter.checkedPosition.get(i)+",";
                        }
                    }
                }else{
                    ToastClass.showShortToast(getActivity().getApplicationContext(),"Please Select Anemities");
                    return;
                }

                addPropertyPOJO.setAmenities(amneties);

                Bundle bundle = new Bundle();
                bundle.putSerializable("addPropertyPOJO", addPropertyPOJO);

                FillDetailFragment fillDetailFragment=new FillDetailFragment();
                fillDetailFragment.setArguments(bundle);
                activityManager.startFragment(R.id.frame_home,fillDetailFragment);
            }
        });

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        callAPI();

    }


    public void callAPI(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("selected_language", Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.LANGUAGE,StringUtils.ENGLISH)));
        new WebServiceBaseResponseList<AmenitiesPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<AmenitiesPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<AmenitiesPOJO> responseListPOJO) {
                if(responseListPOJO.isSuccess()){
//                    setUpViewPager(responseListPOJO.getResultList());
                    amenitiesStringList.addAll(responseListPOJO.getResultList());
                    amenitiesAdapter.notifyDataSetChanged();
                }
            }
        },AmenitiesPOJO.class,"GET_ALL_AMNITIES",true).execute(WebServicesUrls.AMNIETIES);
    }

    AmenitiesAdapter amenitiesAdapter;
    public void attachAdapter() {
        amenitiesAdapter = new AmenitiesAdapter(getActivity(), this, amenitiesStringList);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        rv_amenities.setHasFixedSize(true);
        rv_amenities.setAdapter(amenitiesAdapter);
        rv_amenities.setLayoutManager(linearLayoutManager);
        rv_amenities.setItemAnimator(new DefaultItemAnimator());
    }

}
