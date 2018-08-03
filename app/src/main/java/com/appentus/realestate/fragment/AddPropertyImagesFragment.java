package com.appentus.realestate.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appentus.realestate.R;
import com.appentus.realestate.activity.HomeActivity;
import com.appentus.realestate.adapter.GalleryAdapter;
import com.appentus.realestate.fragmentcontroller.FragmentController;
import com.appentus.realestate.pojo.AddPropertyPOJO;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class AddPropertyImagesFragment extends FragmentController {

    @BindView(R.id.rv_images)
    RecyclerView rv_images;
    @BindView(R.id.cv_select_image)
    CircleImageView cv_select_image;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.tv_previous)
    TextView tv_previous;
    @BindView(R.id.ic_back)
    ImageView ic_back;

    List<String> imageStrings = new ArrayList<>();
    AddPropertyPOJO addPropertyPOJO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_add_property, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments()!=null){
            addPropertyPOJO= (AddPropertyPOJO) getArguments().getSerializable("addPropertyPOJO");
        }

        attachAdapter();

        cv_select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImagePicker.Builder(getActivity())
                        .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
                        .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                        .directory(ImagePicker.Directory.DEFAULT)
                        .allowMultipleImages(true)
                        .enableDebuggingMode(true)
                        .build();
            }
        });

        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> attachments = new ArrayList<>();
                attachments.addAll(imageStrings);
                addPropertyPOJO.setAddPropertyAttachmentsPOJOS(attachments);
                final Bundle bundle=new Bundle();
                bundle.putSerializable("addPropertyPOJO",addPropertyPOJO);
                if (imageStrings.size() > 0) {

                    PropertyLocationFragment propertyLocationFragment=new PropertyLocationFragment();
                    propertyLocationFragment.setArguments(bundle);

                    activityManager.startFragment(R.id.frame_home, propertyLocationFragment);
                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setTitle("Warning");
                    alertDialog.setMessage("Do you want to proceed without adding property Images?");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            PropertyLocationFragment propertyLocationFragment=new PropertyLocationFragment();
                            propertyLocationFragment.setArguments(bundle);

                            activityManager.startFragment(R.id.frame_home, propertyLocationFragment);
                        }
                    });
                    alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
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

    }

    GalleryAdapter galleryAdapter;

    public void attachAdapter() {
        galleryAdapter = new GalleryAdapter(getActivity(), this, imageStrings);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(getActivity(), 2);
        rv_images.setHasFixedSize(true);
        rv_images.setAdapter(galleryAdapter);
        rv_images.setLayoutManager(gridLayoutManager);
        rv_images.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            List<String> mPaths = (List<String>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_PATH);
            if (mPaths.size() > 0) {
                for (String path : mPaths) {
                    if (!imageStrings.contains(path)) {
                        imageStrings.add(path);
                        galleryAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }
}
