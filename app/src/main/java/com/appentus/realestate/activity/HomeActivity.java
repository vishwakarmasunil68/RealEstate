package com.appentus.realestate.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.appentus.realestate.PaidPromotion;
import com.appentus.realestate.R;
import com.appentus.realestate.adapter.ViewPagerAdapter;
import com.appentus.realestate.fragment.AboutUsFragment;
import com.appentus.realestate.fragment.ChatFragment;
import com.appentus.realestate.fragment.ConditionForAddPropertyFragment;
import com.appentus.realestate.fragment.FavoriteFragment;
import com.appentus.realestate.fragment.ManagePropertiesFragment;
import com.appentus.realestate.fragment.ProfileUpdateFragment;
import com.appentus.realestate.fragment.PropertyListFragment;
import com.appentus.realestate.fragmentcontroller.ActivityManager;
import com.appentus.realestate.pojo.PropertyPOJO;
import com.appentus.realestate.utils.Constants;
import com.appentus.realestate.utils.Pref;
import com.appentus.realestate.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends ActivityManager {

    @BindView(R.id.ll_list)
    LinearLayout ll_list;
    @BindView(R.id.ll_map)
    LinearLayout ll_map;
    @BindView(R.id.ll_chat)
    LinearLayout ll_chat;
    @BindView(R.id.ll_fav)
    LinearLayout ll_fav;

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.spinner_type)
    public Spinner spinner_type;


    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView nvDrawer;
    private Toolbar toolbar;
    @BindView(R.id.ic_ham)
    ImageView ic_ham;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        settingNavDrawer();
        setUpViewPager();
        ll_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
                propertyListFragment.showViewPager();
            }
        });

        ll_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
                propertyListFragment.hideViewPager();
            }
        });

        ll_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
                tv_title.setText(getResources().getString(R.string.chat));
            }
        });

        ll_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
                tv_title.setText(getResources().getString(R.string.favorite));
            }
        });
        checkLocation();

    }


    private void settingNavDrawer() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        setupDrawerContent(nvDrawer);

        View headerLayout = nvDrawer.inflateHeaderView(R.layout.home_nav_header);

        headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(R.id.frame_home, new ProfileUpdateFragment());
            }
        });

        setupDrawerToggle();
        drawerToggle.setDrawerIndicatorEnabled(false);

        nvDrawer.setItemIconTintList(null);
        ic_ham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.openDrawer(GravityCompat.START);
            }
        });
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    private void setupDrawerToggle() {
        drawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // Do whatever you want here

            }
        };
        // Set the drawer toggle as the DrawerListener
        mDrawer.addDrawerListener(drawerToggle);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void selectDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_add_property:
                startFragment(R.id.frame_home, new ConditionForAddPropertyFragment());
                break;
            case R.id.menu_manage_property:
                startFragment(R.id.frame_home, new ManagePropertiesFragment());
                break;
            case R.id.menu_profile:
                startFragment(R.id.frame_home, new ProfileUpdateFragment());
                break;
            case R.id.menu_logout:
                Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, false);
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finishAffinity();
                break;
            case R.id.menu_about_app:
                startFragment(R.id.frame_home, new AboutUsFragment());
                break;
            case R.id.paid_promotion:
                Intent i = new Intent(getApplicationContext(), PaidPromotion.class);
                startActivity(i);
                break;
        }
        mDrawer.closeDrawers();
    }


    public void checkLocation() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkLocationPermission() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.ACCESS_LOCATION);
            return;
        }
    }


    PropertyListFragment propertyListFragment;
    ChatFragment chatFragment;
    FavoriteFragment favoriteFragment;

    public void setUpViewPager() {
        propertyListFragment = new PropertyListFragment();
        chatFragment = new ChatFragment();
        favoriteFragment = new FavoriteFragment();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(propertyListFragment, "ALL");
        adapter.addFrag(chatFragment, "APARTMENT");
        adapter.addFrag(favoriteFragment, "LAND");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        spinner_type.setVisibility(View.VISIBLE);
                        tv_title.setVisibility(View.GONE);
                        break;
                    case 1:
                        spinner_type.setVisibility(View.GONE);
                        tv_title.setVisibility(View.VISIBLE);
                        chatFragment.getList();
                        break;
                    case 2:
                        spinner_type.setVisibility(View.GONE);
                        tv_title.setVisibility(View.VISIBLE);
                        favoriteFragment.callAPI();
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void passList(List<PropertyPOJO> propertyPOJOS) {
        if (propertyListFragment != null) {
            propertyListFragment.listMapMarkers(propertyPOJOS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment != null) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
