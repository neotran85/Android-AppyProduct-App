package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductOrder;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductCartConfirmationBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.completed.OrderCompleteActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation.adapter.CartAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation.visa.VisaPaymentActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.ProductCartListActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.payment.PaymentActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.ShippingAddressActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;
import com.appyhome.appyproduct.mvvm.utils.manager.PaymentManager;
import com.molpay.molpayxdk.MOLPayActivity;

import javax.inject.Inject;

import io.realm.RealmResults;

public class ConfirmationActivity extends BaseActivity<ActivityProductCartConfirmationBinding, ConfirmationViewModel> implements ConfirmationNavigator {

    final int HEIGHT_CART_ITEM = 144;
    final int HEIGHT_TITLE_CART_ITEM = 48;
    public static final int REQUEST_VISA_PAYMENT = 10;
    @Inject
    public CartAdapter mAdapter;
    @Inject
    public ConfirmationViewModel mMainViewModel;
    ActivityProductCartConfirmationBinding mBinder;
    @Inject
    int mLayoutId;

    private long generatedOrderId = 0;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ConfirmationActivity.class);
        return intent;
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    @Override
    public void handleErrors(Throwable throwable) {
        showAlert(getString(R.string.error_unknown));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setNavigator(this);
        mBinder.setViewModel(mMainViewModel);
        mBinder.rvCartRecyclerView.setAdapter(mAdapter);
        setUpRecyclerViewList(mBinder.rvCartRecyclerView);
        mMainViewModel.setNavigator(this);
    }

    private void setUpRecyclerViewList(RecyclerView rv) {
        ViewUtils.setUpRecyclerViewListVertical(rv, false);
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rv.setNestedScrollingEnabled(false);
    }

    @Override
    public void editShippingAddress() {
        Intent intent = ShippingAddressActivity.getStartIntent(this);
        intent.putExtra("edit_mode", true);
        startActivity(intent);
    }

    @Override
    public void editPaymentMethod() {
        Intent intent = PaymentActivity.getStartIntent(this);
        intent.putExtra("edit_mode", true);
        startActivity(intent);
    }

    @Override
    public void editCart() {
        Intent intent = ProductCartListActivity.getStartIntent(this);
        intent.putExtra("edit_mode", true);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        getViewModel().update();
    }

    @Override
    public void showCheckedItems(RealmResults<ProductCart> result, int addressId) {
        mAdapter.addItems(result, addressId, this);
        mAdapter.notifyDataSetChanged();
        updateCartContainerHeight(result.size());
    }

    private void updateCartContainerHeight(int count) {
        int height = count * HEIGHT_CART_ITEM + mAdapter.getTotalStores() * HEIGHT_TITLE_CART_ITEM;
        height = ViewUtils.dpToPx(height);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mBinder.llCartContainer.getLayoutParams();
        params.height = height;
        mBinder.llCartContainer.setLayoutParams(params);
    }

    @Override
    public void openVisaPayment() {
        Intent intent = VisaPaymentActivity.getStartIntent(this);
        startActivityForResult(intent, REQUEST_VISA_PAYMENT);
    }

    @Override
    public void gotoNextStep() {
        if (mMainViewModel.isMolpay.get()) {
            generatedOrderId = System.currentTimeMillis();
            PaymentManager.getInstance().startMolpayActivity(this,
                    getViewModel().totalAllCost.get().toString(), generatedOrderId + "",
                    getViewModel().getPhoneNumberOfUser(),
                    getViewModel().getEmailOfUser(),
                    getViewModel().getNameOfUser(), getString(R.string.appy_home_product_payment));
        } else {
            openVisaPayment();
        }
    }

    public void addOrderOk(ProductOrder order) {
        gotoOrderCompleted(order.id);
    }

    public void gotoOrderCompleted(long idOrder) {
        Intent i = OrderCompleteActivity.getStartIntent(this);
        i.putExtra("order_id", idOrder);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case MOLPayActivity.MOLPayXDK:
                    //getViewModel().addOrder(generatedOrderId);
                    break;
                case REQUEST_VISA_PAYMENT:
                    //getViewModel().addOrder(0);
                    break;
            }
        }
    }

    @Override
    public ConfirmationViewModel getViewModel() {
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
}
