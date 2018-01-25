package com.appyhome.appyproduct.mvvm.ui.bookingservices.step5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityServicesBookingStep5Binding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.ServiceOrderInfo;
import com.appyhome.appyproduct.mvvm.ui.main.MainActivity;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

public class ServicesStep5Activity extends BaseActivity<ActivityServicesBookingStep5Binding, ServicesStep5ViewModel> implements ServicesStep5Navigator, View.OnClickListener {

    @Inject
    ServicesStep5ViewModel mServicesStep5ViewModel;

    ActivityServicesBookingStep5Binding mBinder;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ServicesStep5Activity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mServicesStep5ViewModel);
        mBinder.rlMainView.setVisibility(View.INVISIBLE);
        mServicesStep5ViewModel.setNavigator(this);
        mBinder.btnNext.setOnClickListener(this);
        mBinder.btnViewRequest.setOnClickListener(this);
        mBinder.tvOrderId.setText("Order Id: #"  + ServiceOrderInfo.getInstance().getAppointmentId());
        mServicesStep5ViewModel.createAppointment(ServiceOrderInfo.getInstance().getAppointmentCreateRequest());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnViewRequest:
                openRequestsScreen();
                break;
            case R.id.btnNext:
                backToHomeScreen();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public ServicesStep5ViewModel getViewModel() {
        return mServicesStep5ViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_services_booking_step5;
    }

    @Override
    public void openRequestsScreen() {
        Intent i = MainActivity.getStartIntent(this);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra(MainActivity.TAB, MainActivity.TAB_REQUEST);
        startActivity(i);
    }

    @Override
    public void backToHomeScreen() {
        Intent i = MainActivity.getStartIntent(this);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        backToHomeScreen();
    }

    @Override
    public void showCongratulationForm() {
        mBinder.rlMainView.setVisibility(View.VISIBLE);
    }

    @Override
    public void handleErrorService(Throwable throwable) {
        AlertManager.getInstance(this).showLongToast(getString(R.string.error_network_general));
        this.finish();
    }
}
