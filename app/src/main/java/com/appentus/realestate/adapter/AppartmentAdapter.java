package com.appentus.realestate.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.appentus.realestate.R;
import com.appentus.realestate.activity.HomeActivity;
import com.appentus.realestate.fragment.PropertyViewFragment;
import com.appentus.realestate.pojo.PropertyPOJO;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class AppartmentAdapter extends RecyclerView.Adapter<AppartmentAdapter.ViewHolder>{
    private List<PropertyPOJO> items;
    Activity activity;
    Fragment fragment;

    public AppartmentAdapter(Activity activity, Fragment fragment, List<PropertyPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_appartment_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.ll_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activity instanceof HomeActivity){
                    HomeActivity homeActivity= (HomeActivity) activity;
                    PropertyViewFragment propertyViewFragment=new PropertyViewFragment();
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("propertyPOJO",items.get(position));

                    propertyViewFragment.setArguments(bundle);

                    homeActivity.startFragment(R.id.frame_home,propertyViewFragment);
                }
            }
        });

        if(items.get(position).getAttchment_file_path()!=null){
            Glide.with(activity.getApplicationContext())
                    .load(items.get(position).getAttchment_file_path())
                    .placeholder(R.drawable.ic_default_pic)
                    .error(R.drawable.ic_default_pic)
                    .dontAnimate()
                    .into(holder.iv_property);
        }

        holder.tv_property_name.setText(items.get(position).getPropertyLocation());
        holder.tv_price.setText(items.get(position).getPropertyPrice()+" "+items.get(position).getPrice());
        holder.tv_facilities.setText(items.get(position).getRoomCount()+" rooms, "+items.get(position).getToiletCount()+" toilets");
        holder.tv_size.setText(items.get(position).getPropertySize()+" "+items.get(position).getSize());

        if(items.get(position).getAdditionalNote()!=null){
            holder.tv_description.setText(items.get(position).getAdditionalNote());
        }
        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_property;
        TextView tv_property_name;
        TextView tv_price;
        TextView tv_description;
        TextView tv_facilities;
        TextView tv_size;
        ImageView iv_property;
        public ViewHolder(View itemView) {
            super(itemView);
            ll_property=itemView.findViewById(R.id.ll_property);
            tv_property_name=itemView.findViewById(R.id.tv_property_name);
            tv_price=itemView.findViewById(R.id.tv_price);
            tv_description=itemView.findViewById(R.id.tv_description);
            tv_facilities=itemView.findViewById(R.id.tv_facilities);
            tv_size=itemView.findViewById(R.id.tv_size);
            iv_property=itemView.findViewById(R.id.iv_property);
        }
    }
}
