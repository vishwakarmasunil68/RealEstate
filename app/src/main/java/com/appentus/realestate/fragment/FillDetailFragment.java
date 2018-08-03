package com.appentus.realestate.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.appentus.realestate.R;
import com.appentus.realestate.fragmentcontroller.FragmentController;
import com.appentus.realestate.pojo.AddPropertyPOJO;
import com.appentus.realestate.pojo.AmenitiesPOJO;
import com.appentus.realestate.pojo.PricePOJO;
import com.appentus.realestate.pojo.ResponseListPOJO;
import com.appentus.realestate.pojo.SizePOJO;
import com.appentus.realestate.utils.Pref;
import com.appentus.realestate.utils.StringUtils;
import com.appentus.realestate.utils.ToastClass;
import com.appentus.realestate.webservice.ResponseListCallback;
import com.appentus.realestate.webservice.WebServiceBaseResponseList;
import com.appentus.realestate.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FillDetailFragment extends FragmentController {

    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.tv_previous)
    TextView tv_previous;
    @BindView(R.id.ic_back)
    ImageView ic_back;
    @BindView(R.id.et_size)
    EditText et_size;
    @BindView(R.id.spinner_size)
    Spinner spinner_size;
    @BindView(R.id.et_price)
    EditText et_price;
    @BindView(R.id.spinner_price)
    Spinner spinner_price;
    @BindView(R.id.et_notes)
    EditText et_notes;
    AddPropertyPOJO addPropertyPOJO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_fill_details, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            addPropertyPOJO = (AddPropertyPOJO) getArguments().getSerializable("addPropertyPOJO");
        }
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(et_price.getText().toString().length()>0
                        &&et_size.getText().toString().length()>0
                        ){
                    addPropertyPOJO.setSize(et_size.getText().toString());
                    addPropertyPOJO.setPrice(et_price.getText().toString());
                    addPropertyPOJO.setSize_id(sizePOJOS.get(spinner_size.getSelectedItemPosition()).getProperty_size_id());
                    addPropertyPOJO.setPrice_id(pricePOJOS.get(spinner_price.getSelectedItemPosition()).getProperty_price_id());
                    addPropertyPOJO.setExtra_notes(et_notes.getText().toString());

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("addPropertyPOJO", addPropertyPOJO);

                    PropertyPreviewFragment propertyPreviewFragment=new PropertyPreviewFragment();
                    propertyPreviewFragment.setArguments(bundle);

                    activityManager.startFragment(R.id.frame_home, propertyPreviewFragment);
                }else{
                    ToastClass.showShortToast(getActivity().getApplicationContext(),"please fill data properly");
                }


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

        callSizeAPI();
        callPriceAPI();
    }

    List<SizePOJO> sizePOJOS=new ArrayList<>();

    public void callSizeAPI(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
//        nameValuePairs.add(new BasicNameValuePair("selected_language", Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.LANGUAGE,StringUtils.ENGLISH)));
        nameValuePairs.add(new BasicNameValuePair("selected_language", "en"));
        new WebServiceBaseResponseList<SizePOJO>(nameValuePairs, getActivity(), new ResponseListCallback<SizePOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<SizePOJO> responseListPOJO) {
                if(responseListPOJO.isSuccess()){
//                    setUpViewPager(responseListPOJO.getResultList());
                    sizePOJOS.clear();
                    sizePOJOS.addAll(responseListPOJO.getResultList());
                    List<String> stringList=new ArrayList<>();
                    for(SizePOJO sizePOJO:sizePOJOS){
                        stringList.add(sizePOJO.getSize());
                    }
                    populateSpinnerAdapter(stringList,spinner_size);
                }
            }
        },SizePOJO.class,"GET_ALL_SIZE",true).execute(WebServicesUrls.SIZE_URL);
    }

    List<PricePOJO> pricePOJOS=new ArrayList<>();
    public void callPriceAPI(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
//        nameValuePairs.add(new BasicNameValuePair("selected_language", Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.LANGUAGE,StringUtils.ENGLISH)));
        nameValuePairs.add(new BasicNameValuePair("selected_language", "en"));
        new WebServiceBaseResponseList<PricePOJO>(nameValuePairs, getActivity(), new ResponseListCallback<PricePOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<PricePOJO> responseListPOJO) {
                if(responseListPOJO.isSuccess()){
//                    setUpViewPager(responseListPOJO.getResultList());
                    pricePOJOS.addAll(responseListPOJO.getResultList());
                    List<String> stringList=new ArrayList<>();
                    for(PricePOJO pricePOJO:pricePOJOS){
                        stringList.add(pricePOJO.getPrice());
                    }
                    populateSpinnerAdapter(stringList,spinner_price);
                }
            }
        },PricePOJO.class,"GET_ALL_PRICE",true).execute(WebServicesUrls.PRICE_URL);
    }

    public void populateSpinnerAdapter(List<String> spinnerArray,Spinner spinner){
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
    }
}
