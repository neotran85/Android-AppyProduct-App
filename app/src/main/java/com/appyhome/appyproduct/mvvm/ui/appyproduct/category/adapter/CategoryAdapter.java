package com.appyhome.appyproduct.mvvm.ui.appyproduct.category.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCategory;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductSub;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCategoryBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;

import java.util.ArrayList;

import io.realm.RealmResults;

public class CategoryAdapter extends SampleAdapter {

    public static final int TYPE_CATEGORY = 0;
    public static final int TYPE_SUB_CATEGORY = 1;

    private int mType = TYPE_CATEGORY;
    private CategoryItemViewModel mCurrentClickedViewModel = null;

    public CategoryAdapter() {
        this.mItems = null;
    }

    @Override
    public void onClick(View view) {
        Object tag = view.getTag();
        if (tag instanceof CategoryItemViewModel) {
            CategoryItemViewModel viewModel = (CategoryItemViewModel) tag;
            if (isCategory()) {
                clickViewModel(viewModel);
            } else {
                viewModel.getNavigator().showContent(this, view, viewModel.getIdCategory());
            }
        }
    }

    private void clickViewModel(CategoryItemViewModel viewModel) {
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
            viewModel.getNavigator().showContent(this, null, viewModel.getIdCategory());
        }
    }

    public void clickTheFirstItem() {
        if (mItems != null && mItems.size() > 0) {
            CategoryItemViewModel viewModel = (CategoryItemViewModel) mItems.get(0);
            clickViewModel(viewModel);
        }
    }

    public void addItems(Object results, int type, CategoryItemNavigator navigator) {
        mType = type;
        if (type == TYPE_CATEGORY) {
            mItems = new ArrayList<>();
            if (results != null) {
                RealmResults<ProductCategory> temp = (RealmResults<ProductCategory>) results;
                for (ProductCategory item : temp) {
                    CategoryItemViewModel itemViewModel = new CategoryItemViewModel();
                    itemViewModel.title.set(item.name);
                    itemViewModel.imageURL.set(item.thumbnail);
                    itemViewModel.setIdCategory(item.id);
                    itemViewModel.setNavigator(navigator);
                    mItems.add(itemViewModel);
                }
            }
        }
        if (type == TYPE_SUB_CATEGORY) {
            mItems = new ArrayList<>();
            if (results != null) {
                RealmResults<ProductSub> temp = (RealmResults<ProductSub>) results;
                for (ProductSub item : temp) {
                    CategoryItemViewModel itemViewModel = new CategoryItemViewModel();
                    itemViewModel.title.set(item.name);
                    itemViewModel.imageURL.set(item.thumbnail);
                    itemViewModel.setIdCategory(item.id);
                    itemViewModel.setNavigator(navigator);
                    mItems.add(itemViewModel);
                }
            }
        }
    }

    @Override
    protected int getEmptyItemLayout() {
        return R.layout.view_item_sample_empty;
    }

    @Override
    protected CategoryItemViewHolder getContentHolder(ViewGroup parent) {
        ViewItemProductCategoryBinding itemViewBinding = ViewItemProductCategoryBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryItemViewHolder(itemViewBinding);
    }

    public boolean isSubCategory() {
        return mType == TYPE_SUB_CATEGORY;
    }

    public boolean isCategory() {
        return mType == TYPE_CATEGORY;
    }

    public class CategoryItemViewHolder extends BaseViewHolder {

        private ViewItemProductCategoryBinding mBinding;

        public ViewItemProductCategoryBinding getBinding() {
            return mBinding;
        }

        public void setOnClickListener(View.OnClickListener listener) {
            if (mBinding != null) {
                mBinding.llItemCategoryView.setTag(mBinding.getViewModel());
                mBinding.llItemCategoryView.setOnClickListener(listener);
            }
        }

        private CategoryItemViewHolder(ViewItemProductCategoryBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        private void createCategoryView() {
            View view = mBinding.llItemCategoryView;
            boolean isHighLight = mBinding.getViewModel().isHighLight;
            view.setBackgroundResource(isHighLight ? R.color.colorAccent : R.color.transparent);
            Context context = view.getContext();
            mBinding.tvTitleProduct.setTextColor(ContextCompat.getColor(context, isHighLight ? R.color.white : R.color.semi_gray));
            ViewGroup.LayoutParams params = mBinding.ivThumbnail.getLayoutParams();
            params.width = Math.round(context.getResources().getDimension(R.dimen.size_category_image));
            params.height = params.width;
            mBinding.ivThumbnail.setLayoutParams(params);
        }

        public void setViewModel(CategoryItemViewModel viewModel) {
            if (mBinding != null) {
                mBinding.setViewModel(viewModel);
                if (isCategory()) {
                    createCategoryView();
                }
            }
        }

        @Override
        public void onBind(int position) {
            CategoryItemViewModel viewModel = (CategoryItemViewModel) mItems.get(position);
            this.setViewModel(viewModel);
            this.setOnClickListener(CategoryAdapter.this);
        }
    }
}