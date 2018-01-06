package com.appyhome.appyproduct.mvvm.ui.feed.opensource;


public class OpenSourceEmptyItemViewModel {

    private OpenSourceEmptyItemViewModelListener mListener;

    public OpenSourceEmptyItemViewModel(OpenSourceEmptyItemViewModelListener listener) {
        this.mListener = listener;
    }

    public void onRetryClick() {
        mListener.onRetryClick();
    }

    public interface OpenSourceEmptyItemViewModelListener {
        void onRetryClick();
    }
}
