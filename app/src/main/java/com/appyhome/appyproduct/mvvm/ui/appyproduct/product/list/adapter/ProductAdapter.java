package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductBinding;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductListEmptyBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;

import java.util.ArrayList;

import io.realm.OrderedRealmCollection;
import io.realm.RealmResults;

public class ProductAdapter extends SampleAdapter<Product, ProductItemNavigator> {

    private ProductItemNavigator mNavigator;
    private ProductItemEmptyViewModel mViewModelEmpty;

    public ProductAdapter() {
        this.mItems = null;
    }

    private boolean mUseSmallLayout = false;

    @Override
    public void onClick(View view) {
    }

    public void setUseSmallLayoutItem(boolean value) {
        mUseSmallLayout = value;
    }

    public void addItems(OrderedRealmCollection<Product> results, ProductItemNavigator navigator, ArrayList<Integer> favoritesId) {
        mItems = new ArrayList<>();
        mNavigator = navigator;
        mViewModelEmpty = createEmptyViewModel(navigator);
        if (results != null) {
            for (Product item : results) {
                boolean isFavorite = checkIfFavorite(item.id, favoritesId);
                mItems.add(createViewModel(item, navigator, isFavorite));
            }
        }
    }

    public void addMoreItems(OrderedRealmCollection<Product> results, ArrayList<Integer> favoritesId) {
        if (results != null) {
            for (Product item : results) {
                boolean isFavorite = checkIfFavorite(item.id, favoritesId);
                mItems.add(createViewModel(item, mNavigator, isFavorite));
            }
        }
    }

    private ProductItemEmptyViewModel createEmptyViewModel(ProductItemNavigator navigator) {
        BaseViewModel viewModel = navigator.getMainViewModel();
        ProductItemEmptyViewModel viewModelEmpty = new ProductItemEmptyViewModel(viewModel.getDataManager(), viewModel.getSchedulerProvider());
        viewModelEmpty.setNavigator(navigator);
        return viewModelEmpty;
    }

    private boolean checkIfFavorite(int id, ArrayList<Integer> listId) {
        if (listId == null || listId.size() <= 0) return false;
        for (Integer item : listId) {
            if (item.equals(id))
                return true;
        }
        return false;
    }

    @Override
    public void recycle() {
        mItems.clear();
        mItems = null;
        mViewModelEmpty = null;
    }

    @Override
    protected int getEmptyItemLayout() {
        return R.layout.view_item_sample_empty;
    }

    private ProductItemViewModel createViewModel(Product product, ProductItemNavigator navigator, boolean isAllFavorited) {
        BaseViewModel viewModel = navigator.getMainViewModel();
        ProductItemViewModel itemViewModel = new ProductItemViewModel(viewModel.getDataManager(), viewModel.getSchedulerProvider());
        itemViewModel.setNavigator(navigator);
        itemViewModel.isSmall.set(mUseSmallLayout);
        itemViewModel.inputValue(product, isAllFavorited);
        return itemViewModel;
    }

    @Override
    public void addItems(RealmResults<Product> results, ProductItemNavigator navigator) {
        mItems = new ArrayList<>();
        mNavigator = navigator;
        mViewModelEmpty = createEmptyViewModel(navigator);
        if (results != null) {
            for (Product item : results) {
                mItems.add(createViewModel(item, navigator, false));
            }
        }
    }

    public void addItems(RealmResults<Product> results, ProductItemNavigator navigator, ArrayList<Integer> favoritesId) {
        mItems = new ArrayList<>();
        mNavigator = navigator;
        mViewModelEmpty = createEmptyViewModel(navigator);
        if (results != null) {
            for (Product item : results) {
                boolean isFavorite = checkIfFavorite(item.id, favoritesId);
                mItems.add(createViewModel(item, navigator, isFavorite));
            }
        }
    }

    public void addItems(Product[] results, ProductItemNavigator navigator, boolean isAllFavorited) {
        mItems = new ArrayList<>();
        mNavigator = navigator;
        mViewModelEmpty = createEmptyViewModel(navigator);
        if (results != null) {
            for (Product item : results) {
                mItems.add(createViewModel(item, navigator, isAllFavorited));
            }
        }
    }

    @Override
    protected BaseViewHolder getEmptyHolder(ViewGroup parent) {
        ViewItemProductListEmptyBinding itemViewBinding = ViewItemProductListEmptyBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new EmptyProductItemViewHolder(itemViewBinding);
    }

    @Override
    protected ProductItemViewHolder getContentHolder(ViewGroup parent) {
        ViewItemProductBinding itemViewBinding = ViewItemProductBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductItemViewHolder(itemViewBinding);
    }

    public class EmptyProductItemViewHolder extends BaseViewHolder {

        private ViewItemProductListEmptyBinding mBinding;

        private EmptyProductItemViewHolder(ViewItemProductListEmptyBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (mNavigator instanceof ProductItemFilterNavigator) {
                ProductItemFilterNavigator nav = (ProductItemFilterNavigator) mNavigator;
                mBinding.setNavigator(nav);
                mBinding.setViewModel(mViewModelEmpty);
                mViewModelEmpty.getCurrentFilter();
            }
        }
    }

    public class ProductItemViewHolder extends BaseViewHolder {

        private ViewItemProductBinding mBinding;

        private ProductItemViewHolder(ViewItemProductBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.tvOriginalPrice.setPaintFlags(mBinding.tvOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        public ViewItemProductBinding getBinding() {
            return mBinding;
        }

        private View.OnClickListener getListener() {
            return v -> {
                switch (v.getId()) {
                    case R.id.ibAddFavorite:
                        ProductItemViewModel vm = (ProductItemViewModel) v.getTag();
                        vm.updateProductFavorite(mItems.indexOf(vm));
                        break;
                }
            };
        }

        @Override
        public void onBind(int position) {
            ProductItemViewModel viewModel = (ProductItemViewModel) mItems.get(position);
            if (mBinding != null) {
                mBinding.setViewModel(viewModel);
                mBinding.getRoot().setTag(viewModel);
                viewModel.setNavigator(mNavigator);
                mBinding.setNavigator(mNavigator);
                View.OnClickListener listener = getListener();
                mBinding.ibAddFavorite.setTag(viewModel);
                mBinding.ibAddFavorite.setOnClickListener(listener);
            }
        }
    }
}