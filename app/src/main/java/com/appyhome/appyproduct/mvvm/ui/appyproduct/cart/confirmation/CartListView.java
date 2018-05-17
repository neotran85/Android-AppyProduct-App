package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.common.component.HorizontalListView;

import javax.annotation.Nullable;

public class CartListView extends HorizontalListView {

    public CartListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public View getItemView(int position) {
        BaseViewHolder viewHolder = mAdapter.getContentHolder(this);
        viewHolder.onBind(position);
        return viewHolder.itemView;
    }
}
