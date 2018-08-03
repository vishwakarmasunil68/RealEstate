package com.appentus.realestate.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.appentus.realestate.R;
import com.appentus.realestate.activity.HomeActivity;
import com.appentus.realestate.adapter.AppartmentAdapter;
import com.appentus.realestate.adapter.ImageSliderAdapter;
import com.appentus.realestate.fragmentcontroller.FragmentController;
import com.appentus.realestate.pojo.CategoryPOJO;
import com.appentus.realestate.pojo.PropertyPOJO;
import com.appentus.realestate.pojo.ResponseListPOJO;
import com.appentus.realestate.webservice.ResponseListCallback;
import com.appentus.realestate.webservice.WebServiceBaseResponseList;
import com.appentus.realestate.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import me.relex.circleindicator.CircleIndicator;

public class ApartmentFragment extends FragmentController {

    @BindView(R.id.rv_appartments)
    RecyclerView rv_appartments;
    @BindView(R.id.pb_loader)
    ProgressBar pb_loader;
    CategoryPOJO categoryPOJO;
    @BindView(R.id.pager)
    ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] sliderImage = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image2, R.drawable.image3, R.drawable.image2};
    private ArrayList<Integer> ImageArray = new ArrayList<Integer>();
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_appartment, container, false);
        setUpView(getActivity(), this, view);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            categoryPOJO = (CategoryPOJO) getArguments().getSerializable("categoryPOJO");
        }
        attachMediaAdapter();
        initImageSlider();

    }

    private void initImageSlider() {
        for (int i = 0; i < sliderImage.length; i++)
            ImageArray.add(sliderImage[i]);

        mPager = (ViewPager) view.findViewById(R.id.pager);
        mPager.setAdapter(new ImageSliderAdapter(getContext(), ImageArray));
        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        indicator.setViewPager(mPager);


        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage == ImageArray.size() - 1) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };


        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 1000, 5000);
    }

    public void callAPI() {
        pb_loader.setVisibility(View.VISIBLE);
        if(getActivity() instanceof HomeActivity){
            HomeActivity homeActivity= (HomeActivity) getActivity();
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("category_id", categoryPOJO.getCategory_id()));
            nameValuePairs.add(new BasicNameValuePair("offer_Id",String.valueOf(homeActivity.spinner_type.getSelectedItemPosition()+1)));
            new WebServiceBaseResponseList<PropertyPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<PropertyPOJO>() {
                @Override
                public void onGetMsg(ResponseListPOJO<PropertyPOJO> responseListPOJO) {
                    pb_loader.setVisibility(View.GONE);
                    if (responseListPOJO.isSuccess()) {
                        stringArrayList.clear();
                        stringArrayList.addAll(responseListPOJO.getResultList());
                        appartmentAdapter.notifyDataSetChanged();

                        if (getActivity() instanceof HomeActivity) {
                            HomeActivity homeActivity = (HomeActivity) getActivity();
                            homeActivity.passList(stringArrayList);
                        }
                    }
                }
            }, PropertyPOJO.class, "GET_ALL_PROPERTY", false).execute(WebServicesUrls.SHOW_URL);
        }

    }

    boolean is_initialize = false;

    public void initialize() {
        if (!is_initialize) {
            callAPI();
            is_initialize = true;
        } else {
            if (stringArrayList != null && stringArrayList.size() > 0) {
                if (getActivity() instanceof HomeActivity) {
                    HomeActivity homeActivity = (HomeActivity) getActivity();
                    homeActivity.passList(stringArrayList);
                }
            }
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
        rv_appartments.setNestedScrollingEnabled(false);
        if (stringArrayList.size() > 0) {


        }

    }

}
