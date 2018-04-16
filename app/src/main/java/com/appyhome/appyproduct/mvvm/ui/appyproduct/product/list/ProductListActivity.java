package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.appyhome.appyproduct.mvvm.AppConstants;
import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductListBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.category.CategoryFragment;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.common.component.cart.SearchToolbarViewHolder;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.ProductDetailActivityModule;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.sort.SortFragment;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class ProductListActivity extends ProductListNavigatorActivity implements HasSupportFragmentInjector {

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

    private CategoryFragment mCategoryFragment = null;

    /************************* LIFE RECYCLE METHODS ************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mColumns = calculateSubColumns();
        mColumns = mColumns > 1 ? mColumns : DEFAULT_SPAN_COUNT;
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mViewModel);
        mBinder.setNavigator(this);
        mViewModel.setNavigator(this);
        getProductAdapter().setUseSmallLayoutItem(mIsUsingSmallItem);
        ViewUtils.setUpRecyclerViewListVertical(mBinder.productsRecyclerView, false);
        mBinder.productsRecyclerView.setAdapter(mProductAdapter);
        mSearchToolbarViewHolder = new SearchToolbarViewHolder(this, mBinder.toolbar, true, true, getTitleSearch());
        getViewModel().isAbleToSelectCategories.set(getKeywordString() == null || getKeywordString().length() == 0);
        createCategoriesSelection();
        fetchProductsNew();
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
    public void onDestroy() {
        super.onDestroy();
        ProductDetailActivityModule.clickedViewModel = null;
    }

    @Override
    public void onStop() {
        super.onStop();
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

    /************************* PRODUCTS METHODS SETUP  ************************/

    @Override
    public void restartFetching() {
        getViewModel().resetPageNumber();
        getViewModel().setIsAbleToLoadMore(false);
        fetchProducts();
    }

    @Override
    public void fetchProductsNew() {
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
    public void fetchMore() {
        getViewModel().setIsAbleToLoadMore(false);
        getViewModel().increasePageNumber();
        fetchProducts();
    }

    @Override
    public void fetchProducts() {
        getViewModel().fetchProductsByCommand(getCategoryIds(), getKeywordString());
    }

    /************************* ABSTRACT METHODS ************************/

    @Override
    ProductAdapter getProductAdapter() {
        return mProductAdapter;
    }

    @Override
    SortFragment getSortFragment() {
        return mSortFragment;
    }

    @Override
    void setSortFragment(SortFragment fragment) {
        mSortFragment = fragment;
    }

    @Override
    SearchToolbarViewHolder getSearchToolbarViewHolder() {
        return mSearchToolbarViewHolder;
    }

    @Override
    void setFavoriteIds(ArrayList<Integer> listId) {
        mFavoritesId = listId;
    }

    @Override
    ArrayList<Integer> getFavoriteIds() {
        return mFavoritesId;
    }

    /************************* GET METHODS ************************/

    @Override
    public BaseViewModel getMainViewModel() {
        return mViewModel;
    }

    private String getSearchTopics() {
        if (getIntent().hasExtra("topics"))
            return getIntent().getStringExtra("topics");
        return "";
    }

    private String getKeywordString() {
        if (getIntent().hasExtra("keyword"))
            return getIntent().getStringExtra("keyword");
        return "";
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

    private String getCategoryIds() {
        Intent intent = getIntent();
        if (intent.hasExtra("categoryIds")) {
            return intent.getStringExtra("categoryIds");
        } else {
            getIntent().getStringExtra("id_subs");
            return getIntent().getStringExtra("id_subs");
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

    private String getTitleSearch() {
        String result = getKeywordString();
        if (getSearchTopics().length() > 0) {
            result = '"' + result + '"' + " in " + getSearchTopics();
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        if (getViewModel().isCategoriesSelectionShowed.get()) {
            applyCategoriesSelected(null);
        } else super.onBackPressed();
    }

    private void createCategoriesSelection() {
        if (mCategoryFragment == null) {
            mCategoryFragment = CategoryFragment.newInstance(this);
            addFragment(mCategoryFragment, CategoryFragment.TAG, R.id.llCategoriesSelection);
            toggleFragment(mCategoryFragment, false);
        }
    }

    @Override
    public void openCategoriesSelection() {
        if (mCategoryFragment != null) {
            toggleFragment(mCategoryFragment, true);
            getViewModel().isCategoriesSelectionShowed.set(true);
        }
    }

    @Override
    public void applyCategoriesSelected(String subsId) {
        getViewModel().isCategoriesSelectionShowed.set(false);
        toggleFragment(mCategoryFragment, false);
        if (subsId != null && subsId.length() > 0) {
            showLoading();
            getIntent().putExtra("id_subs", subsId);
            fetchProductsNew();
        }
    }

}
