package com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFavorite;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;
import com.appyhome.appyproduct.mvvm.databinding.FragmentFavoriteBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.AppyProductConstants;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.variant.EditVariantFragment;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.variant.EditVariantNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite.adapter.FavoriteAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite.adapter.FavoriteItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;
import com.crashlytics.android.Crashlytics;

import javax.inject.Inject;

import io.realm.RealmResults;

public class FavoriteFragment extends BaseFragment<FragmentFavoriteBinding, FavoriteViewModel> implements FavoriteNavigator, ProductItemNavigator, EditVariantNavigator {

    public static final String TAG = "FavoriteFragment";
    private ProductCartItemViewModel mProductCartItemViewModel;
    private EditVariantFragment mEditVariantFragment;

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

    }

    @Override
    public void onResume() {
        super.onResume();
        getViewModel().fetchUserData();
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
        ViewUtils.setUpRecyclerViewListVertical(mBinder.productsRecyclerView, false);
        mBinder.productsRecyclerView.setAdapter(mFavoriteAdapter);
        mProductCartItemViewModel = new ProductCartItemViewModel(getViewModel().getDataManager(), getViewModel().getSchedulerProvider());
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
                AppyProductConstants.PRODUCT_CONFIG.PRODUCT_SPAN_COUNT, GridLayoutManager.VERTICAL,
                false));
        rv.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void showProducts(RealmResults<ProductFavorite> result) {
        if (result != null && result.size() > 0) {
            setUpRecyclerViewGrid(mBinder.productsRecyclerView);
        } else {
            ViewUtils.setUpRecyclerViewListVertical(mBinder.productsRecyclerView, false);
        }
        mFavoriteAdapter.addItems(result, this);
        mFavoriteAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(ProductItemViewModel viewModel) {
        //Intent intent = ProductDetailActivity.getStartIntent(this.getActivity(), viewModel, mFavoriteAdapter);
        //startActivity(intent);
    }

    @Override
    public void onFavoriteClick(ProductItemViewModel vm) {
        int pos = mFavoriteAdapter.indexOf(vm);
        mFavoriteAdapter.remove(vm);
        int count = mFavoriteAdapter.getFavoriteCount();
        getViewModel().updateFavoriteCount(count);
        getViewModel().isFavoriteEmpty.set(count == 0);
        if (count == 0) {
            ViewUtils.setUpRecyclerViewListVertical(mBinder.productsRecyclerView, false);
        }

        showSnackBar(getString(R.string.removed_wishlist), "UNDO", v -> {
            if (mFavoriteAdapter != null) {
                mFavoriteAdapter.add(vm, pos);
            }
        }, new SnackBarCallBack(vm, pos));
    }

    @Override
    public void addedToCartCompleted(int amount, boolean isBuyNow) {
        // DO NOTHING
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
        if (!getActivity().isFinishing())
            AlertManager.getInstance(getActivity()).showConfirmationDialog("", getString(R.string.sure_to_empty_wishlist), (dialog, which) -> {
                getViewModel().emptyUserWishList();
            });
    }

    @Override
    public void addToCart(ProductItemViewModel viewModel) {
        getViewModel().isEditVariantShowed.set(true);
        showEditProductVariantFragment(viewModel.getProductId());
    }

    @Override
    public void showAlert(String message) {
        if (!getActivity().isFinishing())
            AlertManager.getInstance(getActivity()).showLongToast(message, R.style.AppyToast_Favorite);
    }

    @Override
    public void onFetchUserInfo_Done() {
        // GET ALL FAVORITE AGAIN.
        getViewModel().getAllFavorites();
    }

    @Override
    public void onFetchUserInfo_Failed() {
        Crashlytics.log("onFetchUserInfo_Failed");
    }

    private void showEditProductVariantFragment(long productId) {
        mProductCartItemViewModel.setProductId(productId);
        mEditVariantFragment = EditVariantFragment.newInstance(mProductCartItemViewModel, this, -1);
        showFragment(mEditVariantFragment, EditVariantFragment.TAG, R.id.llEditProductVariant);
    }

    @Override
    public void closeEditVariantFragment() {
        getViewModel().isEditVariantShowed.set(false);
        closeFragment(EditVariantFragment.TAG);
        mEditVariantFragment = null;
    }

    @Override
    public void saveProductCartItem_Done() {

    }

    @Override
    public void onEditVariantSelected(ProductVariant variant) {

    }

    protected class SnackBarCallBack extends Snackbar.Callback {
        BaseViewModel viewModel;
        int position;

        public SnackBarCallBack(BaseViewModel vm, int pos) {
            position = pos;
            viewModel = vm;
        }

        @Override
        public void onShown(Snackbar sb) {
            // Stub implementation to make API check happy.
        }

        @Override
        public void onDismissed(Snackbar transientBottomBar, @DismissEvent int event) {
            if (viewModel instanceof ProductItemViewModel) {
                ProductItemViewModel vm = (ProductItemViewModel) viewModel;
                vm.updateProductFavorite(position);
            }
        }
    }
}
