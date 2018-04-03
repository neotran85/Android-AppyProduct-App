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
import io.realm.OrderedRealmCollection;

public class ProductListActivity extends BaseActivity<ActivityProductListBinding, ProductListViewModel> implements HasSupportFragmentInjector, ProductListNavigator, ProductItemFilterNavigator, SortNavigator {
    public static final int ID_SUB_EMPTY = -1;
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

    private int mColumns = 0;

    /************************* LIFE RECYCLE METHODS ************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mViewModel);
        mBinder.setNavigator(this);
        mViewModel.setNavigator(this);
        mColumns = calculateSubColumns();
        ViewUtils.setUpRecyclerViewList(mBinder.productsRecyclerView, false);
        mBinder.productsRecyclerView.setAdapter(mProductAdapter);
        mSearchToolbarViewHolder = new SearchToolbarViewHolder(this, mBinder.toolbar, true, true);
        fetchProductsNew();
    }

    private void fetchProductsNew() {
        // CLEARED PRODUCTS LOADED BEFORE
        getViewModel().resetPageNumber();
        getViewModel().setIsAbleToLoadMore(false);
        getViewModel().clearProductsLoaded();
    }

    @Override
    public void clearProductsLoaded_Done() {
        getViewModel().getAllFavorites();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ProductDetailActivityModule.clickedViewModel = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        getViewModel().emptyProductsLoaded();
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

    @Override
    public void restartFetching() {
        getViewModel().resetPageNumber();
        getViewModel().setIsAbleToLoadMore(false);
        fetchProducts();
    }

    /************************* PRODUCTS SETUP  ************************/

    private void fetchProducts() {
        int categoryId = getIdSubCategory();
        if (categoryId != ID_SUB_EMPTY) {
            getViewModel().fetchProductsByCommand(new Integer(categoryId));
        } else {
            String keyword = getKeywordString();
            if (keyword != null && keyword.length() > 0) {
                getViewModel().fetchProductsByCommand(keyword);
            }
        }
    }

    private void setUpRecyclerViewGrid(RecyclerView rv) {
        if (getViewModel().isFirstLoaded()) {
            GridLayoutManager layoutManager = new GridLayoutManager(this,
                    mColumns, GridLayoutManager.VERTICAL,
                    false);
            rv.setLayoutManager(layoutManager);
            rv.setItemAnimator(new DefaultItemAnimator());
            rv.clearOnScrollListeners();
            rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (getViewModel().isIsAbleToLoadMore()) {
                        int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
                        int childCount = mProductAdapter.getItemCount();
                        if (lastVisiblePosition == childCount - 1) {
                            getViewModel().setIsAbleToLoadMore(false);
                            getViewModel().increasePageNumber();
                            fetchProducts();
                        }
                    }
                }
            });
        }
    }

    /************************* GET METHODS ************************/

    @Override
    public BaseViewModel getMainViewModel() {
        return mViewModel;
    }

    private int getIdSubCategory() {
        int idSubCategory = getIntent().getIntExtra("id_sub", ID_SUB_EMPTY);
        return idSubCategory;
    }

    private String getKeywordString() {
        return getIntent().getStringExtra("keyword");
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
        showLoading();
        restartFetching();
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
        showLoading();
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
            showLoading();
            SortOption option = (SortOption) view.getTag();
            getViewModel().saveSortCurrent(option);
            toggleSortOptions();
            fetchProductsNew();
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
    public void getAllFavorites_Done(ArrayList<Integer> listId) {
        mFavoritesId = listId;
        fetchProducts();
    }

    @Override
    public void updatedFilterCount() {
        mSearchToolbarViewHolder.onBind(0);
    }

    @Override
    public void showProducts(OrderedRealmCollection<Product> result) {
        if (result != null && result.size() > 0) {
            setUpRecyclerViewGrid(mBinder.productsRecyclerView);
            mProductAdapter.setUseSmallLayoutItem(mIsUsingSmallItem);
            if (getViewModel().isFirstLoaded()) {
                mProductAdapter.addItems(result, this, mFavoritesId);
                mProductAdapter.notifyDataSetChanged();
            } else { // LOAD MORE
                int rangeStart = mProductAdapter.getItemCount();
                int itemCount = result.size() - rangeStart;
                mProductAdapter.addItems(result, this, mFavoritesId);
                mProductAdapter.notifyItemRangeInserted(rangeStart, itemCount);
            }
        }
        closeLoading();
    }

    @Override
    public void showEmptyProducts() {
        ViewUtils.setUpRecyclerViewList(mBinder.productsRecyclerView, false);
        mProductAdapter.addItems(null, this, mFavoritesId);
        mProductAdapter.notifyDataSetChanged();
        closeLoading();
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

    @Override
    public void showLoading() {
        AlertManager.getInstance(this).showLoading();
    }

    @Override
    public void closeLoading() {
        AlertManager.getInstance(this).closeLoading();
    }

}
