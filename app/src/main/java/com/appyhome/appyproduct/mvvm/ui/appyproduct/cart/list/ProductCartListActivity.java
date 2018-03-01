package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductCartListBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

import io.realm.RealmResults;

public class ProductCartListActivity extends BaseActivity<ActivityProductCartListBinding, ProductCartListViewModel> implements ProductCartListNavigator, ProductCartItemNavigator {
    @Inject
    ProductCartListViewModel mViewModel;
    ActivityProductCartListBinding mBinder;

    private ProductCartAdapter mProductCartAdapter;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ProductCartListActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mViewModel);
        mViewModel.setNavigator(this);
        mProductCartAdapter = new ProductCartAdapter();
        setUpRecyclerViewList(mBinder.cartRecyclerView, mProductCartAdapter);
        mViewModel.getAllProductCarts("1234");
    }

    private void setUpRecyclerViewList(RecyclerView rv, ProductCartAdapter adapter) {
        rv.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
    }

    @Override
    public void handleErrorService(Throwable throwable) {
        AlertManager.getInstance(this).showLongToast(getString(R.string.error_network_general));
    }

    @Override
    public void showErrorServer() {
        showAlert(getString(R.string.login_error_internal_server));
    }

    @Override
    public void showErrorOthers() {
        showAlert(getString(R.string.login_error));
    }

    @Override
    public void showAlert(String message) {
        AlertManager.getInstance(this).showLongToast(message);
    }

    @Override
    public ProductCartListViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public void showContent(ProductCartAdapter adapter, View view, int idProduct) {

    }

    @Override
    public void showCart(RealmResults<ProductCart> result) {
        mProductCartAdapter.addItems(result, this);
        mProductCartAdapter.notifyDataSetChanged();
    }


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_cart_list;
    }
}
