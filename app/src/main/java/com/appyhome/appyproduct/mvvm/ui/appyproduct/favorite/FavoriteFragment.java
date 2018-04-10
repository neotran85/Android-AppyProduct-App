package com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFavorite;
import com.appyhome.appyproduct.mvvm.databinding.FragmentFavoriteBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite.adapter.FavoriteAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.ProductDetailActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

import io.realm.RealmResults;

public class FavoriteFragment extends BaseFragment<FragmentFavoriteBinding, FavoriteViewModel> implements FavoriteNavigator, ProductItemNavigator {

    public static final String TAG = "FavoriteFragment";

    public static final int DEFAULT_SPAN_COUNT = 2;

    @Inject
    FavoriteViewModel mViewModel;

    @Inject
    FavoriteAdapter mFavoriteAdapter;

    @Inject
    int mLayoutId;

    FragmentFavoriteBinding mBinder;

    public static FavoriteFragment newInstance() {
        Bundle args = new Bundle();
        FavoriteFragment fragment = new FavoriteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void notifyFavoriteChanged(int position, boolean isFavorite) {
        mFavoriteAdapter.removedFavorite(position, isFavorite);
        int count = mFavoriteAdapter.getFavoriteCount();
        getViewModel().updateFavoriteCount(count);
        getViewModel().isFavoriteEmpty.set(count == 0);
        if (count == 0) {
            ViewUtils.setUpRecyclerViewList(mBinder.productsRecyclerView, false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.getAllFavorites();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp();
    }

    private void setUp() {
        mViewModel.setNavigator(this);
        mBinder = getViewDataBinding();
        mBinder.setNavigator(this);
        mBinder.setViewModel(mViewModel);
        mBinder.productsRecyclerView.setAdapter(mFavoriteAdapter);
        mViewModel.getAllFavorites();
    }

    @Override
    public FavoriteViewModel getViewModel() {
        return mViewModel;
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void setUpRecyclerViewGrid(RecyclerView rv) {
        rv.setLayoutManager(new GridLayoutManager(this.getActivity(),
                DEFAULT_SPAN_COUNT, GridLayoutManager.VERTICAL,
                false));
        rv.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void showProducts(RealmResults<ProductFavorite> result) {
        if (result != null && result.size() > 0) {
            setUpRecyclerViewGrid(mBinder.productsRecyclerView);
        } else {
            ViewUtils.setUpRecyclerViewList(mBinder.productsRecyclerView, false);
        }
        mFavoriteAdapter.addItems(this, result);
        mFavoriteAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(ProductItemViewModel viewModel) {
        Intent intent = ProductDetailActivity.getStartIntent(this.getActivity(), viewModel);
        startActivity(intent);
    }

    @Override
    public void onFavoriteClick(ProductItemViewModel vm) {
        vm.updateProductFavorite(mFavoriteAdapter.indexOf(vm));
    }

    @Override
    public void addedToCartCompleted(boolean isBuyNow) {
    }

    @Override
    public BaseViewModel getMainViewModel() {
        return mViewModel;
    }

    @Override
    public void updateCartCount() {
        // DO NOTHING
    }

    @Override
    public void emptyFavorites() {
        AlertManager.getInstance(getActivity()).showConfirmationDialog("", getString(R.string.sure_to_empty_wishlist), (dialog, which) -> {
            getViewModel().emptyUserWishList();
        });
    }

    @Override
    public void showAlert(String message) {

    }
}
