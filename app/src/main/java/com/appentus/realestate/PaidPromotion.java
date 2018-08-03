package com.appentus.realestate;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.appentus.realestate.activity.HomeActivity;
import com.appentus.realestate.activity.PaymentWebViewActivity;
import com.appentus.realestate.adapter.AppartmentAdapter;
import com.appentus.realestate.adapter.PaidPromotionAdapter;
import com.appentus.realestate.fragment.Moview;
import com.appentus.realestate.fragmentcontroller.ActivityManager;
import com.appentus.realestate.pojo.PropertyPOJO;
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
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaidPromotion extends ActivityManager {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_pic)
    ImageView iv_pic;
    @BindView(R.id.btn_select)
    Button btn_select;
    @BindView(R.id.btn_buy)
    Button btn_buy;
    @BindView(R.id.spinner)
    Spinner spinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_promotion);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiatePicker();
            }
        });
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(new File(image_path).exists()) {
                    String amount = "";
                    if (spinner.getSelectedItemPosition() == 0) {
                        amount = "10";
                    } else {
                        amount = "20";
                    }

                    String url = WebServicesUrls.paymentUrl(String.valueOf(spinner.getSelectedItemPosition() + 1),
                            Constants.userDetailPOJO.getUserId(), "", amount);

                    Intent intent = new Intent(PaidPromotion.this, PaymentWebViewActivity.class);
                    intent.putExtra("url", url);
                    startActivityForResult(intent, 101);
                }
            }
        });
    }

    public void initiatePicker(){
        new ImagePicker.Builder(this)
                .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
                .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                .directory(ImagePicker.Directory.DEFAULT)
                .allowMultipleImages(false)
                .enableDebuggingMode(true)
                .build();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    String image_path="";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            List<String> mPaths = (List<String>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_PATH);
            if (mPaths.size() > 0) {
                image_path = mPaths.get(0);
                Glide.with(getApplicationContext())
                        .load(mPaths.get(0))
                        .placeholder(R.drawable.ic_default_pic)
                        .error(R.drawable.ic_default_pic)
                        .dontAnimate()
                        .into(iv_pic);
            }
        }
        if (requestCode == 101) {
            if(resultCode == Activity.RESULT_OK){
//                String result=data.getStringExtra("result");
                uploadBanner();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }


    public void uploadBanner(){
        try{
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("user_id", new StringBody(Constants.userDetailPOJO.getUserId()));
            reqEntity.addPart("plan_id", new StringBody(String.valueOf(spinner.getSelectedItemPosition()+1)));
            reqEntity.addPart("morephoto",new FileBody(new File(image_path)));


            new WebUploadService(reqEntity,this, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    Log.d(TagUtils.getTag(), apicall + " :- " + response);
                    try{
                        JSONObject jsonObject=new JSONObject(response);
                        if(jsonObject.optString("status").equalsIgnoreCase("success")){
                            ToastClass.showShortToast(getApplicationContext(),"Banner Uploaded");
                            startActivity(new Intent(PaidPromotion.this, HomeActivity.class));
                            finishAffinity();
                        }else{
                            ToastClass.showShortToast(getApplicationContext(),jsonObject.optString("message"));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }, "UPDATE_PROFILE", true).execute(WebServicesUrls.UPLOAD_BANNER);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}