package com.appyhome.appyproduct.mvvm.ui.bookingservices.step3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityServicesBookingStep3Binding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.step4.ServicesStep4Activity;

import javax.inject.Inject;

public class ServicesStep3Activity extends BaseActivity<ActivityServicesBookingStep3Binding, ServicesStep3ViewModel> implements ServicesStep3Navigator, View.OnClickListener {

    @Inject
    ServicesStep3ViewModel mServicesStep3ViewModel;

    ActivityServicesBookingStep3Binding mActivityServicesBookingStep3Binding;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ServicesStep3Activity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityServicesBookingStep3Binding = getViewDataBinding();
        mServicesStep3ViewModel.setNavigator(this);
        mActivityServicesBookingStep3Binding.btnNext.setOnClickListener(this);
        setTitle("Set Location");
        activeBackButton();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
                goToStep4();
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
    public ServicesStep3ViewModel getViewModel() {
        return mServicesStep3ViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_services_booking_step3;
    }

    @Override
    public void goToStep4() {
        startActivity(ServicesStep4Activity.getStartIntent(this));
    }
}
