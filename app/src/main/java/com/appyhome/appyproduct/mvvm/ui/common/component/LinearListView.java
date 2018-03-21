package com.appyhome.appyproduct.mvvm.ui.common.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import javax.annotation.Nullable;

public abstract class LinearListView<T> extends LinearLayout {

    private T[] mListItem;

    public LinearListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public void setAdapter(T[] listItems) {
        mListItem = listItems;
        notifyAdapter();
    }

    public void notifyAdapter() {
        if (mListItem != null && mListItem.length > 0) {
            for (int i = 0; i < mListItem.length; i++) {
                View view = getItemView(i);
                this.addView(view);
            }
        }
    }

    public T getItem(int i) {
        return mListItem != null && mListItem.length > 0 ? mListItem[i] : null;
    }

    public abstract View getItemView(int position);

}
