package com.appentus.realestate.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appentus.realestate.R;
import com.appentus.realestate.activity.HomeActivity;
import com.appentus.realestate.fragment.PropertyViewFragment;
import com.appentus.realestate.pojo.ChatMsgPOJO;
import com.appentus.realestate.pojo.PropertyPOJO;
import com.appentus.realestate.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{
    private List<ChatMsgPOJO> items;
    Activity activity;
    Fragment fragment;

    public ChatAdapter(Activity activity, Fragment fragment, List<ChatMsgPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_chat_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_msg.setText(items.get(position).getMsg());
        holder.tv_time.setText(items.get(position).getTime());

        if(Constants.userDetailPOJO.getUserId().equalsIgnoreCase(items.get(position).getFriId())){
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.FILL_PARENT);
            params.gravity = Gravity.LEFT;
            holder.ll_msg.setLayoutParams(params);
            holder.ll_msg.setBackgroundResource(R.drawable.et_msg_incoming_back);
        }else{
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.FILL_PARENT);
            params.gravity = Gravity.RIGHT;
            holder.ll_msg.setLayoutParams(params);
            holder.ll_msg.setBackgroundResource(R.drawable.et_msg_outgoing_back);
        }
        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_msg;
        TextView tv_time;
        LinearLayout ll_msg;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_msg=itemView.findViewById(R.id.tv_msg);
            tv_time=itemView.findViewById(R.id.tv_time);
            ll_msg=itemView.findViewById(R.id.ll_msg);
        }
    }
}
