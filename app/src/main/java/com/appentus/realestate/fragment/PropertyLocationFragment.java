package com.appentus.realestate.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.appentus.realestate.R;
import com.appentus.realestate.activity.CheckInActivity;
import com.appentus.realestate.fragmentcontroller.FragmentController;
import com.appentus.realestate.pojo.AddPropertyPOJO;
import com.appentus.realestate.pojo.location.LocationPOJO;
import com.appentus.realestate.pojo.location.NewLocationPOJO;
import com.appentus.realestate.utils.Constants;
import com.appentus.realestate.utils.ToastClass;
import com.appentus.realestate.utils.UtilityFunction;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;

public class PropertyLocationFragment extends FragmentController implements OnMapReadyCallback {

    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.tv_previous)
    TextView tv_previous;
    @BindView(R.id.ic_back)
    ImageView ic_back;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.frame_map)
    FrameLayout frame_map;
    @BindView(R.id.et_property_address)
    EditText et_property_address;

    AddPropertyPOJO addPropertyPOJO;
    GoogleMap googleMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_property_location, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            addPropertyPOJO = (AddPropertyPOJO) getArguments().getSerializable("addPropertyPOJO");
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locationPOJO != null) {
                    addPropertyPOJO.setLocationPOJO(locationPOJO);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("addPropertyPOJO", addPropertyPOJO);
                    PropertyAttributesFragment propertyAttributesFragment = new PropertyAttributesFragment();
                    propertyAttributesFragment.setArguments(bundle);

                    activityManager.startFragment(R.id.frame_home, propertyAttributesFragment);
                } else {
                    ToastClass.showShortToast(getActivity().getApplicationContext(), "Please Select Location");
                }

            }
        });

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    frame_map.setVisibility(View.VISIBLE);
                } else {
                    frame_map.setVisibility(View.GONE);
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

        et_property_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLocation();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
    }


    public void checkLocation() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        } else {
            UtilityFunction.getLocation(getActivity().getApplicationContext());
            startActivityForResult(new Intent(getActivity(), CheckInActivity.class), Constants.ACTIVITY_LOCATION);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkLocationPermission() {
        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.ACCESS_LOCATION);
            return;
        } else {
            UtilityFunction.getLocation(getActivity().getApplicationContext());
            startActivityForResult(new Intent(getActivity(), CheckInActivity.class), Constants.ACTIVITY_LOCATION);
        }
    }

    LocationPOJO locationPOJO;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.ACTIVITY_LOCATION) {
            if (resultCode == Activity.RESULT_OK) {
                locationPOJO= (LocationPOJO) data.getSerializableExtra("location");
                try{
                    LatLng latLng=new LatLng(locationPOJO.getGeometry().getLocation().getLat(),locationPOJO.getGeometry().getLocation().getLng());
                    if(googleMap!=null){
                        googleMap.addMarker(new MarkerOptions().position(latLng)
                                .title(locationPOJO.getFormatted_address()));
//                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,12.0f));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                et_property_address.setText(locationPOJO.getFormatted_address());
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                locationPOJO = null;
            }
        }
    }
}
