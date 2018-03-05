package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductListBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.ProductCartListActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

import io.realm.RealmResults;

public class ProductListActivity extends BaseActivity<ActivityProductListBinding, ProductListViewModel> implements ProductListNavigator, ProductItemNavigator, TabLayout.OnTabSelectedListener {
    @Inject
    ProductListViewModel mViewModel;

    ActivityProductListBinding mBinder;

    @Inject
    ProductAdapter mProductAdapter;

    private static final int TAB_SORT = 0;
    private static final int TAB_FILTER = 1;
    private int mIdSubCategory;
    public static final int ID_DEFAULT_SUB = 138;
    public static final int DEFAULT_SPAN_COUNT = 2;


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ProductListActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mViewModel);
        mBinder.setNavigator(this);
        mViewModel.setNavigator(this);
        mIdSubCategory = getIntent().getIntExtra("id_sub", ID_DEFAULT_SUB);
        mViewModel.fetchProductsByIdCategory(mIdSubCategory);
        mBinder.tabLayout.setVisibility(View.GONE);
        setUpTabLayout(mBinder.tabLayout);
        setUpRecyclerViewList(mBinder.productsRecyclerView, mProductAdapter);
    }

    @Override
    public void openProductCart() {
        Intent intent = ProductCartListActivity.getStartIntent(this);
        startActivity(intent);
    }

    private void setUpRecyclerViewList(RecyclerView rv, ProductAdapter adapter) {
        rv.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
    }

    private void setUpTabLayout(TabLayout tabs) {
        tabs.addOnTabSelectedListener(this);
        tabs.getTabAt(TAB_SORT).setCustomView(R.layout.view_item_product_tab_sort);
        tabs.getTabAt(TAB_FILTER).setCustomView(R.layout.view_item_product_tab_filter);
    }

    private void setUpRecyclerViewGrid(RecyclerView rv) {
        rv.setLayoutManager(new GridLayoutManager(this,
                DEFAULT_SPAN_COUNT, GridLayoutManager.VERTICAL,
                false));
        rv.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void handleErrorService(Throwable throwable) {
        AlertManager.getInstance(this).showLongToast(getString(R.string.error_unknown));
    }

    @Override
    public void showAlert(String message) {
        AlertManager.getInstance(this).showLongToast(message);
    }

    @Override
    public ProductListViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public void showProducts(RealmResults<Product> result) {
        if (result != null && result.size() > 0) {
            setUpRecyclerViewGrid(mBinder.productsRecyclerView);
            mProductAdapter.addItems(result, this);
            mProductAdapter.notifyDataSetChanged();
            toggleTabLayout(result.size());
        }
    }

    private void toggleTabLayout(int countProduct) {
        if (countProduct > 1) {
            mBinder.tabLayout.setVisibility(View.VISIBLE);
        } else mBinder.tabLayout.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyProducts() {
        mProductAdapter.addItems(new Product[]{}, this);
        mProductAdapter.notifyDataSetChanged();
        mBinder.tabLayout.setVisibility(View.GONE);
    }

    @Override
    public void showProducts(Product[] list) {
        if (list != null && list.length > 0) {
            setUpRecyclerViewGrid(mBinder.productsRecyclerView);
            mProductAdapter.addItems(list, this);
            mProductAdapter.notifyDataSetChanged();
            toggleTabLayout(list.length);
        }
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_list;
    }

    @Override
    public void showContent(ProductAdapter adapter, View view, int idProduct) {
        mViewModel.getProductById(idProduct);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.updateTotalCountProductCart();
    }
    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
