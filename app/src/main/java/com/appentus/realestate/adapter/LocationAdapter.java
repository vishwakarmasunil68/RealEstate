package com.appentus.realestate.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.appentus.realestate.R;
import com.appentus.realestate.activity.CheckInActivity;
import com.appentus.realestate.pojo.location.NewLocationPOJO;

import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    private List<NewLocationPOJO> items;
    Activity activity;
    Fragment fragment;

    public LocationAdapter(Activity activity, Fragment fragment, List<NewLocationPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_location, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.location_name.setText(items.get(position).getMain_text());
        holder.tv_area_name.setText(items.get(position).getSecondary_text());
        holder.ll_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activity instanceof CheckInActivity){
                    CheckInActivity checkInActivity= (CheckInActivity) activity;
                    checkInActivity.setActivityLocation(items.get(position));
                }
            }
        });

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView location_name;
        public TextView tv_area_name;
        public LinearLayout ll_location;

        public ViewHolder(View itemView) {
            super(itemView);
            location_name=itemView.findViewById(R.id.location_name);
            tv_area_name=itemView.findViewById(R.id.tv_area_name);
            ll_location=itemView.findViewById(R.id.ll_location);
        }
    }
}
