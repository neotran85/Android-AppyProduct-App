package com.appyhome.appyproduct.mvvm.ui.bookingservices.step1;

import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.RadioButton;

import com.appyhome.appyproduct.mvvm.R;

import java.util.ArrayList;

public class AirConOptionView {
    private View mMainView;
    private int numberOfAirCons = 1;

    public AirConOptionView(View mainView) {
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
        RadioButton rbService1 = mMainView.findViewById(R.id.rbService1);
        RadioButton rbService2 = mMainView.findViewById(R.id.rbService2);
        if(rbService1.isChecked()) {
            result.add(rbService1.getText().toString());
        }
        if(rbService2.isChecked()) {
            result.add(rbService2.getText().toString());
        }

        RadioButton rbMounting1 = mMainView.findViewById(R.id.rbMounting1);
        RadioButton rbMounting2 = mMainView.findViewById(R.id.rbMounting2);
        if(rbMounting1.isChecked()) {
            result.add(rbMounting1.getText().toString());
        }
        if(rbMounting2.isChecked()) {
            result.add(rbMounting2.getText().toString());
        }

        RadioButton rbSize1 = mMainView.findViewById(R.id.rbSize1);
        RadioButton rbSize2 = mMainView.findViewById(R.id.rbSize2);
        RadioButton rbSize3 = mMainView.findViewById(R.id.rbSize3);
        RadioButton rbSize4 = mMainView.findViewById(R.id.rbSize4);

        if(rbSize1.isChecked()) {
            result.add("air con size " + rbSize1.getText().toString());
        }
        if(rbSize2.isChecked()) {
            result.add("air con size " + rbSize2.getText().toString());
        }
        if(rbSize3.isChecked()) {
            result.add("air con size " + rbSize3.getText().toString());
        }
        if(rbSize4.isChecked()) {
            result.add("air con size unknown");
        }

        TextInputEditText etAirConNumber = mMainView.findViewById(R.id.etAirConNumber);
        if(etAirConNumber.getText().length() > 0) {
            numberOfAirCons = Integer.valueOf(etAirConNumber.getText().toString());
            result.add(numberOfAirCons + " air cons");
        }
        return result;
    }

    public int getNumberOfAirCons() {
        return numberOfAirCons;
    }
}