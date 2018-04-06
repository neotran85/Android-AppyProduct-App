package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.variant;


import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;
import com.appyhome.appyproduct.mvvm.databinding.FragmentProductVariantBinding;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductVariantBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.ProductDetailNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.ProductDetailVariantNavigator;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;

import javax.inject.Inject;

import io.realm.RealmResults;

public class ProductVariantFragment extends BaseFragment<FragmentProductVariantBinding, ProductVariantViewModel> implements ProductVariantNavigator {

    public static final String TAG = "ProductVariantFragment";

    @Inject
    ProductVariantViewModel mViewModel;

    FragmentProductVariantBinding mBinder;

    @Inject
    int mLayoutId;

    private int mProductId;

    private View mSelectedVariantView;

    private ProductDetailVariantNavigator mDetailNavigator;

    private int mTotalStock = 0;

    public void setDetailNavigator(ProductDetailVariantNavigator navigator) {
        mDetailNavigator = navigator;
    }

    public int getTotalStock() {
        return mTotalStock;
    }

    public static ProductVariantFragment newInstance(int productId) {
        Bundle args = new Bundle();
        ProductVariantFragment fragment = new ProductVariantFragment();
        fragment.setArguments(args);
        fragment.setProductId(productId);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    @Override
    public void showProductVariants(RealmResults<ProductVariant> result) {
        if (result != null && result.size() == 1) {
            ProductVariant variant = result.first();
            View view = createVariantView(variant);
            mBinder.llContainer.addView(view);
            mTotalStock = variant.quantity;
            selectVariant(view);
        } else {
            for (ProductVariant variant : result) {
                View view = createVariantView(variant);
                mBinder.llContainer.addView(view);
                mTotalStock = mTotalStock + variant.quantity;
            }
        }
        if (mDetailNavigator != null) {
            mDetailNavigator.showedVariants();
        }
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
        getViewModel().fetchProductVariant(mProductId);
    }

    public void selectVariant(String variantModelId) {
        int count = mBinder.llContainer.getChildCount();
        for(int i = 0; i < count; i++) {
            View view = mBinder.llContainer.getChildAt(i);
            if (view.getTag() instanceof ProductVariant) {
                ProductVariant variant = (ProductVariant) view.getTag();
                if (variant.model_id.equals(variantModelId)) {
                    selectVariant(view);
                }
            }
        }
    }

    @Override
    public void selectVariant(View view) {
        if (mSelectedVariantView != view) {
            if (mSelectedVariantView != null)
                mSelectedVariantView.findViewById(R.id.tvVariantName).setBackgroundColor(ContextCompat.getColor(getContext(), R.color.dark_gray));
            view.findViewById(R.id.tvVariantName).setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            mSelectedVariantView = view;
            if (mSelectedVariantView.getTag() instanceof ProductVariant) {
                ProductVariant variant = (ProductVariant) mSelectedVariantView.getTag();
                getViewModel().selectedVariantName.set(variant.variant_name);
                if (mDetailNavigator != null)
                    mDetailNavigator.selectedVariant(variant);
            }
        }
    }

    public ProductVariant getSelectedVariant() {
        if (mSelectedVariantView != null) {
            return (ProductVariant) mSelectedVariantView.getTag();
        }
        return null;
    }

    @Override
    public ProductVariantViewModel getViewModel() {
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

    public int getProductId() {
        return mProductId;
    }

    public void setProductId(int id) {
        this.mProductId = id;
    }

    private View createVariantView(ProductVariant item) {
        ViewItemProductVariantBinding binding = ViewItemProductVariantBinding.inflate(getLayoutInflater(), null, false);
        binding.setData(item);
        binding.getRoot().setTag(item);
        binding.setNavigator(this);
        return binding.getRoot();
    }
}
