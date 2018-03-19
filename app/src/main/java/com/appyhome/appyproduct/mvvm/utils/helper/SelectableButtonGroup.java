package com.appyhome.appyproduct.mvvm.utils.helper;

import android.view.View;
import android.widget.Button;

public class SelectableButtonGroup implements View.OnClickListener {
    private Button[] mViews;
    private int mCurrent = 0;
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
        mViews[0].setBackgroundResource(mHighLight);
        mViews[0].setTextColor(mHighLightColor);
        mCurrent = 0;
    }

    @Override
    public void onClick(View view) {
        setCurrent((Button) view);
    }

    public void setCurrent(String text) {
        for (Button button: mViews) {
            if(button.getText().equals(text)) {
                setCurrent(button);
                return;
            }
        }
    }

    public void setCurrent(Button view) {
        int pos = getPosition(view);
        if(pos >= 0) {
            getCurrent().setBackgroundResource(mNormal);
            getCurrent().setTextColor(mNormalColor);
            view.setBackgroundResource(mHighLight);
            view.setTextColor(mHighLightColor);
            mCurrent = pos;
        }
    }

    private int getPosition(Button view) {
        for (int i = 0; i < mViews.length; i++) {
            if (mViews[i] == view)
                return i;
        }
        return -1;
    }

    public Button getCurrent() {
        return mViews[mCurrent];
    }

}
