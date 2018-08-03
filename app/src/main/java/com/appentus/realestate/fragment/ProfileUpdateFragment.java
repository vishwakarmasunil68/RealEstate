package com.appentus.realestate.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.appentus.realestate.R;
import com.appentus.realestate.activity.HomeActivity;
import com.appentus.realestate.activity.LoginActivity;
import com.appentus.realestate.fragmentcontroller.FragmentController;
import com.appentus.realestate.pojo.UserDetailPOJO;
import com.appentus.realestate.utils.Constants;
import com.appentus.realestate.utils.Pref;
import com.appentus.realestate.utils.StringUtils;
import com.appentus.realestate.utils.TagUtils;
import com.appentus.realestate.utils.ToastClass;
import com.appentus.realestate.webservice.WebServicesCallBack;
import com.appentus.realestate.webservice.WebServicesUrls;
import com.appentus.realestate.webservice.WebUploadService;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import butterknife.BindView;

public class ProfileUpdateFragment extends FragmentController {

    @BindView(R.id.iv_profile_image)
    ImageView iv_profile_image;
    @BindView(R.id.et_full_name)
    EditText et_full_name;
    @BindView(R.id.et_bio)
    EditText et_bio;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.iv_add_pic)
    ImageView iv_add_pic;
    @BindView(R.id.btn_save)
    Button btn_save;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_profile_update, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        et_full_name.setText(getValue(Constants.userDetailPOJO.getUserName()));
        et_bio.setText(getValue(Constants.userDetailPOJO.getUserBio()));
        et_email.setText(getValue(Constants.userDetailPOJO.getUserEmail()));

        Glide.with(getActivity().getApplicationContext())
                .load(getValue(Constants.userDetailPOJO.getUserProfile()))
                .placeholder(R.drawable.ic_default_pic)
                .error(R.drawable.ic_default_pic)
                .dontAnimate()
                .into(iv_profile_image);


        iv_add_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImagePicker.Builder(getActivity())
                        .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
                        .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                        .directory(ImagePicker.Directory.DEFAULT)
                        .allowMultipleImages(false)
                        .enableDebuggingMode(true)
                        .build();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

    }

    public void updateProfile(){
        try {
            if(et_full_name.getText().toString().length()>0
                    &&et_email.getText().toString().length()>0
                    &&et_bio.getText().toString().length()>0
                    ) {
                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                reqEntity.addPart("user_id", new StringBody(Constants.userDetailPOJO.getUserId()));
                reqEntity.addPart("user_name", new StringBody(et_full_name.getText().toString()));
                reqEntity.addPart("user_mobile", new StringBody(Constants.userDetailPOJO.getUserMobile()));
                reqEntity.addPart("user_bio", new StringBody(et_bio.getText().toString()));
                reqEntity.addPart("user_email", new StringBody(et_email.getText().toString()));
                if (profile_pic.length() > 0 && new File(profile_pic).exists()) {
                    reqEntity.addPart("morephoto", new FileBody(new File(profile_pic)));
                }
                new WebUploadService(reqEntity, getActivity(), new WebServicesCallBack() {
                    @Override
                    public void onGetMsg(String apicall, String response) {
                        Log.d(TagUtils.getTag(), apicall + " :- " + response);
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            if(jsonObject.optString("status").equalsIgnoreCase("success")){
                                Pref.SetStringPref(getActivity().getApplicationContext(), StringUtils.USER_DETAILS,jsonObject.optJSONArray("user_details").optJSONObject(0).toString());

                                Gson gson=new Gson();
                                UserDetailPOJO userDetailPOJO=gson.fromJson(jsonObject.optJSONArray("user_details").optJSONObject(0).toString(),UserDetailPOJO.class);
                                Constants.userDetailPOJO=userDetailPOJO;
                                onBackPressed();

                            }
                            ToastClass.showShortToast(getActivity().getApplicationContext(),jsonObject.optString("message"));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, "UPDATE_PROFILE", true).execute(WebServicesUrls.UPDATE_PROFILE);
            }else{
                ToastClass.showShortToast(getActivity().getApplicationContext(),"Please Enter details");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public String getValue(String val) {
        if (val != null) {
            return val;
        }
        return "";
    }

    String profile_pic = "";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            List<String> mPaths = (List<String>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_PATH);
            if (mPaths.size() > 0) {
                profile_pic = mPaths.get(0);
                Glide.with(getActivity().getApplicationContext())
                        .load(mPaths.get(0))
                        .placeholder(R.drawable.ic_default_pic)
                        .error(R.drawable.ic_default_pic)
                        .dontAnimate()
                        .into(iv_profile_image);
            }
        }
    }


}
