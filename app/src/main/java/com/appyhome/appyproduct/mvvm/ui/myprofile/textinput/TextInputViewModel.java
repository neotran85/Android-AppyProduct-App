package com.appyhome.appyproduct.mvvm.ui.myprofile.textinput;

import android.databinding.ObservableField;
import android.view.View;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;


public class TextInputViewModel extends BaseViewModel<TextInputCallback> {

    private final ObservableField<Integer> numberOfFields = new ObservableField<>(4);
    private final ObservableField<String> hint0 = new ObservableField<String>("");
    private final ObservableField<String> hint1 = new ObservableField<String>("");
    private final ObservableField<String> hint2 = new ObservableField<String>("");
    private final ObservableField<String> hint3 = new ObservableField<String>("");
    private final ObservableField<String> errorText = new ObservableField<String>("");

    public TextInputViewModel(DataManager dataManager,
                              SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onLaterClick() {
        getNavigator().dismissDialog();
    }

    public void onSubmitClick() {
        getNavigator().dismissDialog();
    }

    public ObservableField<Integer> getNumberOfFields() {
        return numberOfFields;
    }

    public void setNumberOfFields(int number) {
        numberOfFields.set(number);
    }

    public int isFieldVisible0() {
        return isFieldVisible(0);
    }

    public int isFieldVisible1() {
        return isFieldVisible(1);
    }

    public int isFieldVisible2() {
        return isFieldVisible(2);
    }

    public int isFieldVisible3() {
        return isFieldVisible(3);
    }

    public int isFieldVisible(int index) {
        if (index < 0 || index >= numberOfFields.get()) {
            return View.GONE;
        }
        return View.VISIBLE;
    }

    public void setHint0(String hint) {
        hint0.set(hint);
    }

    public void setHint1(String hint) {
        hint1.set(hint);
    }

    public void setHint2(String hint) {
        hint2.set(hint);
    }

    public void setHint3(String hint) {
        hint3.set(hint);
    }

    public String getHint0() {
        return hint0.get();
    }

    public String getHint1() {
        return hint1.get();
    }

    public String getHint2() {
        return hint2.get();
    }

    public String getHint3() {
        return hint3.get();
    }

    public String getErrorText() {
        return errorText.get();
    }

    public void setErrorText(String text) {
        errorText.set(text);
    }

}
