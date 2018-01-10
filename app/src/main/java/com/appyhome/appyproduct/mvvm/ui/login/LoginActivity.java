package com.appyhome.appyproduct.mvvm.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityLoginBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.main.MainActivity;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements LoginNavigator {

    @Inject
    LoginViewModel mLoginViewModel;

    ActivityLoginBinding mActivityLoginBinding;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityLoginBinding = getViewDataBinding();
        mLoginViewModel.setNavigator(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.getStartIntent(LoginActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void login() {
        openMainActivity();
        /*
        String phoneNumber = mActivityLoginBinding.etPhoneNumber.getText().toString();
        String password = mActivityLoginBinding.etPassword.getText().toString();
        if (mLoginViewModel.isPhoneNumberAndPasswordValid(phoneNumber, password)) {
            hideKeyboard();
            mLoginViewModel.login(phoneNumber, password);
        } else {
            Toast.makeText(this, getString(R.string.invalid_phone_password), Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public LoginViewModel getViewModel() {
        return mLoginViewModel;
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
