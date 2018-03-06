package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductCartListBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.ShippingAddressActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.main.MainActivity;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

import io.realm.RealmResults;

public class ProductCartListActivity extends BaseActivity<ActivityProductCartListBinding, ProductCartListViewModel>
        implements ProductCartListNavigator, ProductCartItemNavigator, View.OnClickListener, DialogInterface.OnClickListener {
    @Inject
    ProductCartListViewModel mViewModel;
    @Inject
    ProductCartAdapter mProductCartAdapter;

    ActivityProductCartListBinding mBinder;
    boolean isEditMode = false;


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ProductCartListActivity.class);
        return intent;
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
        mViewModel.getAllProductCarts("1234");
        ViewUtils.setOnClickListener(this, mBinder.cbCheckAll, mBinder.ivTrash);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivTrash:
                AlertManager.getInstance(this).showConfirmationDialog("", getString(R.string.warning_empty_cart), this);
                break;
            case R.id.cbCheckAll:
                if (mProductCartAdapter != null)
                    mProductCartAdapter.checkAllItems(mBinder.cbCheckAll.isChecked());
                break;
        }
    }

    private void setUpEmptyRecyclerViewList(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());
    }

    private void setUpRecyclerViewList(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());
        if (rv.getItemDecorationCount() <= 0)
            rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
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
    public void onResume() {
        super.onResume();
        mViewModel.getAllProductCarts("1234");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mProductCartAdapter != null) {
            mProductCartAdapter.onUpdateDatabase();
        }
    }

    @Override
    public ProductCartListViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public void showContent(ProductCartAdapter adapter, View view, int idProduct) {

    }

    @Override
    public void showCart(RealmResults<ProductCart> result) {
        if (result != null && result.size() > 0) {
            setUpRecyclerViewList(mBinder.cartRecyclerView);
        }
        mProductCartAdapter.addItems(result, this);
        mProductCartAdapter.notifyDataSetChanged();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_cart_list;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        emptyProductCarts();
    }
}
