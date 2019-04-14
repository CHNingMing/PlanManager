package com.example.root.planmanager;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * Created by root on 19-4-6.
 * 主要功能，通过此类配合控件实现自定义属性
 * 描述列表中其中一项
 */
/*
    extends FrameLayout
    extends ConstraintLayout    可以使用约束布局
* */
public class item_layout_itemsOpt extends ConstraintLayout {

    private Integer plan_id = -1;

    public Integer getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(Integer plan_id) {
        this.plan_id = plan_id;
    }

    public item_layout_itemsOpt(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemImageItemsOpt);
        Integer plan_id = typedArray.getInteger(R.styleable.ItemImageItemsOpt_plan_id_image,36);



    }

    public item_layout_itemsOpt(@NonNull Context context) {
        super(context);
    }
}
