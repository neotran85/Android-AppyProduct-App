package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductListBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.ProductDetailActivityModule;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.sort.SortFragment;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.ui.common.component.cart.SearchToolbarViewHolder;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class ProductListActivity extends ProductListNavigatorActivity implements HasSupportFragmentInjector{

    public static final int ID_SUB_EMPTY = -1;

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
        mSearchToolbarViewHolder = new SearchToolbarViewHolder(this, mBinder.toolbar, true, true, getTitleSearch());
        fetchProductsNew();
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

    private String getCategoryIdsForSearch() {
        Intent intent = getIntent();
        if (intent.hasExtra("categoryIds")) {
            return intent.getStringExtra("categoryIds");
        }
        return "";
    }

    @Override
    public void fetchMore() {
        getViewModel().setIsAbleToLoadMore(false);
        getViewModel().increasePageNumber();
        fetchProducts();
    }

    @Override
    public void fetchProducts() {
        int categoryId = getIdSubCategory();
        if (categoryId != ID_SUB_EMPTY) {
            getViewModel().fetchProductsByCommand(categoryId + "", "");
        } else {
            String keyword = getKeywordString();
            if (keyword != null && keyword.length() > 0) {
                getViewModel().fetchProductsByCommand(getCategoryIdsForSearch(), keyword);
            }
        }
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

    private int getIdSubCategory() {
        int idSubCategory = getIntent().getIntExtra("id_sub", ID_SUB_EMPTY);
        return idSubCategory;
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


}
