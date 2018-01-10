package com.appyhome.appyproduct.mvvm.ui.myprofile;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.api.LogoutResponse;
import com.appyhome.appyproduct.mvvm.data.model.others.QuestionCardData;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import java.util.List;

import io.reactivex.functions.Consumer;

public class MyProfileViewModel extends BaseViewModel<MyProfileNavigator> {

    public static final int NO_ACTION = -1, ACTION_ADD_ALL = 0, ACTION_DELETE_SINGLE = 1;

    private final ObservableField<String> appVersion = new ObservableField<>();
    private final ObservableField<String> userName = new ObservableField<>();
    private final ObservableField<String> userEmail = new ObservableField<>();
    private final ObservableField<String> userProfilePicUrl = new ObservableField<>();
    private final ObservableArrayList<QuestionCardData> questionDataList = new ObservableArrayList<>();

    private int action = NO_ACTION;

    public MyProfileViewModel(DataManager dataManager,
                              SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public ObservableField<String> getAppVersion() {
        return appVersion;
    }

    public ObservableField<String> getUserName() {
        return userName;
    }

    public ObservableField<String> getUserEmail() {
        return userEmail;
    }

    public ObservableArrayList<QuestionCardData> getQuestionDataList() {
        return questionDataList;
    }

    public int getAction() {
        return action;
    }
}
