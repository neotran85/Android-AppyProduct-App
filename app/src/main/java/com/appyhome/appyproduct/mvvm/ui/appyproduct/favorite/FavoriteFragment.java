package com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.databinding.FragmentFavoriteBinding;
import com.appyhome.appyproduct.mvvm.databinding.FragmentSampleBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite.adapter.FavoriteAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.ProductListViewModel;
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
    FragmentFavoriteBinding mBinder;

    @Inject
    FavoriteAdapter mFavoriteAdapter;

    public static FavoriteFragment newInstance() {
        Bundle args = new Bundle();
        FavoriteFragment fragment = new FavoriteFragment();
        fragment.setArguments(args);
        return fragment;
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
        return R.layout.fragment_favorite;
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
        if (result != null && result.size() > 0) {
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

    @Override
    public void notifyItemChanged(int position) {
        mFavoriteAdapter.notifyItemChanged(position);
    }
}
