package com.appentus.realestate.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appentus.realestate.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeaderView extends LinearLayout {

    public HeaderView(Context context) {
        super(context);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }
//
//    public void bindTo(String name, String lastSeen) {
//        this.name.setText(name);
//        this.lastSeen.setText(lastSeen);
//    }
//
//    public void setTextSize(float size) {
//        name.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
//    }
}