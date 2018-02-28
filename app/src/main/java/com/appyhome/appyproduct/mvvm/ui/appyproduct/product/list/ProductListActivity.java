package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductListBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.category.CategoryAdapter;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

import io.realm.RealmResults;

public class ProductListActivity extends BaseActivity<ActivityProductListBinding, ProductListViewModel> implements ProductListNavigator, ProductItemNavigator  {
    @Inject
    ProductListViewModel mViewModel;
    ActivityProductListBinding mBinder;

    private ProductAdapter mProductAdapter;

    private int mIdSubCategory;
    public static final int ID_DEFAULT_SUB = 138;
    public static final int  DEFAULT_SPAN_COUNT = 2;


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ProductListActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mViewModel);
        mViewModel.setNavigator(this);
        mIdSubCategory = getIntent().getIntExtra("id_sub", ID_DEFAULT_SUB);
        mViewModel.fetchProductsByIdCategory(mIdSubCategory);
        mProductAdapter = new ProductAdapter(null);
        setUpRecyclerViewGrid(mBinder.productsRecyclerView, mProductAdapter);
        setUpTabLayout(mBinder.tabLayout);
    }

    private void setUpTabLayout(TabLayout tabs) {
        tabs.getTabAt(0).setCustomView(R.layout.view_item_product_tab_sort);
        tabs.getTabAt(1).setCustomView(R.layout.view_item_product_tab_filter);
    }
    private void setUpRecyclerViewGrid(RecyclerView rv, ProductAdapter adapter) {
        rv.setLayoutManager(new GridLayoutManager(this,
                DEFAULT_SPAN_COUNT, GridLayoutManager.VERTICAL,
                false));
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
    public ProductListViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public void showProducts(RealmResults<Product> result) {
        mProductAdapter.addItems(result, this);
        mProductAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProducts(Product[] list) {
        mProductAdapter.addItems(list, this);
        mProductAdapter.notifyDataSetChanged();
    }

    @Override
    public void showContent(CategoryAdapter adapter, View view, int idCategory) {

    }


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_list;
    }
}
