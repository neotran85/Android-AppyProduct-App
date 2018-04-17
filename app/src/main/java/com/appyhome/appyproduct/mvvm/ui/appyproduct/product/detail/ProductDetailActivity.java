package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewTreeObserver;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductDetailBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.ProductCartListActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.common.component.cart.SearchToolbarViewHolder;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.gallery.ProductGalleryActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.variant.ProductVariantFragment;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.helper.AppAnimator;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class ProductDetailActivity extends BaseActivity<ActivityProductDetailBinding, ProductItemViewModel>
        implements HasSupportFragmentInjector, ProductDetailNavigator,
        ProductDetailVariantNavigator, ViewTreeObserver.OnScrollChangedListener {

    @Inject
    public ProductItemViewModel mViewModel;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    ProductAdapter mRelatedProductAdapter;

    ActivityProductDetailBinding mBinder;

    private SearchToolbarViewHolder mSearchToolbarViewHolder;

    private ProductVariantFragment mProductVariantFragment;

    private Point mCartPosition = new Point();

    private Point mAddToCartPosition = new Point();

    private int mTotalStock = 0;

    private ProductItemNavigator mPreviousNavigator;

    private String selectedVariantModelId = "";


    public static Intent getStartIntent(Context context, ProductItemViewModel viewModel, ProductAdapter adapter) {
        ProductDetailActivityModule.clickedViewModel = viewModel;
        ProductDetailActivityModule.relatedProductAdapter = adapter;
        Intent intent = new Intent(context, ProductDetailActivity.class);
        return intent;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setNavigator(this);
        mBinder.setViewModel(mViewModel);
        mPreviousNavigator = mViewModel.getNavigator();
        mViewModel.setNavigator(this);
        mSearchToolbarViewHolder = new SearchToolbarViewHolder(this, mBinder.toolbar, false, false, getKeywordString());
        getCartPosition();
        int productId = getProductIdByIntent();
        if (productId > 0) {
            getViewModel().setProductId(productId);
            getViewModel().getProductCachedById();
            mProductVariantFragment = ProductVariantFragment.newInstance(productId);
            mProductVariantFragment.setDetailNavigator(this);
            showFragment(mProductVariantFragment, ProductVariantFragment.TAG, R.id.llProductVariant, false);
        }
        mBinder.scrollView.getViewTreeObserver().addOnScrollChangedListener(this);

        if (mRelatedProductAdapter != null) {
            ViewUtils.setUpRecyclerViewListHorizontal(mBinder.rvRecommendSet, false);
            ViewUtils.setUpRecyclerViewListHorizontal(mBinder.rvProductSet, false);
            mBinder.rvRecommendSet.setAdapter(mRelatedProductAdapter);
            mBinder.rvProductSet.setAdapter(mRelatedProductAdapter);
        }
    }

    @Override
    public void onScrollChanged() {
        int scrollY = mBinder.scrollView.getScrollY();
        if (scrollY >= 200) {
            float value = ((float) scrollY - 200) / 400;
            getViewModel().alphaTitle.set(value);
        } else {
            getViewModel().alphaTitle.set(0.0f);
        }
    }

    private String getKeywordString() {
        if (getIntent().hasExtra("keyword"))
            return getIntent().getStringExtra("keyword");
        return "";
    }

    private int getProductIdByIntent() {
        return getIntent().getIntExtra("product_id", 0);
    }

    private void getCartPosition() {
        mBinder.toolbar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                mBinder.toolbar.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                View cartIcon = mBinder.toolbar.findViewById(R.id.ivCart);
                int[] cartLocations = new int[2];
                int[] addToCartLocations = new int[2];
                cartIcon.getLocationOnScreen(cartLocations);
                mCartPosition.x = cartLocations[0];
                mCartPosition.y = cartLocations[1];
                mBinder.btAddToCart.getLocationOnScreen(addToCartLocations);
                mAddToCartPosition.x = addToCartLocations[0];
                mAddToCartPosition.y = addToCartLocations[1];
            }
        });
    }

    @Override
    public void showedVariants() {
        mTotalStock = mProductVariantFragment.getTotalStock();
        getViewModel().stockCount.set(mTotalStock + "");
    }

    @Override
    public void selectedVariant(ProductVariant variant) {
        selectedVariantModelId = variant.model_id;
        getViewModel().setVariantId(variant.id);
        getViewModel().price.set("RM " + variant.price);
        getViewModel().isVariantSelected.set(true);
        mTotalStock = variant.quantity;
        getViewModel().stockCount.set(mTotalStock + "");
        getViewModel().amountAdded.set("1");
        loadImages(variant);
    }

    @Override
    public void gotoCart() {
        ProductVariant variant = mProductVariantFragment.getSelectedVariant();
        if (variant == null) {
            showAlert(getString(R.string.please_choose_variant));
        } else {
            getViewModel().addProductToCart(variant.model_id, true);
        }
    }

    @Override
    public void addedToCartCompleted(boolean isBuyNow) {
        if (isBuyNow) {
            startActivity(ProductCartListActivity.getStartIntent(this));
        } else {
            showAlert(getViewModel().amountAdded.get() + " " + getString(R.string.items_added_to_your_card));
        }
    }

    @Override
    public BaseViewModel getMainViewModel() {
        return mViewModel;
    }

    @Override
    public void updateCartCount() {
        mSearchToolbarViewHolder.onBind(0);
    }


    @Override
    public void addToCart() {
        if (!askForLogin("Please login to add product to your cart.")) {
            ProductVariant variant = mProductVariantFragment.getSelectedVariant();
            if (variant == null) {
                showAlert(getString(R.string.please_choose_variant));
            } else
                animateProductToCart();
        }
    }

    private void animateProductToCart() {
        mBinder.ivProductBox.setVisibility(View.VISIBLE);
        int sizeInPixels = getResources().getDimensionPixelSize(R.dimen.size_box_animation);
        AppAnimator.animateMoving(1000, mBinder.ivProductBox, sizeInPixels, mAddToCartPosition, mCartPosition, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (getViewModel() != null) {
                    ProductVariant variant = mProductVariantFragment.getSelectedVariant();
                    if (variant != null) {
                        getViewModel().addProductToCart(variant.model_id, false);
                        mBinder.ivProductBox.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @Override
    public void share() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mViewModel.title.get());
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSearchToolbarViewHolder.onBind(0);
    }

    private void loadImages(ProductVariant productVariant) {
        mBinder.sliderPhotos.setAdapter(new VariantPhotosAdapter(productVariant));
    }

    @Override
    public ProductItemViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void showAlert(String message) {
        AlertManager.getInstance(this).showQuickToast(message, R.style.AppyToast_Cart);
    }

    @Override
    public void showGallery(int position) {
        Intent intent = ProductGalleryActivity.getStartIntent(this);
        intent.putExtra("position", position);
        intent.putExtra("variant_model_id", selectedVariantModelId);
        startActivity(intent);
    }

    @Override
    public void notifyFavoriteChanged(int position, boolean isFavorite) {
        AlertManager.getInstance(this).showQuickToast(isFavorite ?
                getString(R.string.added_wishlist) : getString(R.string.removed_wishlist), R.style.AppyToast_Favorite);
    }

    @Override
    public void increaseAmount() {
        int amount = mViewModel.getIntegerAmountAdded() + 1;
        if (amount <= mTotalStock)
            mViewModel.setIntegerAmountAdded(amount);
        else {
            showAlert(getString(R.string.unable_to_add_more_than) + " " + mTotalStock);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getViewModel() != null && mPreviousNavigator != null) {
            getViewModel().setNavigator(mPreviousNavigator);
        }
        mViewModel = null;
        ProductDetailActivityModule.clickedViewModel = null;
    }

    @Override
    public void decreaseAmount() {
        int amount = mViewModel.getIntegerAmountAdded() - 1;
        if (amount >= 1)
            mViewModel.setIntegerAmountAdded(amount);
    }

    @Override
    public void back() {
        finish();
    }

    @Override
    public void onItemClick(ProductItemViewModel viewModel) {
        // DO NOTHING HERE
    }

    @Override
    public void onFavoriteClick(ProductItemViewModel vm) {
        if (!askForLogin(getString(R.string.ask_login_to_add_wishlist))) {
            vm.updateProductFavorite(0);
        }
    }
}
