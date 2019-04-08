package com.example.root.planmanager;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by root on 19-4-7.
 */

public class ItemImageItemsOpt extends android.support.v7.widget.AppCompatImageView {

    private Integer plan_id = -1;
    private Integer plan_state = -1;

    public ItemImageItemsOpt(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Integer getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(Integer plan_id) {
        this.plan_id = plan_id;
    }

    public ItemImageItemsOpt(Context context) {
        super(context);
    }

    public Integer getPlan_state() {
        return plan_state;
    }

    public void setPlan_state(Integer plan_state) {
        this.plan_state = plan_state;
    }

    public ItemImageItemsOpt(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemImageItemsOpt);
        Integer plan_id = typedArray.getInteger(R.styleable.ItemImageItemsOpt_plan_id_image,36);
        Integer plan_state = typedArray.getInteger(R.styleable.ItemImageItemsOpt_plan_state_image,36);
        setPlan_id(plan_id);
        setPlan_state(plan_state);
    }
}
