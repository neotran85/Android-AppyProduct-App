package com.appyhome.appyproduct.mvvm.ui.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.remote.ApiUrlConfig;
import com.appyhome.appyproduct.mvvm.databinding.ActivityLoginBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.register.RegisterActivity;
import com.appyhome.appyproduct.mvvm.utils.helper.NetworkUtils;
import com.appyhome.appyproduct.mvvm.utils.helper.TextUtils;
import com.appyhome.appyproduct.mvvm.utils.helper.ValidationUtils;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import java.util.ArrayList;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements LoginNavigator, View.OnClickListener {

    @Inject
    LoginViewModel mLoginViewModel;

    ActivityLoginBinding mBinder;

    public static final int REQUEST_SIGN_UP = 2222;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mLoginViewModel);
        mLoginViewModel.setNavigator(this);

        ViewUtils.setOnClickListener(this, mBinder.btnForgetPassword,
                mBinder.btnSignUp);

        ArrayList<TextInputEditText> arrayTextInputs = new ArrayList<>();
        arrayTextInputs.add(mBinder.etPhoneNumber);
        arrayTextInputs.add(mBinder.etPassword);
        for (int i = 0; i < arrayTextInputs.size(); i++) {
            final TextInputEditText edt = arrayTextInputs.get(i);
            edt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    edt.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.white));
                    edt.setHintTextColor(ContextCompat.getColor(LoginActivity.this, R.color.hint_text));
                    showError("");
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        Intent data = getIntent();
        if (data.hasExtra("message")) {
            String message = data.getStringExtra("message");
            showError(message);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignUp:
                openSignUpActivity();
                break;
            case R.id.btnForgetPassword:
                openForgetPassword();
                break;
        }

    }

    private void openForgetPassword() {
        AlertManager.getInstance(this).openInformationBrowser("Forget Password",
                ApiUrlConfig.URL_FORGET_PASSWORD);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void openSignUpActivity() {
        Intent intent = RegisterActivity.getStartIntent(LoginActivity.this);
        intent.putExtra("phone", mBinder.etPhoneNumber.getText().toString());
        startActivityForResult(intent, REQUEST_SIGN_UP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_SIGN_UP:
                if (resultCode == RESULT_OK) {
                    doAfterLoginSucceeded();
                }
                break;
        }
    }

    @Override
    public void doAfterLoginSucceeded() {
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void handleErrorService(Throwable throwable) {
        AlertManager.getInstance(this).showLongToast(getString(R.string.error_network_general));
    }

    @Override
    public void login() {
        View view = getCurrentFocus();
        if (view != null) {
            view.clearFocus();
        }
        if (!NetworkUtils.isNetworkConnected(this)) {
            return;
        }
        String phoneNumber = TextUtils.getString(mBinder.etPhoneNumber.getText().toString());
        String password = TextUtils.getString(mBinder.etPassword.getText().toString());
        if (phoneNumber.length() == 0) {
            showError(getString(R.string.login_error_missing_phone));
            showTextInputError(mBinder.etPhoneNumber);
            return;
        } else {
            phoneNumber = ValidationUtils.correctNumberPhone(phoneNumber, "60");
            if (!ValidationUtils.isPhoneNumberValid(phoneNumber)) {
                showError(getString(R.string.login_error_invalid_phone));
                showTextInputError(mBinder.etPhoneNumber);
                return;
            }
        }

        if (password.length() == 0) {
            showError(getString(R.string.login_error_missing_phone));
            showTextInputError(mBinder.etPassword);
            return;
        } else if (!ValidationUtils.isPasswordValid(password)) {
            showError(getString(R.string.login_error_invalid_password));
            showTextInputError(mBinder.etPassword);
            return;
        }
        hideKeyboard();
        mLoginViewModel.login(phoneNumber, password);
    }

    private void showTextInputError(TextInputEditText edt) {
        edt.requestFocus();
        if (edt.getText().length() > 0)
            edt.setTextColor(ContextCompat.getColor(this, R.color.red_dark));
        else
            edt.setHintTextColor(ContextCompat.getColor(this, R.color.red_dark2));
    }

    @Override
    public void showSuccessLogin() {
        AlertManager.getInstance(this).showLongToast(getString(R.string.login_success));
    }

    @Override
    public void showErrorServer() {
        showError(getString(R.string.login_error_internal_server));
    }

    @Override
    public void showErrorOthers() {
        showError(getString(R.string.login_error));
    }

    private void showError(String text) {
        mBinder.txtError.setText(text);
        mBinder.txtError.setVisibility(text.length() > 0 ? View.VISIBLE : View.INVISIBLE);
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
    public void showSignUpDialog() {
        AlertManager.getInstance(this).showDialog(getString(R.string.title_login_error), getString(R.string.suggestion_sign_up), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openSignUpActivity();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }
}
