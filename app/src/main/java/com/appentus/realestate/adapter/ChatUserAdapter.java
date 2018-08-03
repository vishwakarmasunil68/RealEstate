package com.appentus.realestate.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appentus.realestate.R;
import com.appentus.realestate.activity.ChatActivity;
import com.appentus.realestate.pojo.ChatMsgPOJO;
import com.appentus.realestate.utils.Constants;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunil on 03-11-2017.
 */

public class ChatUserAdapter extends RecyclerView.Adapter<ChatUserAdapter.ViewHolder>{
    private List<ChatMsgPOJO> items;
    Activity activity;
    Fragment fragment;

    public ChatUserAdapter(Activity activity, Fragment fragment, List<ChatMsgPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_chat_users, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_chat.setText(items.get(position).getMsg());
        holder.tv_time.setText(items.get(position).getTime());

//        if(items.get(position).getUserDetailPOJO().getUserName()!=null) {
            holder.tv_user_name.setText(items.get(position).getUserDetailPOJO().getUserName());
//        }
        holder.frame_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, ChatActivity.class);
                intent.putExtra("fri_id",items.get(position).getUserDetailPOJO().getUserId());
                intent.putExtra("user_name",items.get(position).getUserDetailPOJO().getUserName());
                activity.startActivity(intent);
            }
        });

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_chat;
        TextView tv_user_name;
        TextView tv_time;
        CircleImageView cv_user_profile;
        FrameLayout frame_user;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_chat=itemView.findViewById(R.id.tv_chat);
            tv_user_name=itemView.findViewById(R.id.tv_user_name);
            tv_time=itemView.findViewById(R.id.tv_time);
            cv_user_profile=itemView.findViewById(R.id.cv_user_profile);
            frame_user=itemView.findViewById(R.id.frame_user);
        }
    }
}
