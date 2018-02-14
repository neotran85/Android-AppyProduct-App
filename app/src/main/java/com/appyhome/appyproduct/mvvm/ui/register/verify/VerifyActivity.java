package com.appyhome.appyproduct.mvvm.ui.register.verify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityVerifyBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

public class VerifyActivity extends BaseActivity<ActivityVerifyBinding, VerifyViewModel> implements VerifyNavigator, View.OnClickListener {

    @Inject
    VerifyViewModel mVerifyViewModel;

    ActivityVerifyBinding mBinder;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, VerifyActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mVerifyViewModel);
        mVerifyViewModel.setNavigator(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btSendCode:
                if(isNetworkConnected()) {
                    getViewModel().verifyTrue();
                }
                break;
            case R.id.btResendCode:
                if(isNetworkConnected()) {
                    getViewModel().doVerifyUser();
                }
                break;
        }
    }

    @Override
    public void showCodeSentMessage() {
        AlertManager.getInstance(this).showLongToast(getString(R.string.verification_code_message));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewUtils.setOnClickListener(this, mBinder.btResendCode, mBinder.btSendCode);
    }

    @Override
    public void doAfterRegisterSucceeded() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void handleErrorService(Throwable throwable) {
        AlertManager.getInstance(this).showLongToast(getString(R.string.error_network_general));
    }

    private void showError(String text) {
        mBinder.txtError.setText(text);
        mBinder.txtError.setVisibility(text.length() > 0 ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public VerifyViewModel getViewModel() {
        return mVerifyViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_verify;
    }

    @Override
    public void showSuccessLogin() {
        AlertManager.getInstance(this).showLongToast(getString(R.string.register_success_logged));
    }

    @Override
    public void showErrorServer() {
        showError(getString(R.string.login_error_internal_server));
    }

    @Override
    public void showErrorOthers() {
        showError(getString(R.string.verification_code_error));
    }

}
