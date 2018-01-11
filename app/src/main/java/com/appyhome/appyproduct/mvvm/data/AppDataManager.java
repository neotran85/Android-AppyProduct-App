package com.appyhome.appyproduct.mvvm.data;

import android.content.Context;

import com.appyhome.appyproduct.mvvm.data.local.db.DbHelper;
import com.appyhome.appyproduct.mvvm.data.local.prefs.PreferencesHelper;
import com.appyhome.appyproduct.mvvm.data.model.api.BlogResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.LoginRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.LoginResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.LogoutResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.OpenSourceResponse;
import com.appyhome.appyproduct.mvvm.data.model.db.Option;
import com.appyhome.appyproduct.mvvm.data.model.db.Question;
import com.appyhome.appyproduct.mvvm.data.model.db.User;
import com.appyhome.appyproduct.mvvm.data.model.others.QuestionCardData;
import com.appyhome.appyproduct.mvvm.data.remote.ApiHeader;
import com.appyhome.appyproduct.mvvm.data.remote.ApiHelper;
import com.appyhome.appyproduct.mvvm.AppConstants;
import com.appyhome.appyproduct.mvvm.utils.CommonUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

@Singleton
public class AppDataManager implements DataManager {

    private final Context mContext;
    private final DbHelper mDbHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;

    @Inject
    public AppDataManager(Context context,
                          DbHelper dbHelper,
                          PreferencesHelper preferencesHelper,
                          ApiHelper apiHelper) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHelper.getApiHeader();
    }

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
        mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }

    @Override
    public Observable<Boolean> insertUser(User user) {
        return mDbHelper.insertUser(user);
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        return mDbHelper.getAllUsers();
    }

    @Override
    public Single<LoginResponse> doUserLogin(LoginRequest.ServerLoginRequest
                                                              request) {
        return mApiHelper.doUserLogin(request);
    }

    @Override
    public Single<LogoutResponse> doUserLogout() {
        return mApiHelper.doUserLogout();
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPreferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode);
    }

    @Override
    public String getCurrentUsername() {
        return mPreferencesHelper.getCurrentUsername();
    }

    @Override
    public void setCurrentUsername(String userName) {
        mPreferencesHelper.setCurrentUsername(userName);
    }
    @Override
    public String getCurrentPhoneNumber() {
        return mPreferencesHelper.getCurrentPhoneNumber();
    }

    @Override
    public void setCurrentPhoneNumber(String phoneNumber) {
        mPreferencesHelper.setCurrentPhoneNumber(phoneNumber);
    }
    @Override
    public String getCurrentUserEmail() {
        return mPreferencesHelper.getCurrentUserEmail();
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPreferencesHelper.setCurrentUserEmail(email);
    }

    @Override
    public String getCurrentUserProfilePicUrl() {
        return mPreferencesHelper.getCurrentUserProfilePicUrl();
    }

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPreferencesHelper.setCurrentUserProfilePicUrl(profilePicUrl);
    }

    @Override
    public void updateApiHeader(String phoneNumber, String accessToken) {
        mApiHelper.getApiHeader().getProtectedApiHeader().setPhoneNumber(phoneNumber);
        mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }

    @Override
    public void updateUserInfo(
            String accessToken,
            LoggedInMode loggedInMode,
            String username,
            String phoneNumber,
            String email,
            String profilePicPath) {

        setAccessToken(accessToken);
        setCurrentUserLoggedInMode(loggedInMode);
        setCurrentUsername(username);
        setCurrentUserEmail(email);
        setCurrentUserProfilePicUrl(profilePicPath);

        updateApiHeader(phoneNumber, accessToken);
    }

    @Override
    public void setUserAsLoggedOut() {
        updateUserInfo(
                null,
                LoggedInMode.LOGGED_OUT,
                null,
                null,
                null,
                null);
    }

    @Override
    public Observable<Boolean> isQuestionEmpty() {
        return mDbHelper.isQuestionEmpty();
    }

    @Override
    public Observable<Boolean> isOptionEmpty() {
        return mDbHelper.isOptionEmpty();
    }

    @Override
    public Observable<Boolean> saveQuestion(Question question) {
        return mDbHelper.saveQuestion(question);
    }

    @Override
    public Observable<Boolean> saveOption(Option option) {
        return mDbHelper.saveOption(option);
    }

    @Override
    public Observable<Boolean> saveQuestionList(List<Question> questionList) {
        return mDbHelper.saveQuestionList(questionList);
    }

    @Override
    public Observable<Boolean> saveOptionList(List<Option> optionList) {
        return mDbHelper.saveOptionList(optionList);
    }

    @Override
    public Observable<List<Question>> getAllQuestions() {
        return mDbHelper.getAllQuestions();
    }

    @Override
    public Observable<List<Option>> getOptionsForQuestionId(Long questionId) {
        return mDbHelper.getOptionsForQuestionId(questionId);
    }

    @Override
    public Observable<Boolean> seedDatabaseQuestions() {

        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();

        return mDbHelper.isQuestionEmpty()
                .concatMap(new Function<Boolean, ObservableSource<? extends Boolean>>() {
                    @Override
                    public ObservableSource<? extends Boolean> apply(Boolean isEmpty)
                            throws Exception {
                        if (isEmpty) {
                            Type type = $Gson$Types
                                    .newParameterizedTypeWithOwner(null, List.class,
                                            Question.class);
                            List<Question> questionList = gson.fromJson(
                                    CommonUtils.loadJSONFromAsset(mContext,
                                            AppConstants.SEED_DATABASE_QUESTIONS),
                                    type);

                            return saveQuestionList(questionList);
                        }
                        return Observable.just(false);
                    }
                });
    }

    @Override
    public Observable<Boolean> seedDatabaseOptions() {

        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();

        return mDbHelper.isOptionEmpty()
                .concatMap(new Function<Boolean, ObservableSource<? extends Boolean>>() {
                    @Override
                    public ObservableSource<? extends Boolean> apply(Boolean isEmpty)
                            throws Exception {
                        if (isEmpty) {
                            Type type = new TypeToken<List<Option>>() {
                            }
                                    .getType();
                            List<Option> optionList = gson.fromJson(
                                    CommonUtils.loadJSONFromAsset(mContext,
                                            AppConstants.SEED_DATABASE_OPTIONS),
                                    type);

                            return saveOptionList(optionList);
                        }
                        return Observable.just(false);
                    }
                });
    }

    @Override
    public Single<BlogResponse> getBlogApiCall() {
        return mApiHelper.getBlogApiCall();
    }

    @Override
    public Single<OpenSourceResponse> getOpenSourceApiCall() {
        return mApiHelper.getOpenSourceApiCall();
    }

    @Override
    public Observable<List<QuestionCardData>> getQuestionCardData() {

        return mDbHelper.getAllQuestions()
                .flatMap(new Function<List<Question>, ObservableSource<Question>>() {
                    @Override
                    public ObservableSource<Question> apply(List<Question> questions) throws Exception {
                        return Observable.fromIterable(questions);
                    }
                })
                .flatMap(new Function<Question, ObservableSource<QuestionCardData>>() {
                    @Override
                    public ObservableSource<QuestionCardData> apply(Question question) throws Exception {
                        return Observable.zip(mDbHelper.getOptionsForQuestionId(question.id), Observable.just(question),
                                new BiFunction<List<Option>, Question, QuestionCardData>() {
                                    @Override
                                    public QuestionCardData apply(List<Option> options, Question question) throws Exception {
                                        return new QuestionCardData(question, options);
                                    }
                                });
                    }
                })
                .toList()
                .toObservable();
    }
}
