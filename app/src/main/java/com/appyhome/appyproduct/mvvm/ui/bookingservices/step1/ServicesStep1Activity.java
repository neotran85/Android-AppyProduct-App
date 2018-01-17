package com.appyhome.appyproduct.mvvm.ui.bookingservices.step1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityServicesBookingStep1Binding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.step2.ServicesStep2Activity;

import javax.inject.Inject;

public class ServicesStep1Activity extends BaseActivity<ActivityServicesBookingStep1Binding, ServicesStep1ViewModel> implements ServicesStep1Navigator, View.OnClickListener {

    @Inject
    ServicesStep1ViewModel mServicesStep1ViewModel;

    ActivityServicesBookingStep1Binding mActivityServicesBookingStep1Binding;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ServicesStep1Activity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityServicesBookingStep1Binding = getViewDataBinding();
        mServicesStep1ViewModel.setNavigator(this);
        mActivityServicesBookingStep1Binding.btnNext.setOnClickListener(this);
        setTitle("Home Cleaning");
        activeBackButton();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
                goToStep2();
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
    public ServicesStep1ViewModel getViewModel() {
        return mServicesStep1ViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_services_booking_step1;
    }

    @Override
    public void goToStep2() {
        startActivity(ServicesStep2Activity.getStartIntent(this));
    }

}
