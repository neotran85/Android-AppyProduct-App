package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.variant;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;
import com.appyhome.appyproduct.mvvm.databinding.FragmentProductVariantEditBinding;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCartItemBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartItemViewHolder;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.ProductDetailVariantNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.variant.ProductVariantFragment;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;

import javax.inject.Inject;

import io.realm.RealmResults;


public class EditVariantFragment extends BaseFragment<FragmentProductVariantEditBinding, EditVariantViewModel> implements ProductDetailVariantNavigator {

    public static final String TAG = "EditVariantFragment";

    @Inject
    EditVariantViewModel mViewModel;

    FragmentProductVariantEditBinding mBinder;

    @Inject
    int mLayoutId;

    private ProductCartItemViewModel mProductCartItemViewModel;

    private ProductVariantFragment mProductVariantFragment;

    private EditVariantNavigator mNavigator;

    private SparseIntArray mArrayAmountAdded;

    private long mOldVariantId = 0;

    public static EditVariantFragment newInstance(ProductCartItemViewModel viewModel, EditVariantNavigator navigator, long currentVariantId) {
        Bundle args = new Bundle();
        EditVariantFragment fragment = new EditVariantFragment();
        fragment.setNavigator(navigator);
        fragment.setArguments(args);
        fragment.setProductCartItemViewModel(viewModel, currentVariantId);
        return fragment;
    }

    public void setNavigator(EditVariantNavigator navigator) {
        mNavigator = navigator;
    }

    public void setProductCartItemViewModel(ProductCartItemViewModel viewModel, long currentVariantId) {
        mProductCartItemViewModel = viewModel.duplicate();
        mProductCartItemViewModel.isFirstProductOfStore.set(true);
        mProductCartItemViewModel.checked.set(true);
        mOldVariantId = currentVariantId;
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp();
    }

    private void setUp() {
        mViewModel.setNavigator(mNavigator);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mViewModel);
        mBinder.setNavigator(mNavigator);
        getViewModel().setProductCartItemViewModel(mProductCartItemViewModel);
        getViewModel().setOldVariantId(mOldVariantId);
        showProductVariantFragment();
        showProductCartItem();
    }

    private void showProductVariantFragment() {
        mProductVariantFragment = ProductVariantFragment.newInstance(mProductCartItemViewModel.getProductId(), mProductCartItemViewModel.getVariantModelId());
        mProductVariantFragment.setDetailNavigator(this);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.llProductVariant, mProductVariantFragment).commit();
    }

    private void showProductCartItem() {
        ViewItemProductCartItemBinding itemViewBinding = ViewItemProductCartItemBinding
                .inflate(LayoutInflater.from(getActivity()), mBinder.llProductCartItem, true);
        itemViewBinding.cbCheckAll.setVisibility(View.GONE);
        itemViewBinding.cbWillBuy.setVisibility(View.INVISIBLE);
        itemViewBinding.tvEdit.setVisibility(View.GONE);
        ProductCartItemViewHolder viewHolder = new ProductCartItemViewHolder(itemViewBinding, null, mProductCartItemViewModel.getNavigator());
        viewHolder.setViewModel(mProductCartItemViewModel);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mProductCartItemViewModel = null;
    }

    @Override
    public EditVariantViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void showedVariants(RealmResults<ProductVariant> variants) {
        mArrayAmountAdded = new SparseIntArray();
        for (ProductVariant variant : variants) {
            mArrayAmountAdded.put(variant.id, 0);
        }
    }

    @Override
    public void selectedVariant(ProductVariant variant) {
        getViewModel().setSelectedVariant(variant);
        mProductCartItemViewModel.update(variant);
        if (mNavigator != null) {
            mNavigator.onEditVariantSelected(variant);
        }
    }
}
