package com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;

import com.appyhome.appyproduct.mvvm.AppConstants;
import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;
import com.appyhome.appyproduct.mvvm.databinding.FragmentFavoriteBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.AppyProductConstants;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.variant.EditVariantFragment;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.variant.EditVariantNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.common.component.cart.SearchToolbarViewHolder;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite.adapter.FavoriteAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.ProductDetailActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.helper.AppAnimator;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;
import com.crashlytics.android.Crashlytics;

import javax.inject.Inject;

import io.realm.RealmResults;

public class FavoriteFragment extends BaseFragment<FragmentFavoriteBinding, FavoriteViewModel> implements FavoriteNavigator, ProductItemNavigator, EditVariantNavigator {

    public static final String TAG = "FavoriteFragment";
    private ProductCartItemViewModel mProductCartItemViewModel;
    private EditVariantFragment mEditVariantFragment;
    private SearchToolbarViewHolder mSearchToolbarViewHolder;
    private Point mCartPosition = new Point();
    private Point mAddToCartPosition = new Point();
    private SnackBarCallBack mSnackBarCallBack;

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
        mSearchToolbarViewHolder.onBind(0);
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
        if (getActivity() instanceof BaseActivity) {
            mSearchToolbarViewHolder = new SearchToolbarViewHolder((BaseActivity) getActivity(), mBinder.llCartContainer, false, false, "");
        }
        getCartPosition();
        getViewModel().fetchUserData();
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
    public void showProducts(RealmResults<Product> result) {
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
        Intent intent = ProductDetailActivity.getStartIntent(this.getActivity(), viewModel, mFavoriteAdapter);
        intent.putExtra("product_id", viewModel.getProductId());
        startActivity(intent);
    }

    @Override
    public void onFavoriteClick(ProductItemViewModel vm) {
        int pos = mFavoriteAdapter.indexOf(vm);
        mFavoriteAdapter.remove(vm);
        int count = mFavoriteAdapter.getItemSize();
        getViewModel().updateFavoriteCount(count);
        getViewModel().isFavoriteEmpty.set(count == 0);
        if (count == 0) {
            ViewUtils.setUpRecyclerViewListVertical(mBinder.productsRecyclerView, false);
        }
        mSnackBarCallBack = new SnackBarCallBack(vm, pos);
        showSnackBar(getString(R.string.removed_wishlist), "UNDO", v -> {
            if (mFavoriteAdapter != null) {
                if(mFavoriteAdapter.getItemSize() == 0)
                    setUpRecyclerViewGrid(mBinder.productsRecyclerView);
                mFavoriteAdapter.add(vm, pos);
                if (mSnackBarCallBack != null)
                    mSnackBarCallBack.setData(null, -1);
            }
        }, mSnackBarCallBack);
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
        if (isActivityRunning())
            AlertManager.getInstance(getActivity()).showConfirmationDialog("", getString(R.string.sure_to_empty_wishlist), (dialog, which) -> {
                getViewModel().emptyUserWishList();
            });
    }

    @Override
    public void addToCart(ProductItemViewModel viewModel) {
        getViewModel().isEditVariantShowed.set(true);
        mProductCartItemViewModel.title.set(viewModel.title.get());
        mProductCartItemViewModel.sellerName.set(viewModel.sellerName.get());
        mProductCartItemViewModel.setProductId(viewModel.getProductId());
        mProductCartItemViewModel.imageURL.set(viewModel.imageURL.get());
        mProductCartItemViewModel.amount.set("1");
        mProductCartItemViewModel.price.set(viewModel.price.get());
        mEditVariantFragment = EditVariantFragment.newInstance(mProductCartItemViewModel, this, -1);
        showFragment(mEditVariantFragment, EditVariantFragment.TAG, R.id.llEditProductVariant);
    }

    @Override
    public void showAlert(String message) {
        if (isActivityRunning())
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

    @Override
    public void closeEditVariantFragment() {
        getViewModel().isEditVariantShowed.set(false);
        closeFragment(EditVariantFragment.TAG);
        mEditVariantFragment = null;
    }

    @Override
    public void saveProductCartItem_Done() {
        animateProductToCart();
        closeEditVariantFragment();
    }

    private void getCartPosition() {
        mBinder.llCartContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                int sizeInPixels = getResources().getDimensionPixelSize(R.dimen.size_box_animation);
                mBinder.llCartContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                View cartIcon = mBinder.llCartContainer.findViewById(R.id.ivCart);
                int[] cartLocations = new int[2];
                cartIcon.getLocationOnScreen(cartLocations);
                mCartPosition.x = cartLocations[0];
                mCartPosition.y = cartLocations[1];
                mAddToCartPosition.y = AppConstants.SCREEN_HEIGHT;
                mAddToCartPosition.x = AppConstants.SCREEN_WIDTH / 2 - sizeInPixels;
            }
        });
    }

    private void animateProductToCart() {
        mBinder.ivProductBox.setVisibility(View.VISIBLE);
        int sizeInPixels = getResources().getDimensionPixelSize(R.dimen.size_box_animation);
        AppAnimator.animateMoving(2000, mBinder.ivProductBox, sizeInPixels, mAddToCartPosition, mCartPosition, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mBinder.ivProductBox.setVisibility(View.GONE);
                mSearchToolbarViewHolder.onBind(0);
            }
        });
    }

    @Override
    public void onEditVariantSelected(ProductVariant variant) {

    }

    protected class SnackBarCallBack extends Snackbar.Callback {
        BaseViewModel viewModel;
        int position;

        public SnackBarCallBack(BaseViewModel vm, int pos) {
            super();
            setData(vm, pos);
        }

        public void setData(BaseViewModel vm, int pos) {
            position = pos;
            viewModel = vm;
        }

        @Override
        public void onShown(Snackbar sb) {
            // Stub implementation to make API check happy.
        }

        @Override
        public void onDismissed(Snackbar transientBottomBar, @DismissEvent int event) {
            if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {
                if (viewModel instanceof ProductItemViewModel) {
                    ProductItemViewModel vm = (ProductItemViewModel) viewModel;
                    vm.updateProductFavorite(position);
                }
            }
        }
    }
}
