package com.appyhome.appyproduct.mvvm.ui.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.model.api.SignUpResponse;
import com.appyhome.appyproduct.mvvm.databinding.ActivityRegisterBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.main.MainActivity;
import com.appyhome.appyproduct.mvvm.utils.AppLogger;
import com.appyhome.appyproduct.mvvm.utils.ValidationUtils;

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

    }

    @Override
    public void register() {
        String firstName = mActivityRegisterBinding.etFirstName.getText().toString();
        if (firstName == null || firstName.length() == 0) {
            showError(getString(R.string.register_error_missing_first_name));
            mActivityRegisterBinding.etFirstName.requestFocus();
            return;
        }
        String lastName = mActivityRegisterBinding.etLastName.getText().toString();
        if (lastName == null || lastName.length() == 0) {
            showError(getString(R.string.register_error_missing_last_name));
            mActivityRegisterBinding.etLastName.requestFocus();
            return;
        }
        String email = mActivityRegisterBinding.etEmailAddress.getText().toString();
        if (email == null || email.length() == 0) {
            showError(getString(R.string.register_error_missing_email_address));
            mActivityRegisterBinding.etEmailAddress.requestFocus();
            return;
        } else if (!ValidationUtils.isEmailValid(email)) {
            mActivityRegisterBinding.etEmailAddress.requestFocus();
            showError(getString(R.string.register_error_invalid_email));
            return;
        }

        String phoneNumber = mActivityRegisterBinding.etNumberPhone.getText().toString();
        if (phoneNumber == null || phoneNumber.length() == 0) {
            mActivityRegisterBinding.etNumberPhone.requestFocus();
            showError(getString(R.string.register_error_missing_phone));
            return;
        } else if (!ValidationUtils.isPhoneNumberValid(phoneNumber)) {
            mActivityRegisterBinding.etNumberPhone.requestFocus();
            showError(getString(R.string.register_error_invalid_phone));
            return;
        }

        String password = mActivityRegisterBinding.etAccountPassword.getText().toString();
        if (password == null || password.length() == 0) {
            showError(getString(R.string.register_error_missing_password));
            mActivityRegisterBinding.etAccountPassword.requestFocus();
            return;
        } else if (!ValidationUtils.isPasswordValid(password)) {
            showError(getString(R.string.register_error_invalid_password));
            mActivityRegisterBinding.etAccountPassword.requestFocus();
            return;
        }

        String retypedPassword = mActivityRegisterBinding.etRetypedPassword.getText().toString();
        if (retypedPassword == null || retypedPassword.length() == 0) {
            showError(getString(R.string.register_error_missing_retyped_password));
            mActivityRegisterBinding.etRetypedPassword.requestFocus();
            return;
        }

        if (!password.equals(retypedPassword)) {
            mActivityRegisterBinding.etRetypedPassword.requestFocus();
            showError(getString(R.string.register_error_password_not_matched));
            return;
        }
        hideKeyboard();
        mRegisterViewModel.register(firstName, lastName, email, phoneNumber, password);
    }

    private void showError(String text) {
        mActivityRegisterBinding.txtError.setText(text);
        mActivityRegisterBinding.txtError.setVisibility(text.length() > 0 ? View.VISIBLE : View.INVISIBLE);
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
        return R.layout.activity_register;
    }

    @Override
    public void handleSignUpResponse(SignUpResponse response) {
        AppLogger.d(response.toString());
        if (response == null || response.getStatusCode() == null
                || response.getStatusCode().length() <= 0
                || response.getMessage() == null
                || response.getMessage().length() <= 0) {
            showError(getString(R.string.register_error_internal_server));
            return;
        }

        String statusCode = response.getStatusCode();
        String message = response.getMessage();
        if (message.equals("phone_number_duplicate")) {
            showError(getString(R.string.register_error_phone_duplicated));
            return;
        }
        if (statusCode.equals("200")) {
            if (message.contains("user_created")) {
                String[] result = message.split(":");
                if (result != null && result.length == 2) {
                    String userId = result[1];
                    showError(getString(R.string.register_success_logged));
                    openMainActivity();
                    return;
                }
            }
            showError(getString(R.string.register_error_something));
            return;
        }
    }

}
