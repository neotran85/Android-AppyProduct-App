package com.appyhome.appyproduct.mvvm.ui.myprofile.textinput;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appyhome.appyproduct.mvvm.R;

import java.util.ArrayList;

public class TextInputUIHandler {
    private View mViewMain;
    private ArrayList<TextInputEditText> mArrayEts;
    private DialogInputHandler mHandler;
    private TextView txtErrorTitle;

    public TextInputUIHandler(View mainView) {
        mViewMain = mainView;
        mArrayEts = new ArrayList<>();
        mArrayEts.add((TextInputEditText) mViewMain.findViewById(R.id.etNewValue0));
        mArrayEts.add((TextInputEditText) mViewMain.findViewById(R.id.etNewValue1));
        mArrayEts.add((TextInputEditText) mViewMain.findViewById(R.id.etNewValue2));
        mArrayEts.add((TextInputEditText) mViewMain.findViewById(R.id.etNewValue3));
        txtErrorTitle = mViewMain.findViewById(R.id.txtError);

        Button btnOkay = mViewMain.findViewById(R.id.btnSubmit);
        Button btnCancel = mViewMain.findViewById(R.id.btnCancel);

        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHandler != null)
                    mHandler.onOkayHandler();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHandler != null)
                    mHandler.onCancelHandler();
            }
        });
    }

    public String getNewValue(int index) {
        return mArrayEts.get(index).getText().toString();
    }
    public void setError(String text) {
        if (txtErrorTitle != null) {
            txtErrorTitle.setText(text);
            txtErrorTitle.setVisibility((text != null && text.length() > 0)
                    ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void setDialogInputHandler(DialogInputHandler handler) {
        mHandler = handler;
    }

    public void setUp(int numberOfFields, String title, String [] arrayValues, int[] arrayHints) {
        for (TextInputEditText edt : mArrayEts) {
            edt.setText("");
            edt.setHint("");
            edt.setVisibility(View.GONE);
        }
        txtErrorTitle.setText(title);
        Context context = mViewMain.getContext();
        for (int i = 0; i < numberOfFields; i++) {
            TextInputEditText edt = mArrayEts.get(i);
            edt.setVisibility(View.VISIBLE);
            edt.setHint(context.getString(arrayHints[i]));
            edt.setText(arrayValues[i]);
        }
    }

    public interface DialogInputHandler {
        void onOkayHandler();

        void onCancelHandler();
    }
}
