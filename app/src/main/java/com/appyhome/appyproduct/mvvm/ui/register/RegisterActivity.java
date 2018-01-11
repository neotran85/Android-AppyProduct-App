package com.appyhome.appyproduct.mvvm.ui.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityRegisterBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.main.MainActivity;

import javax.inject.Inject;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding, RegisterViewModel> implements RegisterNavigator {

    @Inject
    RegisterViewModel mRegisterViewModel;

    ActivityRegisterBinding mActivityRegisterBinding;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRegisterBinding = getViewDataBinding();
        mRegisterViewModel.setNavigator(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.getStartIntent(RegisterActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void register() {
        openMainActivity();
        /*
        String phoneNumber = mActivityLoginBinding.etPhoneNumber.getText().toString();
        String password = mActivityLoginBinding.etPassword.getText().toString();
        if (mRegisterViewModel.isPhoneNumberAndPasswordValid(phoneNumber, password)) {
            hideKeyboard();
            mRegisterViewModel.login(phoneNumber, password);
        } else {
            Toast.makeText(this, getString(R.string.invalid_phone_password), Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public RegisterViewModel getViewModel() {
        return mRegisterViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

}
