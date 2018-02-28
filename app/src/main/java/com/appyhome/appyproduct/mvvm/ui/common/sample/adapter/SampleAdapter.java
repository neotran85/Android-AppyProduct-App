package com.appyhome.appyproduct.mvvm.ui.common.sample.adapter;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Sample;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductBinding;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemSampleBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;

import java.util.ArrayList;

import io.realm.RealmResults;

public class SampleAdapter extends RecyclerView.Adapter<BaseViewHolder> implements View.OnClickListener {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_LOADING = -1;
    private ArrayList<SampleItemViewModel> mItems;
    public SampleAdapter(ArrayList<SampleItemViewModel> arrayList) {
        this.mItems = arrayList;
    }

    @Override
    public void onClick(View view) {

    }

    public void addItems(Sample[] results, SampleItemNavigator navigator) {
        mItems = new ArrayList<>();
        if (results != null) {
            for (Sample item : results) {
                mItems.add(createViewModel(item, navigator));
            }
        }
    }

    private SampleItemViewModel createViewModel(Sample product, SampleItemNavigator navigator) {
        SampleItemViewModel itemViewModel = new SampleItemViewModel();
        return itemViewModel;
    }

    public void addItems(RealmResults<Sample> results, SampleItemNavigator navigator) {
        mItems = new ArrayList<>();
        if (results != null) {
            for (Sample item : results) {
                mItems.add(createViewModel(item, navigator));
            }
        }
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

    private SampleItemViewHolder getContentHolder(ViewGroup parent) {
        ViewItemSampleBinding itemViewBinding = ViewItemSampleBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SampleItemViewHolder(itemViewBinding);
    }

    private SampleItemLoadingViewHolder getLoadingHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View loadingView = inflater.inflate(R.layout.view_item_sample_loading, parent, false);
        return new SampleItemLoadingViewHolder(loadingView);
    }

    private SampleItemEmptyViewHolder getEmptyHolder(ViewGroup parent) {
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

    public class SampleItemViewHolder extends BaseViewHolder {

        private ViewItemSampleBinding mBinding;

        public ViewItemSampleBinding getBinding() {
            return mBinding;
        }

        private SampleItemViewHolder(ViewItemSampleBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            SampleItemViewModel viewModel = mItems.get(position);
            if (mBinding != null) {
                mBinding.setViewModel(viewModel);
                mBinding.llItemView.setTag(mBinding.getViewModel());
                mBinding.llItemView.setOnClickListener(SampleAdapter.this);
            }
        }
    }
}