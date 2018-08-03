package com.appentus.realestate.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appentus.realestate.R;
import com.appentus.realestate.activity.HomeActivity;
import com.appentus.realestate.fragmentcontroller.FragmentController;
import com.appentus.realestate.pojo.AddPropertyPOJO;
import com.appentus.realestate.utils.Constants;
import com.appentus.realestate.utils.Pref;
import com.appentus.realestate.utils.StringUtils;
import com.appentus.realestate.utils.TagUtils;
import com.appentus.realestate.utils.ToastClass;
import com.appentus.realestate.webservice.WebServiceBase;
import com.appentus.realestate.webservice.WebServicesCallBack;
import com.appentus.realestate.webservice.WebServicesUrls;
import com.appentus.realestate.webservice.WebUploadService;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;

public class PropertyPreviewFragment extends FragmentController {

    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.tv_previous)
    TextView tv_previous;
    @BindView(R.id.ic_back)
    ImageView ic_back;
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
    AddPropertyPOJO addPropertyPOJO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_property_preview, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            addPropertyPOJO = (AddPropertyPOJO) getArguments().getSerializable("addPropertyPOJO");

            try {
                tv_age.setText(addPropertyPOJO.getAge());
                tv_price.setText(addPropertyPOJO.getPrice());
                tv_additional_notes.setText(addPropertyPOJO.getExtra_notes());
                tv_size.setText(addPropertyPOJO.getSize());
                tv_halls.setText(addPropertyPOJO.getHalls());
                tv_toilets.setText(addPropertyPOJO.getToilet());
                tv_rooms.setText(addPropertyPOJO.getRoom());
                tv_property_address.setText(addPropertyPOJO.getLocationPOJO().getFormatted_address());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
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




    }

    public void submit() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle(getResources().getString(R.string.submit_property));
        alertDialog.setMessage(getResources().getString(R.string.sure_submit));
        alertDialog.setPositiveButton(getResources().getString(R.string.ok),new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
//                startActivity(new Intent(getActivity(), HomeActivity.class));
//                getActivity().finishAffinity();
                submitProperty();
            }
        });
        alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public void submitProperty(){
        try{
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("user_id", new StringBody(Constants.userDetailPOJO.getUserId()));
            reqEntity.addPart("category_id", new StringBody(addPropertyPOJO.getProperty_category()));
            reqEntity.addPart("offer_id", new StringBody(addPropertyPOJO.getProperty_offer()));
            reqEntity.addPart("property_price", new StringBody(addPropertyPOJO.getPrice()));
            reqEntity.addPart("property_price_id", new StringBody(addPropertyPOJO.getPrice_id()));
            reqEntity.addPart("property_size_id", new StringBody(addPropertyPOJO.getSize_id()));
            reqEntity.addPart("property_size", new StringBody(addPropertyPOJO.getSize()));
            reqEntity.addPart("property_location", new StringBody(addPropertyPOJO.getLocationPOJO().getFormatted_address()));
            reqEntity.addPart("property_lat", new StringBody(String.valueOf(addPropertyPOJO.getLocationPOJO().getGeometry().getLocation().getLat())));
            reqEntity.addPart("property_long", new StringBody(String.valueOf(addPropertyPOJO.getLocationPOJO().getGeometry().getLocation().getLng())));
            reqEntity.addPart("marital_status", new StringBody("all"));
            reqEntity.addPart("property_purpose", new StringBody(addPropertyPOJO.getProperty_category()));
            reqEntity.addPart("rent_period", new StringBody(addPropertyPOJO.getProperty_category()));
            reqEntity.addPart("room_count", new StringBody(addPropertyPOJO.getRoom()));
            reqEntity.addPart("toilet_count", new StringBody(addPropertyPOJO.getToilet()));
            reqEntity.addPart("additional_note", new StringBody(addPropertyPOJO.getExtra_notes()));
            reqEntity.addPart("shop_count", new StringBody(addPropertyPOJO.getProperty_category()));
            reqEntity.addPart("hall_count", new StringBody(addPropertyPOJO.getHalls()));
            reqEntity.addPart("floor_number", new StringBody(addPropertyPOJO.getProperty_category()));
            reqEntity.addPart("property_age", new StringBody(addPropertyPOJO.getAge()));

            try {
                String split[]=addPropertyPOJO.getAmenities().split(",");
                for(int i=0;i<split.length;i++) {
                    reqEntity.addPart("amenities["+i+"]",new StringBody(split[i]));
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            int count=0;

            for(String images:addPropertyPOJO.getAddPropertyAttachmentsPOJOS()){
                reqEntity.addPart("morephoto["+count+"]",new FileBody(new File(images)));
            }


            new WebUploadService(reqEntity, getActivity(), new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    Log.d(TagUtils.getTag(), apicall + " :- " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));
                        if (jsonObject.optString("status").equals("success")) {
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                            getActivity().finishAffinity();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "POST_PROPERTY", true).execute(WebServicesUrls.ADD_PROPERTY_URL);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
