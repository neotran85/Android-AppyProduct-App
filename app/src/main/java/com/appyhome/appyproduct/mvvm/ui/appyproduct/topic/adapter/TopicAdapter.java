package com.appyhome.appyproduct.mvvm.ui.appyproduct.topic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductTopic;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductTopicBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;

import java.util.ArrayList;

import io.realm.RealmResults;

public class TopicAdapter extends SampleAdapter {

    public TopicAdapter() {
        this.mItems = null;
    }

    @Override
    public void onClick(View view) {
        Object tag = view.getTag();
        if (tag instanceof TopicItemViewModel) {
            TopicItemViewModel viewModel = (TopicItemViewModel) tag;
            viewModel.getNavigator().showContent(this, view, viewModel.getIdTopic());
        }
    }

    @Override
    public void recycle() {
        mItems.clear();
        mItems = null;
    }

    public void addItems(RealmResults<ProductTopic> results, TopicItemNavigator navigator) {
        mItems = new ArrayList<>();
        if (results != null) {
            for (ProductTopic item : results) {
                TopicItemViewModel itemViewModel = new TopicItemViewModel();
                itemViewModel.imageURL.set(item.thumbnail);
                itemViewModel.setIdTopic(item.id);
                itemViewModel.setNavigator(navigator);
                mItems.add(itemViewModel);
            }
        }
    }

    @Override
    protected TopicItemViewHolder getContentHolder(ViewGroup parent) {
        ViewItemProductTopicBinding itemViewBinding = ViewItemProductTopicBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TopicItemViewHolder(itemViewBinding);
    }

    @Override
    protected int getEmptyItemLayout() {
        return R.layout.view_item_product_cart_empty;
    }

    public class TopicItemViewHolder extends BaseViewHolder {

        private ViewItemProductTopicBinding mBinding;

        private TopicItemViewHolder(ViewItemProductTopicBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public ViewItemProductTopicBinding getBinding() {
            return mBinding;
        }

        public void setOnClickListener(View.OnClickListener listener) {
            if (mBinding != null) {
                mBinding.llItemView.setTag(mBinding.getViewModel());
                mBinding.llItemView.setOnClickListener(listener);
            }
        }

        public void setViewModel(TopicItemViewModel viewModel) {
            if (mBinding != null) {
                mBinding.setViewModel(viewModel);
            }
        }

        @Override
        public void onBind(int position) {
            TopicItemViewModel viewModel = (TopicItemViewModel) mItems.get(position);
            this.setViewModel(viewModel);
            this.setOnClickListener(TopicAdapter.this);
        }

    }
}