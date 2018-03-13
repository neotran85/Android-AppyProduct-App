package com.appyhome.appyproduct.mvvm.ui.appyproduct.category.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductSub;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCategorySubBinding;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;

import java.util.ArrayList;

import io.realm.RealmResults;

public class SubCategoryAdapter extends SampleAdapter<ProductSub, CategoryItemNavigator> {

    private CategoryItemViewModel mCurrentClickedViewModel = null;

    public SubCategoryAdapter() {
        this.mItems = null;
    }

    @Override
    public void recycle() {
        if (mItems != null) {
            mItems.clear();
            mItems = null;
        }
        mCurrentClickedViewModel = null;
    }

    @Override
    public void onClick(View view) {
       // DO NOTHING
    }

    public void clickViewModel(CategoryItemViewModel viewModel) {
        if (mCurrentClickedViewModel != viewModel) {
            viewModel.isHighLight = true;
            if (mCurrentClickedViewModel != null) {
                mCurrentClickedViewModel.isHighLight = false;
                int positionOld = mItems.indexOf(mCurrentClickedViewModel);
                notifyItemChanged(positionOld);
            }
            int positionNew = mItems.indexOf(viewModel);
            notifyItemChanged(positionNew);
            mCurrentClickedViewModel = viewModel;
        }
    }

    @Override
    protected int getEmptyItemLayout() {
        return R.layout.view_item_sample_empty;
    }

    @Override
    protected SubCategoryItemViewHolder getContentHolder(ViewGroup parent) {
        ViewItemProductCategorySubBinding itemViewBinding = ViewItemProductCategorySubBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SubCategoryItemViewHolder(itemViewBinding, this);
    }

    @Override
    public void addItems(RealmResults<ProductSub> items, CategoryItemNavigator navigator) {
        mItems = new ArrayList<>();
        if (items != null) {
            for (ProductSub item : items) {
                CategoryItemViewModel itemViewModel = new CategoryItemViewModel();
                itemViewModel.title.set(item.name);
                itemViewModel.imageURL.set(item.thumbnail);
                itemViewModel.setIdCategory(item.id);
                itemViewModel.setNavigator(navigator);
                itemViewModel.isSub = true;
                mItems.add(itemViewModel);
            }
        }
    }
}