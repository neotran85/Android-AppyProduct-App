package com.appyhome.appyproduct.mvvm.data;

import android.content.Context;

import com.appyhome.appyproduct.mvvm.AppConstants;
import com.appyhome.appyproduct.mvvm.data.local.db.DbHelper;
import com.appyhome.appyproduct.mvvm.data.local.prefs.PreferencesHelper;
import com.appyhome.appyproduct.mvvm.data.model.api.BlogResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.OpenSourceResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LoginRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LoginResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LogoutResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.account.SignUpRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.account.SignUpResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.service.AppointmentCreateRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.service.AppointmentCreateResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.service.AppointmentDeleteRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.service.AppointmentDeleteResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.service.AppointmentGetResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.service.OrderGetRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.service.OrderGetResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.service.ReceiptGetRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.service.ReceiptGetResponse;
import com.appyhome.appyproduct.mvvm.data.model.db.Option;
import com.appyhome.appyproduct.mvvm.data.model.db.Question;
import com.appyhome.appyproduct.mvvm.data.model.db.ServiceAddress;
import com.appyhome.appyproduct.mvvm.data.model.db.User;
import com.appyhome.appyproduct.mvvm.data.model.others.QuestionCardData;
import com.appyhome.appyproduct.mvvm.data.remote.ApiHeader;
import com.appyhome.appyproduct.mvvm.data.remote.ApiHelper;
import com.appyhome.appyproduct.mvvm.utils.helper.CommonUtils;
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
    public String getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(String userId) {
        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHelper.getApiHeader();
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
    public Single<SignUpResponse> doUserSignUp(SignUpRequest request) {
        return mApiHelper.doUserSignUp(request);
    }

    @Override
    public Single<LogoutResponse> doUserLogout() {
        return mApiHelper.doUserLogout();
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
    public void updateApiHeader(String token) {
        mApiHelper.getApiHeader().getProtectedApiHeader().setAuthorization(token);
    }

    @Override
    public void updateUserInfo(
            String userId,
            LoggedInMode loggedInMode,
            String username,
            String phoneNumber,
            String email,
            String profilePicPath, String token) {
        setCurrentUsername(username);
        setCurrentUserEmail(email);
        setCurrentUserProfilePicUrl(profilePicPath);
        setCurrentUserId(userId);
        updateApiHeader(token);
    }

    @Override
    public void setUserAsLoggedOut() {
        updateUserInfo(
                null,
                LoggedInMode.LOGGED_OUT,
                null,
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

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String token) {
        mPreferencesHelper.setAccessToken(token);
    }

    @Override
    public ServiceAddress getServiceAddress() {
        return mPreferencesHelper.getServiceAddress();
    }

    @Override
    public void setServiceAddress(ServiceAddress address) {
        mPreferencesHelper.setServiceAddress(address);
    }

    @Override
    public Single<AppointmentCreateResponse> createAppointment(AppointmentCreateRequest dataRequest) {
        return mApiHelper.createAppointment(dataRequest);
    }

    @Override
    public Single<AppointmentGetResponse> getAppointmentAll() {
        return mApiHelper.getAppointmentAll();
    }

    @Override
    public Single<AppointmentGetResponse> getAppointment(String idNumber) {
        return mApiHelper.getAppointment(idNumber);
    }

    @Override
    public Single<AppointmentDeleteResponse> deleteAppointment(AppointmentDeleteRequest request) {
        return mApiHelper.deleteAppointment(request);
    }

    @Override
    public Single<OrderGetResponse> getOrder(OrderGetRequest request) {
        return mApiHelper.getOrder(request);
    }
    @Override
    public Single<OrderGetResponse> getOrderAll() {
        return mApiHelper.getOrderAll();
    }
    @Override
    public Single<ReceiptGetResponse> getReceipt(ReceiptGetRequest request) {
        return mApiHelper.getReceipt(request);
    }
}