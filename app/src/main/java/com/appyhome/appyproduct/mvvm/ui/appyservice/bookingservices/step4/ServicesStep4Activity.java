package com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.model.db.AppyService;
import com.appyhome.appyproduct.mvvm.data.model.db.ServiceOrderUserInput;
import com.appyhome.appyproduct.mvvm.databinding.ActivityServicesBookingStep4Binding;
import com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step1.ServicesAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step5.ServicesStep5Activity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;
import com.appyhome.appyproduct.mvvm.utils.manager.PaymentManager;
import com.molpay.molpayxdk.MOLPayActivity;

import java.util.ArrayList;

import javax.inject.Inject;

public class ServicesStep4Activity extends BaseActivity<ActivityServicesBookingStep4Binding, ServicesStep4ViewModel> implements ServicesStep4Navigator, View.OnClickListener {

    @Inject
    ServicesStep4ViewModel mServicesStep4ViewModel;

    ActivityServicesBookingStep4Binding mBinder;
    @Inject
    int mLayoutId;
    private ArrayList<AppyService> mServicesList;
    private String mAppointmentId;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ServicesStep4Activity.class);
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
        mBinder.setViewModel(mServicesStep4ViewModel);

        setTitle("Payment");
        setUpData();
        setUpListener();
        activeBackButton();
    }

    private void setUpListener() {
        ViewUtils.setOnClickListener(this,
                mBinder.btnNext,
                mBinder.rlVisaPayment,
                mBinder.rlBankPayment);
        getViewModel().setNavigator(this);
    }

    private void setUpData() {
        getViewModel().setUpData();
        mServicesList = new ArrayList<>();
        mServicesList.add(getOrderUserInput().getSelectedService());
        mBinder.lvServices.setAdapter(new ServicesAdapter(this, mServicesList));
        mAppointmentId = setAppointmentId();
    }


    private String setAppointmentId() {
        String id = System.currentTimeMillis() + "";
        getOrderUserInput().setAppointmentId(id);
        return id;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
                if (!isFinishing())
                    AlertManager.getInstance(this).showLongToast(getString(R.string.payment_advice));
                mBinder.svMainView.fullScroll(View.FOCUS_DOWN);
                break;
            case R.id.rlVisaPayment:
                if (!isFinishing())
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
    public void goToStep5() {
        Intent i = ServicesStep5Activity.getStartIntent(this);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    public void openBankPaymentActivity() {
        PaymentManager.getInstance().startMolpayActivity(this,
                getViewModel().getTotalCost(), mAppointmentId,
                getViewModel().getPhoneNumberOfUser(),
                getViewModel().getEmailOfUser(),
                getViewModel().getNameOfUser(), getString(R.string.bill_description));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MOLPayActivity.MOLPayXDK && resultCode == RESULT_OK) {
            boolean success = getViewModel().setTxn_IDPayment(data);
            if (success) {
                if (!isFinishing())
                    AlertManager.getInstance(this).showLongToast(getString(R.string.payment_success));
                goToStep5();
            } else {
                if (!isFinishing())
                    AlertManager.getInstance(this).showLongToast(getString(R.string.payment_error_something));
            }
        }

    }

    private ServiceOrderUserInput getOrderUserInput() {
        return getViewModel().getDataManager().getServiceOrderUserInput();
    }
}
