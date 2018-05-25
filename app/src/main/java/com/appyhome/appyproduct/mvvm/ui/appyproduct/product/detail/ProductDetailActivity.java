package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;

import com.appyhome.appyproduct.mvvm.AppConstants;
import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductDetailBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.ProductCartListActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.variant.EditVariantFragment;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.variant.EditVariantNavigator;
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
import com.appyhome.appyproduct.mvvm.utils.manager.MapManager;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.realm.RealmResults;

public class ProductDetailActivity extends BaseActivity<ActivityProductDetailBinding, ProductItemViewModel>
        implements HasSupportFragmentInjector, ProductDetailNavigator,
        ProductDetailVariantNavigator, ViewTreeObserver.OnScrollChangedListener, EditVariantNavigator {

    private final int REQUEST_SELECT_LOCATION = 10;
    @Inject
    public ProductItemViewModel mViewModel;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    ProductAdapter mRelatedProductAdapter;
    ActivityProductDetailBinding mBinder;
    private SearchToolbarViewHolder mSearchToolbarViewHolder;
    private EditVariantFragment mEditVariantFragment;
    private Point mCartPosition = new Point();
    private Point mAddToCartPosition = new Point();
    private int mTotalStock = 0;
    private ProductItemNavigator mPreviousNavigator;
    private ProductVariant mSelectedVariant;
    private ProductVariantFragment mProductVariantFragment;
    private ProductCartItemViewModel mProductCartItemViewModel;
    private boolean isBuyNow = false;
    private int POSITION_START = 0;
    private int POSITION_DETAIL = 0;
    private View mCurrentTab = null;
    private Runnable mTabRunnable = null;
    private Handler mHandler = null;
    private int mHeightTopBar = 0;

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
        mViewModel.setNavigator(this);
        setUp();
    }

    private void setUpPositionTabsForScroll() {
        if (POSITION_START == 0) {
            POSITION_START = getResources().getDimensionPixelSize(R.dimen.detail_gallery_height);
        }
        if (POSITION_DETAIL == 0) {
            Point childOffset = new Point();
            ViewUtils.getDeepChildOffset(mBinder.scrollView, mBinder.tableDescription.getParent(), mBinder.tableDescription, childOffset);
            POSITION_DETAIL = childOffset.y - mHeightTopBar;
        }
    }

    private void setUp() {
        mPreviousNavigator = mViewModel.getNavigator();
        mSearchToolbarViewHolder = new SearchToolbarViewHolder(this, mBinder.toolbar, false, false, getKeywordString());
        getCartPosition();
        long productId = getProductIdByIntent();
        if (productId > 0) {
            getViewModel().setProductId(productId);
            getViewModel().getProductCachedById();
            mProductVariantFragment = ProductVariantFragment.newInstance(productId, "");
            mProductVariantFragment.setDetailNavigator(this);
            showFragment(mProductVariantFragment, ProductVariantFragment.TAG, R.id.llProductVariant, false);
        }
        mBinder.scrollView.getViewTreeObserver().addOnScrollChangedListener(this);

        ViewUtils.setUpRecyclerViewListHorizontal(mBinder.rvRecommendSet, false);
        ViewUtils.setUpRecyclerViewListHorizontal(mBinder.rvProductSet, false);

        Spanned spanned = Html.fromHtml(getString(R.string.warranty_text));
        mBinder.tvWarranty.setText(spanned, true);
        mHeightTopBar = getResources().getDimensionPixelSize(R.dimen.title_bar_height)
                + getResources().getDimensionPixelSize(R.dimen.detail_tab_height);
        ViewUtils.loadImageAssetAsResource(this, mBinder.promotionBanners, getViewModel().promotionBannerURL.get());
        getViewModel().relatedAdapter.set(mRelatedProductAdapter);
        getViewModel().productsAdapter.set(mRelatedProductAdapter);
    }

    private void selectTab(View tab) {
        if (tab != mCurrentTab) {
            if (mCurrentTab != null) {
                View view = mCurrentTab.findViewWithTag("highlight");
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }
            mCurrentTab = tab;
            View view = mCurrentTab.findViewWithTag("highlight");
            if (view != null) {
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onScrollChanged() {
        int scrollY = mBinder.scrollView.getScrollY();
        if (scrollY >= POSITION_START) {
            float value = ((float) scrollY - POSITION_START) / 200;
            getViewModel().alphaTitle.set(value);
        } else {
            getViewModel().alphaTitle.set(0.0f);
        }
        if (scrollY <= POSITION_DETAIL) {
            selectTab(mBinder.tabOverview);
        }
        if (scrollY >= POSITION_DETAIL) {
            selectTab(mBinder.tabDetails);
        }
    }

    private String getKeywordString() {
        if (getIntent().hasExtra("keyword"))
            return getIntent().getStringExtra("keyword");
        return "";
    }

    private long getProductIdByIntent() {
        return getIntent().getLongExtra("product_id", 0);
    }

    private void getCartPosition() {
        mBinder.toolbar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                int sizeInPixels = getResources().getDimensionPixelSize(R.dimen.size_box_animation);
                mBinder.toolbar.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                View cartIcon = mBinder.toolbar.findViewById(R.id.ivCart);
                int[] cartLocations = new int[2];
                cartIcon.getLocationOnScreen(cartLocations);
                mCartPosition.x = cartLocations[0];
                mCartPosition.y = cartLocations[1];
                mAddToCartPosition.y = AppConstants.SCREEN_HEIGHT;
                mAddToCartPosition.x = AppConstants.SCREEN_WIDTH / 2 - sizeInPixels;
            }
        });
    }

    @Override
    public void selectedVariant(ProductVariant variant) {
        mSelectedVariant = variant;
        getViewModel().setVariantId(variant.id);
        getViewModel().updateIfVariantFavorite();
        getViewModel().price.set(variant.price);
        getViewModel().isVariantSelected.set(true);
        mTotalStock = variant.quantity;
        getViewModel().stockCount.set(mTotalStock + "");
        calculateShippingFee(variant);
        getViewModel().fetchSellerInformation(variant.seller_id);
        loadImages(variant);
        mBinder.tableDescription.loadData(variant);
        setUpPositionTabsForScroll();
    }

    @Override
    public void showedVariants(RealmResults<ProductVariant> variants) {
        // DO NOTHING HERE
    }


    @Override
    public void gotoCart() {
        isBuyNow = true;
        showEditProductVariantFragment();
    }

    @Override
    public void addedToCartCompleted(int amount, boolean isBoughtNow) {
        showAlert(amount + " " + getString(R.string.items_added_to_your_card));
        if (isBoughtNow) {
            startActivity(ProductCartListActivity.getStartIntent(this));
        }
        isBuyNow = false;
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
    public void addToCart(ProductItemViewModel viewModel) {
        isBuyNow = false;
        showEditProductVariantFragment();
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
        mBinder.sliderPhotos.setOnSlideClickListener(position -> {
            Intent intent = ProductGalleryActivity.getStartIntent(ProductDetailActivity.this);
            intent.putExtra("position", position);
            intent.putExtra("variant_model_id", mSelectedVariant.model_id);
            startActivity(intent);
        });
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
        if (!isFinishing())
            AlertManager.getInstance(this).showQuickToast(message, R.style.AppyToast_Cart);
    }

    @Override
    public void notifyFavoriteChanged(int position, boolean isFavorite) {
        if (!isFinishing())
            AlertManager.getInstance(this).showQuickToast(isFavorite ?
                    getString(R.string.added_wishlist) : getString(R.string.removed_wishlist), R.style.AppyToast_Favorite);
    }

    @Override
    public void onStop() {
        super.onStop();
        getViewModel().updateIfProductFavorite();
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
    public void back() {
        finish();
    }

    @Override
    public void goToDetailSection() {
        if (getViewModel().alphaTitle.get() > 0.8f) {
            mTabRunnable = () -> {
                if (mHandler != null) mHandler.removeCallbacks(mTabRunnable);
                ViewUtils.scrollToView(mBinder.scrollView, mBinder.tableDescription, mHeightTopBar);
                mTabRunnable = null;
                mHandler = null;
            };
            mHandler = new Handler();
            mHandler.postDelayed(mTabRunnable, 300);
        }
    }

    @Override
    public void goToOverviewSection() {
        if (getViewModel().alphaTitle.get() > 0.8f) {
            mTabRunnable = () -> {
                if (mHandler != null) mHandler.removeCallbacks(mTabRunnable);
                mBinder.scrollView.smoothScrollTo(0, 0);
                mTabRunnable = null;
                mHandler = null;
            };
            mHandler = new Handler();
            mHandler.postDelayed(mTabRunnable, 300);
        }
    }

    @Override
    public void goToReviewSection() {

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

    private void showEditProductVariantFragment() {
        if (!askForLogin(getString(R.string.pls_login_add_to_cart))) {
            ProductVariant variant = mProductVariantFragment.getSelectedVariant();
            if (variant != null) {
                if (mEditVariantFragment == null) {
                    mProductCartItemViewModel = new ProductCartItemViewModel(getViewModel());
                    mProductCartItemViewModel.update(getViewModel(), mSelectedVariant);
                    mEditVariantFragment = EditVariantFragment.newInstance(mProductCartItemViewModel, this, -1);
                    showFragment(mEditVariantFragment, EditVariantFragment.TAG, R.id.llEditProductVariant);
                    getViewModel().isEditVariantShowed.set(true);
                }
            } else {
                showAlert(getString(R.string.please_choose_variant));
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mEditVariantFragment != null) {
            closeEditVariantFragment();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onEditVariantSelected(ProductVariant variant) {
        if (mProductVariantFragment != null) {
            mProductVariantFragment.selectVariant(variant.model_id);
        }
    }

    @Override
    public void closeEditVariantFragment() {
        hideKeyboard();
        closeFragment(EditVariantFragment.TAG);
        getViewModel().isEditVariantShowed.set(false);
        mEditVariantFragment = null;
        mProductCartItemViewModel = null;
    }

    private String getLocalCity(List<Address> addresses) {
        for (Address address : addresses) {
            if (address.getLocality() != null) {
                return address.getLocality();
            }
        }
        return "";
    }

    private String getPostCode(List<Address> addresses) {
        for (Address address : addresses) {
            if (address.getPostalCode() != null) {
                return address.getPostalCode();
            }
        }
        return "";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case MapManager.PLACE_PICKER_REQUEST:
                    calculateShippingFee(data);
                    break;
            }
        }
    }

    private void calculateShippingFee(ProductVariant variant) {
        getViewModel().calculateShippingFee(variant);
    }

    private void calculateShippingFee(Intent data) {
        if (data != null) {
            Place place = PlacePicker.getPlace(this, data);
            if (place != null) {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());
                try {
                    double longitude = place.getLatLng().longitude;
                    double latitude = place.getLatLng().latitude;
                    addresses = geocoder.getFromLocation(latitude, longitude, 10);
                    if (addresses != null && addresses.size() > 0) {
                        String stateStr = addresses.get(0).getAdminArea();
                        String countryStr = addresses.get(0).getCountryName();
                        String cityStr = getLocalCity(addresses);
                        String addressArea = TextUtils.join(", ", new String[]{cityStr, stateStr, countryStr});
                        getViewModel().calculateShippingFee(addressArea, getPostCode(addresses), mSelectedVariant);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Crashlytics.logException(e);
                }
            }
        } else {
            getViewModel().calculateShippingFee("", "", mSelectedVariant);
        }
    }

    @Override
    public void selectShippingLocation() {
        MapManager.openMapForPlaceSelection(this);
    }

    @Override
    public void saveProductCartItem_Done() {
        closeEditVariantFragment();
        if (isBuyNow) {
            mSearchToolbarViewHolder.onBind(0);
            startActivity(ProductCartListActivity.getStartIntent(this));
        } else animateProductToCart();
        isBuyNow = false;
    }
}
