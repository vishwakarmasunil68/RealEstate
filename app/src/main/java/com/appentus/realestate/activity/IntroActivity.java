package com.appentus.realestate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appentus.realestate.R;
import com.appentus.realestate.adapter.ViewPagerAdapter;
import com.appentus.realestate.fragment.IntroFragment;
import com.appentus.realestate.fragmentcontroller.ActivityManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntroActivity extends ActivityManager {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.iv_dot1)
    ImageView iv_dot1;
    @BindView(R.id.iv_dot2)
    ImageView iv_dot2;
    @BindView(R.id.iv_dot3)
    ImageView iv_dot3;
    @BindView(R.id.tv_skip)
    TextView tv_skip;
    @BindView(R.id.tv_next)
    TextView tv_next;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);
        setUpViewPager();

        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tv_skip.getText().toString().equalsIgnoreCase("back")){
                    viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
                }else{
                    startActivity(new Intent(IntroActivity.this,LoginActivity.class));
                }
            }
        });

        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewPager.getCurrentItem()==2){
                    startActivity(new Intent(IntroActivity.this,LoginActivity.class));
                }else{
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                }
            }
        });

    }

    public void setUpViewPager() {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new IntroFragment(), "ALL");
        adapter.addFrag(new IntroFragment(), "APARTMENT");
        adapter.addFrag(new IntroFragment(), "LAND");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());
        changeDots(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void changeDots(int position) {
        iv_dot1.setImageResource(R.drawable.ic_smalldot);
        iv_dot2.setImageResource(R.drawable.ic_smalldot);
        iv_dot3.setImageResource(R.drawable.ic_smalldot);

        switch (position) {
            case 0:
                iv_dot1.setImageResource(R.drawable.ic_fulldot);
                tv_skip.setText("Skip");
                break;
            case 1:
                iv_dot2.setImageResource(R.drawable.ic_fulldot);
                tv_skip.setText("Back");
                break;
            case 2:
                iv_dot3.setImageResource(R.drawable.ic_fulldot);
                tv_skip.setText("Back");
                break;
        }

    }

}
