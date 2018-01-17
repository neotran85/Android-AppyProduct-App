package com.appyhome.appyproduct.mvvm.ui.bookingservices.step5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityServicesBookingStep5Binding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.main.MainActivity;

import javax.inject.Inject;

public class ServicesStep5Activity extends BaseActivity<ActivityServicesBookingStep5Binding, ServicesStep5ViewModel> implements ServicesStep5Navigator, View.OnClickListener {

    @Inject
    ServicesStep5ViewModel mServicesStep5ViewModel;

    ActivityServicesBookingStep5Binding mActivityServicesBookingStep5Binding;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ServicesStep5Activity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityServicesBookingStep5Binding = getViewDataBinding();
        mServicesStep5ViewModel.setNavigator(this);
        mActivityServicesBookingStep5Binding.btnNext.setOnClickListener(this);
        mActivityServicesBookingStep5Binding.btnViewRequest.setOnClickListener(this);
        setTitle("FINISH");
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
    public void handleError(Throwable throwable) {
        // handle error
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
        Intent i= MainActivity.getStartIntent(this);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
    @Override
    public void backToHomeScreen() {
        Intent i= MainActivity.getStartIntent(this);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
