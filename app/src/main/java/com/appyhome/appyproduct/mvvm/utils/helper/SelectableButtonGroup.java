package com.appyhome.appyproduct.mvvm.utils.helper;

import android.view.View;
import android.widget.Button;

public class SelectableButtonGroup implements View.OnClickListener {
    private Button[] mViews;
    private int mCurrent = -1;
    private int mHighLight;
    private int mNormal;
    private int mHighLightColor;
    private int mNormalColor;

    public SelectableButtonGroup(int highlightResourceId, int normalResourceId,
                                 int highlightColor, int normalColor, Button... views) {
        mViews = views;
        mHighLight = highlightResourceId;
        mNormal = normalResourceId;
        mHighLightColor = highlightColor;
        mNormalColor = normalColor;
        ViewUtils.setOnClickListener(this, mViews);
    }

    @Override
    public void onClick(View view) {
        setCurrent((Button) view);
    }

    public void setCurrent(String text) {
        if (text.length() <= 0) {
            if (mCurrent >= 0) {
                getCurrent().setBackgroundResource(mNormal);
                getCurrent().setTextColor(mNormalColor);
            }
            mCurrent = -1;
            return;
        }
        for (Button button : mViews) {
            if (button.getText().equals(text)) {
                setCurrent(button);
                return;
            }
        }
    }

    private int getPosition(Button view) {
        for (int i = 0; i < mViews.length; i++) {
            if (mViews[i] == view)
                return i;
        }
        return -1;
    }

    private Button getCurrent() {
        return mViews[mCurrent];
    }

    public void setCurrent(Button view) {
        int pos = getPosition(view);
        if (pos >= 0) {
            if (mCurrent >= 0) {
                getCurrent().setBackgroundResource(mNormal);
                getCurrent().setTextColor(mNormalColor);
            }
            view.setBackgroundResource(mHighLight);
            view.setTextColor(mHighLightColor);
            mCurrent = pos;
        }
    }

    public String getCurrentValue() {
        if (mCurrent >= 0 && mCurrent < mViews.length)
            return mViews[mCurrent].getText().toString();
        return "";
    }

}
