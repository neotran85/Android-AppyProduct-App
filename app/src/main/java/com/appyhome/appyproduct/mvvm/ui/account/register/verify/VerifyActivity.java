package com.appyhome.appyproduct.mvvm.ui.account.register.verify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityVerifyBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

public class VerifyActivity extends BaseActivity<ActivityVerifyBinding, VerifyViewModel> implements VerifyNavigator {

    @Inject
    VerifyViewModel mVerifyViewModel;

    ActivityVerifyBinding mBinder;

    @Inject
    int mLayoutId;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, VerifyActivity.class);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mVerifyViewModel);
        mBinder.setNavigator(this);
        mVerifyViewModel.setNavigator(this);
        mBinder.etVerifyingCode.addTextChangedListener(new InputTextWatcher(mBinder.etVerifyingCode));
    }

    @Override
    public void resendNewCode() {
        if (isNetworkConnected()) {
            hideKeyboard();
            getViewModel().doVerifyUser();
        }
    }

    @Override
    public void sendVerifyingCode() {
        String verifyCode = mBinder.etVerifyingCode.getText().toString();
        if (verifyCode == null || verifyCode.length() <= 0) {
            if (!isFinishing())
                AlertManager.getInstance(this).showQuickToast(getString(R.string.pls_enter_the_code));
            return;
        }
        if (isNetworkConnected()) {
            hideKeyboard();
            getViewModel().verifyTrue(verifyCode);
        } else {
            if (!isFinishing())
                AlertManager.getInstance(this).showQuickToast(getString(R.string.error_network_not_connected));
        }
    }

    @Override
    public void showCodeSentMessage() {
        clearTextInputError(mBinder.etVerifyingCode);
        mBinder.etVerifyingCode.setText("");
        mBinder.etVerifyingCode.requestFocus();
        showError(getString(R.string.verification_code_message));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getViewModel().isVerified()) {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void doAfterRegisterSucceeded() {
        if (!isFinishing())
            AlertManager.getInstance(this).showOKDialog("", getString(R.string.phone_activated), (dialogInterface, i) -> {
                AlertManager.getInstance(this).closeDialog();
                setResult(RESULT_OK);
                finish();
            });
    }

    @Override
    public void showAlert(String str) {
        if (!isFinishing())
            AlertManager.getInstance(this).showLongToast(str);
    }

    private void showTextInputError(TextInputEditText edt) {
        mBinder.etVerifyingCode.getParent().requestChildFocus(mBinder.etVerifyingCode, mBinder.etVerifyingCode);
        if (edt.getText().length() > 0)
            edt.setTextColor(ContextCompat.getColor(this, R.color.red_dark));
        else
            edt.setHintTextColor(ContextCompat.getColor(this, R.color.red_dark2));
    }

    @Override
    public void handleErrorService(Throwable throwable) {
        if (!isFinishing())
            AlertManager.getInstance(this).showErrorToast(getString(R.string.error_network_general));
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
    public void showErrorServer() {
        showError(getString(R.string.login_error_internal_server));
    }

    @Override
    public void showErrorOthers() {
        showTextInputError(mBinder.etVerifyingCode);
        mBinder.etVerifyingCode.getParent().requestChildFocus(mBinder.etVerifyingCode, mBinder.etVerifyingCode);
        showError(getString(R.string.verification_code_error));
    }

    private void clearTextInputError(TextInputEditText edt) {
        edt.setTextColor(ContextCompat.getColor(this, R.color.medium_dark_gray));
        edt.setHintTextColor(ContextCompat.getColor(this, R.color.dark_gray));
        showError("");
    }

    private class InputTextWatcher implements TextWatcher {
        private TextInputEditText edt;

        public InputTextWatcher(TextInputEditText et) {
            edt = et;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            clearTextInputError(edt);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
