package com.appyhome.appyproduct.mvvm.ui.common.sample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;

import java.util.ArrayList;

public abstract class SampleAdapter extends RecyclerView.Adapter<BaseViewHolder> implements View.OnClickListener {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_LOADING = -1;

    protected ArrayList<BaseViewModel> mItems;

    @Override
    public void onClick(View view) {

    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return getContentHolder(parent);
            case VIEW_TYPE_EMPTY:
                return getEmptyHolder(parent);
            case VIEW_TYPE_LOADING:
                return getLoadingHolder(parent);
            default:
                return getDefaultHolder(parent);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        holder.onBind(position);
    }

    private BaseViewHolder getDefaultHolder(ViewGroup parent) {
        return new BaseViewHolder(parent) {
            @Override
            public void onBind(int position) {
                // Do nothing
            }
        };
    }

    protected abstract BaseViewHolder getContentHolder(ViewGroup parent);

    protected BaseViewHolder getLoadingHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View loadingView = inflater.inflate(R.layout.view_item_sample_loading, parent, false);
        return new SampleItemLoadingViewHolder(loadingView);
    }

    protected BaseViewHolder getEmptyHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View view = inflater.inflate(R.layout.view_item_sample_empty, parent, false);
        return new SampleItemEmptyViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (mItems == null) {
            return VIEW_TYPE_LOADING; // loading item
        }
        if (mItems.size() == 0) {
            return VIEW_TYPE_EMPTY; // empty item
        }
        return VIEW_TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        if (mItems != null && mItems.size() > 0) {
            return mItems.size();
        }
        return 1;
    }

    public class SampleItemEmptyViewHolder extends BaseViewHolder {
        private SampleItemEmptyViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBind(int position) {
        }
    }

    public class SampleItemLoadingViewHolder extends BaseViewHolder {
        private SampleItemLoadingViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBind(int position) {
        }
    }
}