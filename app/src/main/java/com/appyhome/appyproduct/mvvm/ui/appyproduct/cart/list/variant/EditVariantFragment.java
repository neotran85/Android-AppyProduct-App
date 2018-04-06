package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.variant;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
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


public class EditVariantFragment extends BaseFragment<FragmentProductVariantEditBinding, EditVariantViewModel> implements EditVariantNavigator, ProductDetailVariantNavigator {

    public static final String TAG = "EditVariantFragment";

    @Inject
    EditVariantViewModel mViewModel;

    FragmentProductVariantEditBinding mBinder;

    @Inject
    int mLayoutId;

    private ProductCartItemViewModel mProductCartItemViewModel;

    private ProductVariantFragment mProductVariantFragment;

    public static EditVariantFragment newInstance(ProductCartItemViewModel viewModel) {
        Bundle args = new Bundle();
        EditVariantFragment fragment = new EditVariantFragment();
        fragment.setArguments(args);
        fragment.setProductCartItemViewModel(viewModel);
        return fragment;
    }

    public void setProductCartItemViewModel(ProductCartItemViewModel viewModel) {
        mProductCartItemViewModel = cloneViewModel(viewModel, viewModel.getNavigator());
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
        mViewModel.setNavigator(this);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mViewModel);
        mBinder.setNavigator(this);
        mViewModel.setProductCartItemViewModel(mProductCartItemViewModel);
        showProductVariantFragment();
        showProductCartItem();
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

    private ProductCartItemViewModel cloneViewModel(ProductCartItemViewModel productCart, ProductCartItemNavigator navigator) {
        ProductCartItemViewModel itemViewModel = new ProductCartItemViewModel(productCart.getDataManager(), productCart.getSchedulerProvider());
        itemViewModel.title.set(productCart.title.get());
        itemViewModel.isFirstProductOfStore.set(true);
        itemViewModel.imageURL.set(productCart.imageURL.get());
        itemViewModel.setProductCartId(productCart.getProductCartId());
        itemViewModel.setProductId(productCart.getProductId());
        itemViewModel.sellerName.set(productCart.sellerName.get());
        itemViewModel.amount.set(productCart.amount.get());
        itemViewModel.price.set(productCart.price.get());
        itemViewModel.setNavigator(navigator);
        itemViewModel.checked.set(productCart.checked.get());
        //Variant Display
        itemViewModel.variationName.set(productCart.variationName.get());
        itemViewModel.setVariantModelId(productCart.getVariantModelId());
        itemViewModel.variantStock.set(productCart.variantStock.get());
        itemViewModel.setVariantStockNumber(productCart.getVariantStockNumber());
        return itemViewModel;
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
    public void confirmVariantChanges() {
        getViewModel().saveProductCartItem();
    }

    @Override
    public void showedVariants() {
        mProductVariantFragment.selectVariant(mProductCartItemViewModel.getVariantModelId());
    }
}
