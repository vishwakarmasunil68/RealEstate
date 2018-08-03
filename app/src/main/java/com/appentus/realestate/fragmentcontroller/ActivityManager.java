package com.appentus.realestate.fragmentcontroller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;

import java.util.ArrayList;
import java.util.List;

public class ActivityManager extends LocalizationActivity {
    List<Fragment> fragmentList=new ArrayList<>();
    public void startFragment(int id,Fragment fragment) {
        fragmentList.add(fragment);
        getSupportFragmentManager().beginTransaction()
                .add(id, fragment)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }
    public void startReplaceFragment(int id,Fragment fragment) {
        fragmentList.add(fragment);
        getSupportFragmentManager().beginTransaction()
                .replace(id, fragment)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }
    //
    public void startFragmentForResult(int id, Fragment startingFragment, Fragment targetFragment, int requestCode){
        fragmentList.add(targetFragment);
        getSupportFragmentManager().beginTransaction()
                .add(id, targetFragment)
                .addToBackStack(targetFragment.getClass().getSimpleName())
                .commit();

        FragmentController fragmentController = (FragmentController) targetFragment;
        fragmentController.setUpStartingFragment(startingFragment,requestCode);
    }

    public void popFragment(){
        fragmentList.remove(fragmentList.size()-1);
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.popBackStack();
    }

    public void popBackResultFragment(Fragment startingFragment, int requestCode, int resultCode, Bundle data){
        fragmentList.remove(fragmentList.size()-1);
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.popBackStack();

        if(startingFragment!=null){
            FragmentController fragmentController = (FragmentController) startingFragment;
            fragmentController.onFragmentResult(requestCode,resultCode,data);
        }
    }

    public void clearAllFragments(){
        for(Fragment fragment:fragmentList){
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.popBackStack();
        }

        fragmentList.clear();
    }
}
