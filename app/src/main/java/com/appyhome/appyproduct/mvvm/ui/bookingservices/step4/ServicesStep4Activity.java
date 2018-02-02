package com.appyhome.appyproduct.mvvm.ui.bookingservices.step4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.model.db.AppyService;
import com.appyhome.appyproduct.mvvm.databinding.ActivityServicesBookingStep4Binding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.ServiceOrderInfo;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.step1.ServicesAdapter;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.step5.ServicesStep5Activity;
import com.appyhome.appyproduct.mvvm.utils.helper.AppLogger;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;
import com.appyhome.appyproduct.mvvm.utils.manager.PaymentManager;
import com.molpay.molpayxdk.MOLPayActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

public class ServicesStep4Activity extends BaseActivity<ActivityServicesBookingStep4Binding, ServicesStep4ViewModel> implements ServicesStep4Navigator, View.OnClickListener {

    @Inject
    ServicesStep4ViewModel mServicesStep4ViewModel;

    ActivityServicesBookingStep4Binding mBinder;

    private ArrayList<AppyService> mServicesList;

    private String mAppointmentId;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ServicesStep4Activity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mServicesStep4ViewModel);

        setTitle("Payment");
        setUpData();
        setUpListener();
        activeBackButton();
    }

    private void setUpListener() {
        ViewUtils.setOnClickListener(this, mBinder.btnNext,
                mBinder.rlVisaPayment,
                mBinder.rlBankPayment);
        mServicesStep4ViewModel.setNavigator(this);
    }

    private void setUpData() {
        mServicesStep4ViewModel.setUpData();
        mServicesList = new ArrayList<>();
        mServicesList.add(ServiceOrderInfo.getInstance().getSelectedService());
        mBinder.lvServices.setAdapter(new ServicesAdapter(this, mServicesList));
        mAppointmentId = setAppointmentId();
    }


    private String setAppointmentId() {
        String id = System.currentTimeMillis() + "";
        ServiceOrderInfo.getInstance().setAppointmentId(id);
        return id;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
                AlertManager.getInstance(this).showLongToast(getString(R.string.payment_advice));
                mBinder.svMainView.fullScroll(View.FOCUS_DOWN);
                break;
            case R.id.rlVisaPayment:
                AlertManager.getInstance(this).showComingSoonDialog();
                break;
            case R.id.rlBankPayment:
                openBankPaymentActivity();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public ServicesStep4ViewModel getViewModel() {
        return mServicesStep4ViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_services_booking_step4;
    }

    @Override
    public void goToStep5() {
        Intent i = ServicesStep5Activity.getStartIntent(this);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    public void openBankPaymentActivity() {
        PaymentManager.getInstance().startPaymentActivity(this,
                ServiceOrderInfo.getInstance().getTotalCost(), mAppointmentId,
                mServicesStep4ViewModel.getPhoneNumberOfUser(),
                mServicesStep4ViewModel.getEmailOfUser(),
                mServicesStep4ViewModel.getNameOfUser());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MOLPayActivity.MOLPayXDK && resultCode == RESULT_OK) {
            AppLogger.d(MOLPayActivity.MOLPAY, "MOLPay result = " + data.getStringExtra(MOLPayActivity.MOLPayTransactionResult));
            try {
                JSONObject result = new JSONObject(data.getStringExtra(MOLPayActivity.MOLPayTransactionResult));
                if (result.getString("status_code").equals("00")) {
                    // PAYMENT SUCCESS
                    String txn_ID = result.getString("txn_ID");
                    AppLogger.d(txn_ID);
                    ServiceOrderInfo.getInstance().setTxn_ID(txn_ID);
                    goToStep5();
                    AlertManager.getInstance(this).showLongToast(getString(R.string.payment_success));
                }
                return;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AlertManager.getInstance(this).showLongToast(getString(R.string.payment_error_something));
        }

    }
}
