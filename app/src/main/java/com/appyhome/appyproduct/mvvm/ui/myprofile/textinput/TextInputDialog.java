package com.appyhome.appyproduct.mvvm.ui.myprofile.textinput;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.DialogTextInputBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseDialog;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class TextInputDialog extends BaseDialog implements TextInputCallback {

    private static final String TAG = "TextInputDialog";

    @Inject
    TextInputViewModel mTextInputViewModel;

    private TextInputUIHandler mUIHandler;
    private int mNumberOfFields;
    private String[] mHints;
    private String mTitle;

    public static TextInputDialog newInstance() {
        TextInputDialog fragment = new TextInputDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        DialogTextInputBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.dialog_text_input, container, false);
        View view = binding.getRoot();

        AndroidSupportInjection.inject(this);

        binding.setViewModel(mTextInputViewModel);

        mTextInputViewModel.setNavigator(this);
        mTextInputViewModel.setNumberOfFields(mNumberOfFields);
        mUIHandler = new TextInputUIHandler(view);
        mTextInputViewModel.setHint0(mHints[0]);
        mTextInputViewModel.setHint1(mHints[1]);
        mTextInputViewModel.setHint2(mHints[2]);
        mTextInputViewModel.setHint3(mHints[3]);
        mTextInputViewModel.setErrorText(mTitle);
        return view;
    }

    public TextInputUIHandler getHandler() {
        return mUIHandler;
    }

    public void show(FragmentManager fragmentManager, String title, int numberOfFields, String[] hints) {
        super.show(fragmentManager, TAG);
        mNumberOfFields = numberOfFields;
        mHints = hints;
        mTitle = title;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void dismissDialog() {
        dismissDialog(TAG);
    }

}
