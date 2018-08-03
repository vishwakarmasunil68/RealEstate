package com.appentus.realestate.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appentus.realestate.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder>{
    private List<String> items;
    Activity activity;
    Fragment fragment;

    public GalleryAdapter(Activity activity, Fragment fragment, List<String> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_gallery_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Glide.with(activity.getApplicationContext())
                .load(items.get(position))
                .placeholder(R.drawable.ic_default_pic)
                .error(R.drawable.ic_default_pic)
                .dontAnimate()
                .into(holder.iv_image);

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_image;
        ImageView iv_delete;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_image=itemView.findViewById(R.id.iv_image);
            iv_delete=itemView.findViewById(R.id.iv_delete);
        }
    }

}
