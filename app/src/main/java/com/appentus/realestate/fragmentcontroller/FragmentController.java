package com.appentus.realestate.fragmentcontroller;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import butterknife.ButterKnife;

public abstract class FragmentController extends Fragment {

    public ActivityManager activityManager;
    public Fragment startingFragment;
    public int requestCode;


    public void setUpStartingFragment(Fragment startingFragment, int requestCode){
        this.startingFragment=startingFragment;
        this.requestCode=requestCode;
    }

    public void setUpView(Activity activity, final Fragment fragment, View view) {
        activityManager= (ActivityManager) activity;
        ButterKnife.bind(fragment,view);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i(FragmentController.class.getSimpleName(), "keyCode: " + keyCode);
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.i(FragmentController.class.getSimpleName(), "onKey Back listener is working!!!");
                    FragmentController fragmentController = (FragmentController) fragment;
                    fragmentController.onBackPressed();
                    return true;
                }
                return false;
            }
        });
    }

    public void onBackPressed(){
        activityManager.onBackPressed();
    }
    public void onFragmentResult(int requestCode, int resultCode, Bundle data){

    }

}
