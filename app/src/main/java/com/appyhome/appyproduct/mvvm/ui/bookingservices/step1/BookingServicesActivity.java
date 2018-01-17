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

public class BookingServicesActivity extends BaseActivity<ActivityServicesBookingStep1Binding, BookingServicesViewModel> implements BookingServicesNavigator, View.OnClickListener {

    @Inject
    BookingServicesViewModel mBookingServicesViewModel;

    ActivityServicesBookingStep1Binding mActivityServicesBookingStep1Binding;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, BookingServicesActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityServicesBookingStep1Binding = getViewDataBinding();
        mBookingServicesViewModel.setNavigator(this);
        mActivityServicesBookingStep1Binding.btnNext.setOnClickListener(this);
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
    public BookingServicesViewModel getViewModel() {
        return mBookingServicesViewModel;
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
