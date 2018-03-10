package com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.databinding.FragmentFavoriteBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite.adapter.FavoriteAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;

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
        mViewModel.updateFavoriteCount(mFavoriteAdapter.getFavoriteCount());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp();
    }

    private void setUp() {
        mViewModel.setNavigator(this);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mViewModel);
        mViewModel.getAllFavorites();
        mBinder.productsRecyclerView.setAdapter(mFavoriteAdapter);
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
    public void showProducts(RealmResults<Product> result) {
        if (result != null) {
            setUpRecyclerViewGrid(mBinder.productsRecyclerView);
            mFavoriteAdapter.addItems(result, this, true);
            mFavoriteAdapter.notifyDataSetChanged();
        }
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
    public void showAlert(String message) {

    }
}
