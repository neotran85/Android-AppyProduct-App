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
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.ProductDetailActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.ProductDetailActivityModule;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.gallery.ProductGalleryActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.ui.common.component.cart.SearchToolbarViewHolder;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import java.util.ArrayList;

import javax.inject.Inject;

import io.realm.RealmResults;

public class ProductListActivity extends BaseActivity<ActivityProductListBinding, ProductListViewModel> implements ProductListNavigator, ProductItemNavigator, TabLayout.OnTabSelectedListener {
    public static final int ID_DEFAULT_SUB = 138;
    public static final int DEFAULT_SPAN_COUNT = 2;
    private static final int TAB_SORT = 0;
    private static final int TAB_FILTER = 1;

    @Inject
    ProductListViewModel mViewModel;

    ActivityProductListBinding mBinder;

    @Inject
    ProductAdapter mProductAdapter;

    @Inject
    int mLayoutId;

    private ArrayList<Integer> mFavoritesId;

    private SearchToolbarViewHolder mSearchToolbarViewHolder;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ProductListActivity.class);
        return intent;
    }

    @Override
    public BaseViewModel getMainViewModel() {
        return mViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mViewModel);
        mBinder.setNavigator(this);
        mViewModel.setNavigator(this);
        mBinder.tabLayout.setVisibility(View.GONE);
        setUpTabLayout(mBinder.tabLayout);
        setUpRecyclerViewList(mBinder.productsRecyclerView, mProductAdapter);
        mSearchToolbarViewHolder = new SearchToolbarViewHolder(this, mBinder.toolbar);
        mViewModel.getAllFavorites();
    }

    private void fetchProducts() {
        int idSubCategory = getIntent().getIntExtra("id_sub", ID_DEFAULT_SUB);
        mViewModel.fetchProductsByIdCategory(idSubCategory);
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
    public void updateFavorites(ArrayList<Integer> listId) {
        mFavoritesId = listId;
        fetchProducts();
    }

    @Override
    public void showProducts(RealmResults<Product> result) {
        if (result != null && result.size() > 0) {
            setUpRecyclerViewGrid(mBinder.productsRecyclerView);
            mProductAdapter.addItems(result, this, mFavoritesId);
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
        mProductAdapter.addItems(new Product[]{}, this, mFavoritesId);
        mProductAdapter.notifyDataSetChanged();
        mBinder.tabLayout.setVisibility(View.GONE);
    }

    @Override
    public void showProducts(Product[] list) {
        if (list != null && list.length > 0) {
            setUpRecyclerViewGrid(mBinder.productsRecyclerView);
            mProductAdapter.addItems(list, this, mFavoritesId);
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
        return mLayoutId;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {}

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ProductDetailActivityModule.clickedViewModel = null;
    }

    @Override
    public void onItemClick(View view) {
        ProductItemViewModel viewModel = (ProductItemViewModel) view.getTag();
        ProductDetailActivityModule.clickedViewModel = viewModel;
        Intent intent = ProductDetailActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void addedToCartCompleted() {

    }

    @Override
    public void notifyFavoriteChanged(int position, boolean isFavorite) {
        mProductAdapter.notifyItemChanged(position);
    }

    @Override
    public void onResume() {
        super.onResume();
        mProductAdapter.notifyDataSetChanged();
        mSearchToolbarViewHolder.onBind(0);
    }

    @Override
    public void updateCartCount() {
        mSearchToolbarViewHolder.onBind(0);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
