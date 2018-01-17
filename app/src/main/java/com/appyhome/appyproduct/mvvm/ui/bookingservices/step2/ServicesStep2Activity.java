package com.appyhome.appyproduct.mvvm.ui.bookingservices.step2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityServicesBookingStep2Binding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;

import javax.inject.Inject;

public class ServicesStep2Activity extends BaseActivity<ActivityServicesBookingStep2Binding, ServicesStep2ViewModel> implements ServicesStep2Navigator, View.OnClickListener {

    @Inject
    ServicesStep2ViewModel mServicesStep2ViewModel;

    ActivityServicesBookingStep2Binding mActivityServicesBookingStep2Binding;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ServicesStep2Activity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityServicesBookingStep2Binding = getViewDataBinding();
        mServicesStep2ViewModel.setNavigator(this);
    }

    @Override
    public void onClick(View view) {

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
    public ServicesStep2ViewModel getViewModel() {
        return mServicesStep2ViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_services_booking_step2;
    }

}
