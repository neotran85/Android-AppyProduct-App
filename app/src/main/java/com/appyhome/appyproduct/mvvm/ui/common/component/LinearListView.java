package com.appyhome.appyproduct.mvvm.ui.common.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import javax.annotation.Nullable;

public abstract class LinearListView<T> extends LinearLayout {

    private ArrayList<T> mListItem;

    public LinearListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public void setAdapter(ArrayList<T> listItems) {
        mListItem = listItems;
        notifyAdapter();
    }

    public int getItemCount() {
        return mListItem != null ? mListItem.size() : 0;
    }

    public void notifyAdapter() {
        if (mListItem != null && mListItem.size() > 0) {
            for (int i = 0; i < mListItem.size(); i++) {
                View view = getItemView(i);
                this.addView(view);
            }
        }
    }

    public T getItem(int i) {
        return mListItem != null && mListItem.size() > 0 ? mListItem.get(i) : null;
    }

    public abstract View getItemView(int position);

}
