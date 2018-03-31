package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appyhome.appyproduct.mvvm.AppConstants;
import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductListBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.ProductDetailActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.ProductDetailActivityModule;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemFilterNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.filter.FilterFragment;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.sort.SortFragment;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.sort.SortNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.sort.SortOption;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.ui.common.component.cart.SearchToolbarViewHolder;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.realm.RealmResults;

public class ProductListActivity extends BaseActivity<ActivityProductListBinding, ProductListViewModel> implements HasSupportFragmentInjector, ProductListNavigator, ProductItemFilterNavigator, SortNavigator {
    public static final int ID_DEFAULT_SUB = 138;
    public static final int DEFAULT_SPAN_COUNT = 2;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    @Inject
    ProductListViewModel mViewModel;

    ActivityProductListBinding mBinder;

    @Inject
    ProductAdapter mProductAdapter;

    private ArrayList<Integer> mFavoritesId;

    private SearchToolbarViewHolder mSearchToolbarViewHolder;

    private SortFragment mSortFragment;

    private boolean mIsUsingSmallItem = false;

    /************************* LIFE RECYCLE METHODS ************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mViewModel);
        mBinder.setNavigator(this);
        mViewModel.setNavigator(this);
        ViewUtils.setUpRecyclerViewList(mBinder.productsRecyclerView, false);
        mBinder.productsRecyclerView.setAdapter(mProductAdapter);
        mSearchToolbarViewHolder = new SearchToolbarViewHolder(this, mBinder.toolbar, true, true);
        mViewModel.getAllFavorites();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ProductDetailActivityModule.clickedViewModel = null;
    }

    @Override
    public void onFragmentClosed() {
        mBinder.llSortFilterContainer.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSearchToolbarViewHolder.onBind(0);
        mProductAdapter.notifyDataSetChanged();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    /************************* SETUP  ************************/

    private void fetchProducts() {
        mViewModel.fetchProductsByIdCategory(getIdSubCategory());
    }

    private void setUpRecyclerViewGrid(RecyclerView rv) {
        int columns = calculateSubColumns();
        rv.setLayoutManager(new GridLayoutManager(this,
                columns, GridLayoutManager.VERTICAL,
                false));
        rv.setItemAnimator(new DefaultItemAnimator());
    }

    /************************* GET METHODS ************************/

    @Override
    public BaseViewModel getMainViewModel() {
        return mViewModel;
    }

