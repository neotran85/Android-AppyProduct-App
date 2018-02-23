package com.appyhome.appyproduct.mvvm.ui.account.myprofile;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.FragmentMyProfileBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;
import com.appyhome.appyproduct.mvvm.ui.main.MainActivity;
import com.appyhome.appyproduct.mvvm.ui.account.myprofile.textinput.TextInputDialog;
import com.appyhome.appyproduct.mvvm.ui.account.myprofile.textinput.TextInputUIHandler;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

public class MyProfileFragment extends BaseFragment<FragmentMyProfileBinding, MyProfileViewModel> implements MyProfileNavigator, TextInputUIHandler.DialogInputHandler, View.OnClickListener {

    public static final String TAG = "MyProfileFragment";

    @Inject
    MyProfileViewModel mMyProfileViewModel;
    FragmentMyProfileBinding mBinder;
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
        mMyProfileViewModel.setNavigator(this);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mMyProfileViewModel);
        setUp();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMyProfileViewModel.fetchUserProfile();
    }

    private void setUp() {
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
        setTitle(getString(R.string.my_profile));
        mBinder.tvLogout.setOnClickListener(this);
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void handleErrorService(Throwable throwable) {
        AlertManager.getInstance(getBaseActivity()).showLongToast(getString(R.string.error_network_general));
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
            case R.id.etCity:
            case R.id.etState:
            case R.id.etPostCode:
                showAddressTextInputDialog();
                break;
        }
    }

    private void showAddressTextInputDialog() {
        if (mTextInputDialog != null) {
            mTextInputDialog.show(this.getActivity().getSupportFragmentManager(),
                    getResources().getString(R.string.my_profile_change_address), 4, mAddressHints);
        }
    }

    private void showEmailTextInputDialog() {
        if (mTextInputDialog != null) {
            mTextInputDialog.show(this.getActivity().getSupportFragmentManager(),
                    getResources().getString(R.string.my_profile_change_email), 1, mEmailHints);
        }
    }

    private void showPhoneTextInputDialog() {
        if (mTextInputDialog != null) {
            mTextInputDialog.show(this.getActivity().getSupportFragmentManager(),
                    getResources().getString(R.string.my_profile_change_phone), 1, mPhoneHints);
        }
    }

    private void showPasswordTextInputDialog() {
        if (mTextInputDialog != null) {
            mTextInputDialog.show(this.getActivity().getSupportFragmentManager(),
                    getResources().getString(R.string.my_profile_change_password), 3, mPasswordHints);
        }
    }


    @Override
    public void onOkayHandler() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvLogout:
                mMyProfileViewModel.logout();
                break;
        }
    }

    @Override
    public void backToHomeScreen() {
        Intent i = MainActivity.getStartIntent(getActivity());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    public void onCancelHandler() {

    }
}
