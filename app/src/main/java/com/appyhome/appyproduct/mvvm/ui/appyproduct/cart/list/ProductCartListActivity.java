package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductCartListBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.variant.EditVariantFragment;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.ShippingAddressActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class ProductCartListActivity extends ProductCartListNavigatorActivity
        implements HasSupportFragmentInjector {

    @Inject
    ProductCartListViewModel mViewModel;

    @Inject
    ProductCartAdapter mProductCartAdapter;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    ActivityProductCartListBinding mBinder;

    private EditVariantFragment mEditVariantFragment;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ProductCartListActivity.class);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_cart_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mViewModel);
        mBinder.setNavigator(this);
        mViewModel.setNavigator(this);
        mProductCartAdapter.setMainViewModel(mViewModel);
        mBinder.cartRecyclerView.setAdapter(mProductCartAdapter);
        setUpEmptyRecyclerViewList(mBinder.cartRecyclerView);
        getViewModel().getAllProductCarts();
        getViewModel().fetchShippingAddresses();
        getViewModel().fetchAndSyncCartsServer();
    }

    @Override
    public ProductCartAdapter getAdapter() {
        return mProductCartAdapter;
    }

    @Override
    public EditVariantFragment getEditVariantFragment() {
        return mEditVariantFragment;
    }

    @Override
    public void setEditVariantFragment(EditVariantFragment fragment) {
        mEditVariantFragment = fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setUpEmptyRecyclerViewList(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mProductCartAdapter.recycle();
    }

    @Override
    public ProductCartListViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_DETAIL) {
            getViewModel().getAllProductCarts();
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void onFetchUserInfo_Done() {
        getViewModel().getAllProductCarts();
        if (isLoadingShowed()) {
            closeLoading();
            showAlert(getString(R.string.pls_cart_need_updated));
        }
    }

    @Override
    public void onFetchUserInfo_Failed() {
        closeLoading();
    }

    @Override
    public void verifyOrder_FAILED(String message) {
        showLoading();
        getViewModel().fetchAndSyncCartsServer();
    }


    @Override
    public void verifyOrder_PASSED() {
        closeLoading();
        if (isEditMode()) {
            finish();
        } else {
            Intent intent = ShippingAddressActivity.getStartIntent(this);
            startActivity(intent);
        }
    }
}
