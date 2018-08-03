package com.appentus.realestate.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appentus.realestate.R;
import com.appentus.realestate.pojo.AmenitiesPOJO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 03-11-2017.
 */

public class AmenitiesAdapter extends RecyclerView.Adapter<AmenitiesAdapter.ViewHolder>{
    private List<AmenitiesPOJO> items;
    Activity activity;
    Fragment fragment;
    List<CheckBox> checkBoxes=new ArrayList<>();
    public List<String> checkedPosition=new ArrayList<>();

    public AmenitiesAdapter(Activity activity, Fragment fragment, List<AmenitiesPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_anemities_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.checkbox.setText(items.get(position).getAmenities_name());

        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkedPosition.add(String.valueOf(position));
                }else{
                    if(checkedPosition.contains(String.valueOf(position))){
                        checkedPosition.remove(String.valueOf(position));
                    }
                }
            }
        });

//        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    for(CheckBox checkBox:checkBoxes){
//                        checkBox.setChecked(false);
//                    }
//
//                    holder.checkbox.setChecked(true);
//                }
//            }
//        });

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.checkbox)
        CheckBox checkbox;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
