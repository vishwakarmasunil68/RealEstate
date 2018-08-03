package com.appentus.realestate.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appentus.realestate.R;
import com.appentus.realestate.adapter.ViewPagerWithTitleAdapter;
import com.appentus.realestate.fragmentcontroller.FragmentController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ManagePropertiesFragment extends FragmentController {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.ic_back)
    ImageView ic_back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_manage_properties, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViewPager();

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    public void setUpViewPager() {

        ViewPagerWithTitleAdapter adapter = new ViewPagerWithTitleAdapter(getChildFragmentManager());

        final List<ManageApartmentFragment> apartmentFragments=new ArrayList<>();

        ManageApartmentFragment manageApartmentFragment1=new ManageApartmentFragment("0");
        ManageApartmentFragment manageApartmentFragment2=new ManageApartmentFragment("1");
        ManageApartmentFragment manageApartmentFragment3=new ManageApartmentFragment("2");
        ManageApartmentFragment manageApartmentFragment4=new ManageApartmentFragment("3");
        ManageApartmentFragment manageApartmentFragment5=new ManageApartmentFragment("4");

        apartmentFragments.add(manageApartmentFragment1);
        apartmentFragments.add(manageApartmentFragment2);
        apartmentFragments.add(manageApartmentFragment3);
        apartmentFragments.add(manageApartmentFragment4);
        apartmentFragments.add(manageApartmentFragment5);

        adapter.addFrag(manageApartmentFragment1, getResources().getString(R.string.pending));
        adapter.addFrag(manageApartmentFragment2, getResources().getString(R.string.open));
        adapter.addFrag(manageApartmentFragment3, getResources().getString(R.string.hidden));
        adapter.addFrag(manageApartmentFragment4, getResources().getString(R.string.closed));
        adapter.addFrag(manageApartmentFragment5, getResources().getString(R.string.rejected));
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

}
