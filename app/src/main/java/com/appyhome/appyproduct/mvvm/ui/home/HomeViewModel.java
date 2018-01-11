package com.appyhome.appyproduct.mvvm.ui.home;

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

public class HomeViewModel extends BaseViewModel<HomeNavigator> {

    public static final int NO_ACTION = -1, ACTION_ADD_ALL = 0, ACTION_DELETE_SINGLE = 1;

    private final ObservableField<String> appVersion = new ObservableField<>();
    private final ObservableField<String> userName = new ObservableField<>();
    private final ObservableField<String> userEmail = new ObservableField<>();
    private final ObservableField<String> userProfilePicUrl = new ObservableField<>();
    private final ObservableArrayList<QuestionCardData> questionDataList = new ObservableArrayList<>();

    private final MutableLiveData<List<QuestionCardData>> questionCardData;

    private int action = NO_ACTION;

    public HomeViewModel(DataManager dataManager,
                         SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        questionCardData = new MutableLiveData<>();
        loadQuestionCards();
    }

    public void loadQuestionCards() {
        getCompositeDisposable().add(getDataManager()
                .getQuestionCardData()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<QuestionCardData>>() {
                    @Override
                    public void accept(List<QuestionCardData> questionList) throws Exception {
                        if (questionList != null) {
                            action = ACTION_ADD_ALL;
                            questionCardData.setValue(questionList);
                        }
                    }
                }));
    }


    public void logout() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().doUserLogout()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<LogoutResponse>() {
                    @Override
                    public void accept(LogoutResponse response) throws Exception {
                        getDataManager().setUserAsLoggedOut();
                        setIsLoading(false);
                        getNavigator().openLoginActivity();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().handleError(throwable);
                    }
                }));
    }

    public MutableLiveData<List<QuestionCardData>> getQuestionCardData() {
        return questionCardData;
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
