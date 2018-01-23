package com.appyhome.appyproduct.mvvm.ui.bookingservices.step1;

import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.RadioButton;

import com.appyhome.appyproduct.mvvm.R;

import java.util.ArrayList;

public class HomeCleaningOptionView {
    private View mMainView;

    public HomeCleaningOptionView(View mainView) {
        mMainView = mainView;
    }

    public ArrayList<String> getResultStrings() {
        ArrayList<String> result = new ArrayList<>();
        RadioButton rbHighRise = mMainView.findViewById(R.id.rbHighRise);
        RadioButton rbLanRise = mMainView.findViewById(R.id.rbLanRise);
        if(rbHighRise.isChecked()) {
            result.add(rbHighRise.getText().toString());
        }
        if(rbLanRise.isChecked()) {
            result.add(rbLanRise.getText().toString());
        }
        RadioButton rbYes = mMainView.findViewById(R.id.rbYes);
        RadioButton rbNo = mMainView.findViewById(R.id.rbNo);
        if(rbYes.isChecked()) {
            result.add("provide cleaning supplies");
        }
        if(rbNo.isChecked()) {
            result.add("no provide cleaning supplies");
        }
        TextInputEditText etRoomNumber = mMainView.findViewById(R.id.etRoomNumber);
        if(etRoomNumber.getText().length() > 0) {
            result.add(etRoomNumber + " rooms");
        }
        return result;
    }
}