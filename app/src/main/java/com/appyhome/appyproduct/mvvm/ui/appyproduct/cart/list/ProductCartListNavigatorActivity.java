package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list;

import android.content.Intent;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductCartListBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.variant.EditVariantFragment;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.variant.EditVariantNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.ShippingAddressActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.ProductDetailActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.main.MainActivity;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import io.realm.RealmResults;

public abstract class ProductCartListNavigatorActivity extends BaseActivity<ActivityProductCartListBinding, ProductCartListViewModel>
        implements ProductCartListNavigator, EditVariantNavigator,
        ProductCartItemNavigator {

    protected static final int REQUEST_DETAIL = 11;

    public abstract ProductCartAdapter getAdapter();

    public abstract EditVariantFragment getEditVariantFragment();

    public abstract void setEditVariantFragment(EditVariantFragment fragment);

    @Override
    public void backToHomeScreen() {
        Intent i = MainActivity.getStartIntent(this);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    protected boolean isEditMode() {
        return getIntent().getBooleanExtra("edit_mode", false);
    }

    @Override
    public void gotoNextStep() {
        if (getAdapter().isNotItemsChecked()) {
            showAlert(getString(R.string.please_choose_at_least_one));
            return;
        }
        showLoading(false);
        getViewModel().doVerifyOrder();
    }

    @Override
    public void goBack() {
        if (getViewModel().isCartEmpty.get()) {
            backToHomeScreen();
        } else {
            finish();
        }
    }

    @Override
    public void clearCarts() {
        AlertManager.getInstance(this).showConfirmationDialog("", getString(R.string.warning_empty_cart), (dialog, which) -> emptyProductCarts());
    }

    @Override
    public void selectAllCarts() {
        getAdapter().checkAllItems(getViewDataBinding().cbCheckAll.isChecked());
    }

    @Override
    public void handleErrorService(Throwable throwable) {
        AlertManager.getInstance(this).showErrorToast(getString(R.string.error_unknown));
    }

    @Override
    public void showAlert(String message) {
        AlertManager.getInstance(this).showLongToast(message, R.style.AppyToast_Cart);
    }

    @Override
    public void editVariant(ProductCartItemViewModel viewModel) {
        if (getEditVariantFragment() != null) {
            closeEditVariantFragment();
            setEditVariantFragment(null);
        }
        setEditVariantFragment(EditVariantFragment.newInstance(viewModel, this));
        showFragment(getEditVariantFragment(), EditVariantFragment.TAG, R.id.llEditVariantContainer, true);
    }

    @Override
    public void showProductDetail(ProductCartItemViewModel viewModel) {
        Intent intent = ProductDetailActivity.getStartIntent(this, null, null);
        intent.putExtra("product_id", viewModel.getProductId());
        startActivityForResult(intent, REQUEST_DETAIL);
    }

    @Override
    public void showCarts(RealmResults<ProductCart> result) {
        if (result != null && result.size() > 0) {
            ViewUtils.setUpRecyclerViewListVertical(getViewDataBinding().cartRecyclerView, true);
        }
        getAdapter().addItems(result, this);
        getAdapter().notifyDataSetChanged();
    }

    @Override
    public void saveProductCartItem_Done(ProductCart productCart) {
        getAdapter().updateProductCartItem(productCart);
        closeFragment(EditVariantFragment.TAG);
    }

    @Override
    public void closeEditVariantFragment() {
        closeFragment(EditVariantFragment.TAG);
    }

    @Override
    public void onEditVariantSelected(ProductVariant variant) {
        // DO NOTHING HERE
    }

    @Override
    public void emptyProductCarts() {
        getAdapter().getItems().clear();
        getAdapter().notifyDataSetChanged();
        getViewModel().emptyProductCarts();
        getViewModel().isCartEmpty.set(true);
    }
}
