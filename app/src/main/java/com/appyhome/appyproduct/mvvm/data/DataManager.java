package com.appyhome.appyproduct.mvvm.data;

import com.appyhome.appyproduct.mvvm.data.local.db.DbHelper;
import com.appyhome.appyproduct.mvvm.data.local.prefs.PreferencesHelper;
import com.appyhome.appyproduct.mvvm.data.model.others.QuestionCardData;
import com.appyhome.appyproduct.mvvm.data.remote.ApiHelper;

import java.util.List;

import io.reactivex.Observable;


public interface DataManager extends DbHelper, PreferencesHelper, ApiHelper {

    void updateApiHeader(String phoneNumber, String accessToken);

    void setUserAsLoggedOut();

    Observable<Boolean> seedDatabaseQuestions();

    Observable<Boolean> seedDatabaseOptions();

    Observable<List<QuestionCardData>> getQuestionCardData();

    void updateUserInfo(
            String accessToken,
            LoggedInMode loggedInMode,
            String userName,
            String phoneNumber,
            String email,
            String profilePicPath);

    enum LoggedInMode {
        LOGGED_OUT(0),
        LOGGED_IN(1);

        private final int mType;

        LoggedInMode(int type) {
            mType = type;
        }

        public int getType() {
            return mType;
        }
    }
}
