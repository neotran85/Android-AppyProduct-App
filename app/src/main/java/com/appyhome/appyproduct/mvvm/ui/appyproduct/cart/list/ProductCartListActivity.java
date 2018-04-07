package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
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

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.realm.RealmResults;

public class ProductCartListActivity extends BaseActivity<ActivityProductCartListBinding, ProductCartListViewModel>
        implements HasSupportFragmentInjector, ProductCartListNavigator, EditVariantNavigator,
        ProductCartItemNavigator, DialogInterface.OnClickListener {

    @Inject
    ProductCartListViewModel mViewModel;

    @Inject
    ProductCartAdapter mProductCartAdapter;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    ActivityProductCartListBinding mBinder;

    boolean isEditMode = false;

    private static final int REQUEST_DETAIL = 0;

    private EditVariantFragment mEditVariantFragment;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ProductCartListActivity.class);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_cart_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mViewModel);
        mBinder.setNavigator(this);
        mViewModel.setNavigator(this);
        mProductCartAdapter.setMainViewModel(mViewModel);
        mBinder.cartRecyclerView.setAdapter(mProductCartAdapter);
        setUpEmptyRecyclerViewList(mBinder.cartRecyclerView);
        mViewModel.getAllProductCarts();
        isEditMode = getIntent().getBooleanExtra("edit_mode", false);
    }

    public void emptyProductCarts() {
        if (mProductCartAdapter != null) {
            mProductCartAdapter.getItems().clear();
            mProductCartAdapter.notifyDataSetChanged();
            mViewModel.emptyProductCarts();
            mViewModel.isCartEmpty.set(true);
        }
    }

    @Override
    public void askBeforeRemoved(DialogInterface.OnClickListener listener) {
        AlertManager.getInstance(this).showConfirmationDialog("", getString(R.string.warning_remove_item), listener);
    }

    @Override
    public void backToHomeScreen() {
        Intent i = MainActivity.getStartIntent(this);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    public void gotoNextStep() {
        if (isEditMode) {
            finish();
        } else {
            Intent intent = ShippingAddressActivity.getStartIntent(this);
            startActivity(intent);
        }
    }

    public void goBack() {
        if (mViewModel.isCartEmpty.get()) {
            backToHomeScreen();
        } else {
            finish();
        }
    }

    @Override
    public void clearCarts() {
        AlertManager.getInstance(this).showConfirmationDialog("", getString(R.string.warning_empty_cart), this);
    }

    @Override
    public void selectAllCarts() {
        if (mProductCartAdapter != null)
            mProductCartAdapter.checkAllItems(mBinder.cbCheckAll.isChecked());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setUpEmptyRecyclerViewList(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void handleErrorService(Throwable throwable) {
        AlertManager.getInstance(this).showLongToast(getString(R.string.error_unknown));
    }

    @Override
    public void showAlert(String message) {
        AlertManager.getInstance(this).showLongToast(message);
    }

    @Override
    public void editVariant(ProductCartItemViewModel viewModel) {
        if (mEditVariantFragment != null) {
            closeEditVariantFragment();
            mEditVariantFragment = null;
        }
        mEditVariantFragment = EditVariantFragment.newInstance(viewModel, this);
        showFragment(mEditVariantFragment, EditVariantFragment.TAG, R.id.llEditVariantContainer, true);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mProductCartAdapter != null) {
            mProductCartAdapter.onUpdateDatabase();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mProductCartAdapter.recycle();
    }

    @Override
    public ProductCartListViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public void showProductDetail(ProductCartItemViewModel viewModel) {
        Intent intent = ProductDetailActivity.getStartIntent(this, null);
        intent.putExtra("product_id", viewModel.getProductId());
        startActivityForResult(intent, REQUEST_DETAIL);
    }

    @Override
    public void showContent(ProductCartAdapter adapter, View view, int idProduct, int index) {
        Intent intent = ProductDetailActivity.getStartIntent(this, null);
        intent.putExtra("product_id", idProduct);
        startActivityForResult(intent, REQUEST_DETAIL);
    }

    @Override
    public void showCarts(RealmResults<ProductCart> result) {
        if (result != null && result.size() > 0) {
            ViewUtils.setUpRecyclerViewList(mBinder.cartRecyclerView, true);
        }
        mProductCartAdapter.addItems(result, this);
        mProductCartAdapter.notifyDataSetChanged();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        emptyProductCarts();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_DETAIL) {
            mViewModel.getAllProductCarts();
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void saveProductCartItem_Done(ProductCart productCart) {
        mProductCartAdapter.updateProductCartItem(productCart);
        closeFragment(EditVariantFragment.TAG);
    }

    @Override
    public void closeEditVariantFragment() {
        closeFragment(EditVariantFragment.TAG);
    }
}
