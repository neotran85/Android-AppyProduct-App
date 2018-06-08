package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.variant;


import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;
import com.appyhome.appyproduct.mvvm.databinding.FragmentProductVariantBinding;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductVariantBinding;
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

    private View mSelectedVariantView;

    private ProductDetailVariantNavigator mDetailNavigator;

    private int widthVariantItem = 0;

    private int mVariantCount = 0;

    private int mMaxScroll = 0;

    public static ProductVariantFragment newInstance(long productId, String selectedVariantModelId) {
        Bundle args = new Bundle();
        ProductVariantFragment fragment = new ProductVariantFragment();
        fragment.setArguments(args);
        args.putString("product_id", productId + "");
        args.putString("sent_variant_id", selectedVariantModelId);
        return fragment;
    }

    public void setDetailNavigator(ProductDetailVariantNavigator navigator) {
        mDetailNavigator = navigator;
    }

    private String getSentVariantModelId() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            return bundle.getString("sent_variant_id", "");
        }
        return "";
    }

    private int getProductId() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            return Integer.valueOf(bundle.getString("product_id", "0"));
        }
        return 0;
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    @Override
    public void showProductVariants(RealmResults<ProductVariant> result) {
        mBinder.llContainer.removeAllViews();
        if (result != null && result.size() > 0) {
            mVariantCount = result.size();
            mMaxScroll = (mVariantCount * widthVariantItem);
            for (int i = 0; i < result.size(); i++) {
                ProductVariant variant = result.get(i);
                View view = createVariantView(variant);
                mBinder.llContainer.addView(view);
            }
            String sentVariant = getSentVariantModelId();
            if (sentVariant.length() > 0) {
                selectVariant(sentVariant);
            } else {
                selectVariant(result.first().model_id);
            }
        }
        mDetailNavigator.showedVariants(result);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp();
    }

    private void setUp() {
        widthVariantItem = getResources().getDimensionPixelSize(R.dimen.variant_width_with_padding);
        mViewModel.setNavigator(this);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mViewModel);
        mBinder.setNavigator(this);
        getViewModel().fetchProductVariant(getProductId());
    }

    public void selectVariant(String variantModelId) {
        int count = mBinder.llContainer.getChildCount();
        for (int i = 0; i < count; i++) {
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
                getViewModel().selectedVariantName.set("Selected Variant: " + variant.variant_name);
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
    public void nextVariantView() {
        float currentPos = mBinder.scrollView.getScrollX();
        int currentIndex = Math.round(currentPos / widthVariantItem);
        int pos = (currentIndex + 1) * widthVariantItem;
        pos = pos > mMaxScroll ? mMaxScroll : pos;
        mBinder.scrollView.scrollTo(pos, 0);
    }

    @Override
    public void previousVariantView() {
        float currentPos = mBinder.scrollView.getScrollX();
        int currentIndex = Math.round(currentPos / widthVariantItem);
        int pos = (currentIndex - 1) * widthVariantItem;
        pos = pos > 0 ? pos : 0;
        mBinder.scrollView.scrollTo(pos, 0);
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

    private View createVariantView(ProductVariant item) {
        ViewItemProductVariantBinding binding = ViewItemProductVariantBinding.inflate(getLayoutInflater(), null, false);
        binding.setData(item);
        binding.getRoot().setTag(item);
        binding.setNavigator(this);
        return binding.getRoot();
    }
}
