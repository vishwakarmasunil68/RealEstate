package com.appentus.realestate.adapter;

import android.app.Activity;
import android.content.Context;
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
import com.appentus.realestate.fragment.Moview;
import com.appentus.realestate.fragment.PropertyViewFragment;
import com.appentus.realestate.pojo.PropertyPOJO;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class PaidPromotionAdapter extends RecyclerView.Adapter<PaidPromotionAdapter.ViewHolder>{
    private List<Moview> movieList;
    private List<String> promotion;
   Context mContext;
    public PaidPromotionAdapter(List<Moview> movieList) {
        this.movieList= movieList;
//        this.promotion= promotion;
//        this.mContext=mContext;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.paid_promotion_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_property_name.setText(movieList.get(position).getTitle());
        holder.tv_price.setText(movieList.get(position).getGenre());

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_property_name;
        TextView tv_price;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_property_name=itemView.findViewById(R.id.tv_property_name);
            tv_price=itemView.findViewById(R.id.tv_price);
        }
    }
}
