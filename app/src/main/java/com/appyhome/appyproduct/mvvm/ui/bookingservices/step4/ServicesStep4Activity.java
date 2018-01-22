package com.appyhome.appyproduct.mvvm.ui.bookingservices.step4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityServicesBookingStep4Binding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.ServiceOrderInfo;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.step1.ServicesAdapter;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.step5.ServicesStep5Activity;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;
import com.appyhome.appyproduct.mvvm.utils.manager.PaymentManager;
import com.molpay.molpayxdk.MOLPayActivity;

import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

public class ServicesStep4Activity extends BaseActivity<ActivityServicesBookingStep4Binding, ServicesStep4ViewModel> implements ServicesStep4Navigator, View.OnClickListener {

    @Inject
    ServicesStep4ViewModel mServicesStep4ViewModel;

    ActivityServicesBookingStep4Binding mBinder;

    private ArrayList<JSONObject> mServicesList;

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
        mServicesStep4ViewModel.setNavigator(this);
        mBinder.btnNext.setOnClickListener(this);
        mBinder.rlVisaPayment.setOnClickListener(this);
    }
    private void setUpData() {
        mServicesStep4ViewModel.setAddress(ServiceOrderInfo.getInstance().getAddress());
        mServicesStep4ViewModel.setTimeSlot1(ServiceOrderInfo.getInstance().getTimeSlot1());
        mServicesStep4ViewModel.setTimeSlot2(ServiceOrderInfo.getInstance().getTimeSlot2());
        mServicesStep4ViewModel.setTimeSlot3(ServiceOrderInfo.getInstance().getTimeSlot3());
        mServicesStep4ViewModel.setTotalCost(ServiceOrderInfo.getInstance().getTotalCost());
        mServicesStep4ViewModel.setNameService(ServiceOrderInfo.getInstance().getServiceName());
        mServicesStep4ViewModel.setAdditionalDetail(ServiceOrderInfo.getInstance().getAdditionalInfo());
        mServicesStep4ViewModel.setAdditionalServices(ServiceOrderInfo.getInstance().getExtraServices());

        mServicesList = new ArrayList<>();
        mServicesList.add(ServiceOrderInfo.getInstance().getSelectedService());
        mBinder.lvServices.setAdapter(new ServicesAdapter(this, mServicesList));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
                goToStep5();
                break;
            case R.id.rlVisaPayment:
                openPaymentActivity();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void handleError(Throwable throwable) {
        // handle error
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
        startActivity(ServicesStep5Activity.getStartIntent(this));
    }

    @Override
    public void openPaymentActivity() {
        PaymentManager.getInstance().startPaymentActivity(this, "2.0");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MOLPayActivity.MOLPayXDK && resultCode == RESULT_OK) {
            Log.d(MOLPayActivity.MOLPAY, "MOLPay result = " + data.getStringExtra(MOLPayActivity.MOLPayTransactionResult));
            AlertManager.getInstance(this).showLongToast(data.getStringExtra(MOLPayActivity.MOLPayTransactionResult));
        }

    }
}
