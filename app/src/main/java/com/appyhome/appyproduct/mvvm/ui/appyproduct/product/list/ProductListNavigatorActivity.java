package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appyhome.appyproduct.mvvm.AppConstants;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductListBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.ProductDetailActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemFilterNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.filter.FilterFragment;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.sort.SortFragment;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.sort.SortNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.sort.SortOption;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;
import com.appyhome.appyproduct.mvvm.ui.common.component.cart.SearchToolbarViewHolder;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import java.util.ArrayList;

import dagger.android.support.HasSupportFragmentInjector;
import io.realm.OrderedRealmCollection;

public abstract class ProductListNavigatorActivity extends BaseActivity<ActivityProductListBinding, ProductListViewModel> implements HasSupportFragmentInjector, ProductListNavigator, ProductItemFilterNavigator, SortNavigator {

    public static final int DEFAULT_SPAN_COUNT = 2;

    protected boolean mIsUsingSmallItem = false;

    abstract ProductAdapter getProductAdapter();

    abstract SortFragment getSortFragment();

    abstract void setSortFragment(SortFragment fragment);

    abstract SearchToolbarViewHolder getSearchToolbarViewHolder();

    abstract void setFavoriteIds(ArrayList<Integer> listId);

    abstract ArrayList<Integer> getFavoriteIds();

    private int mColumns = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mColumns = calculateSubColumns();
        getProductAdapter().setUseSmallLayoutItem(mIsUsingSmallItem);
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
        getProductAdapter().notifyItemChanged(position);
    }

    @Override
    public void showFragment(BaseFragment fragment, String tag, int idContainer) {
        clearFragment();
        getViewDataBinding().llSortFilterContainer.setVisibility(View.VISIBLE);
        super.showFragment(fragment, tag, idContainer, true);
    }

    @Override
    public void closeFragment(String tag) {
        getViewDataBinding().llSortFilterContainer.setVisibility(View.GONE);
        super.closeFragment(tag, true);
    }

    @Override
    public void onSortItemClick(View view) {
        if (getSortFragment() != null) {
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
            setSortFragment(null);
        } else {
            setSortFragment(SortFragment.newInstance(this));
            showFragment(getSortFragment(), SortFragment.TAG, R.id.llSortFilterContainer);
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
        getSearchToolbarViewHolder().onBind(0);
    }

    @Override
    public void getAllFavorites_Done(ArrayList<Integer> listId) {
        setFavoriteIds(listId);
        fetchProducts();
    }

    @Override
    public void updatedFilterCount() {
        getSearchToolbarViewHolder().onBind(0);
    }

    @Override
    public void showProducts(OrderedRealmCollection<Product> result) {
        if (result != null && result.size() > 0) {
            boolean isFirstShowed = getViewModel().isFirstLoaded();
            if (isFirstShowed) {
                setUpRecyclerViewGrid(getViewDataBinding().productsRecyclerView);
                getViewModel().setIsFirstLoaded(false);
                getProductAdapter().addItems(result, this, getFavoriteIds());
                getProductAdapter().notifyDataSetChanged();
                closeLoading();
            } else { // LOAD MORE
                int rangeStart = getProductAdapter().getItemCount();
                int itemCount = result.size() - rangeStart;
                getProductAdapter().addItems(result, this, getFavoriteIds());
                getProductAdapter().notifyItemRangeInserted(rangeStart, itemCount);
                closeLoading();
            }
        } else {
            if (getViewModel().isIsAbleToLoadMore()) {
                fetchMore();
            } else showEmptyProducts();
        }
    }

    @Override
    public void showEmptyProducts() {
        ViewUtils.setUpRecyclerViewList(getViewDataBinding().productsRecyclerView, false);
        getProductAdapter().addItems(null, this, getFavoriteIds());
        getProductAdapter().notifyDataSetChanged();
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

    @Override
    public void showLoading() {
        AlertManager.getInstance(this).showLoading();
    }

    @Override
    public void closeLoading() {
        AlertManager.getInstance(this).closeLoading();
    }

    /************************* RECYCLER VIEW SETUP  ************************/

    @Override
    public void setUpRecyclerViewGrid(RecyclerView rv) {
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
                    int childCount = getProductAdapter().getItemCount();
                    if (lastVisiblePosition == childCount - 1) {
                        fetchMore();
                    }
                }
            }
        });
    }


    @Override
    public void onItemClick(View view) {
        ProductItemViewModel viewModel = (ProductItemViewModel) view.getTag();
        Intent intent = ProductDetailActivity.getStartIntent(this, viewModel);
        intent.putExtra("product_id", viewModel.getProductId());
        startActivity(intent);
    }
}
