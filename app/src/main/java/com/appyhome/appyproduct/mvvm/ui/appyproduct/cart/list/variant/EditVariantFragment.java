package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.variant;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;
import com.appyhome.appyproduct.mvvm.databinding.FragmentProductVariantEditBinding;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCartItemBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartItemViewHolder;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.ProductDetailVariantNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.variant.ProductVariantFragment;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;

import javax.inject.Inject;


public class EditVariantFragment extends BaseFragment<FragmentProductVariantEditBinding, EditVariantViewModel> implements ProductDetailVariantNavigator, View.OnClickListener {

    public static final String TAG = "EditVariantFragment";

    @Inject
    EditVariantViewModel mViewModel;

    FragmentProductVariantEditBinding mBinder;

    @Inject
    int mLayoutId;

    private ProductCartItemViewModel mProductCartItemViewModel;

    private ProductVariantFragment mProductVariantFragment;

    private EditVariantNavigator mNavigator;

    public static EditVariantFragment newInstance(ProductCartItemViewModel viewModel, EditVariantNavigator navigator) {
        Bundle args = new Bundle();
        EditVariantFragment fragment = new EditVariantFragment();
        fragment.setNavigator(navigator);
        fragment.setArguments(args);
        fragment.setProductCartItemViewModel(viewModel);
        return fragment;
    }

    public void setNavigator(EditVariantNavigator navigator) {
        mNavigator = navigator;
    }

    public void setProductCartItemViewModel(ProductCartItemViewModel viewModel) {
        mProductCartItemViewModel = viewModel.cl);
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
        mViewModel.setProductCartItemViewModel(mProductCartItemViewModel);
        showProductVariantFragment();
        showProductCartItem();
        mBinder.btConfirm.setOnClickListener(this);
    }

    private void showProductVariantFragment() {
        mProductVariantFragment = ProductVariantFragment.newInstance(mProductCartItemViewModel.getProductId());
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
    public void selectedVariant(ProductVariant variant) {
        mProductCartItemViewModel.price.set("RM " + variant.price);
        mProductCartItemViewModel.variationName.set(variant.variant_name);
        mProductCartItemViewModel.setVariantModelId(variant.model_id);
        mProductCartItemViewModel.variantStock.set("(Stock: " + variant.quantity + ")");
        mProductCartItemViewModel.setVariantStockNumber(variant.quantity);
    }

    @Override
    public void onClick(View view) {
        getViewModel().saveProductCartItem();
    }

    @Override
    public void showedVariants() {
        mProductVariantFragment.selectVariant(mProductCartItemViewModel.getVariantModelId());
    }
}
