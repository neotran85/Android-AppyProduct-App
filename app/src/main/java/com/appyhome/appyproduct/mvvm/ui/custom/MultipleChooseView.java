package com.appyhome.appyproduct.mvvm.ui.custom;

import android.support.v4.content.ContextCompat;
import android.view.View;

import com.appyhome.appyproduct.mvvm.R;

import java.util.ArrayList;

public class MultipleChooseView implements View.OnClickListener{
    private ArrayList<View> mArrayItems;
    private View.OnClickListener mListener;

    public MultipleChooseView(View... items) {
        mArrayItems = new ArrayList<>();
        for(int i = 0; i < items.length; i++) {
            mArrayItems.add(items[i]);
            items[i].setOnClickListener(this);
        }
    }
    public void setOnClickListener(View.OnClickListener listener) {
        mListener = listener;
    }
    @Override
    public void onClick(View view) {
        if (view != null) {
            Boolean booleanValue = (Boolean) view.getTag(R.id.selected);
            if (booleanValue == null || !booleanValue) {
                view.setBackgroundResource(R.drawable.view_rounded_bg_orange);
                booleanValue = true;
            } else {
                view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.white));
                booleanValue = false;
            }
            view.setTag(R.id.selected, booleanValue);
            if(mListener!= null) mListener.onClick(view);
        }
    }
    public ArrayList<String> getSelectedStringValue() {
        ArrayList<String> arrayList = new ArrayList<>();
        for(View item: mArrayItems) {
            Boolean booleanValue = (Boolean) item.getTag(R.id.selected);
            if (booleanValue != null && booleanValue) {
                arrayList.add(item.getTag().toString());
            }
        }
        return arrayList;
    }
}