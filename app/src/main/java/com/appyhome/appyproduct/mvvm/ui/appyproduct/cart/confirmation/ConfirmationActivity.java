package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductCartConfirmationBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation.adapter.CartAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.ProductCartListActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.payment.PaymentActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.ShippingAddressActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

import io.realm.RealmResults;

public class ConfirmationActivity extends BaseActivity<ActivityProductCartConfirmationBinding, ConfirmationViewModel> implements ConfirmationNavigator {

    ActivityProductCartConfirmationBinding mBinder;

    final int HEIGHT_CART_ITEM = 124;
    final int HEIGHT_TITLE_CART_ITEM = 40;
    @Inject
    public CartAdapter mAdapter;

    @Inject
    public ConfirmationViewModel mMainViewModel;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ConfirmationActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setNavigator(this);
        mBinder.setViewModel(mMainViewModel);
        mBinder.rvCartRecyclerView.setAdapter(mAdapter);
        setUpRecyclerViewList(mBinder.rvCartRecyclerView);
        mMainViewModel.setNavigator(this);
    }

    private void setUpRecyclerViewList(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rv.setNestedScrollingEnabled(false);
    }

    @Override
    public void editShippingAddress() {
        Intent intent = ShippingAddressActivity.getStartIntent(this);
        intent.putExtra("edit_mode", true);
        startActivity(intent);
    }

    @Override
    public void editPaymentMethod() {
        Intent intent = PaymentActivity.getStartIntent(this);
        intent.putExtra("edit_mode", true);
        startActivity(intent);
    }

    @Override
    public void editCart() {
        Intent intent = ProductCartListActivity.getStartIntent(this);
        intent.putExtra("edit_mode", true);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMainViewModel.getAllCheckedProductCarts();
        mMainViewModel.fetchPaymentMethods();
        mMainViewModel.getDefaultShippingAddress();
    }

    @Override
    public void showCheckedItems(RealmResults<ProductCart> result) {
        mAdapter.addItems(result);
        mAdapter.notifyDataSetChanged();
        updateCartContainerHeight(result.size());
    }

    private void updateCartContainerHeight(int count) {
        int height = count * HEIGHT_CART_ITEM + mAdapter.getTotalStores() * HEIGHT_TITLE_CART_ITEM;
        height = ViewUtils.dpToPx(height);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams ) mBinder.llCartContainer.getLayoutParams();
        params.height = height;
        mBinder.llCartContainer.setLayoutParams(params);
    }

    @Override
    public void gotoNextStep() {

    }

    @Override
    public ConfirmationViewModel getViewModel() {
        return mMainViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_cart_confirmation;
    }

    @Override
    public void showAlert(String message) {
        AlertManager.getInstance(this).showLongToast(message);
    }
}
