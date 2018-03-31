package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductDetailBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.ProductCartListActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.gallery.ProductGalleryActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.ui.common.component.cart.SearchToolbarViewHolder;
import com.appyhome.appyproduct.mvvm.utils.helper.AppAnimator;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import javax.inject.Inject;

public class ProductDetailActivity extends BaseActivity<ActivityProductDetailBinding, ProductItemViewModel> implements ProductDetailNavigator, BaseSliderView.OnSliderClickListener {

    @Inject
    public ProductItemViewModel mMainViewModel;

    ActivityProductDetailBinding mBinder;

    @Inject
    int mLayoutId;

    private SearchToolbarViewHolder mSearchToolbarViewHolder;

    private boolean isBuyNow = false;

    private Point mCartPosition = new Point();
    private Point mAddToCartPosition = new Point();

    public static Intent getStartIntent(Context context, ProductItemViewModel viewModel) {
        ProductDetailActivityModule.clickedViewModel = viewModel;
        Intent intent = new Intent(context, ProductDetailActivity.class);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setNavigator(this);
        mBinder.setViewModel(mMainViewModel);
        mMainViewModel.setNavigator(this);
        mSearchToolbarViewHolder = new SearchToolbarViewHolder(this, mBinder.toolbar, true, true);
        loadImages();
        getCartPosition();
        getProductIdByIntent();
        getViewModel().getProductCachedById();
        getViewModel().fetchProductVariant();
    }

    private void getProductIdByIntent() {
        int productId = getIntent().getIntExtra("product_id", 0);
        if(productId != 0) {
            getViewModel().setProductId(productId);
        }
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
    public void gotoCart() {
        isBuyNow = true;
        mMainViewModel.addProductToCart();
    }

    @Override
    public void addedToCartCompleted() {
        if (isBuyNow) {
            startActivity(ProductCartListActivity.getStartIntent(this));
        } else {
            showAlert(mMainViewModel.amountAdded.get() + getString(R.string.items_added_to_your_card));
        }
        isBuyNow = false;
    }

    @Override
    public BaseViewModel getMainViewModel() {
        return mMainViewModel;
    }

    @Override
    public void updateCartCount() {
        mSearchToolbarViewHolder.onBind(0);
    }


    @Override
    public void addToCart() {
        animateProductToCart();
    }

    private void animateProductToCart() {
        mBinder.ivProductBox.setVisibility(View.VISIBLE);
        int sizeInPixels = getResources().getDimensionPixelSize(R.dimen.size_box_animation);
        AppAnimator.animateMoving(mBinder.ivProductBox, sizeInPixels, mAddToCartPosition, mCartPosition, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mMainViewModel.addProductToCart();
                mBinder.ivProductBox.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void share() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mMainViewModel.title.get());
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSearchToolbarViewHolder.onBind(0);
    }

    private void loadImages() {
        for (String name : getViewModel().images) {
            DefaultSliderView vDefaultSliderView = new DefaultSliderView(this);
            // initialize a SliderLayout
            vDefaultSliderView
                    .image(name)
                    .setScaleType(BaseSliderView.ScaleType.FitCenterCrop);
            vDefaultSliderView.setOnSliderClickListener(this);
            mBinder.slider.addSlider(vDefaultSliderView);
        }
        mBinder.slider.setPresetTransformer(SliderLayout.Transformer.Default);
        mBinder.slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mBinder.slider.setCustomIndicator(mBinder.customIndicator);
        mBinder.slider.stopAutoCycle();
        mBinder.slider.setCurrentPosition(0);
    }

    @Override
    public void addToFavorite() {
        mMainViewModel.updateProductFavorite(0);
    }

    @Override
    public ProductItemViewModel getViewModel() {
        return mMainViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void showAlert(String message) {
        AlertManager.getInstance(this).showLongToast(message);
    }

    @Override
    public void showGallery(int position) {
        Intent intent = ProductGalleryActivity.getStartIntent(this);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public void notifyFavoriteChanged(int position, boolean isFavorite) {
        // DO NOTHING HERE
    }

    @Override
    public void increaseAmount() {
        int amount = mMainViewModel.getIntegerAmountAdded() + 1;
        mMainViewModel.setIntegerAmountAdded(amount);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMainViewModel = null;
        ProductDetailActivityModule.clickedViewModel = null;
    }

    @Override
    public void decreaseAmount() {
        int amount = mMainViewModel.getIntegerAmountAdded() - 1;
        if (amount >= 1)
            mMainViewModel.setIntegerAmountAdded(amount);
    }

    @Override
    public void onItemClick(View view) {
        // DO NOTHING HERE
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        showGallery(mBinder.slider.getCurrentPosition());
    }
}
