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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.appentus.realestate.R;
import com.appentus.realestate.adapter.CategoryAdapter;
import com.appentus.realestate.fragmentcontroller.FragmentController;
import com.appentus.realestate.pojo.AddPropertyPOJO;
import com.appentus.realestate.pojo.CategoryPOJO;
import com.appentus.realestate.pojo.ResponseListPOJO;
import com.appentus.realestate.utils.Pref;
import com.appentus.realestate.utils.StringUtils;
import com.appentus.realestate.utils.ToastClass;
import com.appentus.realestate.webservice.ResponseListCallback;
import com.appentus.realestate.webservice.WebServiceBase;
import com.appentus.realestate.webservice.WebServiceBaseResponseList;
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

public class SelectCategoryFragment extends FragmentController {
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.tv_previous)
    TextView tv_previous;
    @BindView(R.id.ic_back)
    ImageView ic_back;

    @BindView(R.id.rv_catrgory)
    RecyclerView rv_catrgory;
    @BindView(R.id.rg_offer_type)
    RadioGroup rg_offer_type;

    List<CategoryPOJO> category = new ArrayList<>();
    String property_offer_id = "";
    String property_category_id = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_select_category, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        category.add("Apartment");
//        category.add("Land");
//        category.add("Villa");
//        category.add("House");
//        category.add("Building");
//        category.add("Hall");
//        category.add("Floor");
//        category.add("Office");
//        category.add("Farm");

        attachAdapter();
        callAPI();
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedNext();

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

        rg_offer_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rg_offer_type.getCheckedRadioButtonId() != -1) {
                    if (rg_offer_type.getCheckedRadioButtonId() == R.id.rb_rent) {
                        property_offer_id = "1";
                    } else {
                        property_offer_id = "2";
                    }
                }
            }
        });
    }

    public void proceedNext() {
        if (property_offer_id.length() > 0) {
            String category_id = "";
            for (CheckBox checkBox : categoryAdapter.checkBoxes) {
                if (checkBox.isChecked()) {
                    category_id = checkBox.getTag().toString();
                }
            }

            if (category_id.length() > 0) {

                AddPropertyPOJO addPropertyPOJO = new AddPropertyPOJO();
                addPropertyPOJO.setProperty_category(category_id);
                addPropertyPOJO.setProperty_offer(property_offer_id);

                AddPropertyImagesFragment addPropertyImagesFragment = new AddPropertyImagesFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("addPropertyPOJO", addPropertyPOJO);
                addPropertyImagesFragment.setArguments(bundle);
                activityManager.startFragment(R.id.frame_home, addPropertyImagesFragment);
            } else {
                ToastClass.showShortToast(getActivity().getApplicationContext(), "Please Select Category of Property");
            }
        } else {
            ToastClass.showShortToast(getActivity().getApplicationContext(), "Please select property offer");
        }
    }

    public void callAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("selected_language", Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.LANGUAGE, StringUtils.ENGLISH)));
        if(Pref.GetStringPref(getActivity().getApplicationContext(),StringUtils.LANGUAGE,StringUtils.ENGLISH).equals("en")){
            new WebServiceBaseResponseList<CategoryPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<CategoryPOJO>() {
                @Override
                public void onGetMsg(ResponseListPOJO<CategoryPOJO> responseListPOJO) {
                    if(responseListPOJO.isSuccess()){
                        category.clear();
//                    setUpViewPager(responseListPOJO.getResultList());
                        category.addAll(responseListPOJO.getResultList());
                        categoryAdapter.notifyDataSetChanged();
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
                            category.clear();
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.optJSONObject(i);
                                CategoryPOJO categoryPOJO=new CategoryPOJO();
                                categoryPOJO.setCategory_id(jsonObject1.optString("category_id"));
                                categoryPOJO.setCategory_name(jsonObject1.optString("category_arabic"));
                                categoryPOJO.setCategory_status(jsonObject1.optString("category_status"));
                                category.add(categoryPOJO);
                            }
                            categoryAdapter.notifyDataSetChanged();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "GET_ALL_CATEGORY", true).execute(WebServicesUrls.CATEGORY_URL);
        }
    }

    CategoryAdapter categoryAdapter;

    public void attachAdapter() {
        categoryAdapter = new CategoryAdapter(getActivity(), this, category);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_catrgory.setHasFixedSize(true);
        rv_catrgory.setAdapter(categoryAdapter);
        rv_catrgory.setLayoutManager(linearLayoutManager);
        rv_catrgory.setItemAnimator(new DefaultItemAnimator());
    }

}
