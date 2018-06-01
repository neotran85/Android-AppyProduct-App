package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductBinding;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductListEmptyBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

import io.realm.OrderedRealmCollection;
import io.realm.RealmResults;

public class ProductAdapter extends SampleAdapter<Product, ProductItemNavigator> {

    protected ProductItemNavigator mNavigator;
    protected ProductItemEmptyViewModel mViewModelEmpty;

    public ProductAdapter() {
        this.mItems = null;
    }

    public void addItems(OrderedRealmCollection<Product> results, ProductItemNavigator navigator) {
        mItems = new ArrayList<>();
        mNavigator = navigator;
        mViewModelEmpty = (ProductItemEmptyViewModel) createEmptyViewModel(navigator);
        if (results != null) {
            for (Product item : results) {
                mItems.add(createViewModel(item, navigator));
            }
        }
    }

    protected BaseViewModel createEmptyViewModel(ProductItemNavigator navigator) {
        BaseViewModel viewModel = navigator.getMainViewModel();
        ProductItemEmptyViewModel viewModelEmpty = new ProductItemEmptyViewModel(viewModel.getDataManager(), viewModel.getSchedulerProvider());
        viewModelEmpty.setNavigator(navigator);
        return viewModelEmpty;
    }

    public int getItemsSize() {
        return mItems != null ? mItems.size() : 0;
    }

    private boolean checkIfFavorite(long id, ArrayList<Long> listId) {
        if (listId == null || listId.size() <= 0) return false;
        for (Long item : listId) {
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

    protected BaseViewModel createViewModel(Product product, ProductItemNavigator navigator) {
        BaseViewModel viewModel = navigator.getMainViewModel();
        ProductItemViewModel itemViewModel = new ProductItemViewModel(viewModel.getDataManager(), viewModel.getSchedulerProvider());
        itemViewModel.setNavigator(navigator);
        itemViewModel.inputValue(product);
        return itemViewModel;
    }

    @Override
    public void addItems(RealmResults<Product> results, ProductItemNavigator navigator) {
        mItems = new ArrayList<>();
        mNavigator = navigator;
        mViewModelEmpty = (ProductItemEmptyViewModel) createEmptyViewModel(navigator);
        if (results != null) {
            for (Product item : results) {
                mItems.add(createViewModel(item, navigator));
            }
        }
    }

    public void addItems(Product[] results, ProductItemNavigator navigator) {
        mItems = new ArrayList<>();
        mNavigator = navigator;
        mViewModelEmpty = (ProductItemEmptyViewModel) createEmptyViewModel(navigator);
        if (results != null) {
            for (Product item : results) {
                mItems.add(createViewModel(item, navigator));
            }
        }
    }

    public void addItems(ArrayList<Product> results, ProductItemNavigator navigator) {
        mItems = new ArrayList<>();
        mNavigator = navigator;
        mViewModelEmpty = (ProductItemEmptyViewModel) createEmptyViewModel(navigator);
        if (results != null) {
            for (Product item : results) {
                mItems.add(createViewModel(item, navigator));
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
    public BaseViewHolder getContentHolder(ViewGroup parent) {
        ViewItemProductBinding itemViewBinding = ViewItemProductBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductItemViewHolder(itemViewBinding, mNavigator);
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

        public ProductItemViewHolder(ViewItemProductBinding binding, ProductItemNavigator navigator) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.setNavigator(navigator);
        }

        public ViewItemProductBinding getBinding() {
            return mBinding;
        }

        @Override
        public void onBind(int position) {
            ProductItemViewModel viewModel = (ProductItemViewModel) mItems.get(position);
            mBinding.setViewModel(viewModel);
        }
    }
}