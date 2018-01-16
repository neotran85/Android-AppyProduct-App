package com.appyhome.appyproduct.mvvm.ui.myprofile;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.FragmentMyProfileBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;
import com.appyhome.appyproduct.mvvm.ui.myprofile.textinput.TextInputDialog;
import com.appyhome.appyproduct.mvvm.ui.myprofile.textinput.TextInputUIHandler;

import javax.inject.Inject;

public class MyProfileFragment extends BaseFragment<FragmentMyProfileBinding, MyProfileViewModel> implements MyProfileNavigator, TextInputUIHandler.DialogInputHandler {

    public static final String TAG = "MyProfileFragment";

    @Inject
    MyProfileViewModel mMyProfileViewModel;
    FragmentMyProfileBinding mFragmentMyProfileBinding;
    private int etCurrent;

    private TextInputUIHandler mTextInputHandler;
    private TextInputDialog mTextInputDialog;

    private String[] mPhoneHints;
    private String[] mPasswordHints;
    private String[] mEmailHints;
    private String[] mAddressHints;

    public static MyProfileFragment newInstance() {
        Bundle args = new Bundle();
        MyProfileFragment fragment = new MyProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setUp() {
        mMyProfileViewModel.setNavigator(this);
        mFragmentMyProfileBinding = getViewDataBinding();
        mTextInputDialog = TextInputDialog.newInstance();
        mPasswordHints = new String[]{getResources().getString(R.string.my_profile_hint_old_password),
                getResources().getString(R.string.my_profile_hint_new_password),
                getResources().getString(R.string.my_profile_hint_retyped_password), ""};
        mAddressHints = new String[]{getResources().getString(R.string.my_profile_hint_address),
                getResources().getString(R.string.my_profile_hint_address_city),
                getResources().getString(R.string.my_profile_hint_address_state),
                getResources().getString(R.string.my_profile_hint_address_code)};
        mPhoneHints = new String[]{getResources().getString(R.string.my_profile_hint_phone),
                "", "", ""};
        mEmailHints = new String[]{getResources().getString(R.string.my_profile_hint_email),
                "", "", ""};
    }

    @Override
    public MyProfileViewModel getViewModel() {
        return mMyProfileViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_profile;
    }

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void onFieldClick(View view) {
        switch (view.getId()) {
            case R.id.etPassword:
                showPasswordTextInputDialog();
                break;
            case R.id.etPhoneNumber:
                showPhoneTextInputDialog();
                break;
            case R.id.etEmailAddress:
                showEmailTextInputDialog();
                break;
            case R.id.etAddress:
                showAddressTextInputDialog();
                break;
        }
    }
    private void showAddressTextInputDialog() {
        if (mTextInputDialog != null) {
            mTextInputDialog.show(this.getActivity().getSupportFragmentManager(), 4, mAddressHints);
        }
    }
    private void showEmailTextInputDialog() {
        if (mTextInputDialog != null) {
            mTextInputDialog.show(this.getActivity().getSupportFragmentManager(), 1, mEmailHints);
        }
    }
    private void showPhoneTextInputDialog() {
        if (mTextInputDialog != null) {
            mTextInputDialog.show(this.getActivity().getSupportFragmentManager(), 1, mPhoneHints);
        }
    }
    private void showPasswordTextInputDialog() {
        if (mTextInputDialog != null) {
            mTextInputDialog.show(this.getActivity().getSupportFragmentManager(), 3, mPasswordHints);
        }
    }


    @Override
    public void onOkayHandler() {

    }

    @Override
    public void onCancelHandler() {

    }
}