    private int getIdSubCategory() {
        int idSubCategory = getIntent().getIntExtra("id_sub", ID_DEFAULT_SUB);
        return idSubCategory;
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ProductListActivity.class);
        return intent;
    }

    @Override
    public ProductListViewModel getViewModel() {
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ProductListViewModel.class);
        return mViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_list;
    }

    /************************* USER INTERACTION METHODS ************************/

    @Override
    public void onItemClick(View view) {
        ProductItemViewModel viewModel = (ProductItemViewModel) view.getTag();
        Intent intent = ProductDetailActivity.getStartIntent(this, viewModel);
        intent.putExtra("product_id", viewModel.getProductId());
        startActivity(intent);
    }

    /************************* NAVIGATOR METHODS ************************/

    @Override
    public void applyFilter() {
        getViewModel().getAllProductsWithFilter();
        getViewModel().getCurrentFilter();
    }

    @Override
    public void clearFragment() {
        closeFragment(FilterFragment.TAG);
        closeFragment(SortFragment.TAG);
    }

    @Override
    public void editFilter() {
        toggleFilters();
    }

    @Override
    public void resetFilter() {
        getViewModel().resetFilter();
    }

    @Override
    public void addedToCartCompleted() {

    }

    @Override
    public void notifyFavoriteChanged(int position, boolean isFavorite) {
        mProductAdapter.notifyItemChanged(position);
    }

    @Override
    public void showFragment(BaseFragment fragment, String tag, int idContainer) {
        clearFragment();
        mBinder.llSortFilterContainer.setVisibility(View.VISIBLE);
        super.showFragment(fragment, tag, idContainer, true);
    }

    @Override
    public void closeFragment(String tag) {
        mBinder.llSortFilterContainer.setVisibility(View.GONE);
        super.closeFragment(tag, true);
    }

    @Override
    public void onSortItemClick(View view) {
        if (mSortFragment != null) {
            SortOption option = (SortOption) view.getTag();
            getViewModel().saveSortCurrent(option);
            toggleSortOptions();
            getViewModel().fetchProductsByIdCategory(getIdSubCategory());
        }
    }

    @Override
    public void toggleSortOptions() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(SortFragment.TAG);
        boolean isShowed = fragment != null;
        if (isShowed) {
            closeFragment(SortFragment.TAG);
            mSortFragment = null;
        } else {
            mSortFragment = SortFragment.newInstance(this);
            showFragment(mSortFragment, SortFragment.TAG, R.id.llSortFilterContainer);
        }
        getViewModel().updateSortCurrentLabel();
        getViewModel().isSortShowed.set(!isShowed);
    }

    @Override
    public void toggleFilters() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FilterFragment.TAG);
        boolean isShowed = fragment != null;
        if (isShowed) {
            closeFragment(FilterFragment.TAG);
        } else {
            FilterFragment vFilterFragment = FilterFragment.newInstance();
            vFilterFragment.setNavigator(this);
            showFragment(vFilterFragment, FilterFragment.TAG, R.id.llSortFilterContainer);
        }
    }

    @Override
    public void updateCartCount() {
        mSearchToolbarViewHolder.onBind(0);
    }

    @Override
    public void updateFavorites(ArrayList<Integer> listId) {
        mFavoritesId = listId;
        fetchProducts();
    }

    @Override
    public void updatedFilterCount() {
        mSearchToolbarViewHolder.onBind(0);
    }

    @Override
    public void showProducts(RealmResults<Product> result) {
        if (result != null && result.size() > 0) {
            setUpRecyclerViewGrid(mBinder.productsRecyclerView);
            mProductAdapter.setUseSmallLayoutItem(mIsUsingSmallItem);
            mProductAdapter.addItems(result, this, mFavoritesId);
            mProductAdapter.notifyDataSetChanged();
            getViewModel().getCurrentFilter();
        }
    }

    @Override
    public void showEmptyProducts() {
        ViewUtils.setUpRecyclerViewList(mBinder.productsRecyclerView, false);
        mProductAdapter.addItems(new Product[]{}, this, mFavoritesId);
        mProductAdapter.notifyDataSetChanged();
        getViewModel().getCurrentFilter();
    }

    @Override
    public void showProducts(Product[] list) {
        if (list != null && list.length > 0) {
            setUpRecyclerViewGrid(mBinder.productsRecyclerView);
            mProductAdapter.setUseSmallLayoutItem(mIsUsingSmallItem);
            mProductAdapter.addItems(list, this, mFavoritesId);
            mProductAdapter.notifyDataSetChanged();
            getViewModel().getCurrentFilter();
        }
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
    public void removeFragment(String tag) {
        closeFragment(tag);
    }

    private int calculateSubColumns() {
        int widthScreen = AppConstants.SCREEN_WIDTH;
        int padding = getResources().getDimensionPixelSize(R.dimen.padding_product_in_list);
        int space = widthScreen - 2 * padding;
        int widthItem = getResources().getDimensionPixelSize(R.dimen.width_thumbnail_product_in_list) + 4 * padding;
        int value = Math.round(space / widthItem);
        if (value < DEFAULT_SPAN_COUNT) {
            widthItem = getResources().getDimensionPixelSize(R.dimen.width_thumbnail_product_in_list_small) + 4 * padding;
            value = Math.round(space / widthItem);
            mIsUsingSmallItem = true;
        }
        return value;
    }

}
