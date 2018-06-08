package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
import com.crashlytics.android.Crashlytics;
import com.molpay.molpayxdk.MOLPayActivity;

import org.json.JSONObject;

import javax.inject.Inject;

import io.realm.RealmResults;

public class ConfirmationActivity extends BaseActivity<ActivityProductCartConfirmationBinding, ConfirmationViewModel> implements ConfirmationNavigator {

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
        closeLoading();
        showAlert(getString(R.string.error_unknown));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setNavigator(this);
        mBinder.setViewModel(mMainViewModel);
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
        mBinder.cartListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void openVisaPayment() {
        Intent intent = VisaPaymentActivity.getStartIntent(this);
        startActivityForResult(intent, REQUEST_VISA_PAYMENT);
    }

    @Override
    public void gotoNextStep() {
        mBinder.btnFinalConfirm.setText(getString(R.string.verifying_order));
        getViewModel().doVerifyOrderBeforePayment();
    }

    public void addOrderOk(ProductOrder order) {
        closeLoading();
        Intent i = OrderCompleteActivity.getStartIntent(this);
        i.putExtra("order_id", order.id);
        startActivity(i);
    }

    @Override
    public void addOrderFailed(String message) {
        getViewModel().updateUserCartAgain();
    }

    public String getMolpayTransactionId(Intent data) {
        try {
            JSONObject result = new JSONObject(data.getStringExtra(MOLPayActivity.MOLPayTransactionResult));
            if (result.getString("status_code").equals("00")) {
                // PAYMENT SUCCESS
                String txn_ID = result.getString("txn_ID");
                return txn_ID;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Crashlytics.logException(e);
        }
        return "";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case MOLPayActivity.MOLPayXDK:
                    showLoading();
                    getViewModel().addOrder("paid", "",
                            getMolpayTransactionId(data));
                    break;
                case REQUEST_VISA_PAYMENT:
                    showLoading();
                    getViewModel().addOrder("paid", "", "");
                    break;
            }
        }
    }

    @Override
    public ConfirmationViewModel getViewModel() {
        return mMainViewModel;
    }

    @Override
    public void updateTotalShippingCost(double total) {
        getViewModel().totalShippingCost.set(total);
        getViewModel().updateAllCost();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void showAlert(String message) {
        if (!isFinishing())
            AlertManager.getInstance(this).showLongToast(message, R.style.AppyToast_Cart);
    }

    @Override
    public void onFetchUserInfo_Done() {
        closeLoading();
        showAlert(getString(R.string.pls_cart_need_updated));
        editCart();
    }

    @Override
    public void onFetchUserInfo_Failed() {
        closeLoading();
        showAlert(getString(R.string.pls_cart_need_updated));
        editCart();
    }

    @Override
    public void verifyOrder_PASSED() {
        mBinder.btnFinalConfirm.setText(getString(R.string.cart_final_confirm));
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

    @Override
    public void verifyOrder_FAILED(String message) {
        mBinder.btnFinalConfirm.setText(getString(R.string.cart_final_confirm));
        getViewModel().updateUserCartAgain();
    }
}
