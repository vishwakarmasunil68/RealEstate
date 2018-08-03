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
import com.appentus.realestate.pojo.CategoryPOJO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 03-11-2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{
    private List<CategoryPOJO> items;
    Activity activity;
    Fragment fragment;
    public List<CheckBox> checkBoxes=new ArrayList<>();

    public CategoryAdapter(Activity activity, Fragment fragment, List<CategoryPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_category_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        checkBoxes.add(holder.checkbox);

        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    for(CheckBox checkBox:checkBoxes){
                        checkBox.setChecked(false);
                    }

                    holder.checkbox.setChecked(true);
                }
            }
        });
        holder.tv_category.setText(items.get(position).getCategory_name().toUpperCase().toString());
        holder.ll_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.checkbox.setChecked(true);
            }
        });

        holder.checkbox.setTag(items.get(position).getCategory_id());

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.checkbox)
        CheckBox checkbox;
        @BindView(R.id.tv_category)
        TextView tv_category;
        @BindView(R.id.ll_category)
        LinearLayout ll_category;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
